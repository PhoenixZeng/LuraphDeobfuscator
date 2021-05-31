import ASTNodes.*;
import ASTNodes.Number;
import ASTNodes.LuaString;
import org.antlr.v4.runtime.tree.TerminalNode;

public class BuildASTVisitor extends LuaBaseVisitor<Node> {

    @Override
    public Node visitChunk(LuaParser.ChunkContext ctx) {
        return visitBlock(ctx.block());
    }

    @Override
    public Block visitBlock(LuaParser.BlockContext ctx) {
        Block block = new Block();

        for (LuaParser.StatContext stat : ctx.stat()) {
            Node node = visit(stat);
            if (node instanceof Semicolon) {
                continue;
            }
            block.stmts.add((Statement)node);
        }

        if (ctx.retstat() != null) {
            block.ret = visitRetstat(ctx.retstat());
        }

        return block;
    }

    // Statements


    @Override
    public Semicolon visitStmtSemicolon(LuaParser.StmtSemicolonContext ctx) {
        return new Semicolon();
    }

    @Override
    public Assign visitStmtAssign(LuaParser.StmtAssignContext ctx) {
        Assign assign = new Assign();

        assign.vars = GetVarList(ctx.varlist());
        assign.exprs = GetExprList(ctx.explist());

        return assign;
    }

    @Override
    public FunctionCall visitStmtFuncCall(LuaParser.StmtFuncCallContext ctx) {
        FunctionCall call = visitFunctioncall(ctx.functioncall());
        call.isStatement = true;
        return call;
    }

    @Override
    public Label visitStmtLabel(LuaParser.StmtLabelContext ctx) {
        Label label = new Label();

        label.name = new LuaString(ctx.label().NAME().getText());

        return label;
    }

    @Override
    public Break visitStmtBreak(LuaParser.StmtBreakContext ctx) {
        return new Break();
    }

    @Override
    public GoTo visitStmtGoto(LuaParser.StmtGotoContext ctx) {
        GoTo stmt = new GoTo();
        stmt.name = new LuaString(ctx.NAME().getText());
        return stmt;
    }

    @Override
    public Do visitStmtDo(LuaParser.StmtDoContext ctx) {
        Do stmt = new Do();

        stmt.block = visitBlock(ctx.block());

        return stmt;
    }

    @Override
    public While visitStmtWhile(LuaParser.StmtWhileContext ctx) {
        While stmt = new While();

        stmt.expr = (Expression)visit(ctx.exp());
        stmt.block = visitBlock(ctx.block());

        return stmt;
    }

    @Override
    public Repeat visitStmtRepeat(LuaParser.StmtRepeatContext ctx) {
        Repeat stmt = new Repeat();

        stmt.block = visitBlock(ctx.block());
        stmt.expr = (Expression)visit(ctx.exp());

        return stmt;
    }

    @Override
    public If visitStmtIf(LuaParser.StmtIfContext ctx) {
        If stmt = new If();

        Expression expr = (Expression)visit(ctx.ifstmt().exp());
        Block block = visitBlock(ctx.ifstmt().block());

        stmt.ifstmt = new Pair<>(expr, block);

        int i = 0;

        while (true) {

            if (ctx.elseifstmt().exp(i) == null) {
                break;
            }

            expr = (Expression)visit(ctx.elseifstmt().exp(i));
            block = visitBlock(ctx.elseifstmt().block(i));

            stmt.elseifstmt.add(new Pair<>(expr, block));

            ++i;
        }


        if (ctx.elsestmt().block() != null) {
            stmt.elsestmt = visitBlock(ctx.elsestmt().block());
        }

        return stmt;
    }

    @Override
    public ForStep visitStmtForStep(LuaParser.StmtForStepContext ctx) {
        ForStep stmt = new ForStep();

        stmt.iteratorName = new LuaString(ctx.NAME().getText());
        stmt.init = (Expression)visit(ctx.exp(0));
        stmt.condition = (Expression)visit(ctx.exp(1));

        if (ctx.exp(2) != null) {
            stmt.step = (Expression)visit(ctx.exp(2));
        }

        stmt.block = visitBlock(ctx.block());
        stmt.block.parent = stmt;

        return stmt;
    }

    @Override
    public ForIn visitStmtForIn(LuaParser.StmtForInContext ctx) {
        ForIn stmt = new ForIn();

        stmt.names = GetNameList(ctx.namelist());
        stmt.exprs = GetExprList(ctx.explist());
        stmt.block = visitBlock(ctx.block());
        stmt.block.parent = stmt;

        return stmt;
    }

