package LuaVM;

public class LuaValue {
    public enum Type {
        INVALID,
        TABLE, // ignored

        NUMBER,
        BOOLEAN,
        STRING,
        NIL,
    }

    @Override
    public String toString() {
        switch (type) {
            default:
            case INVALID:
            case TABLE:
                return "<INVALID>";
            case NUMBER:
                return Double.toString(_nv);
            case BOOLEAN:
                return Boolean.toString(_bv);
            case STRING:
                return _sv;
        }
    }

    public Type type = Type.INVALID;

    public double _nv;
    public boolean _bv;
    public String _sv;
}
