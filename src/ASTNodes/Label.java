package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class Label extends Statement {
    public LuaString name;

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(name);

        return children;
    }

    @Override
    public String line() {
        return "::" + name.line() + "::";
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            return name.matches(((Label)obj).name);
        }

        return false;
    }

    @Override
    public Label clone() {
        Label label = new Label();
        label.name = name.clone();

        return label;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        name = (LuaString)name.accept(visitor);
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