    @Override
    public Function visitStmtFuncDef(LuaParser.StmtFuncDefContext ctx) {
        Function func = GetFunctionBody(ctx.funcbody(), Function.Type.GLOBAL);

        func.name = new LuaString(ctx.funcname().getText());

        return func;
    }

    @Override
    public Function visitStmtLocalFuncDef(LuaParser.StmtLocalFuncDefContext ctx) {
        Function fn = GetFunctionBody(ctx.funcbody(), Function.Type.LOCAL);

        fn.name = new LuaString(ctx.NAME().getText());

        return fn;
    }

    @Override
    public LocalDeclare visitStmtLocalDecl(LuaParser.StmtLocalDeclContext ctx) {
        LocalDeclare stmt = new LocalDeclare();

        stmt.names = GetNameList(ctx.namelist());

        if (ctx.explist() != null) {
            stmt.exprs = GetExprList(ctx.explist());
        }

        return stmt;
    }

    @Override
    public Return visitRetstat(LuaParser.RetstatContext ctx) {
        Return ret = new Return();

        if (ctx.explist() != null) {
            ret.exprs = GetExprList(ctx.explist());
        }

        return ret;
    }

    // Expressions

    @Override
    public Nil visitExpNil(LuaParser.ExpNilContext ctx) {
        return new Nil();
    }

    @Override
    public False visitExpFalse(LuaParser.ExpFalseContext ctx) {
        return new False();
    }

    @Override
    public True visitExpTrue(LuaParser.ExpTrueContext ctx) {
        return new True();
    }

    @Override
    public Number visitExpNumber(LuaParser.ExpNumberContext ctx) {
        return new Number(ctx.getText());
    }

    @Override
    public LuaString visitString(LuaParser.StringContext ctx) {
        return new LuaString(ctx.getText());
    }

    @Override
    public LuaString visitExpThreeDots(LuaParser.ExpThreeDotsContext ctx) {
        return new LuaString("...");
    }

    @Override
    public Function visitExpFuncDef(LuaParser.ExpFuncDefContext ctx) {
        return GetFunctionBody(ctx.functiondef().funcbody(), Function.Type.EXPR);
    }

    @Override
    public Expression visitPrefixexp(LuaParser.PrefixexpContext ctx) {

        // rewrite prefix
        //         - varOrExp
        if (ctx.nameAndArgs().size() == 0) {
            if (ctx.varOrExp().var() != null) {
                return visitVar(ctx.varOrExp().var());
            }
            else if (ctx.varOrExp().exp() != null) {
                return (Expression)visit(ctx.varOrExp().exp());
            }
        }

        // return call

        FunctionCall prefix = new FunctionCall();

        prefix.varOrExp = visitVarOrExp(ctx.varOrExp());

        for (LuaParser.NameAndArgsContext namesAndArgsContext : ctx.nameAndArgs()) {
            prefix.nameAndArgs.add(visitNameAndArgs(namesAndArgsContext));
        }

        return prefix;
    }

    @Override
    public TableConstructor visitTableconstructor(LuaParser.TableconstructorContext ctx) {
        return GetTableConstructor(ctx);
    }

    @Override
    public BinaryExpression visitExpPow(LuaParser.ExpPowContext ctx) {
        return new BinaryExpression(
                BinaryExpression.Operator.POW,
                (Expression)visit(ctx.exp(0)),
                (Expression)visit(ctx.exp(1)));
    }

    @Override
    public UnaryExpression visitExpUnary(LuaParser.ExpUnaryContext ctx) {
        String token = ctx.operatorUnary().getText();

        UnaryExpression.Operator op;

        switch(token) {
            case "not":
                op = UnaryExpression.Operator.NOT;
                break;
            case "#":
                op = UnaryExpression.Operator.HASHTAG;
                break;
            case "-":
                op = UnaryExpression.Operator.MINUS;
                break;
            case "~":
                op = UnaryExpression.Operator.TILDE;
                break;
            default:
                op = UnaryExpression.Operator.INVALID;
                break;
        }

        return new UnaryExpression(
                op,
                (Expression)visit(ctx.exp()));
    }

