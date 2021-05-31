package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class VarList extends Expression {
    public List<Expression> vars = new ArrayList<>();

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        for (Expression expr : vars) {
            children.add(expr);
        }

        return children;
    }

    @Override
    public String line() {
        String s = "";

        for (int i = 0; i < vars.size(); i++) {
            s += vars.get(i).line();

            if (i < vars.size() - 1) {
                s += ", ";
            }
        }

        return s;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            VarList list = (VarList)obj;

            if (vars.size() != list.vars.size()) {
                return false;
            }

            for (int i = 0; i < vars.size(); i++) {
                if (!vars.get(i).matches(list.vars.get(i))) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public VarList clone() {
        VarList list = new VarList();

        for (Expression expr : vars) {
            list.vars.add(expr.clone());
        }

        return list;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        for (int i = 0; i < vars.size(); i++) {
            Node node = vars.get(i).accept(visitor);

            if (node != vars.get(i)) {
                vars.set(i, (Expression)node);
            }
        }
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
