package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class TableConstructor extends Expression {
    public List<Pair<Expression, Expression>> entries = new ArrayList<>();

    @Override
    public String line() {
        String s = "{ ";

        for (int i = 0; i < entries.size(); i++) {
            Pair<Expression, Expression> entry = entries.get(i);

            if (entry.first != null) {
                s += "[" + entry.first.line() + "] = ";
            }
            if (entry.second != null) {
                s += entry.second.line();
            }

            if (i < entries.size() - 1) {
                s += ", ";
            }
        }

        return s + " }";
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        for (Pair<Expression, Expression> p : entries) {
            children.add(p);
        }

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            TableConstructor ctor = (TableConstructor)obj;

            if (entries.size() != ctor.entries.size()) {
                return false;
            }

            for (int i = 0; i < entries.size(); i++) {
                if (!entries.get(i).matches(ctor.entries.get(i))) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public TableConstructor clone() {
        TableConstructor ctor = new TableConstructor();

        for (Pair<Expression, Expression> p : entries) {
            ctor.entries.add(p.clone());
        }

        return ctor;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        for (int i = 0; i < entries.size(); i++) {
            Node node = entries.get(i).accept(visitor);

            if (node != entries.get(i)) {
                entries.set(i, (Pair<Expression, Expression>)node);
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