    @Override
    public Node visitExpMulDivMod(LuaParser.ExpMulDivModContext ctx) {
        String token = ctx.operatorMulDivMod().getText();

        BinaryExpression.Operator op;

        switch(token) {
            case "*":
                op = BinaryExpression.Operator.MUL;
                break;
            case "/":
                op = BinaryExpression.Operator.REAL_DIV;
                break;
            case "%":
                op = BinaryExpression.Operator.MOD;
                break;
            case "//":
                op = BinaryExpression.Operator.INTEGER_DIV;
                break;
            default:
                op = BinaryExpression.Operator.INVALID;
                break;
        }

        return new BinaryExpression(
                op,
                (Expression)visit(ctx.exp(0)),
                (Expression)visit(ctx.exp(1)));
    }

    @Override
    public BinaryExpression visitExpAddSub(LuaParser.ExpAddSubContext ctx) {
        String token = ctx.operatorAddSub().getText();

        BinaryExpression.Operator op = BinaryExpression.Operator.INVALID;

        if (token.equals("+")) {
            op = BinaryExpression.Operator.ADD;
        }
        else if (token.equals("-")) {
            op = BinaryExpression.Operator.SUB;
        }

        return new BinaryExpression(
                op,
                (Expression)visit(ctx.exp(0)),
                (Expression)visit(ctx.exp(1)));
    }

    @Override
    public BinaryExpression visitExpStrcat(LuaParser.ExpStrcatContext ctx) {
        return new BinaryExpression(
                BinaryExpression.Operator.STRCAT,
                (Expression)visit(ctx.exp(0)),
                (Expression)visit(ctx.exp(1)));
    }

    @Override
    public Node visitExpCmp(LuaParser.ExpCmpContext ctx) {
        String token = ctx.operatorComparison().getText();

        BinaryExpression.Operator op;

        switch (token) {
            case "<":
                op = BinaryExpression.Operator.LT;
                break;
            case ">":
                op = BinaryExpression.Operator.GT;
                break;
            case "<=":
                op = BinaryExpression.Operator.LTE;
                break;
            case ">=":
                op = BinaryExpression.Operator.GTE;
                break;
            case "~=":
                op = BinaryExpression.Operator.NEQ;
                break;
            case "==":
                op = BinaryExpression.Operator.EQ;
                break;
            default:
                op = BinaryExpression.Operator.INVALID;
                break;
        }

        return new BinaryExpression(
                op,
                (Expression)visit(ctx.exp(0)),
                (Expression)visit(ctx.exp(1)));
    }

    @Override
    public BinaryExpression visitExpAnd(LuaParser.ExpAndContext ctx) {
        return new BinaryExpression(
                BinaryExpression.Operator.LOGICAL_AND,
                (Expression)visit(ctx.exp(0)),
                (Expression)visit(ctx.exp(1)));
    }

    @Override
    public BinaryExpression visitExpOr(LuaParser.ExpOrContext ctx) {
        return new BinaryExpression(
                BinaryExpression.Operator.LOGICAL_OR,
                (Expression)visit(ctx.exp(0)),
                (Expression)visit(ctx.exp(1)));
    }

    @Override
    public BinaryExpression visitExpBitwise(LuaParser.ExpBitwiseContext ctx) {
        String token = ctx.operatorBitwise().getText();

        BinaryExpression.Operator op;

        switch (token) {
            case "&":
                op = BinaryExpression.Operator.BITWISE_AND;
                break;
            case "|":
                op = BinaryExpression.Operator.BITWISE_OR;
                break;
            case "~":
                op = BinaryExpression.Operator.BITWISE_NOT;
                break;
            case "<<":
                op = BinaryExpression.Operator.BITWISE_SHL;
                break;
            case ">>":
                op = BinaryExpression.Operator.BITWISE_SHR;
                break;
            default:
                op = BinaryExpression.Operator.INVALID;
                break;
        }

        return new BinaryExpression(
                op,
                (Expression)visit(ctx.exp(0)),
                (Expression)visit(ctx.exp(1)));
    }

    // Misc

    @Override
    public FunctionCall visitFunctioncall(LuaParser.FunctioncallContext ctx) {
        FunctionCall call = new FunctionCall();

        call.varOrExp = visitVarOrExp(ctx.varOrExp());

        for (LuaParser.NameAndArgsContext namesAndArgsContext : ctx.nameAndArgs()) {
            call.nameAndArgs.add(visitNameAndArgs(namesAndArgsContext));
        }

        return call;
    }

    @Override
    public Expression visitVarOrExp(LuaParser.VarOrExpContext ctx) {
        Expression exp;

        if (ctx.exp() != null) {
            // (exp)
            exp = (Expression)visit(ctx.exp());
        }
        else {
            // var
            exp = (Expression)visit(ctx.var());
        }

        return exp;
    }

