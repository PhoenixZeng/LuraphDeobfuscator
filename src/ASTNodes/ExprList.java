package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class ExprList extends Expression {
    public List<Expression> exprs = new ArrayList<>();

    @Override
    public String line() {
        String s = "";

        for (int i = 0; i < exprs.size(); i++) {
            s += exprs.get(i).line();

            if (i < exprs.size() - 1) {
                s += ", ";
            }
        }

        return s;
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        for (Expression expr : exprs) {
            children.add(expr);
        }

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            ExprList list = (ExprList)obj;

            if (exprs.size() != list.exprs.size()) {
                return false;
            }

            for (int i = 0; i < exprs.size(); i++) {
                if (!exprs.get(i).matches(list.exprs.get(i))) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public ExprList clone() {
        ExprList list = new ExprList();

        for (Expression expr : exprs) {
            list.exprs.add(expr.clone());
        }

        return list;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        for (int i = 0; i < exprs.size(); i++) {
            Node node = exprs.get(i).accept(visitor);

            if (node != exprs.get(i)) {
                exprs.set(i, (Expression)node);
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
