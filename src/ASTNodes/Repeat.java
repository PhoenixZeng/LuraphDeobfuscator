package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class Repeat extends Statement {
    public Block block;
    public Expression expr;

    @Override
    public String line() {
        return "repeat";
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(block);
        children.add(expr);

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            Repeat stmt = (Repeat)obj;

            return block.matches(stmt.block) && expr.matches(stmt.expr);
        }

        return false;
    }

    @Override
    public Repeat clone() {
        Repeat stmt = new Repeat();

        stmt.block = block.clone();
        stmt.expr = expr.clone();

        return stmt;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        block = (Block)block.accept(visitor);
        expr = (Expression)expr.accept(visitor);
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
