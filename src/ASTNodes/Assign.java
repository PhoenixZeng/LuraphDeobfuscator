package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class Assign extends Statement {
    public VarList vars = new VarList();
    public ExprList exprs = new ExprList();

    @Override
    public String line() { // doesnt work well with Function blocks
        return vars.line() + " = " + exprs.line();
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(vars);
        children.add(exprs);

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            Assign assign = (Assign)obj;
            return vars.matches(assign.vars) && exprs.matches(assign.exprs);
        }

        return false;
    }

    @Override
    public Assign clone() {
        Assign assign = new Assign();

        assign.vars = vars.clone();
        assign.exprs = exprs.clone();

        return assign;
    }

    @Override
    public String toString() {
        return "=";
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        vars = (VarList)vars.accept(visitor);
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
