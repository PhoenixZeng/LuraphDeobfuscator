package ASTNodes;

public class ASTVisitor implements IASTVisitor {
    @Override
    public Node visit(Assign node) {
        return node;
    }

    @Override
    public Node visit(BinaryExpression node) {
        return node;
    }

    @Override
    public Node visit(Block node) {
        return node;
    }

    @Override
    public Node visit(Break node) {
        return node;
    }

    @Override
    public Node visit(Do node) {
        return node;
    }

    @Override
    public Node visit(Expression node) {
        return node;
    }

    @Override
    public Node visit(ExprList node) {
        return node;
    }

    @Override
    public Node visit(False node) {
        return node;
    }

    @Override
    public Node visit(ForIn node) {
        return node;
    }

    @Override
    public Node visit(ForStep node) {
        return node;
    }

    @Override
    public Node visit(Function node) {
        return node;
    }

    @Override
    public Node visit(FunctionCall node) {
        return node;
    }

    @Override
    public Node visit(GoTo node) {
        return node;
    }

    @Override
    public Node visit(If node) {
        return node;
    }

    @Override
    public Node visit(Label node) {
        return node;
    }

    @Override
    public Node visit(Literal node) {
        return node;
    }

    @Override
    public Node visit(LocalDeclare node) {
        return node;
    }

    @Override
    public Node visit(LuaString node) {
        return node;
    }

    @Override
    public Node visit(NameAndArgs node) {
        return node;
    }

    @Override
    public Node visit(NameList node) {
        return node;
    }

    @Override
    public Node visit(Nil node) {
        return node;
    }

    @Override
    public Node visit(Node node) {
        return node;
    }

    @Override
    public Node visit(Number node) {
        return node;
    }

    @Override
    public Node visit(Pair node) {
        return node;
    }

    @Override
    public Node visit(Repeat node) {
        return node;
    }

    @Override
    public Node visit(Return node) {
        return node;
    }

    @Override
    public Node visit(Suffix node) {
        return node;
    }

    @Override
    public Node visit(TableConstructor node) {
        return node;
    }

    @Override
    public Node visit(True node) {
        return node;
    }

    @Override
    public Node visit(UnaryExpression node) {
        return node;
    }

    @Override
    public Node visit(Variable node) {
        return node;
    }

    @Override
    public Node visit(VarList node) {
        return node;
    }

    @Override
    public Node visit(While node) {
        return node;
    }

    @Override
    public void enterBlock(Block block) {

    }

    @Override
    public void exitBlock(Block block) {

    }
}
