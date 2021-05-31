package ASTNodes;

public class LuaString extends Literal {
    public String value;

    public LuaString(java.lang.String literalValue) {
        value = literalValue;
    }

    public String toString() {
        if (value.length() > 128) {
            return "<<Removed String>>";
        }
        return value;
    }

    @Override
    public String line() {
        return value;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            return value.equals(((LuaString)obj).value);
        }

        return false;
    }

    @Override
    public LuaString clone() {
        return new LuaString(value);
    }

    @Override
    public Node accept(IASTVisitor visitor) {
        return visitor.visit(this);
    }
}
