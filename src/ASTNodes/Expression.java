package ASTNodes;

public class Expression extends Statement {
    @Override
    public Expression clone() {
        return new Expression();
    }
}
