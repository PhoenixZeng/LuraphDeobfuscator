package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class BinaryExpression extends Expression {
    public enum Operator {
        INVALID,

        POW,
        MUL,
        REAL_DIV,
        INTEGER_DIV,
        MOD,
        ADD,
        SUB,
        STRCAT,
        LT,
        GT,
        LTE,
        GTE,
        NEQ,
        EQ,
        LOGICAL_AND,
        LOGICAL_OR,
        BITWISE_AND,
        BITWISE_OR,
        BITWISE_NOT,
        BITWISE_SHL,
        BITWISE_SHR,
    }

    public Operator operator;
    public Expression left;
    public Expression right;

    public BinaryExpression(Operator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public String line() {
        return "(" + left.line() + " " + this.toString() + " " + right.line() + ")";
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(left);
        children.add(right);

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            BinaryExpression exp = (BinaryExpression)obj;
            return exp.operator == operator && exp.right.matches(right) && exp.left.matches(left);
        }

        return false;
    }

    @Override
    public BinaryExpression clone() {
        return new BinaryExpression(operator, left.clone(), right.clone());
    }

    // not complete
    public boolean isCommutative() {
        return operator == Operator.ADD || operator == Operator.MUL;
    }

    // not complete
    public boolean isAssociative() {
        return operator == Operator.ADD || operator == Operator.MUL;
    }

    @Override
    public String toString() {
        switch(operator) {
            default:
            case INVALID:
                return "INVALID";
            case POW:
                return "^";
            case MUL:
                return "*";
            case REAL_DIV:
                return "/";
            case INTEGER_DIV:
                return "//";
            case MOD:
                return "%";
            case ADD:
                return "+";
            case SUB:
                return "-";
            case STRCAT:
                return "..";
            case LT:
                return "<";
            case GT:
                return ">";
            case LTE:
                return "<=";
            case GTE:
                return ">=";
            case NEQ:
                return "~=";
            case EQ:
                return "==";
            case LOGICAL_AND:
                return "and";
            case LOGICAL_OR:
                return "or";
            case BITWISE_AND:
                return "&";
            case BITWISE_OR:
                return "|";
            case BITWISE_NOT:
                return "~";
            case BITWISE_SHL:
                return "<<";
            case BITWISE_SHR:
                return ">>";
        }
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        left = (Expression)left.accept(visitor);
        right = (Expression)right.accept(visitor);
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
