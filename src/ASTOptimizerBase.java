import ASTNodes.ASTVisitor;

public class ASTOptimizerBase extends ASTVisitor {
    protected ASTOptimizerMgr mgr;

    public void setMgr(ASTOptimizerMgr mgr) {
        this.mgr = mgr;
    }
}
