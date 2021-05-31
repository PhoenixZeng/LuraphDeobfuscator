import LuaVM.*;

import java.util.*;

public class LuaChunkOptimizer {
    private int line;

    public LuaChunkOptimizer() {
        line = 0;
    }

    private static List<VMInstructionWrapper> generateFromCfgWithFallthroughJumps(List<VMControlFlowGraph> orderedCfg) {
        // Get instructions from CFG and append jumps to handle fallthrough (will be optimized out later if not necessary)
        List<VMInstructionWrapper> insns = new ArrayList<>();

        for (VMControlFlowGraph vertex : orderedCfg) {
            for (VMInstructionWrapper wrapper : vertex.block.insns) {
                insns.add(wrapper);
            }

            VMInstructionWrapper last = vertex.block.insns.get(vertex.block.insns.size() - 1);

            if (vertex.next != null && last.insn.opcode != VMOp.JUMP && last.insn.opcode != VMOp.FORPREP) {
                VMInstructionWrapper forcedJump = new VMInstructionWrapper();
                forcedJump.insn = new VMInstruction();
                forcedJump.insn.opcode = VMOp.JUMP;
                forcedJump.ref = vertex.next.block;
                insns.add(forcedJump);
            }
        }

        return insns;
    }

    private static List<VMInstructionRefWrapper> generateInstructionRefWrappers(List<VMInstructionWrapper> insns) {
        // Convert refs from basic blocks to refs to instructions
        List<VMInstructionRefWrapper> output = new ArrayList<>();

        for (VMInstructionWrapper wrapper : insns) {
            VMInstructionRefWrapper refWrapper = new VMInstructionRefWrapper();
            refWrapper.insn = wrapper.insn;
            if (wrapper.ref != null) {
                refWrapper.ref = wrapper.ref.insns.get(0).insn;
            }
            output.add(refWrapper);
        }

        return output;
    }

    private static HashMap<Integer, VMInstructionRefWrapper> getInstructionPCs(List<VMInstructionRefWrapper> insns) {
        int PC = 0;

        HashMap<Integer, VMInstructionRefWrapper> insnPCs = new HashMap<>();

        for (VMInstructionRefWrapper insn : insns) {
            insnPCs.put(PC, insn);
            ++PC;
        }

        return insnPCs;
    }

    private static HashMap<VMInstruction, Integer> getPCInstructions(List<VMInstructionRefWrapper> insns) {
        int PC = 0;

        HashMap<VMInstruction, Integer> pcInsns = new HashMap<>();

        for (VMInstructionRefWrapper insn : insns) {
            pcInsns.put(insn.insn, PC);
            ++PC;
        }

        return pcInsns;
    }

    private static void fixBranchTargets(List<VMInstructionRefWrapper> insns) {
        int PC = 0;

        HashMap<VMInstruction, Integer> pcInsns = getPCInstructions(insns);

        for (VMInstructionRefWrapper insn : insns) {
            ++PC;

            if (insn.insn.isVariableTargetBranch()) {
                final int targetPC = pcInsns.get(insn.ref) - PC;
                insn.insn.sBx = targetPC;
            }
        }
    }

    // fixed point algorithm
    private static int removeZeroBranches(List<VMInstructionRefWrapper> insns) {
        // remove JUMP 0.0's
        int numRemoved = 0;

        List fallthroughInsns = Arrays.asList(new VMOp[] { VMOp.EQ, VMOp.LT, VMOp.LTE, VMOp.TEST, VMOp.TESTSET });

        for (int i = 0; i < insns.size(); i++) {
            VMInstructionRefWrapper wrapper = insns.get(i);

            if (wrapper.insn.opcode == VMOp.JUMP && wrapper.insn.sBx == 0) {

                // Don't remove conditional fallthrough branches
                if (i > 0 && fallthroughInsns.contains(insns.get(i - 1).insn.opcode)) {
                    continue;
                }

                // set anything referencing this instruction to reference the instruction after it
                for (int j = 0; j < insns.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    VMInstructionRefWrapper w = insns.get(j);

                    if (w.ref == wrapper.insn) {
                        w.ref = insns.get(i + 1).insn;
                    }
                }

                insns.remove(i);
                ++numRemoved;
                --i;
            }
        }

        return numRemoved;
    }

