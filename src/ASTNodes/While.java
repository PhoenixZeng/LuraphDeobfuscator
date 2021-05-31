package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class While extends Statement {
    public Expression expr;
    public Block block;

    @Override
    public String line() {
        return "while " + expr.line() + " do";
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(expr);
        children.add(block);

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            While stmt = (While)obj;

            return expr.matches(stmt.expr) && block.matches(stmt.block);
        }

        return false;
    }

    @Override
    public While clone() {
        While stmt = new While();

        stmt.expr = expr.clone();
        stmt.block = block.clone();

        return stmt;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        expr = (Expression)expr.accept(visitor);
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
