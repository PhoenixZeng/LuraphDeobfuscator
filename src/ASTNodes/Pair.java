package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class Pair<K extends Node, V extends Node> extends Node {
    public K first;
    public V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            Pair<K, V> p = (Pair<K, V>)obj;

            return first.matches(p.first) && second.matches(p.second);
        }

        return false;
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        if (first != null) {
            children.add(first);
        }

        if (second != null) {
            children.add(second);
        }

        return children;
    }

    @Override
    public Pair<K, V> clone() {
        K f = first == null ? null : (K)first.clone();
        V s = second == null ? null : (V)second.clone();
        return new Pair(f, s);
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        if (first != null) {
            first = (K) first.accept(visitor);
        }

        if (second != null) {
            second = (V) second.accept(visitor);
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
