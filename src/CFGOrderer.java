import LuaVM.VMControlFlowGraph;
import LuaVM.VMOp;

import java.util.*;

/*
    Using Tarjan's algorithm + DFS + Heuristic to rearrange basic blocks correctly
 */

public class CFGOrderer {
    private List<VMControlFlowGraph> cfg;
    private VMControlFlowGraph root;

    private Stack<VMControlFlowGraph> stack;
    private List<VMControlFlowGraph> order;
    private HashMap<VMControlFlowGraph, IndexState> index;
    private HashMap<VMControlFlowGraph, Integer> low;
    private int dfsNum;

    private enum State {
        TO_BE_DONE,
        DONE,
        NUMBER,
    }

    private class IndexState {
        public State state;
        public int val;

        public IndexState(State state, int val) {
            this.state = state;
            this.val = val;
        }
    }

    public CFGOrderer(List<VMControlFlowGraph> cfg, VMControlFlowGraph root) {
        this.cfg = cfg;
        this.root = root;
        reset();
    }

    private void reset() {
        this.stack = new Stack<>();
        this.order = new ArrayList<>();
        this.index = new HashMap<>();
        this.low = new HashMap<>();
        this.dfsNum = 0;
    }

    // heuristic to order loop constructs correctly
    private VMControlFlowGraph selectFromSCC(List<VMControlFlowGraph> scc) {
        if (scc.get(0).getLastInstruction().insn.opcode == VMOp.TFORLOOP) {
            if (scc.contains(scc.get(0).next)) {
                return scc.get(0).next;
            }
        }
        else if (scc.get(0).getLastInstruction().insn.opcode == VMOp.FORLOOP) {
            if (scc.contains(scc.get(0).target)) {
                return scc.get(0).target;
            }
        }

        return scc.get(0);
    }

    public void visit(VMControlFlowGraph v) {
        index.put(v, new IndexState(State.NUMBER, dfsNum));
        low.put(v, dfsNum);
        ++dfsNum;
        stack.push(v);

        for (VMControlFlowGraph w : v.getChildren()) {
            if (!cfg.contains(w)) {
                continue;
            }

            if (index.get(w).state == State.TO_BE_DONE) {
                visit(w);
                final int a = low.get(v);
                final int b = low.get(w);
                low.put(v, Math.min(a, b));
            }
            else if (index.get(w).state == State.DONE) {
                // Do nothing
            }
            else {
                final int a = low.get(v);
                final int b = index.get(w).val;
                low.put(v, Math.min(a, b));
            }
        }

        final int a = low.get(v);
        final int b = index.get(v).val;

        if (a == b) {
            VMControlFlowGraph w;
            List<VMControlFlowGraph> scc = new ArrayList<>();

            do {
                w = stack.pop();
                scc.add(w);
                index.get(w).state = State.DONE;
            } while(w != v);

            Collections.reverse(scc);

            if (scc.size() == 1) {
                order.add(0, v);
            }
            else {
                CFGOrderer orderer = new CFGOrderer(scc, selectFromSCC(scc));
                List<VMControlFlowGraph> suborder = orderer.getOrder();
                suborder.addAll(order);

                order = suborder;
            }
        }

    }

    public List<VMControlFlowGraph> getOrder() {
        reset();

        for (VMControlFlowGraph v : cfg) {
            index.put(v, new IndexState(State.TO_BE_DONE, -1));
        }

        index.put(root, new IndexState(State.DONE, -1));

        for (VMControlFlowGraph v : root.getChildren()) {
            if (cfg.contains(v) && index.get(v).state == State.TO_BE_DONE) {
                visit(v);
            }
        }

        order.add(0, root);

        return order;
    }
}
