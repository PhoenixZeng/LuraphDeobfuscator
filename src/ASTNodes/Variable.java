package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class Variable extends Expression {
    public Expression name;
    public List<Suffix> suffixes = new ArrayList<>();

    @Override
    public String symbol() {
        if (name instanceof LuaString) {
            return name.line();
        }

        return line();
    }

    @Override
    public String line() {
        String s = "";

        if (name instanceof LuaString) {
            s += name.line();
        }
        else {
            s += "(" + name.line() + ")";
        }

        for (int i = 0; i < suffixes.size(); i++) {
            s += suffixes.get(i).line();
        }

        return s;
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(name);

        for (Suffix suffix : suffixes) {
            children.add(suffix);
        }

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            Variable var = (Variable)obj;

            if (suffixes.size() != var.suffixes.size()) {
                return false;
            }

            for (int i = 0; i < suffixes.size(); i++) {
                if (!suffixes.get(i).matches(var.suffixes.get(i))) {
                    return false;
                }
            }

            return name.matches(var.name);
        }

        return false;
    }

    @Override
    public Variable clone() {
        Variable var = new Variable();

        var.name = name.clone();

        for (Suffix suffix : suffixes) {
            var.suffixes.add(suffix.clone());
        }

        return var;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        name = (Expression)name.accept(visitor);

        for (int i = 0; i < suffixes.size(); i++) {
            Node node = suffixes.get(i).accept(visitor);

            if (node != suffixes.get(i)) {
                suffixes.set(i, (Suffix)node);
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
