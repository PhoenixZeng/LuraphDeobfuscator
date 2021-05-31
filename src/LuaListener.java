import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LuaParser}.
 */
public interface LuaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LuaParser#chunk}.
	 * @param ctx the parse tree
	 */
	void enterChunk(LuaParser.ChunkContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#chunk}.
	 * @param ctx the parse tree
	 */
	void exitChunk(LuaParser.ChunkContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(LuaParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(LuaParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtSemicolon}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtSemicolon(LuaParser.StmtSemicolonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtSemicolon}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtSemicolon(LuaParser.StmtSemicolonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtAssign}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtAssign(LuaParser.StmtAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtAssign}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtAssign(LuaParser.StmtAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtFuncCall}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtFuncCall(LuaParser.StmtFuncCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtFuncCall}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtFuncCall(LuaParser.StmtFuncCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtLabel}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtLabel(LuaParser.StmtLabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtLabel}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtLabel(LuaParser.StmtLabelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtBreak}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtBreak(LuaParser.StmtBreakContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtBreak}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtBreak(LuaParser.StmtBreakContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtGoto}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtGoto(LuaParser.StmtGotoContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtGoto}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtGoto(LuaParser.StmtGotoContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtDo}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtDo(LuaParser.StmtDoContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtDo}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtDo(LuaParser.StmtDoContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtWhile}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtWhile(LuaParser.StmtWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtWhile}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtWhile(LuaParser.StmtWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtRepeat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtRepeat(LuaParser.StmtRepeatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtRepeat}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtRepeat(LuaParser.StmtRepeatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtIf}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtIf(LuaParser.StmtIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtIf}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtIf(LuaParser.StmtIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtForStep}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtForStep(LuaParser.StmtForStepContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtForStep}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtForStep(LuaParser.StmtForStepContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtForIn}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtForIn(LuaParser.StmtForInContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtForIn}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtForIn(LuaParser.StmtForInContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtFuncDef}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtFuncDef(LuaParser.StmtFuncDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtFuncDef}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtFuncDef(LuaParser.StmtFuncDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtLocalFuncDef}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtLocalFuncDef(LuaParser.StmtLocalFuncDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtLocalFuncDef}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtLocalFuncDef(LuaParser.StmtLocalFuncDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmtLocalDecl}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStmtLocalDecl(LuaParser.StmtLocalDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmtLocalDecl}
	 * labeled alternative in {@link LuaParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStmtLocalDecl(LuaParser.StmtLocalDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#ifstmt}.
	 * @param ctx the parse tree
	 */
	void enterIfstmt(LuaParser.IfstmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#ifstmt}.
	 * @param ctx the parse tree
	 */
	void exitIfstmt(LuaParser.IfstmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#elseifstmt}.
	 * @param ctx the parse tree
	 */
	void enterElseifstmt(LuaParser.ElseifstmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#elseifstmt}.
	 * @param ctx the parse tree
	 */
	void exitElseifstmt(LuaParser.ElseifstmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#elsestmt}.
	 * @param ctx the parse tree
	 */
	void enterElsestmt(LuaParser.ElsestmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#elsestmt}.
	 * @param ctx the parse tree
	 */
	void exitElsestmt(LuaParser.ElsestmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#retstat}.
	 * @param ctx the parse tree
	 */
	void enterRetstat(LuaParser.RetstatContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#retstat}.
	 * @param ctx the parse tree
	 */
	void exitRetstat(LuaParser.RetstatContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(LuaParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(LuaParser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#funcname}.
	 * @param ctx the parse tree
	 */
	void enterFuncname(LuaParser.FuncnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#funcname}.
	 * @param ctx the parse tree
	 */
	void exitFuncname(LuaParser.FuncnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#varlist}.
	 * @param ctx the parse tree
	 */
	void enterVarlist(LuaParser.VarlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#varlist}.
	 * @param ctx the parse tree
	 */
	void exitVarlist(LuaParser.VarlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#namelist}.
	 * @param ctx the parse tree
	 */
	void enterNamelist(LuaParser.NamelistContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#namelist}.
	 * @param ctx the parse tree
	 */
	void exitNamelist(LuaParser.NamelistContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#explist}.
	 * @param ctx the parse tree
	 */
	void enterExplist(LuaParser.ExplistContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#explist}.
	 * @param ctx the parse tree
	 */
	void exitExplist(LuaParser.ExplistContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expCmp}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpCmp(LuaParser.ExpCmpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expCmp}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpCmp(LuaParser.ExpCmpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expNumber}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpNumber(LuaParser.ExpNumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expNumber}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpNumber(LuaParser.ExpNumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expThreeDots}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpThreeDots(LuaParser.ExpThreeDotsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expThreeDots}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpThreeDots(LuaParser.ExpThreeDotsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expStrcat}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpStrcat(LuaParser.ExpStrcatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expStrcat}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpStrcat(LuaParser.ExpStrcatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expTrue}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpTrue(LuaParser.ExpTrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expTrue}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpTrue(LuaParser.ExpTrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expOr}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpOr(LuaParser.ExpOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expOr}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpOr(LuaParser.ExpOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expBitwise}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpBitwise(LuaParser.ExpBitwiseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expBitwise}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpBitwise(LuaParser.ExpBitwiseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expTableCtor}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpTableCtor(LuaParser.ExpTableCtorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expTableCtor}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpTableCtor(LuaParser.ExpTableCtorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expMulDivMod}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpMulDivMod(LuaParser.ExpMulDivModContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expMulDivMod}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpMulDivMod(LuaParser.ExpMulDivModContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expFuncDef}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpFuncDef(LuaParser.ExpFuncDefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expFuncDef}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpFuncDef(LuaParser.ExpFuncDefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expFalse}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpFalse(LuaParser.ExpFalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expFalse}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpFalse(LuaParser.ExpFalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expString}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpString(LuaParser.ExpStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expString}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpString(LuaParser.ExpStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expPrefix}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpPrefix(LuaParser.ExpPrefixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expPrefix}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpPrefix(LuaParser.ExpPrefixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expUnary}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpUnary(LuaParser.ExpUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expUnary}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpUnary(LuaParser.ExpUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expAnd}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpAnd(LuaParser.ExpAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expAnd}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpAnd(LuaParser.ExpAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expPow}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpPow(LuaParser.ExpPowContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expPow}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpPow(LuaParser.ExpPowContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expNil}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpNil(LuaParser.ExpNilContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expNil}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpNil(LuaParser.ExpNilContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expAddSub}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExpAddSub(LuaParser.ExpAddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expAddSub}
	 * labeled alternative in {@link LuaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExpAddSub(LuaParser.ExpAddSubContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#prefixexp}.
	 * @param ctx the parse tree
	 */
	void enterPrefixexp(LuaParser.PrefixexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#prefixexp}.
	 * @param ctx the parse tree
	 */
	void exitPrefixexp(LuaParser.PrefixexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#functioncall}.
	 * @param ctx the parse tree
	 */
	void enterFunctioncall(LuaParser.FunctioncallContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#functioncall}.
	 * @param ctx the parse tree
	 */
	void exitFunctioncall(LuaParser.FunctioncallContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#varOrExp}.
	 * @param ctx the parse tree
	 */
	void enterVarOrExp(LuaParser.VarOrExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#varOrExp}.
	 * @param ctx the parse tree
	 */
	void exitVarOrExp(LuaParser.VarOrExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(LuaParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(LuaParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#varSuffix}.
	 * @param ctx the parse tree
	 */
	void enterVarSuffix(LuaParser.VarSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#varSuffix}.
	 * @param ctx the parse tree
	 */
	void exitVarSuffix(LuaParser.VarSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#nameAndArgs}.
	 * @param ctx the parse tree
	 */
	void enterNameAndArgs(LuaParser.NameAndArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#nameAndArgs}.
	 * @param ctx the parse tree
	 */
	void exitNameAndArgs(LuaParser.NameAndArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(LuaParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(LuaParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#functiondef}.
	 * @param ctx the parse tree
	 */
	void enterFunctiondef(LuaParser.FunctiondefContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#functiondef}.
	 * @param ctx the parse tree
	 */
	void exitFunctiondef(LuaParser.FunctiondefContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#funcbody}.
	 * @param ctx the parse tree
	 */
	void enterFuncbody(LuaParser.FuncbodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#funcbody}.
	 * @param ctx the parse tree
	 */
	void exitFuncbody(LuaParser.FuncbodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#parlist}.
	 * @param ctx the parse tree
	 */
	void enterParlist(LuaParser.ParlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#parlist}.
	 * @param ctx the parse tree
	 */
	void exitParlist(LuaParser.ParlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#tableconstructor}.
	 * @param ctx the parse tree
	 */
	void enterTableconstructor(LuaParser.TableconstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#tableconstructor}.
	 * @param ctx the parse tree
	 */
	void exitTableconstructor(LuaParser.TableconstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#fieldlist}.
	 * @param ctx the parse tree
	 */
	void enterFieldlist(LuaParser.FieldlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#fieldlist}.
	 * @param ctx the parse tree
	 */
	void exitFieldlist(LuaParser.FieldlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(LuaParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(LuaParser.FieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#fieldsep}.
	 * @param ctx the parse tree
	 */
	void enterFieldsep(LuaParser.FieldsepContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#fieldsep}.
	 * @param ctx the parse tree
	 */
	void exitFieldsep(LuaParser.FieldsepContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorOr}.
	 * @param ctx the parse tree
	 */
	void enterOperatorOr(LuaParser.OperatorOrContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorOr}.
	 * @param ctx the parse tree
	 */
	void exitOperatorOr(LuaParser.OperatorOrContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorAnd}.
	 * @param ctx the parse tree
	 */
	void enterOperatorAnd(LuaParser.OperatorAndContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorAnd}.
	 * @param ctx the parse tree
	 */
	void exitOperatorAnd(LuaParser.OperatorAndContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorComparison}.
	 * @param ctx the parse tree
	 */
	void enterOperatorComparison(LuaParser.OperatorComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorComparison}.
	 * @param ctx the parse tree
	 */
	void exitOperatorComparison(LuaParser.OperatorComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorStrcat}.
	 * @param ctx the parse tree
	 */
	void enterOperatorStrcat(LuaParser.OperatorStrcatContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorStrcat}.
	 * @param ctx the parse tree
	 */
	void exitOperatorStrcat(LuaParser.OperatorStrcatContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorAddSub}.
	 * @param ctx the parse tree
	 */
	void enterOperatorAddSub(LuaParser.OperatorAddSubContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorAddSub}.
	 * @param ctx the parse tree
	 */
	void exitOperatorAddSub(LuaParser.OperatorAddSubContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorMulDivMod}.
	 * @param ctx the parse tree
	 */
	void enterOperatorMulDivMod(LuaParser.OperatorMulDivModContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorMulDivMod}.
	 * @param ctx the parse tree
	 */
	void exitOperatorMulDivMod(LuaParser.OperatorMulDivModContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorBitwise}.
	 * @param ctx the parse tree
	 */
	void enterOperatorBitwise(LuaParser.OperatorBitwiseContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorBitwise}.
	 * @param ctx the parse tree
	 */
	void exitOperatorBitwise(LuaParser.OperatorBitwiseContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorUnary}.
	 * @param ctx the parse tree
	 */
	void enterOperatorUnary(LuaParser.OperatorUnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorUnary}.
	 * @param ctx the parse tree
	 */
	void exitOperatorUnary(LuaParser.OperatorUnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#operatorPower}.
	 * @param ctx the parse tree
	 */
	void enterOperatorPower(LuaParser.OperatorPowerContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#operatorPower}.
	 * @param ctx the parse tree
	 */
	void exitOperatorPower(LuaParser.OperatorPowerContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(LuaParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(LuaParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link LuaParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(LuaParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link LuaParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(LuaParser.StringContext ctx);
}