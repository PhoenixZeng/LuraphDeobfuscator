package ASTNodes;

public class Nil extends Literal {
    @Override
    public Node accept(IASTVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String line() {
        return "nil";
    }

    @Override
    public Nil clone() {
        return new Nil();
    }
}
