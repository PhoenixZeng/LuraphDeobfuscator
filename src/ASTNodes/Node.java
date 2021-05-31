package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String line() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    public List<Node> getChildren() {
        return null;
    }

    public boolean matches(Node node) {
        return node.getClass().equals(this.getClass());
    }

    @Override
    public Node clone() {
        return new Node();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName().toLowerCase();
    }

    public String symbol() {
        return line();
    }

    public void acceptChildren(IASTVisitor visitor) {
    }

    // caller responsible for doing replacement on top level node
    public Node accept(IASTVisitor visitor) {
        Node replacement = visitor.visit(this);

        if (replacement != this) {
            return replacement;
        }

        acceptChildren(visitor);

        return this;
    }

    public int count(Class type) {
        int count = 0;

        List<Node> children = getChildren();

        if (children == null) {
            return 0;
        }

        for (Node node : children) {
            if (type.isInstance(node)) {
                ++count;
            }

            count += node.count(type);
        }

        return count;
    }

    public List<Node> getChildren(Class type) {
        List<Node> res = new ArrayList<>();

        List<Node> children = getChildren();

        if (children != null) {
            for (Node child : children) {
                if (type.isInstance(child)) {
                    res.add(child);
                }
            }
        }

        return res;
    }

    // only check children, no recursion
    public Node child(Class type) {
        List<Node> children = getChildren();

        if (children != null) {
            for (Node node : children) {
                if (type.isInstance(node)) {
                    return node;
                }
            }
        }

        return null;
    }

    // does DFS
    public Node first(Class type) {
        List<Node> children = getChildren();

        if (children != null) {
            for (Node node : children) {
                if (type.isInstance(node)) {
                    return node;
                }
                else {
                    Node ret = node.first(type);

                    if (ret != null) {
                        return ret;
                    }
                }
            }
        }

        return null;
    }
}
