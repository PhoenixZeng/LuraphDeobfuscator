package LuaVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VMControlFlowGraph {
    public VMBasicBlock block;
    public VMControlFlowGraph target;
    public VMControlFlowGraph next;

    public List<VMControlFlowGraph> getChildren() {
        List<VMControlFlowGraph> children = new ArrayList<>();

        // order matters and affects topological ordering

        if (next != null) {
            children.add(next);
        }

        if (target != null) {
            children.add(target);
        }

        return children;
    }

    public VMInstructionWrapper getLastInstruction() {
        return block.insns.get(block.insns.size() - 1);
    }

    private static VMControlFlowGraph generateControlFlowGraphHelper(List<VMBasicBlock> blocks, VMBasicBlock block, List<VMControlFlowGraph> cfg, HashMap<VMBasicBlock, VMControlFlowGraph> created) {
        if (block == null) {
            return null;
        }

        if (created.containsKey(block)) {
            return created.get(block);
        }

        VMControlFlowGraph root = new VMControlFlowGraph();
        cfg.add(root);
        created.put(block, root);

        root.block = block;
        root.block.cfg = root;
        root.next = generateControlFlowGraphHelper(blocks, VMBasicBlock.getNextBlock(blocks, block), cfg, created);
        root.target = generateControlFlowGraphHelper(blocks, VMBasicBlock.getTargetBlock(block), cfg, created);

        return root;
    }

    // root is always first, nothing else guaranteed
    public static List<VMControlFlowGraph> generateControlFlowGraph(List<VMBasicBlock> blocks) {
        if (blocks.size() == 0) {
            throw new RuntimeException("No basic blocks");
        }

        HashMap<VMBasicBlock, VMControlFlowGraph> created = new HashMap<>();

        VMBasicBlock rootBlock = blocks.get(0);
        List<VMControlFlowGraph> cfg = new ArrayList<>();

        generateControlFlowGraphHelper(blocks, rootBlock, cfg, created);

        return cfg;
    }
}
