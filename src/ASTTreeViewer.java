import org.antlr.v4.gui.TreeTextProvider;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.tree.Tree;

import javax.swing.*;

public class ASTTreeViewer {
    private class ASTTreeTextProvider implements TreeTextProvider {
        private final ASTTree tree;

        public ASTTreeTextProvider(ASTTree tree) {
            this.tree = tree;
        }

        @Override
        public String getText(Tree tree) {
            return ((ASTTree)tree).node.toString();
        }
    }

    private final TreeViewer treeViewer;

    public ASTTreeViewer(ASTTree tree) {
        ASTTreeTextProvider provider = new ASTTreeTextProvider(tree);
        treeViewer = new TreeViewer(null, tree);
        treeViewer.setTreeTextProvider(provider);
        treeViewer.setScale(1.5);
    }

    public void open() {
        treeViewer.open();
    }
}
