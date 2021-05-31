package ASTNodes;

public class Number extends Literal {
    public final double value;

    public Number(String literalValue) {
        value = Double.parseDouble(literalValue);
    }

    public Number(double literalValue) {
        value = literalValue;
    }

    @Override
    public String line() {
        return toString();
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            return value == ((Number)obj).value;
        }

        return false;
    }

    @Override
    public Number clone() {
        return new Number(value);
    }

    @Override
    public Node accept(IASTVisitor visitor) {
        return visitor.visit(this);
    }
}
