package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class Return extends Node {
    // optional
    public ExprList exprs = new ExprList();

    @Override
    public String line() {
        return "return " + exprs.line();
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(exprs);

        return children.size() > 0 ? children : null;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            return exprs.matches(((Return)obj).exprs);
        }

        return false;
    }

    @Override
    public Return clone() {
        Return ret = new Return();

        ret.exprs = exprs.clone();

        return ret;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
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
