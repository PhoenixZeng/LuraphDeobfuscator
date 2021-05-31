package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class NameList extends Expression {
    public List<LuaString> names = new ArrayList<>();

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        for (LuaString name : names) {
            children.add(name);
        }

        return children;
    }

    @Override
    public String line() {
        String s = "";

        for (int i = 0; i < names.size(); i++) {
            s += names.get(i).line();

            if (i < names.size() - 1) {
                s += ", ";
            }
        }

        return s;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            NameList list = (NameList)obj;

            if (names.size() != list.names.size()) {
                return false;
            }

            for (int i = 0; i < names.size(); i++) {
                if (!names.get(i).matches(list.names.get(i))) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public NameList clone() {
        NameList list = new NameList();

        for (LuaString name : names) {
            list.names.add(name.clone());
        }

        return list;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        for (int i = 0; i < names.size(); i++) {
            Node node = names.get(i).accept(visitor);

            if (node != names.get(i)) {
                names.set(i, (LuaString)node);
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
