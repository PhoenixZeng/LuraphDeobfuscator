package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class Do extends Statement {
    public Block block;

    @Override
    public String line() {
        return "do";
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(block);

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            return block.matches(((Do)obj).block);
        }

        return false;
    }

    @Override
    public Do clone() {
        Do stmt = new Do();
        stmt.block = block.clone();
        return stmt;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        block = (Block)block.accept(visitor);
    }

    @Override
    public Node accept(IASTVisitor visitor) {
        Node replacement = visitor.visit(this);

        if (replacement != this) {
            return replacement;
        }

        acceptChildren(visitor);

        return this;
    }
}