    private static List<VMInstruction> generateInstructionsFromCfg(List<VMControlFlowGraph> orderedCfg) {
        List<VMInstructionWrapper> insnsWithFallthroughJumps = generateFromCfgWithFallthroughJumps(orderedCfg);
        List<VMInstructionRefWrapper> insnsWithRefs = generateInstructionRefWrappers(insnsWithFallthroughJumps);

        do {
            fixBranchTargets(insnsWithRefs);
        } while (removeZeroBranches(insnsWithRefs) > 0);

        List<VMInstruction> insns = new ArrayList<>();

        for (VMInstructionRefWrapper wrapper : insnsWithRefs) {
            insns.add(wrapper.insn);
        }

        return insns;
    }

    public static LuaChunk removeClosureAntiSymbExecTrick(LuaChunk chunk) {
        // this trick is used to make symbolic execution fail when Lua checks code before execution
        // remove it

        if (chunk.prototypes.size() != 1 || chunk.constants.size() != 1) {
            return chunk;
        }

        VMOp[] matchOps = { VMOp.CLOSURE, VMOp.SETGLOBAL, VMOp.GETGLOBAL, VMOp.CALL, VMOp.RETURN };

        if (chunk.insns.size() == matchOps.length) {
            for (int i = 0; i < matchOps.length; i++) {
                if (chunk.insns.get(i).opcode != matchOps[i]) {
                    return chunk;
                }
            }
        }

        // matched
        return chunk.prototypes.get(0);
    }

    private void setVarArgFlag(LuaChunk chunk) {
        // set to 3 if vararg found
        // otherwise set to 2

        boolean found = false;

        for (VMInstruction insn : chunk.insns) {
            if (insn.opcode == VMOp.VARARG) {
                found = true;
                break;
            }
        }

        if (found) {
            chunk.isVarArgFlag = 3;
        }
        else {
            chunk.isVarArgFlag = 2;
        }
    }

    private void renameGlobalIntegerRefs(LuaChunk chunk) {
        // SetGlobal and GetGlobal require referenced constant string
        // luraph replaces that string with a number to index into environment
        // Lua symbexec requires the reference be a string, so just find and
        // rename such tricks

        for (VMInstruction insn : chunk.insns) {
            if (insn.opcode == VMOp.GETGLOBAL || insn.opcode == VMOp.SETGLOBAL) {
                int constantIdx = (int)insn.Bx;

                LuaValue ref = chunk.constants.get(constantIdx);

                if (ref.type == LuaValue.Type.NUMBER) {
                    // change to string, as required by lua
                    String name = "global_" + Integer.toString((int)ref._nv);
                    ref.type = LuaValue.Type.STRING;
                    ref._sv = name;
                }
            }
        }
    }

    public void optimize(LuaChunk chunk) {
        // note: could propagate debug lines with instructions if we wanted
        // but no point as decompiler doesnt need them
        chunk.debugLines.clear();

        List<VMBasicBlock> blocks = VMBasicBlock.generateBasicBlocks(chunk.insns);
        List<VMControlFlowGraph> cfg = VMControlFlowGraph.generateControlFlowGraph(blocks);
        List<VMControlFlowGraph> orderedCfg = new CFGOrderer(cfg, cfg.get(0)).getOrder();

        List<VMInstruction> insns = generateInstructionsFromCfg(orderedCfg);

        chunk.insns = insns;
        chunk.lineDefined = line++;

        setVarArgFlag(chunk);

        renameGlobalIntegerRefs(chunk);

        for (LuaChunk child : chunk.prototypes) {
            optimize(child);
        }
    }
}
