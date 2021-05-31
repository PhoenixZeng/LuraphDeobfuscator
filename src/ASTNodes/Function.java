package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class Function extends Expression {
    public LuaString name; // function names are optional
    public NameList params = new NameList();
    public Block block;

    public enum Type {
        LOCAL,
        GLOBAL,
        EXPR,
    }

    public final Type type;

    public Function(Type type) {
        this.type = type;
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        if (name != null) {
            children.add(name);
        }

        children.add(params);
        children.add(block);

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            Function fn = (Function)obj;

            if (type != fn.type) {
                return false;
            }

            if (name == null && fn.name != null || name != null && fn.name == null) {
                return false;
            }

            if (name != null && !name.matches(fn.name)) {
                return false;
            }

            return params.matches(fn.params) && block.matches(fn.block);
        }

        return false;
    }

    @Override
    public String line() {
        String s = "";

        if (type == Type.LOCAL) {
            s += "local ";
        }

        s += "function ";

        if (name != null) {
            s += name.line();
        }

        s += "(" + params.line() + ")";

        return s;
    }

    @Override
    public Function clone() {
        Function func = new Function(type);

        if (name != null) {
            func.name = name.clone();
        }

        func.params = params.clone();
        func.block = block.clone();

        func.block.parent = func;

        return func;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        Node node;

        if (name != null) {
            name = (LuaString) name.accept(visitor);
        }

        params = (NameList)params.accept(visitor);

        block = (Block)block.accept(visitor);
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