    @Override
    public Variable visitVar(LuaParser.VarContext ctx) {
        Variable var = new Variable();

        if (ctx.NAME() != null) {
            var.name = new LuaString(ctx.NAME().getText());
        }
        else {
            var.name = (Expression)visit(ctx.exp());
        }

        for (LuaParser.VarSuffixContext suffixContext : ctx.varSuffix()) {
            var.suffixes.add((Suffix)visit(suffixContext));
        }

        return var;
    }

    @Override
    public Suffix visitVarSuffix(LuaParser.VarSuffixContext ctx) {
        Suffix suffix = new Suffix();

        for (LuaParser.NameAndArgsContext context : ctx.nameAndArgs()) {
            suffix.nameAndArgs.add((NameAndArgs)visit(context));
        }

        if (ctx.exp() != null) {
            suffix.expOrName = (Expression)visit(ctx.exp());
        }
        else {
            suffix.expOrName = new LuaString(ctx.NAME().getText());
        }

        return suffix;
    }

    @Override
    public NameAndArgs visitNameAndArgs(LuaParser.NameAndArgsContext ctx) {
        NameAndArgs nameAndArgs = new NameAndArgs();

        if (ctx.NAME() != null) {
            nameAndArgs.name = ctx.NAME().getText();
        }

        nameAndArgs.args = visitArgs(ctx.args());

        return nameAndArgs;
    }

    @Override
    public Expression visitArgs(LuaParser.ArgsContext ctx) {
        Expression expr = null;

        if (ctx.string() != null) {
            expr = new LuaString(ctx.string().getText());
        }
        else if (ctx.tableconstructor() != null) {
            expr = GetTableConstructor(ctx.tableconstructor());
        }
        else if(ctx.explist() != null) {
            expr = GetExprList(ctx.explist());
        }

        return expr;
    }

    // Helpers

    private VarList GetVarList(LuaParser.VarlistContext ctx) {
        VarList list = new VarList();

        for (LuaParser.VarContext varCtx : ctx.var()) {
            list.vars.add((Variable)visit(varCtx));
        }

        return list;
    }

    private NameList GetNameList(LuaParser.NamelistContext ctx) {
        NameList list = new NameList();

        for (TerminalNode node : ctx.NAME()) {
            list.names.add(new LuaString(node.getText()));
        }

        return list;
    }

    private ExprList GetExprList(LuaParser.ExplistContext ctx) {
        ExprList list = new ExprList();

        for (LuaParser.ExpContext context : ctx.exp()) {
            Node node = visit(context);
            list.exprs.add((Expression)node);
        }

        return list;
    }

    private TableConstructor GetTableConstructor(LuaParser.TableconstructorContext ctx) {
        TableConstructor ctor = new TableConstructor();

        LuaParser.FieldlistContext fieldListCtx = ctx.fieldlist();

        if (fieldListCtx != null) {
            for (LuaParser.FieldContext fieldCtx : fieldListCtx.field()) {
                if (fieldCtx.exp(1) != null) {
                    // [exp] = exp
                    Expression first = (Expression)visit(fieldCtx.exp(0));
                    Expression second = (Expression)visit(fieldCtx.exp(1));
                    ctor.entries.add(new Pair<>(first, second));
                }
                else if (fieldCtx.NAME() != null) {
                    // NAME = exp
                    LuaString first = new LuaString(fieldCtx.NAME().getText());
                    Expression second = (Expression)visit(fieldCtx.exp(0));
                    ctor.entries.add(new Pair<>(first, second));
                }
                else {
                    // exp
                    Expression second = (Expression)visit(fieldCtx.exp(0));
                    ctor.entries.add(new Pair<>(null, second));
                }
            }
        }

        return ctor;
    }

    private Function GetFunctionBody(LuaParser.FuncbodyContext ctx, Function.Type type) {
        Function func = new Function(type);

        if (ctx.parlist() != null) {

            //  namelist (',' '...')? | '...'
            if (ctx.parlist().namelist() == null) {
                func.params.names.add(new LuaString("..."));
            }
            else {
                LuaParser.NamelistContext nameListCtx = ctx.parlist().namelist();

                int i = 0;

                while (true) {
                    TerminalNode node = nameListCtx.NAME(i);
                    if (node != null) {
                        func.params.names.add(new LuaString(node.toString()));
                    } else {
                        break;
                    }

                    ++i;
                }
            }
        }

        func.block = (Block)visit(ctx.block());
        func.block.parent = func;

        return func;
    }
}
