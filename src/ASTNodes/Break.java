package ASTNodes;

public class Break extends Statement {
    @Override
    public String line() {
        return "break";
    }

    @Override
    public Node accept(IASTVisitor visitor) {
        visitor.visit(this);

        return this;
    }

    @Override
    public Break clone() {
        return new Break();
    }
}
