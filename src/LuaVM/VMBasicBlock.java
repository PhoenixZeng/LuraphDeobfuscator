package LuaVM;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class VMBasicBlock {
    public List<VMInstructionWrapper> insns = new ArrayList<>();
    public VMControlFlowGraph cfg;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (VMInstructionWrapper wrapper : insns) {
            sb.append(wrapper.insn.toString());

            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    public static void print(List<VMBasicBlock> blocks) {
        for (int i = 0; i < blocks.size(); i++) {

            String s = "[BLOCK #" + i + "]";

            System.out.println(s);
            System.out.println(blocks.get(i).toString());
            System.out.println();
        }
    }

    private static VMBasicBlock findBlock(VMInstruction insn, List<VMBasicBlock> blocks) {
        for (VMBasicBlock block : blocks) {
            if (block.insns.get(0).insn == insn) {
                return block;
            }
        }

        throw new RuntimeException("Reference not in basic block");
    }

    public static HashSet<VMInstruction> computeLeaders(List<VMInstruction> insns) {
        HashSet<VMInstruction> leaders = new HashSet<>();

        int PC = 0;

        leaders.add(insns.get(0));

        for (int i = 0; i < insns.size(); i++) {
            ++PC;

            VMInstruction insn = insns.get(i);

            if (insn.isBranch()) {
                int target = insn.getExitTargetPC(PC);

                // Junk code sometimes has invalid targets, so we ignore them
                if (target >= 0 && target < insns.size()) {
                    leaders.add(insns.get(target));
                }

                if (PC < insns.size()) {
                    leaders.add(insns.get(PC));
                }
            }
            else if (insn.opcode == VMOp.RETURN && PC < insns.size()) {
                // Last instruction might be a return, hence PC == insns.size() as we increment
                leaders.add(insns.get(PC));
            }
        }

        return leaders;
    }

    private static void setInstructionReferences(List<VMBasicBlock> blocks, List<VMInstruction> insns) {
        int PC = 0;
        for (VMBasicBlock bb : blocks) {
            for (VMInstructionWrapper w : bb.insns) {
                ++PC;
                if (w.insn.isBranch()) {
                    int target = w.insn.getExitTargetPC(PC);
                    if (target >= 0 && target < insns.size()) {
                        w.ref = findBlock(insns.get(target), blocks);
                    }
                }
            }
        }
    }

    public static List<VMBasicBlock> generateBasicBlocks(List<VMInstruction> insns) {
        List<VMBasicBlock> blocks = new ArrayList<>();
        HashSet<VMInstruction> leaders = computeLeaders(insns);

        VMBasicBlock block = new VMBasicBlock();

        int PC = 0;

        for (int i = 0; i < insns.size(); i++) {
            ++PC;

            VMInstruction insn = insns.get(i);

            if (i > 0 && leaders.contains(insn)) {
                blocks.add(block);
                block = new VMBasicBlock();
            }

            VMInstructionWrapper wrapper = new VMInstructionWrapper();
            wrapper.insn = insn;

            block.insns.add(wrapper);
        }

        blocks.add(block);

        setInstructionReferences(blocks, insns);

        return blocks;
    }

    public static VMBasicBlock getNextBlock(List<VMBasicBlock> blocks, VMBasicBlock block) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) == block) {
                return getNextBlock(blocks, i);
            }
        }

        throw new RuntimeException("Block argument must be inside blocks list");
    }

    public static VMBasicBlock getNextBlock(List<VMBasicBlock> blocks, int idx) {
        VMBasicBlock block = blocks.get(idx);

        if (block.insns.size() == 0) {
            throw new RuntimeException("Basic block does not contain any instructions");
        }
        else {
            VMInstructionWrapper insn = block.insns.get(block.insns.size() - 1);

            if (insn.insn.opcode == VMOp.JUMP || insn.insn.opcode == VMOp.FORPREP) {
                return insn.ref;
            }

            if (idx + 1 < blocks.size()) {
                VMBasicBlock next = blocks.get(idx + 1);

                // Two returns aren't necessary (even though lua compiler will generate them)
                // and luraph will sometimes insert bogus unreachable returns that break decompilers
                // so no point to do this, which is why I commented out the second expression
                if (insn.insn.opcode == VMOp.RETURN /*&& next.insns.get(0).insn.opcode != VMOp.RETURN*/) {
                    return null;
                }

                // Handle LOADBOOL functionality
                // When C is 1 we skip the next instruction
                if (insn.insn.opcode == VMOp.LOADBOOL && insn.insn.C == 1.0) {
                    if (idx + 2 < blocks.size()) {
                        return blocks.get(idx + 2);
                    }
                    return null;
                }

                return next;
            }

            return null;
        }
    }

    public static VMBasicBlock getTargetBlock(VMBasicBlock block) {
        if (block.insns.size() == 0) {
            throw new RuntimeException("Basic block does not contain any instructions");
        }
        else {
            VMInstructionWrapper insn = block.insns.get(block.insns.size() - 1);

            // Handle LOADBOOL functionality
            // When C is 0 we fallthrough to the next instruction
            if (insn.insn.opcode == VMOp.LOADBOOL && insn.insn.C == 0.0) {
                return null;
            }

            if (insn.insn.opcode != VMOp.JUMP && insn.insn.opcode != VMOp.FORPREP && insn.insn.isBranch()) {
                return insn.ref;
            }

            return null;
        }
    }
}
