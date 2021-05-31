import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LuaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface LuaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link LuaParser#chunk}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChunk(LuaParser.ChunkContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(LuaParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtSemicolon}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtSemicolon(LuaParser.StmtSemicolonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtAssign}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtAssign(LuaParser.StmtAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtFuncCall}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtFuncCall(LuaParser.StmtFuncCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtLabel}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtLabel(LuaParser.StmtLabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtBreak}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtBreak(LuaParser.StmtBreakContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtGoto}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtGoto(LuaParser.StmtGotoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtDo}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtDo(LuaParser.StmtDoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtWhile}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtWhile(LuaParser.StmtWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtRepeat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtRepeat(LuaParser.StmtRepeatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtIf}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtIf(LuaParser.StmtIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtForStep}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtForStep(LuaParser.StmtForStepContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtForIn}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtForIn(LuaParser.StmtForInContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtFuncDef}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtFuncDef(LuaParser.StmtFuncDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtLocalFuncDef}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtLocalFuncDef(LuaParser.StmtLocalFuncDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtLocalDecl}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtLocalDecl(LuaParser.StmtLocalDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#ifstmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfstmt(LuaParser.IfstmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#elseifstmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseifstmt(LuaParser.ElseifstmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#elsestmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElsestmt(LuaParser.ElsestmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#retstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetstat(LuaParser.RetstatContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(LuaParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#funcname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncname(LuaParser.FuncnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#varlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarlist(LuaParser.VarlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#namelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamelist(LuaParser.NamelistContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#explist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplist(LuaParser.ExplistContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expCmp}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpCmp(LuaParser.ExpCmpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expNumber}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpNumber(LuaParser.ExpNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expThreeDots}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpThreeDots(LuaParser.ExpThreeDotsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expStrcat}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpStrcat(LuaParser.ExpStrcatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expTrue}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpTrue(LuaParser.ExpTrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expOr}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpOr(LuaParser.ExpOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expBitwise}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpBitwise(LuaParser.ExpBitwiseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expTableCtor}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpTableCtor(LuaParser.ExpTableCtorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expMulDivMod}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpMulDivMod(LuaParser.ExpMulDivModContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expFuncDef}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpFuncDef(LuaParser.ExpFuncDefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expFalse}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpFalse(LuaParser.ExpFalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expString}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpString(LuaParser.ExpStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expPrefix}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpPrefix(LuaParser.ExpPrefixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expUnary}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpUnary(LuaParser.ExpUnaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expAnd}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpAnd(LuaParser.ExpAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expPow}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpPow(LuaParser.ExpPowContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expNil}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpNil(LuaParser.ExpNilContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expAddSub}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpAddSub(LuaParser.ExpAddSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#prefixexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixexp(LuaParser.PrefixexpContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#functioncall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctioncall(LuaParser.FunctioncallContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#varOrExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarOrExp(LuaParser.VarOrExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(LuaParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#varSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarSuffix(LuaParser.VarSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#nameAndArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameAndArgs(LuaParser.NameAndArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(LuaParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#functiondef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctiondef(LuaParser.FunctiondefContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#funcbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncbody(LuaParser.FuncbodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#parlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParlist(LuaParser.ParlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#tableconstructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTableconstructor(LuaParser.TableconstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#fieldlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldlist(LuaParser.FieldlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(LuaParser.FieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#fieldsep}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldsep(LuaParser.FieldsepContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorOr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorOr(LuaParser.OperatorOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorAnd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAnd(LuaParser.OperatorAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorComparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorComparison(LuaParser.OperatorComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorStrcat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorStrcat(LuaParser.OperatorStrcatContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorAddSub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorAddSub(LuaParser.OperatorAddSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorMulDivMod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorMulDivMod(LuaParser.OperatorMulDivModContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorBitwise}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorBitwise(LuaParser.OperatorBitwiseContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorUnary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorUnary(LuaParser.OperatorUnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#operatorPower}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorPower(LuaParser.OperatorPowerContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(LuaParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link LuaParser#string}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(LuaParser.StringContext ctx);
}