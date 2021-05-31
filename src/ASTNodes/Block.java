package ASTNodes;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.List;

public class Block extends Node {
    public List<Statement> stmts = new ArrayList<>();
    public Return ret;

    // used for optimizing, code gen, etc
    public Statement parent; // used for function, for in, for step because they can declare new variables

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        for (Statement stmt : stmts) {
            children.add(stmt);
        }

        if (ret != null) {
            children.add(ret);
        }

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            Block block = (Block)obj;

            if (stmts.size() != block.stmts.size()) {
                return false;
            }

            for (int i = 0; i < stmts.size(); i++) {
                if (!stmts.get(i).matches(block.stmts.get(i))) {
                    return false;
                }
            }

            return ret.matches(block.ret);
        }

        return false;
    }

    @Override
    public Block clone() {
        Block block = new Block();

        for (Statement stmt : stmts) {
            block.stmts.add((Statement)stmt.clone());
        }

        if (ret != null) {
            block.ret = (Return) ret.clone();
        }

        return block;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        for (int i = 0; i < stmts.size(); i++) {
            Node node = stmts.get(i).accept(visitor);

            if (node != stmts.get(i)) {
                stmts.set(i, (Statement)node);
            }
        }

        if (ret != null) {
            ret = (Return) ret.accept(visitor);
        }
    }

    @Override
    public Node accept(IASTVisitor visitor) {
        visitor.enterBlock(this);

        Node replacement = visitor.visit(this);

        if (replacement == this) {
            acceptChildren(visitor);
        }

        visitor.exitBlock(this);

        return replacement;
    }
}
