package ASTNodes;

public class True extends Literal {
    @Override
    public String line() {
        return "true";
    }

    @Override
    public True clone() {
        return new True();
    }

    @Override
    public Node accept(IASTVisitor visitor) {
        return visitor.visit(this);
    }
}
