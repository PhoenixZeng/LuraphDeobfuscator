import org.antlr.v4.runtime.tree.Tree;
import ASTNodes.*;

import java.util.ArrayList;
import java.util.List;

public class ASTTree implements Tree {
    public Tree parent;
    public List<Tree> children = new ArrayList<>();
    public Node node;

    @Override
    public Tree getChild(int i) {
        return children.get(i);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public Tree getParent() {
        return parent;
    }

    @Override
    public Node getPayload() {
        return node;
    }

    @Override
    public String toStringTree() {
        return "";
    }
}
