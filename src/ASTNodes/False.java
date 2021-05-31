package ASTNodes;

public class False extends Literal {
    @Override
    public Node accept(IASTVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String line() {
        return "false";
    }

    @Override
    public False clone() {
        return new False();
    }
}
