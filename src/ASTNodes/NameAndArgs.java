package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class NameAndArgs extends Node {
    // if name and args are both null then that corresponds to ()
    public String name;
    public Expression args;

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        if (args != null) {
            children.add(args);
        }

        return children.size() > 0 ? children : null;
    }

    @Override
    public String line() {
        String s = "";

        if (name != null) {
            s += ":" + name;
        }

        if (args != null) {
            s += args.line();
        }

        return s;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            NameAndArgs node = (NameAndArgs)obj;

            return name.equals(node.name) && args.equals(node.args);
        }

        return false;
    }

    @Override
    public NameAndArgs clone() {
        NameAndArgs obj = new NameAndArgs();

        obj.name = name;

        if (args != null) {
            obj.args = args.clone();
        }

        return obj;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        if (args != null) {
            args = (Expression) args.accept(visitor);
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
