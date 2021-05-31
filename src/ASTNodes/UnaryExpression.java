package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class UnaryExpression extends Expression {
    public enum Operator {
        INVALID,

        NOT,
        HASHTAG,
        MINUS,
        TILDE,
    }

    public Operator op;
    public Expression expr;

    public UnaryExpression(Operator op, Expression expr) {
        this.op = op;
        this.expr = expr;
    }

    @Override
    public String line()  {
       return toString() + expr.line();
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(expr);

        return children;
    }

    public String toString() {
        switch(op) {
            default:
            case INVALID:
                return "INVALID";
            case NOT:
                return "not ";
            case HASHTAG:
                return "#";
            case MINUS:
                return "-";
            case TILDE:
                return "~";
        }
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            UnaryExpression exp = (UnaryExpression)obj;

            return op == exp.op && expr.matches(exp.expr);
        }

        return false;
    }

    @Override
    public UnaryExpression clone() {
        return new UnaryExpression(op, expr.clone());
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
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
