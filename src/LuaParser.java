import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LuaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, NAME=56, NORMALSTRING=57, CHARSTRING=58, 
		LONGSTRING=59, INT=60, HEX=61, FLOAT=62, HEX_FLOAT=63, COMMENT=64, LINE_COMMENT=65, 
		WS=66, SHEBANG=67;
	public static final int
		RULE_chunk = 0, RULE_block = 1, RULE_stat = 2, RULE_ifstmt = 3, RULE_elseifstmt = 4, 
		RULE_elsestmt = 5, RULE_retstat = 6, RULE_label = 7, RULE_funcname = 8, 
		RULE_varlist = 9, RULE_namelist = 10, RULE_explist = 11, RULE_exp = 12, 
		RULE_prefixexp = 13, RULE_functioncall = 14, RULE_varOrExp = 15, RULE_var = 16, 
		RULE_varSuffix = 17, RULE_nameAndArgs = 18, RULE_args = 19, RULE_functiondef = 20, 
		RULE_funcbody = 21, RULE_parlist = 22, RULE_tableconstructor = 23, RULE_fieldlist = 24, 
		RULE_field = 25, RULE_fieldsep = 26, RULE_operatorOr = 27, RULE_operatorAnd = 28, 
		RULE_operatorComparison = 29, RULE_operatorStrcat = 30, RULE_operatorAddSub = 31, 
		RULE_operatorMulDivMod = 32, RULE_operatorBitwise = 33, RULE_operatorUnary = 34, 
		RULE_operatorPower = 35, RULE_number = 36, RULE_string = 37;
	private static String[] makeRuleNames() {
		return new String[] {
			"chunk", "block", "stat", "ifstmt", "elseifstmt", "elsestmt", "retstat", 
			"label", "funcname", "varlist", "namelist", "explist", "exp", "prefixexp", 
			"functioncall", "varOrExp", "var", "varSuffix", "nameAndArgs", "args", 
			"functiondef", "funcbody", "parlist", "tableconstructor", "fieldlist", 
			"field", "fieldsep", "operatorOr", "operatorAnd", "operatorComparison", 
			"operatorStrcat", "operatorAddSub", "operatorMulDivMod", "operatorBitwise", 
			"operatorUnary", "operatorPower", "number", "string"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'='", "'break'", "'goto'", "'do'", "'end'", "'while'", 
			"'repeat'", "'until'", "'for'", "','", "'in'", "'function'", "'local'", 
			"'if'", "'then'", "'elseif'", "'else'", "'return'", "'::'", "'.'", "':'", 
			"'nil'", "'false'", "'true'", "'...'", "'('", "')'", "'['", "']'", "'{'", 
			"'}'", "'or'", "'and'", "'<'", "'>'", "'<='", "'>='", "'~='", "'=='", 
			"'..'", "'+'", "'-'", "'*'", "'/'", "'%'", "'//'", "'&'", "'|'", "'~'", 
			"'<<'", "'>>'", "'not'", "'#'", "'^'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "NAME", "NORMALSTRING", 
			"CHARSTRING", "LONGSTRING", "INT", "HEX", "FLOAT", "HEX_FLOAT", "COMMENT", 
			"LINE_COMMENT", "WS", "SHEBANG"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Lua.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LuaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ChunkContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode EOF() { return getToken(LuaParser.EOF, 0); }
		public ChunkContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_chunk; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterChunk(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitChunk(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitChunk(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChunkContext chunk() throws RecognitionException {
		ChunkContext _localctx = new ChunkContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_chunk);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			block();
			setState(77);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public RetstatContext retstat() {
			return getRuleContext(RetstatContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__6) | (1L << T__7) | (1L << T__9) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__19) | (1L << T__26) | (1L << NAME))) != 0)) {
				{
				{
				setState(79);
				stat();
				}
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__18) {
				{
				setState(85);
				retstat();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatContext extends ParserRuleContext {
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
	 
		public StatContext() { }
		public void copyFrom(StatContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StmtDoContext extends StatContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StmtDoContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtDo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtDo(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtDo(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtBreakContext extends StatContext {
		public StmtBreakContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtBreak(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtBreak(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtBreak(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtFuncCallContext extends StatContext {
		public FunctioncallContext functioncall() {
			return getRuleContext(FunctioncallContext.class,0);
		}
		public StmtFuncCallContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtFuncCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtFuncCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtRepeatContext extends StatContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public StmtRepeatContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtRepeat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtRepeat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtRepeat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtForInContext extends StatContext {
		public NamelistContext namelist() {
			return getRuleContext(NamelistContext.class,0);
		}
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StmtForInContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtForIn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtForIn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtForIn(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtLocalFuncDefContext extends StatContext {
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public FuncbodyContext funcbody() {
			return getRuleContext(FuncbodyContext.class,0);
		}
		public StmtLocalFuncDefContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtLocalFuncDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtLocalFuncDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtLocalFuncDef(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtSemicolonContext extends StatContext {
		public StmtSemicolonContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtSemicolon(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtSemicolon(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtSemicolon(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtLabelContext extends StatContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public StmtLabelContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtLabel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtWhileContext extends StatContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StmtWhileContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtWhile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtWhile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtGotoContext extends StatContext {
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public StmtGotoContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtGoto(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtGoto(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtGoto(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtIfContext extends StatContext {
		public IfstmtContext ifstmt() {
			return getRuleContext(IfstmtContext.class,0);
		}
		public ElseifstmtContext elseifstmt() {
			return getRuleContext(ElseifstmtContext.class,0);
		}
		public ElsestmtContext elsestmt() {
			return getRuleContext(ElsestmtContext.class,0);
		}
		public StmtIfContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtIf(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtForStepContext extends StatContext {
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StmtForStepContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtForStep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtForStep(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtForStep(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtLocalDeclContext extends StatContext {
		public NamelistContext namelist() {
			return getRuleContext(NamelistContext.class,0);
		}
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public StmtLocalDeclContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtLocalDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtLocalDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtLocalDecl(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtAssignContext extends StatContext {
		public VarlistContext varlist() {
			return getRuleContext(VarlistContext.class,0);
		}
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public StmtAssignContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtAssign(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtFuncDefContext extends StatContext {
		public FuncnameContext funcname() {
			return getRuleContext(FuncnameContext.class,0);
		}
		public FuncbodyContext funcbody() {
			return getRuleContext(FuncbodyContext.class,0);
		}
		public StmtFuncDefContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterStmtFuncDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitStmtFuncDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitStmtFuncDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stat);
		int _la;
		try {
			setState(154);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new StmtSemicolonContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(88);
				match(T__0);
				}
				break;
			case 2:
				_localctx = new StmtAssignContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
				varlist();
				setState(90);
				match(T__1);
				setState(91);
				explist();
				}
				break;
			case 3:
				_localctx = new StmtFuncCallContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(93);
				functioncall();
				}
				break;
			case 4:
				_localctx = new StmtLabelContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(94);
				label();
				}
				break;
			case 5:
				_localctx = new StmtBreakContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(95);
				match(T__2);
				}
				break;
			case 6:
				_localctx = new StmtGotoContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(96);
				match(T__3);
				setState(97);
				match(NAME);
				}
				break;
			case 7:
				_localctx = new StmtDoContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(98);
				match(T__4);
				setState(99);
				block();
				setState(100);
				match(T__5);
				}
				break;
			case 8:
				_localctx = new StmtWhileContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(102);
				match(T__6);
				setState(103);
				exp(0);
				setState(104);
				match(T__4);
				setState(105);
				block();
				setState(106);
				match(T__5);
				}
				break;
			case 9:
				_localctx = new StmtRepeatContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(108);
				match(T__7);
				setState(109);
				block();
				setState(110);
				match(T__8);
				setState(111);
				exp(0);
				}
				break;
			case 10:
				_localctx = new StmtIfContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(113);
				ifstmt();
				setState(114);
				elseifstmt();
				setState(115);
				elsestmt();
				setState(116);
				match(T__5);
				}
				break;
			case 11:
				_localctx = new StmtForStepContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(118);
				match(T__9);
				setState(119);
				match(NAME);
				setState(120);
				match(T__1);
				setState(121);
				exp(0);
				setState(122);
				match(T__10);
				setState(123);
				exp(0);
				setState(126);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(124);
					match(T__10);
					setState(125);
					exp(0);
					}
				}

				setState(128);
				match(T__4);
				setState(129);
				block();
				setState(130);
				match(T__5);
				}
				break;
			case 12:
				_localctx = new StmtForInContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(132);
				match(T__9);
				setState(133);
				namelist();
				setState(134);
				match(T__11);
				setState(135);
				explist();
				setState(136);
				match(T__4);
				setState(137);
				block();
				setState(138);
				match(T__5);
				}
				break;
			case 13:
				_localctx = new StmtFuncDefContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(140);
				match(T__12);
				setState(141);
				funcname();
				setState(142);
				funcbody();
				}
				break;
			case 14:
				_localctx = new StmtLocalFuncDefContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(144);
				match(T__13);
				setState(145);
				match(T__12);
				setState(146);
				match(NAME);
				setState(147);
				funcbody();
				}
				break;
			case 15:
				_localctx = new StmtLocalDeclContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(148);
				match(T__13);
				setState(149);
				namelist();
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(150);
					match(T__1);
					setState(151);
					explist();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfstmtContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public IfstmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifstmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterIfstmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitIfstmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitIfstmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfstmtContext ifstmt() throws RecognitionException {
		IfstmtContext _localctx = new IfstmtContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_ifstmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			match(T__14);
			setState(157);
			exp(0);
			setState(158);
			match(T__15);
			setState(159);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElseifstmtContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public ElseifstmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseifstmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterElseifstmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitElseifstmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitElseifstmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseifstmtContext elseifstmt() throws RecognitionException {
		ElseifstmtContext _localctx = new ElseifstmtContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_elseifstmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__16) {
				{
				{
				setState(161);
				match(T__16);
				setState(162);
				exp(0);
				setState(163);
				match(T__15);
				setState(164);
				block();
				}
				}
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElsestmtContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ElsestmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elsestmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterElsestmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitElsestmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitElsestmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElsestmtContext elsestmt() throws RecognitionException {
		ElsestmtContext _localctx = new ElsestmtContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_elsestmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(171);
				match(T__17);
				setState(172);
				block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RetstatContext extends ParserRuleContext {
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public RetstatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_retstat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterRetstat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitRetstat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitRetstat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RetstatContext retstat() throws RecognitionException {
		RetstatContext _localctx = new RetstatContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_retstat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			match(T__18);
			setState(177);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__30) | (1L << T__42) | (1L << T__49) | (1L << T__52) | (1L << T__53) | (1L << NAME) | (1L << NORMALSTRING) | (1L << CHARSTRING) | (1L << LONGSTRING) | (1L << INT) | (1L << HEX) | (1L << FLOAT) | (1L << HEX_FLOAT))) != 0)) {
				{
				setState(176);
				explist();
				}
			}

			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(179);
				match(T__0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(T__19);
			setState(183);
			match(NAME);
			setState(184);
			match(T__19);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncnameContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(LuaParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(LuaParser.NAME, i);
		}
		public FuncnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcname; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFuncname(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFuncname(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFuncname(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncnameContext funcname() throws RecognitionException {
		FuncnameContext _localctx = new FuncnameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_funcname);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			match(NAME);
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__20) {
				{
				{
				setState(187);
				match(T__20);
				setState(188);
				match(NAME);
				}
				}
				setState(193);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(196);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__21) {
				{
				setState(194);
				match(T__21);
				setState(195);
				match(NAME);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarlistContext extends ParserRuleContext {
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public VarlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterVarlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitVarlist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitVarlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarlistContext varlist() throws RecognitionException {
		VarlistContext _localctx = new VarlistContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_varlist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			var();
			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__10) {
				{
				{
				setState(199);
				match(T__10);
				setState(200);
				var();
				}
				}
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamelistContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(LuaParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(LuaParser.NAME, i);
		}
		public NamelistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namelist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterNamelist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitNamelist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitNamelist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamelistContext namelist() throws RecognitionException {
		NamelistContext _localctx = new NamelistContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_namelist);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(NAME);
			setState(211);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(207);
					match(T__10);
					setState(208);
					match(NAME);
					}
					} 
				}
				setState(213);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExplistContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ExplistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExplist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExplist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExplist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExplistContext explist() throws RecognitionException {
		ExplistContext _localctx = new ExplistContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_explist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			exp(0);
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__10) {
				{
				{
				setState(215);
				match(T__10);
				setState(216);
				exp(0);
				}
				}
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpContext extends ParserRuleContext {
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
	 
		public ExpContext() { }
		public void copyFrom(ExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExpCmpContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OperatorComparisonContext operatorComparison() {
			return getRuleContext(OperatorComparisonContext.class,0);
		}
		public ExpCmpContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpCmp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpCmp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpCmp(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpNumberContext extends ExpContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public ExpNumberContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpThreeDotsContext extends ExpContext {
		public ExpThreeDotsContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpThreeDots(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpThreeDots(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpThreeDots(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpStrcatContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OperatorStrcatContext operatorStrcat() {
			return getRuleContext(OperatorStrcatContext.class,0);
		}
		public ExpStrcatContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpStrcat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpStrcat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpStrcat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpTrueContext extends ExpContext {
		public ExpTrueContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpTrue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpTrue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpTrue(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpOrContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OperatorOrContext operatorOr() {
			return getRuleContext(OperatorOrContext.class,0);
		}
		public ExpOrContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpBitwiseContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OperatorBitwiseContext operatorBitwise() {
			return getRuleContext(OperatorBitwiseContext.class,0);
		}
		public ExpBitwiseContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpBitwise(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpBitwise(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpBitwise(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpTableCtorContext extends ExpContext {
		public TableconstructorContext tableconstructor() {
			return getRuleContext(TableconstructorContext.class,0);
		}
		public ExpTableCtorContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpTableCtor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpTableCtor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpTableCtor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpMulDivModContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OperatorMulDivModContext operatorMulDivMod() {
			return getRuleContext(OperatorMulDivModContext.class,0);
		}
		public ExpMulDivModContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpMulDivMod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpMulDivMod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpMulDivMod(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpFuncDefContext extends ExpContext {
		public FunctiondefContext functiondef() {
			return getRuleContext(FunctiondefContext.class,0);
		}
		public ExpFuncDefContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpFuncDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpFuncDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpFuncDef(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpFalseContext extends ExpContext {
		public ExpFalseContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpFalse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpFalse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpFalse(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpStringContext extends ExpContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public ExpStringContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpString(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpPrefixContext extends ExpContext {
		public PrefixexpContext prefixexp() {
			return getRuleContext(PrefixexpContext.class,0);
		}
		public ExpPrefixContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpPrefix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpPrefix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpPrefix(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpUnaryContext extends ExpContext {
		public OperatorUnaryContext operatorUnary() {
			return getRuleContext(OperatorUnaryContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ExpUnaryContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpUnary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpAndContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OperatorAndContext operatorAnd() {
			return getRuleContext(OperatorAndContext.class,0);
		}
		public ExpAndContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpPowContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OperatorPowerContext operatorPower() {
			return getRuleContext(OperatorPowerContext.class,0);
		}
		public ExpPowContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpPow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpPow(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpPow(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpNilContext extends ExpContext {
		public ExpNilContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpNil(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpNil(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpNil(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpAddSubContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public OperatorAddSubContext operatorAddSub() {
			return getRuleContext(OperatorAddSubContext.class,0);
		}
		public ExpAddSubContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterExpAddSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitExpAddSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitExpAddSub(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		return exp(0);
	}

	private ExpContext exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpContext _localctx = new ExpContext(_ctx, _parentState);
		ExpContext _prevctx = _localctx;
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_exp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__22:
				{
				_localctx = new ExpNilContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(223);
				match(T__22);
				}
				break;
			case T__23:
				{
				_localctx = new ExpFalseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(224);
				match(T__23);
				}
				break;
			case T__24:
				{
				_localctx = new ExpTrueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(225);
				match(T__24);
				}
				break;
			case INT:
			case HEX:
			case FLOAT:
			case HEX_FLOAT:
				{
				_localctx = new ExpNumberContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(226);
				number();
				}
				break;
			case NORMALSTRING:
			case CHARSTRING:
			case LONGSTRING:
				{
				_localctx = new ExpStringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(227);
				string();
				}
				break;
			case T__25:
				{
				_localctx = new ExpThreeDotsContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(228);
				match(T__25);
				}
				break;
			case T__12:
				{
				_localctx = new ExpFuncDefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(229);
				functiondef();
				}
				break;
			case T__26:
			case NAME:
				{
				_localctx = new ExpPrefixContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(230);
				prefixexp();
				}
				break;
			case T__30:
				{
				_localctx = new ExpTableCtorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(231);
				tableconstructor();
				}
				break;
			case T__42:
			case T__49:
			case T__52:
			case T__53:
				{
				_localctx = new ExpUnaryContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(232);
				operatorUnary();
				setState(233);
				exp(8);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(271);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(269);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
					case 1:
						{
						_localctx = new ExpPowContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(237);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(238);
						operatorPower();
						setState(239);
						exp(9);
						}
						break;
					case 2:
						{
						_localctx = new ExpMulDivModContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(241);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(242);
						operatorMulDivMod();
						setState(243);
						exp(8);
						}
						break;
					case 3:
						{
						_localctx = new ExpAddSubContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(245);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(246);
						operatorAddSub();
						setState(247);
						exp(7);
						}
						break;
					case 4:
						{
						_localctx = new ExpStrcatContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(249);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(250);
						operatorStrcat();
						setState(251);
						exp(5);
						}
						break;
					case 5:
						{
						_localctx = new ExpCmpContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(253);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(254);
						operatorComparison();
						setState(255);
						exp(5);
						}
						break;
					case 6:
						{
						_localctx = new ExpAndContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(257);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(258);
						operatorAnd();
						setState(259);
						exp(4);
						}
						break;
					case 7:
						{
						_localctx = new ExpOrContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(261);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(262);
						operatorOr();
						setState(263);
						exp(3);
						}
						break;
					case 8:
						{
						_localctx = new ExpBitwiseContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(265);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(266);
						operatorBitwise();
						setState(267);
						exp(2);
						}
						break;
					}
					} 
				}
				setState(273);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PrefixexpContext extends ParserRuleContext {
		public VarOrExpContext varOrExp() {
			return getRuleContext(VarOrExpContext.class,0);
		}
		public List<NameAndArgsContext> nameAndArgs() {
			return getRuleContexts(NameAndArgsContext.class);
		}
		public NameAndArgsContext nameAndArgs(int i) {
			return getRuleContext(NameAndArgsContext.class,i);
		}
		public PrefixexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterPrefixexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitPrefixexp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitPrefixexp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixexpContext prefixexp() throws RecognitionException {
		PrefixexpContext _localctx = new PrefixexpContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_prefixexp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			varOrExp();
			setState(278);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(275);
					nameAndArgs();
					}
					} 
				}
				setState(280);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctioncallContext extends ParserRuleContext {
		public VarOrExpContext varOrExp() {
			return getRuleContext(VarOrExpContext.class,0);
		}
		public List<NameAndArgsContext> nameAndArgs() {
			return getRuleContexts(NameAndArgsContext.class);
		}
		public NameAndArgsContext nameAndArgs(int i) {
			return getRuleContext(NameAndArgsContext.class,i);
		}
		public FunctioncallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functioncall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFunctioncall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFunctioncall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFunctioncall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctioncallContext functioncall() throws RecognitionException {
		FunctioncallContext _localctx = new FunctioncallContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_functioncall);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(281);
			varOrExp();
			setState(283); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(282);
					nameAndArgs();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(285); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarOrExpContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public VarOrExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varOrExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterVarOrExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitVarOrExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitVarOrExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarOrExpContext varOrExp() throws RecognitionException {
		VarOrExpContext _localctx = new VarOrExpContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_varOrExp);
		try {
			setState(292);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(287);
				var();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(288);
				match(T__26);
				setState(289);
				exp(0);
				setState(290);
				match(T__27);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<VarSuffixContext> varSuffix() {
			return getRuleContexts(VarSuffixContext.class);
		}
		public VarSuffixContext varSuffix(int i) {
			return getRuleContext(VarSuffixContext.class,i);
		}
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_var);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				{
				setState(294);
				match(NAME);
				}
				break;
			case T__26:
				{
				setState(295);
				match(T__26);
				setState(296);
				exp(0);
				setState(297);
				match(T__27);
				setState(298);
				varSuffix();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(305);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(302);
					varSuffix();
					}
					} 
				}
				setState(307);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarSuffixContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public List<NameAndArgsContext> nameAndArgs() {
			return getRuleContexts(NameAndArgsContext.class);
		}
		public NameAndArgsContext nameAndArgs(int i) {
			return getRuleContext(NameAndArgsContext.class,i);
		}
		public VarSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varSuffix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterVarSuffix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitVarSuffix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitVarSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarSuffixContext varSuffix() throws RecognitionException {
		VarSuffixContext _localctx = new VarSuffixContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_varSuffix);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(311);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__26) | (1L << T__30) | (1L << NORMALSTRING) | (1L << CHARSTRING) | (1L << LONGSTRING))) != 0)) {
				{
				{
				setState(308);
				nameAndArgs();
				}
				}
				setState(313);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(320);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__28:
				{
				setState(314);
				match(T__28);
				setState(315);
				exp(0);
				setState(316);
				match(T__29);
				}
				break;
			case T__20:
				{
				setState(318);
				match(T__20);
				setState(319);
				match(NAME);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameAndArgsContext extends ParserRuleContext {
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public NameAndArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nameAndArgs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterNameAndArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitNameAndArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitNameAndArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameAndArgsContext nameAndArgs() throws RecognitionException {
		NameAndArgsContext _localctx = new NameAndArgsContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_nameAndArgs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__21) {
				{
				setState(322);
				match(T__21);
				setState(323);
				match(NAME);
				}
			}

			setState(326);
			args();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgsContext extends ParserRuleContext {
		public ExplistContext explist() {
			return getRuleContext(ExplistContext.class,0);
		}
		public TableconstructorContext tableconstructor() {
			return getRuleContext(TableconstructorContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsContext args() throws RecognitionException {
		ArgsContext _localctx = new ArgsContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_args);
		int _la;
		try {
			setState(335);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__26:
				enterOuterAlt(_localctx, 1);
				{
				setState(328);
				match(T__26);
				setState(330);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__30) | (1L << T__42) | (1L << T__49) | (1L << T__52) | (1L << T__53) | (1L << NAME) | (1L << NORMALSTRING) | (1L << CHARSTRING) | (1L << LONGSTRING) | (1L << INT) | (1L << HEX) | (1L << FLOAT) | (1L << HEX_FLOAT))) != 0)) {
					{
					setState(329);
					explist();
					}
				}

				setState(332);
				match(T__27);
				}
				break;
			case T__30:
				enterOuterAlt(_localctx, 2);
				{
				setState(333);
				tableconstructor();
				}
				break;
			case NORMALSTRING:
			case CHARSTRING:
			case LONGSTRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(334);
				string();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctiondefContext extends ParserRuleContext {
		public FuncbodyContext funcbody() {
			return getRuleContext(FuncbodyContext.class,0);
		}
		public FunctiondefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functiondef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFunctiondef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFunctiondef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFunctiondef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctiondefContext functiondef() throws RecognitionException {
		FunctiondefContext _localctx = new FunctiondefContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_functiondef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337);
			match(T__12);
			setState(338);
			funcbody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncbodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParlistContext parlist() {
			return getRuleContext(ParlistContext.class,0);
		}
		public FuncbodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcbody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFuncbody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFuncbody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFuncbody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncbodyContext funcbody() throws RecognitionException {
		FuncbodyContext _localctx = new FuncbodyContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_funcbody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(340);
			match(T__26);
			setState(342);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__25 || _la==NAME) {
				{
				setState(341);
				parlist();
				}
			}

			setState(344);
			match(T__27);
			setState(345);
			block();
			setState(346);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParlistContext extends ParserRuleContext {
		public NamelistContext namelist() {
			return getRuleContext(NamelistContext.class,0);
		}
		public ParlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterParlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitParlist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitParlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParlistContext parlist() throws RecognitionException {
		ParlistContext _localctx = new ParlistContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_parlist);
		int _la;
		try {
			setState(354);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(348);
				namelist();
				setState(351);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__10) {
					{
					setState(349);
					match(T__10);
					setState(350);
					match(T__25);
					}
				}

				}
				break;
			case T__25:
				enterOuterAlt(_localctx, 2);
				{
				setState(353);
				match(T__25);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TableconstructorContext extends ParserRuleContext {
		public FieldlistContext fieldlist() {
			return getRuleContext(FieldlistContext.class,0);
		}
		public TableconstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableconstructor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterTableconstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitTableconstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitTableconstructor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableconstructorContext tableconstructor() throws RecognitionException {
		TableconstructorContext _localctx = new TableconstructorContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_tableconstructor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(356);
			match(T__30);
			setState(358);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__28) | (1L << T__30) | (1L << T__42) | (1L << T__49) | (1L << T__52) | (1L << T__53) | (1L << NAME) | (1L << NORMALSTRING) | (1L << CHARSTRING) | (1L << LONGSTRING) | (1L << INT) | (1L << HEX) | (1L << FLOAT) | (1L << HEX_FLOAT))) != 0)) {
				{
				setState(357);
				fieldlist();
				}
			}

			setState(360);
			match(T__31);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldlistContext extends ParserRuleContext {
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public List<FieldsepContext> fieldsep() {
			return getRuleContexts(FieldsepContext.class);
		}
		public FieldsepContext fieldsep(int i) {
			return getRuleContext(FieldsepContext.class,i);
		}
		public FieldlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldlist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFieldlist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFieldlist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFieldlist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldlistContext fieldlist() throws RecognitionException {
		FieldlistContext _localctx = new FieldlistContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_fieldlist);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			field();
			setState(368);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(363);
					fieldsep();
					setState(364);
					field();
					}
					} 
				}
				setState(370);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			}
			setState(372);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0 || _la==T__10) {
				{
				setState(371);
				fieldsep();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode NAME() { return getToken(LuaParser.NAME, 0); }
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_field);
		try {
			setState(384);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(374);
				match(T__28);
				setState(375);
				exp(0);
				setState(376);
				match(T__29);
				setState(377);
				match(T__1);
				setState(378);
				exp(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(380);
				match(NAME);
				setState(381);
				match(T__1);
				setState(382);
				exp(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(383);
				exp(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldsepContext extends ParserRuleContext {
		public FieldsepContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldsep; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterFieldsep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitFieldsep(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitFieldsep(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldsepContext fieldsep() throws RecognitionException {
		FieldsepContext _localctx = new FieldsepContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_fieldsep);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(386);
			_la = _input.LA(1);
			if ( !(_la==T__0 || _la==T__10) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorOrContext extends ParserRuleContext {
		public OperatorOrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorOr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorOr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorOrContext operatorOr() throws RecognitionException {
		OperatorOrContext _localctx = new OperatorOrContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_operatorOr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(388);
			match(T__32);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorAndContext extends ParserRuleContext {
		public OperatorAndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorAnd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorAndContext operatorAnd() throws RecognitionException {
		OperatorAndContext _localctx = new OperatorAndContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_operatorAnd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			match(T__33);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorComparisonContext extends ParserRuleContext {
		public OperatorComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorComparison; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorComparisonContext operatorComparison() throws RecognitionException {
		OperatorComparisonContext _localctx = new OperatorComparisonContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_operatorComparison);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(392);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorStrcatContext extends ParserRuleContext {
		public OperatorStrcatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorStrcat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorStrcat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorStrcat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorStrcat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorStrcatContext operatorStrcat() throws RecognitionException {
		OperatorStrcatContext _localctx = new OperatorStrcatContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_operatorStrcat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(394);
			match(T__40);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorAddSubContext extends ParserRuleContext {
		public OperatorAddSubContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorAddSub; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorAddSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorAddSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorAddSub(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorAddSubContext operatorAddSub() throws RecognitionException {
		OperatorAddSubContext _localctx = new OperatorAddSubContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_operatorAddSub);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(396);
			_la = _input.LA(1);
			if ( !(_la==T__41 || _la==T__42) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorMulDivModContext extends ParserRuleContext {
		public OperatorMulDivModContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorMulDivMod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorMulDivMod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorMulDivMod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorMulDivMod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorMulDivModContext operatorMulDivMod() throws RecognitionException {
		OperatorMulDivModContext _localctx = new OperatorMulDivModContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_operatorMulDivMod);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(398);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorBitwiseContext extends ParserRuleContext {
		public OperatorBitwiseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorBitwise; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorBitwise(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorBitwise(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorBitwise(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorBitwiseContext operatorBitwise() throws RecognitionException {
		OperatorBitwiseContext _localctx = new OperatorBitwiseContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_operatorBitwise);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(400);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorUnaryContext extends ParserRuleContext {
		public OperatorUnaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorUnary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorUnary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorUnaryContext operatorUnary() throws RecognitionException {
		OperatorUnaryContext _localctx = new OperatorUnaryContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_operatorUnary);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__42) | (1L << T__49) | (1L << T__52) | (1L << T__53))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorPowerContext extends ParserRuleContext {
		public OperatorPowerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorPower; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterOperatorPower(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitOperatorPower(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitOperatorPower(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorPowerContext operatorPower() throws RecognitionException {
		OperatorPowerContext _localctx = new OperatorPowerContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_operatorPower);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(404);
			match(T__54);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(LuaParser.INT, 0); }
		public TerminalNode HEX() { return getToken(LuaParser.HEX, 0); }
		public TerminalNode FLOAT() { return getToken(LuaParser.FLOAT, 0); }
		public TerminalNode HEX_FLOAT() { return getToken(LuaParser.HEX_FLOAT, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_number);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(406);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << HEX) | (1L << FLOAT) | (1L << HEX_FLOAT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public TerminalNode NORMALSTRING() { return getToken(LuaParser.NORMALSTRING, 0); }
		public TerminalNode CHARSTRING() { return getToken(LuaParser.CHARSTRING, 0); }
		public TerminalNode LONGSTRING() { return getToken(LuaParser.LONGSTRING, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LuaListener ) ((LuaListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof LuaVisitor ) return ((LuaVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(408);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NORMALSTRING) | (1L << CHARSTRING) | (1L << LONGSTRING))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 12:
			return exp_sempred((ExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 9);
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 5);
		case 4:
			return precpred(_ctx, 4);
		case 5:
			return precpred(_ctx, 3);
		case 6:
			return precpred(_ctx, 2);
		case 7:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3E\u019d\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\3\2\3\3\7\3S\n\3\f"+
		"\3\16\3V\13\3\3\3\5\3Y\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\u0081\n\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\5\4\u009b\n\4\5\4\u009d\n\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3"+
		"\6\3\6\7\6\u00a9\n\6\f\6\16\6\u00ac\13\6\3\7\3\7\5\7\u00b0\n\7\3\b\3\b"+
		"\5\b\u00b4\n\b\3\b\5\b\u00b7\n\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\7\n\u00c0"+
		"\n\n\f\n\16\n\u00c3\13\n\3\n\3\n\5\n\u00c7\n\n\3\13\3\13\3\13\7\13\u00cc"+
		"\n\13\f\13\16\13\u00cf\13\13\3\f\3\f\3\f\7\f\u00d4\n\f\f\f\16\f\u00d7"+
		"\13\f\3\r\3\r\3\r\7\r\u00dc\n\r\f\r\16\r\u00df\13\r\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00ee\n\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\7\16\u0110\n\16\f\16\16\16\u0113\13\16\3\17\3\17\7\17\u0117"+
		"\n\17\f\17\16\17\u011a\13\17\3\20\3\20\6\20\u011e\n\20\r\20\16\20\u011f"+
		"\3\21\3\21\3\21\3\21\3\21\5\21\u0127\n\21\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\5\22\u012f\n\22\3\22\7\22\u0132\n\22\f\22\16\22\u0135\13\22\3\23\7\23"+
		"\u0138\n\23\f\23\16\23\u013b\13\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23"+
		"\u0143\n\23\3\24\3\24\5\24\u0147\n\24\3\24\3\24\3\25\3\25\5\25\u014d\n"+
		"\25\3\25\3\25\3\25\5\25\u0152\n\25\3\26\3\26\3\26\3\27\3\27\5\27\u0159"+
		"\n\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\5\30\u0162\n\30\3\30\5\30\u0165"+
		"\n\30\3\31\3\31\5\31\u0169\n\31\3\31\3\31\3\32\3\32\3\32\3\32\7\32\u0171"+
		"\n\32\f\32\16\32\u0174\13\32\3\32\5\32\u0177\n\32\3\33\3\33\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u0183\n\33\3\34\3\34\3\35\3\35\3\36"+
		"\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3"+
		"\'\2\3\32(\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\66"+
		"8:<>@BDFHJL\2\n\4\2\3\3\r\r\3\2%*\3\2,-\3\2.\61\3\2\62\66\5\2--\64\64"+
		"\678\3\2>A\3\2;=\2\u01b5\2N\3\2\2\2\4T\3\2\2\2\6\u009c\3\2\2\2\b\u009e"+
		"\3\2\2\2\n\u00aa\3\2\2\2\f\u00af\3\2\2\2\16\u00b1\3\2\2\2\20\u00b8\3\2"+
		"\2\2\22\u00bc\3\2\2\2\24\u00c8\3\2\2\2\26\u00d0\3\2\2\2\30\u00d8\3\2\2"+
		"\2\32\u00ed\3\2\2\2\34\u0114\3\2\2\2\36\u011b\3\2\2\2 \u0126\3\2\2\2\""+
		"\u012e\3\2\2\2$\u0139\3\2\2\2&\u0146\3\2\2\2(\u0151\3\2\2\2*\u0153\3\2"+
		"\2\2,\u0156\3\2\2\2.\u0164\3\2\2\2\60\u0166\3\2\2\2\62\u016c\3\2\2\2\64"+
		"\u0182\3\2\2\2\66\u0184\3\2\2\28\u0186\3\2\2\2:\u0188\3\2\2\2<\u018a\3"+
		"\2\2\2>\u018c\3\2\2\2@\u018e\3\2\2\2B\u0190\3\2\2\2D\u0192\3\2\2\2F\u0194"+
		"\3\2\2\2H\u0196\3\2\2\2J\u0198\3\2\2\2L\u019a\3\2\2\2NO\5\4\3\2OP\7\2"+
		"\2\3P\3\3\2\2\2QS\5\6\4\2RQ\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2UX\3"+
		"\2\2\2VT\3\2\2\2WY\5\16\b\2XW\3\2\2\2XY\3\2\2\2Y\5\3\2\2\2Z\u009d\7\3"+
		"\2\2[\\\5\24\13\2\\]\7\4\2\2]^\5\30\r\2^\u009d\3\2\2\2_\u009d\5\36\20"+
		"\2`\u009d\5\20\t\2a\u009d\7\5\2\2bc\7\6\2\2c\u009d\7:\2\2de\7\7\2\2ef"+
		"\5\4\3\2fg\7\b\2\2g\u009d\3\2\2\2hi\7\t\2\2ij\5\32\16\2jk\7\7\2\2kl\5"+
		"\4\3\2lm\7\b\2\2m\u009d\3\2\2\2no\7\n\2\2op\5\4\3\2pq\7\13\2\2qr\5\32"+
		"\16\2r\u009d\3\2\2\2st\5\b\5\2tu\5\n\6\2uv\5\f\7\2vw\7\b\2\2w\u009d\3"+
		"\2\2\2xy\7\f\2\2yz\7:\2\2z{\7\4\2\2{|\5\32\16\2|}\7\r\2\2}\u0080\5\32"+
		"\16\2~\177\7\r\2\2\177\u0081\5\32\16\2\u0080~\3\2\2\2\u0080\u0081\3\2"+
		"\2\2\u0081\u0082\3\2\2\2\u0082\u0083\7\7\2\2\u0083\u0084\5\4\3\2\u0084"+
		"\u0085\7\b\2\2\u0085\u009d\3\2\2\2\u0086\u0087\7\f\2\2\u0087\u0088\5\26"+
		"\f\2\u0088\u0089\7\16\2\2\u0089\u008a\5\30\r\2\u008a\u008b\7\7\2\2\u008b"+
		"\u008c\5\4\3\2\u008c\u008d\7\b\2\2\u008d\u009d\3\2\2\2\u008e\u008f\7\17"+
		"\2\2\u008f\u0090\5\22\n\2\u0090\u0091\5,\27\2\u0091\u009d\3\2\2\2\u0092"+
		"\u0093\7\20\2\2\u0093\u0094\7\17\2\2\u0094\u0095\7:\2\2\u0095\u009d\5"+
		",\27\2\u0096\u0097\7\20\2\2\u0097\u009a\5\26\f\2\u0098\u0099\7\4\2\2\u0099"+
		"\u009b\5\30\r\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009d\3"+
		"\2\2\2\u009cZ\3\2\2\2\u009c[\3\2\2\2\u009c_\3\2\2\2\u009c`\3\2\2\2\u009c"+
		"a\3\2\2\2\u009cb\3\2\2\2\u009cd\3\2\2\2\u009ch\3\2\2\2\u009cn\3\2\2\2"+
		"\u009cs\3\2\2\2\u009cx\3\2\2\2\u009c\u0086\3\2\2\2\u009c\u008e\3\2\2\2"+
		"\u009c\u0092\3\2\2\2\u009c\u0096\3\2\2\2\u009d\7\3\2\2\2\u009e\u009f\7"+
		"\21\2\2\u009f\u00a0\5\32\16\2\u00a0\u00a1\7\22\2\2\u00a1\u00a2\5\4\3\2"+
		"\u00a2\t\3\2\2\2\u00a3\u00a4\7\23\2\2\u00a4\u00a5\5\32\16\2\u00a5\u00a6"+
		"\7\22\2\2\u00a6\u00a7\5\4\3\2\u00a7\u00a9\3\2\2\2\u00a8\u00a3\3\2\2\2"+
		"\u00a9\u00ac\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\13"+
		"\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00ae\7\24\2\2\u00ae\u00b0\5\4\3\2"+
		"\u00af\u00ad\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\r\3\2\2\2\u00b1\u00b3\7"+
		"\25\2\2\u00b2\u00b4\5\30\r\2\u00b3\u00b2\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4"+
		"\u00b6\3\2\2\2\u00b5\u00b7\7\3\2\2\u00b6\u00b5\3\2\2\2\u00b6\u00b7\3\2"+
		"\2\2\u00b7\17\3\2\2\2\u00b8\u00b9\7\26\2\2\u00b9\u00ba\7:\2\2\u00ba\u00bb"+
		"\7\26\2\2\u00bb\21\3\2\2\2\u00bc\u00c1\7:\2\2\u00bd\u00be\7\27\2\2\u00be"+
		"\u00c0\7:\2\2\u00bf\u00bd\3\2\2\2\u00c0\u00c3\3\2\2\2\u00c1\u00bf\3\2"+
		"\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c6\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c4"+
		"\u00c5\7\30\2\2\u00c5\u00c7\7:\2\2\u00c6\u00c4\3\2\2\2\u00c6\u00c7\3\2"+
		"\2\2\u00c7\23\3\2\2\2\u00c8\u00cd\5\"\22\2\u00c9\u00ca\7\r\2\2\u00ca\u00cc"+
		"\5\"\22\2\u00cb\u00c9\3\2\2\2\u00cc\u00cf\3\2\2\2\u00cd\u00cb\3\2\2\2"+
		"\u00cd\u00ce\3\2\2\2\u00ce\25\3\2\2\2\u00cf\u00cd\3\2\2\2\u00d0\u00d5"+
		"\7:\2\2\u00d1\u00d2\7\r\2\2\u00d2\u00d4\7:\2\2\u00d3\u00d1\3\2\2\2\u00d4"+
		"\u00d7\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\27\3\2\2"+
		"\2\u00d7\u00d5\3\2\2\2\u00d8\u00dd\5\32\16\2\u00d9\u00da\7\r\2\2\u00da"+
		"\u00dc\5\32\16\2\u00db\u00d9\3\2\2\2\u00dc\u00df\3\2\2\2\u00dd\u00db\3"+
		"\2\2\2\u00dd\u00de\3\2\2\2\u00de\31\3\2\2\2\u00df\u00dd\3\2\2\2\u00e0"+
		"\u00e1\b\16\1\2\u00e1\u00ee\7\31\2\2\u00e2\u00ee\7\32\2\2\u00e3\u00ee"+
		"\7\33\2\2\u00e4\u00ee\5J&\2\u00e5\u00ee\5L\'\2\u00e6\u00ee\7\34\2\2\u00e7"+
		"\u00ee\5*\26\2\u00e8\u00ee\5\34\17\2\u00e9\u00ee\5\60\31\2\u00ea\u00eb"+
		"\5F$\2\u00eb\u00ec\5\32\16\n\u00ec\u00ee\3\2\2\2\u00ed\u00e0\3\2\2\2\u00ed"+
		"\u00e2\3\2\2\2\u00ed\u00e3\3\2\2\2\u00ed\u00e4\3\2\2\2\u00ed\u00e5\3\2"+
		"\2\2\u00ed\u00e6\3\2\2\2\u00ed\u00e7\3\2\2\2\u00ed\u00e8\3\2\2\2\u00ed"+
		"\u00e9\3\2\2\2\u00ed\u00ea\3\2\2\2\u00ee\u0111\3\2\2\2\u00ef\u00f0\f\13"+
		"\2\2\u00f0\u00f1\5H%\2\u00f1\u00f2\5\32\16\13\u00f2\u0110\3\2\2\2\u00f3"+
		"\u00f4\f\t\2\2\u00f4\u00f5\5B\"\2\u00f5\u00f6\5\32\16\n\u00f6\u0110\3"+
		"\2\2\2\u00f7\u00f8\f\b\2\2\u00f8\u00f9\5@!\2\u00f9\u00fa\5\32\16\t\u00fa"+
		"\u0110\3\2\2\2\u00fb\u00fc\f\7\2\2\u00fc\u00fd\5> \2\u00fd\u00fe\5\32"+
		"\16\7\u00fe\u0110\3\2\2\2\u00ff\u0100\f\6\2\2\u0100\u0101\5<\37\2\u0101"+
		"\u0102\5\32\16\7\u0102\u0110\3\2\2\2\u0103\u0104\f\5\2\2\u0104\u0105\5"+
		":\36\2\u0105\u0106\5\32\16\6\u0106\u0110\3\2\2\2\u0107\u0108\f\4\2\2\u0108"+
		"\u0109\58\35\2\u0109\u010a\5\32\16\5\u010a\u0110\3\2\2\2\u010b\u010c\f"+
		"\3\2\2\u010c\u010d\5D#\2\u010d\u010e\5\32\16\4\u010e\u0110\3\2\2\2\u010f"+
		"\u00ef\3\2\2\2\u010f\u00f3\3\2\2\2\u010f\u00f7\3\2\2\2\u010f\u00fb\3\2"+
		"\2\2\u010f\u00ff\3\2\2\2\u010f\u0103\3\2\2\2\u010f\u0107\3\2\2\2\u010f"+
		"\u010b\3\2\2\2\u0110\u0113\3\2\2\2\u0111\u010f\3\2\2\2\u0111\u0112\3\2"+
		"\2\2\u0112\33\3\2\2\2\u0113\u0111\3\2\2\2\u0114\u0118\5 \21\2\u0115\u0117"+
		"\5&\24\2\u0116\u0115\3\2\2\2\u0117\u011a\3\2\2\2\u0118\u0116\3\2\2\2\u0118"+
		"\u0119\3\2\2\2\u0119\35\3\2\2\2\u011a\u0118\3\2\2\2\u011b\u011d\5 \21"+
		"\2\u011c\u011e\5&\24\2\u011d\u011c\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u011d"+
		"\3\2\2\2\u011f\u0120\3\2\2\2\u0120\37\3\2\2\2\u0121\u0127\5\"\22\2\u0122"+
		"\u0123\7\35\2\2\u0123\u0124\5\32\16\2\u0124\u0125\7\36\2\2\u0125\u0127"+
		"\3\2\2\2\u0126\u0121\3\2\2\2\u0126\u0122\3\2\2\2\u0127!\3\2\2\2\u0128"+
		"\u012f\7:\2\2\u0129\u012a\7\35\2\2\u012a\u012b\5\32\16\2\u012b\u012c\7"+
		"\36\2\2\u012c\u012d\5$\23\2\u012d\u012f\3\2\2\2\u012e\u0128\3\2\2\2\u012e"+
		"\u0129\3\2\2\2\u012f\u0133\3\2\2\2\u0130\u0132\5$\23\2\u0131\u0130\3\2"+
		"\2\2\u0132\u0135\3\2\2\2\u0133\u0131\3\2\2\2\u0133\u0134\3\2\2\2\u0134"+
		"#\3\2\2\2\u0135\u0133\3\2\2\2\u0136\u0138\5&\24\2\u0137\u0136\3\2\2\2"+
		"\u0138\u013b\3\2\2\2\u0139\u0137\3\2\2\2\u0139\u013a\3\2\2\2\u013a\u0142"+
		"\3\2\2\2\u013b\u0139\3\2\2\2\u013c\u013d\7\37\2\2\u013d\u013e\5\32\16"+
		"\2\u013e\u013f\7 \2\2\u013f\u0143\3\2\2\2\u0140\u0141\7\27\2\2\u0141\u0143"+
		"\7:\2\2\u0142\u013c\3\2\2\2\u0142\u0140\3\2\2\2\u0143%\3\2\2\2\u0144\u0145"+
		"\7\30\2\2\u0145\u0147\7:\2\2\u0146\u0144\3\2\2\2\u0146\u0147\3\2\2\2\u0147"+
		"\u0148\3\2\2\2\u0148\u0149\5(\25\2\u0149\'\3\2\2\2\u014a\u014c\7\35\2"+
		"\2\u014b\u014d\5\30\r\2\u014c\u014b\3\2\2\2\u014c\u014d\3\2\2\2\u014d"+
		"\u014e\3\2\2\2\u014e\u0152\7\36\2\2\u014f\u0152\5\60\31\2\u0150\u0152"+
		"\5L\'\2\u0151\u014a\3\2\2\2\u0151\u014f\3\2\2\2\u0151\u0150\3\2\2\2\u0152"+
		")\3\2\2\2\u0153\u0154\7\17\2\2\u0154\u0155\5,\27\2\u0155+\3\2\2\2\u0156"+
		"\u0158\7\35\2\2\u0157\u0159\5.\30\2\u0158\u0157\3\2\2\2\u0158\u0159\3"+
		"\2\2\2\u0159\u015a\3\2\2\2\u015a\u015b\7\36\2\2\u015b\u015c\5\4\3\2\u015c"+
		"\u015d\7\b\2\2\u015d-\3\2\2\2\u015e\u0161\5\26\f\2\u015f\u0160\7\r\2\2"+
		"\u0160\u0162\7\34\2\2\u0161\u015f\3\2\2\2\u0161\u0162\3\2\2\2\u0162\u0165"+
		"\3\2\2\2\u0163\u0165\7\34\2\2\u0164\u015e\3\2\2\2\u0164\u0163\3\2\2\2"+
		"\u0165/\3\2\2\2\u0166\u0168\7!\2\2\u0167\u0169\5\62\32\2\u0168\u0167\3"+
		"\2\2\2\u0168\u0169\3\2\2\2\u0169\u016a\3\2\2\2\u016a\u016b\7\"\2\2\u016b"+
		"\61\3\2\2\2\u016c\u0172\5\64\33\2\u016d\u016e\5\66\34\2\u016e\u016f\5"+
		"\64\33\2\u016f\u0171\3\2\2\2\u0170\u016d\3\2\2\2\u0171\u0174\3\2\2\2\u0172"+
		"\u0170\3\2\2\2\u0172\u0173\3\2\2\2\u0173\u0176\3\2\2\2\u0174\u0172\3\2"+
		"\2\2\u0175\u0177\5\66\34\2\u0176\u0175\3\2\2\2\u0176\u0177\3\2\2\2\u0177"+
		"\63\3\2\2\2\u0178\u0179\7\37\2\2\u0179\u017a\5\32\16\2\u017a\u017b\7 "+
		"\2\2\u017b\u017c\7\4\2\2\u017c\u017d\5\32\16\2\u017d\u0183\3\2\2\2\u017e"+
		"\u017f\7:\2\2\u017f\u0180\7\4\2\2\u0180\u0183\5\32\16\2\u0181\u0183\5"+
		"\32\16\2\u0182\u0178\3\2\2\2\u0182\u017e\3\2\2\2\u0182\u0181\3\2\2\2\u0183"+
		"\65\3\2\2\2\u0184\u0185\t\2\2\2\u0185\67\3\2\2\2\u0186\u0187\7#\2\2\u0187"+
		"9\3\2\2\2\u0188\u0189\7$\2\2\u0189;\3\2\2\2\u018a\u018b\t\3\2\2\u018b"+
		"=\3\2\2\2\u018c\u018d\7+\2\2\u018d?\3\2\2\2\u018e\u018f\t\4\2\2\u018f"+
		"A\3\2\2\2\u0190\u0191\t\5\2\2\u0191C\3\2\2\2\u0192\u0193\t\6\2\2\u0193"+
		"E\3\2\2\2\u0194\u0195\t\7\2\2\u0195G\3\2\2\2\u0196\u0197\79\2\2\u0197"+
		"I\3\2\2\2\u0198\u0199\t\b\2\2\u0199K\3\2\2\2\u019a\u019b\t\t\2\2\u019b"+
		"M\3\2\2\2$TX\u0080\u009a\u009c\u00aa\u00af\u00b3\u00b6\u00c1\u00c6\u00cd"+
		"\u00d5\u00dd\u00ed\u010f\u0111\u0118\u011f\u0126\u012e\u0133\u0139\u0142"+
		"\u0146\u014c\u0151\u0158\u0161\u0164\u0168\u0172\u0176\u0182";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}