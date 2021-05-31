import ASTNodes.*;

import java.util.List;

public class ASTTreeBuilder {

    private static ASTTree createASTTree(Node node, ASTTree parent) {
        ASTTree tree = new ASTTree();

        tree.node = node;
        tree.parent = parent;

        List<Node> children = node.getChildren();

        if (children != null) {
            for (Node childNode : children) {
                if (childNode == null) {
                    System.out.println("WARNING: Encountered null node in AST");
                    continue;
                }
                ASTTree childTree = createASTTree(childNode, tree);

                tree.children.add(childTree);
            }
        }

        return tree;
    }

    public static ASTTree create(Node node) {
        return createASTTree(node, null);
    }
}
