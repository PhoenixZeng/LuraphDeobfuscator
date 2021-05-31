package ASTNodes;

import java.util.ArrayList;
import java.util.List;

// Possibly includes a function call
public class Suffix extends Expression {
    public List<NameAndArgs> nameAndArgs = new ArrayList<>();
    public Expression expOrName;

    @Override
    public String line() {
        String s = "";

        for (NameAndArgs it : nameAndArgs) {
            s += it.line();
        }

        if (expOrName instanceof LuaString) {
            s += "." + ((LuaString) expOrName).value;
        }
        else {
            s += "[" + expOrName.line() + "]";
        }

        return s;
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        for (NameAndArgs it : nameAndArgs) {
            children.add(it);
        }

        children.add(expOrName);

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            Suffix s = (Suffix)obj;

            if (nameAndArgs.size() != s.nameAndArgs.size()) {
                return false;
            }

            for (int i = 0; i < nameAndArgs.size(); i++) {
                if (!nameAndArgs.get(i).matches(s.nameAndArgs.get(i))) {
                    return false;
                }
            }

            return expOrName.matches(s.expOrName);
        }

        return false;
    }

    @Override
    public Suffix clone() {
        Suffix s = new Suffix();

        for (NameAndArgs it : nameAndArgs) {
            s.nameAndArgs.add(it.clone());
        }

        s.expOrName = expOrName.clone();

        return s;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        for (int i = 0; i < nameAndArgs.size(); i++) {
            Node node = nameAndArgs.get(i).accept(visitor);

            if (node != nameAndArgs.get(i)) {
                nameAndArgs.set(i, (NameAndArgs)node);
            }
        }

        expOrName = (Expression)expOrName.accept(visitor);
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
