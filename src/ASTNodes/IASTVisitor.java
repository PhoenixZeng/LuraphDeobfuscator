package ASTNodes;

public interface IASTVisitor {
    public Node visit(Assign node);

    public Node visit(BinaryExpression node);

    public Node visit(Block node);

    public Node visit(Break node);

    public Node visit(Do node);

    public Node visit(Expression node);

    public Node visit(ExprList node);

    public Node visit(False node);

    public Node visit(ForIn node);

    public Node visit(ForStep node);

    public Node visit(Function node);

    public Node visit(FunctionCall node);

    public Node visit(GoTo node);

    public Node visit(If node);

    public Node visit(Label node);

    public Node visit(Literal node);

    public Node visit(LocalDeclare node);

    public Node visit(LuaString node);

    public Node visit(NameAndArgs node);

    public Node visit(NameList node);

    public Node visit(Nil node);

    public Node visit(Node node);

    public Node visit(Number node);

    public Node visit(Pair node);

    public Node visit(Repeat node);

    public Node visit(Return node);

    public Node visit(Suffix node);

    public Node visit(TableConstructor node);

    public Node visit(True node);

    public Node visit(UnaryExpression node);

    public Node visit(Variable node);

    public Node visit(VarList node);

    public Node visit(While node);

    public void enterBlock(Block block);

    public void exitBlock(Block block);
}
