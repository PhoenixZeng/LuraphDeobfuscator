package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class ForIn extends Statement {
    public NameList names = new NameList();
    public ExprList exprs = new ExprList();
    public Block block;

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(names);
        children.add(exprs);
        children.add(block);

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            ForIn stmt = (ForIn)obj;
            return names.matches(stmt.names) && exprs.matches(stmt.exprs) && block.matches(stmt.block);
        }

        return false;
    }

    @Override
    public String line() {
        return "for " + names.line() + " in " + exprs.line() + " do";
    }

    @Override
    public ForIn clone() {
        ForIn stmt = new ForIn();

        stmt.names = names.clone();
        stmt.exprs = exprs.clone();
        stmt.block = block.clone();
        stmt.block.parent = stmt;

        return stmt;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        names = (NameList)names.accept(visitor);
        exprs = (ExprList)exprs.accept(visitor);
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
