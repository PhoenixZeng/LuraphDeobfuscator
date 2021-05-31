package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class LocalDeclare extends Statement {
    public NameList names = new NameList();
    public ExprList exprs = new ExprList();

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(names);
        children.add(exprs);

        return children;
    }

    public String toString() {
        return "local";
    }

    @Override
    public String line() { // doesnt work well with Function blocks
        String s = "local ";

        s += names.line();

        if (exprs.exprs.size() > 0) {
            s += " = " + exprs.line();
        }

        return s;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            LocalDeclare stmt = (LocalDeclare)obj;

            return names.matches(stmt.names) && exprs.matches(stmt.exprs);
        }

        return false;
    }

    @Override
    public LocalDeclare clone() {
        LocalDeclare stmt = new LocalDeclare();

        stmt.names = names.clone();
        stmt.exprs = exprs.clone();

        return stmt;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        names = (NameList)names.accept(visitor);
        exprs = (ExprList)exprs.accept(visitor);
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
