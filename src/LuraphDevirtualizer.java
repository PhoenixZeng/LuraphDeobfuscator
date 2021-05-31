import ASTNodes.*;
import ASTNodes.Number;

import LuaVM.*;

import java.util.*;

public class LuraphDevirtualizer {
    private Node root;

    private Function
            vmRun,
            getByte,
            getDword,
            getBits,
            getFloat64,
            getInstruction,
            getString,
            decodeChunk,
            createWrapper,
            vmRunFunc;

    private String vmRunName = "vm_run";
    private String getByteName = "get_byte";
    private String getDwordName = "get_dword";
    private String getBitsName = "get_bits";
    private String getFloat64Name = "get_float64";
    private String getInstructionName = "get_instruction";
    private String getStringName = "get_string";
    private String decodeChunkName = "decode_chunk";
    private String createWrapperName = "create_wrapper";
    private String vmRunFuncName = "vm_run_func";

    // idx inside cache
    // remember string constants must be stripped
    private String constantTableIdx;
    // contains line numbers for debug info
    private String debugTableIdx;
    private String instructionTableIdx;
    private String prototypeTableIdx;

    // defer index in constant table
    private String constantIdx;

    // instruction data
    // also need to keep track of the instruction data bits
    // check if data bits change btw diff VMs
    private List<String> opcodeIdxs = new ArrayList<>();
    private List<String> aIdxs = new ArrayList<>();
    private List<String> bcIdxs = new ArrayList<>();
    private List<String> bxIdxs = new ArrayList<>();

    private String aIdx, bIdx, cIdx, bxIdx, sbxIdx;

    private String cacheName = "cache";
    private String constantsName = "constants";
    private String environmentName = "environment";
    private String stackName = "stack";
    private String varargszName = "varargsz";
    private String varsName = "vars";
    private String upvaluesName = "upvalues";
    private String handleReturnName = "handle_return";
    private String vmHandlerTableName = "vm_handler_table";
    private String vmOpcodeDispatcherTableName = "vm_opcode_handler_table";
    private String biasName = "sbx_bias";

    private String luraphVmString;

    private List<Byte> luraphBytes = new ArrayList<>();

    private int bytePos;

    // note: mapping indices start from 1 (as in Lua)
    // note: this corresponds to the massive handler table in lua code
    private HashMap<Integer, Function> vmHandlerMapping = new HashMap<>();

    // map opcode number to vmHandlerMapping
    private HashMap<Integer, Integer> vmOpcodeDispatcherMapping = new HashMap<>();

    // map Lua vm instruction to vmOpcodeDispatcherMapping
    // indices start from 1 just as in lua
    private HashMap<VMOp, Integer> instructionToOpcodeMapping = new HashMap<>();

    private HashMap<Integer, VMOp> opcodeToInstructionMapping = new HashMap<>();

    // key1 and key2 correspond to keys used for instruction and string decoding
    // we will have to figure out the correspondence by looking at the source code
    private String key1Name;
    private String key2Name;
    private double key1;
    private double key2;

    // also need to parse some locals in decoding chunk (3 free ones) to find out what they are

    // ABC params never switched

    public LuraphDevirtualizer(Node root) {
        this.root = root;
    }

    public LuaChunk process() {
        ASTOptimizerMgr mgr = new ASTOptimizerMgr(root);
        mgr.addRenamer(new RenamerPass1());
        mgr.optimize();

        getDecodeChunkData();

        mgr = new ASTOptimizerMgr(root);
        mgr.addRenamer(new RenamerPass2());
        mgr.optimize();

        mgr = new ASTOptimizerMgr(root);
        mgr.addRenamer(new RenamerPass3());
        mgr.optimize();

        identifyVmHandlers();

        verifyHandlers();

        setupOpcodeToInstructionMapping();

        luraphDecodeString();

        //dumpHandlerInfo();

        getInstructionOpcodeIndices();

        getInstructionAndStringDecryptionKeys();

        // todo: looks like one global is replaced with index 100000 in setglobal/getglobal
        // will have to translate that global into something

        return loadChunks();
    }

    LuaValue evaluateVmExpression(Expression expr) {
        LuaValue v = new LuaValue();

        if (expr instanceof FunctionCall) {
            String name = ((FunctionCall)expr).varOrExp.line();

            if (name.equals(getByteName)) {
                v.type = LuaValue.Type.NUMBER;
                v._nv = get_byte();
                return v;
            }
            else if (name.equals(getDwordName)) {
                v.type = LuaValue.Type.NUMBER;
                v._nv = get_dword();
                return v;
            }
            else if (name.equals(getFloat64Name)) {
                v.type = LuaValue.Type.NUMBER;
                v._nv = get_float64();
                return v;
            }
            else if (name.equals(getStringName)) {
                v.type = LuaValue.Type.STRING;
                String args = ((FunctionCall)expr).nameAndArgs.get(0).line();
                if (args.equals(key1Name)) {
                    v._sv = stripConstantsString(get_string(key1));
                    return v;
                }
                else if (args.equals(key2Name)) {
                    v._sv = stripConstantsString(get_string(key2));
                    return v;
                }
                else {
                    try {
                        double key = Double.parseDouble(args);
                        v._sv = stripConstantsString(get_string(key));
                        return v;
                    }
                    catch (NumberFormatException e) {
                        throw new RuntimeException("Unable to get string decryption key");
                    }
                }

            }
            else if (name.equals(getInstructionName)) {
                throw new RuntimeException("Unsupported location for instruction name");
            }
        }
        else if (expr instanceof BinaryExpression) {
            BinaryExpression e = (BinaryExpression)expr;

            if (e.operator == BinaryExpression.Operator.ADD) {
                LuaValue left = evaluateVmExpression(e.left);
                LuaValue right = evaluateVmExpression(e.right);

                if (left.type == LuaValue.Type.NUMBER && right.type == LuaValue.Type.NUMBER) {
                    v.type = LuaValue.Type.NUMBER;
                    v._nv = left._nv + right._nv;
                    return v;
                }
            }
            else if (e.operator == BinaryExpression.Operator.SUB) {
                LuaValue left = evaluateVmExpression(e.left);
                LuaValue right = evaluateVmExpression(e.right);

                if (left.type == LuaValue.Type.NUMBER && right.type == LuaValue.Type.NUMBER) {
                    v.type = LuaValue.Type.NUMBER;
                    v._nv = left._nv - right._nv;
                    return v;
                }
            }
        }
        else if (expr instanceof True) {
            v.type = LuaValue.Type.BOOLEAN;
            v._bv = true;
            return v;
        }
        else if (expr instanceof False) {
            v.type = LuaValue.Type.BOOLEAN;
            v._bv = false;
            return v;
        }
        else if (expr instanceof Number) {
            v.type = LuaValue.Type.NUMBER;
            v._nv = ((Number)expr).value;
            return v;
        }
        else if (expr instanceof TableConstructor) {
            v.type = LuaValue.Type.TABLE;
            return v;
        }
        else if (expr instanceof Nil) {
            v.type = LuaValue.Type.NIL;
            return v;
        }

        throw new RuntimeException("Could not evaluate expression");
    }

    private LuaValue evalHandleRedirectionHandlerExpression(VMInstruction instruction, Expression expr, HashMap<String, VMOperand> varMap) {
        LuaValue v = new LuaValue();

        if (expr instanceof Variable) {
            Variable var = (Variable)expr;

            v.type = LuaValue.Type.NUMBER;
            v._nv = instruction.getValue(varMap.get(var.symbol()));
            return v;
        }
        else if (expr instanceof BinaryExpression) {
            BinaryExpression bexpr = (BinaryExpression)expr;

            LuaValue left = evalHandleRedirectionHandlerExpression(instruction, bexpr.left, varMap);
            LuaValue right = evalHandleRedirectionHandlerExpression(instruction, bexpr.right, varMap);

            if (left.type != LuaValue.Type.NUMBER || right.type != LuaValue.Type.NUMBER) {
                throw new RuntimeException("Unexpected types in binary expression");
            }

            if (bexpr.operator == BinaryExpression.Operator.MOD) {
                v.type = LuaValue.Type.NUMBER;
                v._nv = modulus(left._nv, right._nv);
                return v;
            }
            else if (bexpr.operator == BinaryExpression.Operator.ADD) {
                v.type = LuaValue.Type.NUMBER;
                v._nv = left._nv + right._nv;
                return v;
            }
            else if (bexpr.operator == BinaryExpression.Operator.SUB) {
                v.type = LuaValue.Type.NUMBER;
                v._nv = left._nv - right._nv;
                return v;
            }
            else {
                throw new RuntimeException("Unexpected binary expression operator");
            }
        }
        else if (expr instanceof Number) {
            v.type = LuaValue.Type.NUMBER;
            v._nv = ((Number)expr).value;
            return v;
        }

        throw new RuntimeException("Unexpected expression in redirect handler");
    }

    // decrypt instruction if it defers into a redirection handler
    private void handleRedirectionHandlers(VMInstruction instruction) {
        Integer opcodeIndex = instructionToOpcodeMapping.get(instruction.opcode);
        Integer handlerIndex = vmOpcodeDispatcherMapping.get(opcodeIndex);

        Function fn = vmHandlerMapping.get(handlerIndex);

        HashMap<String, VMOperand> varMap = new HashMap<>();

        int i;

        for (i = 0; i < 5; i++) {
            LocalDeclare decl = (LocalDeclare)fn.block.stmts.get(i);

            String varName = decl.names.names.get(0).symbol();

            Expression expr = decl.exprs.exprs.get(0);

            if (expr instanceof BinaryExpression) {
                varMap.put(varName, VMOperand.sBx);
            }
            else {
                Variable var = (Variable)expr;

                String idx = var.suffixes.get(0).expOrName.line();

                if (idx.equals(aIdx)) {
                    varMap.put(varName, VMOperand.A);
                }
                else if (idx.equals(bIdx)) {
                    varMap.put(varName, VMOperand.B);
                }
                else if (idx.equals(cIdx)) {
                    varMap.put(varName, VMOperand.C);
                }
                else if (idx.equals(bxIdx)) {
                    varMap.put(varName, VMOperand.Bx);
                }
                else {
                    throw new RuntimeException("Unexpected local variable index");
                }
            }
        }

        for (; i < fn.block.stmts.size(); i++) {
            Statement node = fn.block.stmts.get(i);

            if (node instanceof If) {
                If stmt = (If)node;

                if (stmt.ifstmt.second.ret == null || !(stmt.ifstmt.second.ret.exprs.exprs.get(0) instanceof FunctionCall)) {
                    continue;
                }

                FunctionCall call = (FunctionCall)stmt.ifstmt.second.ret.exprs.exprs.get(0);

                if (call.varOrExp.symbol().equals(vmOpcodeDispatcherTableName)) {

                    // get the variable name being compared
                    BinaryExpression cmpExpr = (BinaryExpression)stmt.ifstmt.first;
                    String cmpName = ((Variable)(cmpExpr).left).symbol();

                    VMOperand cmpOperand = varMap.get(cmpName);

                    if (instruction.getValue(cmpOperand) == ((Number)cmpExpr.right).value) {
                        // handle redirection and recurse to handle multiple redirections

                        int redirectOpcode = (int)((Number)(((Variable)call.varOrExp).suffixes.get(0).expOrName)).value;

                        instruction.opcodeNum = redirectOpcode;
                        instruction.opcode = opcodeToInstructionMapping.get(redirectOpcode);

                        // handle decryption

                        TableConstructor tctor = (TableConstructor)((ExprList)call.nameAndArgs.get(0).args).exprs.get(0);

                        for (Pair<Expression, Expression> p : tctor.entries) {
                            String operandIdx = ((Number)p.first).toString();

                            LuaValue v = evalHandleRedirectionHandlerExpression(instruction, p.second, varMap);

                            if (v.type != LuaValue.Type.NUMBER) {
                                throw new RuntimeException("Expected number value");
                            }

                            if (operandIdx.equals(aIdx)) {
                                instruction.A = v._nv;
                            }
                            else if (operandIdx.equals(bIdx)) {
                                instruction.B = v._nv;
                            }
                            else if (operandIdx.equals(cIdx)) {
                                instruction.C = v._nv;
                            }
                            else if (operandIdx.equals(bxIdx)) {
                                instruction.Bx = v._nv;
                            }
                            else if (operandIdx.equals(sbxIdx)) {
                                instruction.sBx = v._nv;
                            }

                        }

                        handleRedirectionHandlers(instruction);
                    }

                }
            }
        }
    }

    private String stripConstantsString(String s) {
        Function stripStringFunction = (Function)createWrapper.first(Function.class);

        If stmt = (If)stripStringFunction.first(If.class);

        FunctionCall stringSub = (FunctionCall)((TableConstructor)stmt.ifstmt.second.ret.exprs.exprs.get(0)).entries.get(0).second;

        if (stringSub.varOrExp.line().equals("string.sub")) {
            ExprList exprs = (ExprList)stringSub.nameAndArgs.get(0).args;
            int numArgs = exprs.exprs.size();

            if (numArgs != 2) {
                throw new RuntimeException("Unexpected number of arguments to string.sub");
            }

            int index = (int)((Number)exprs.exprs.get(1)).value - 1;

            return s.substring(index);
        }

        return s;
    }

    private void populateChunkParams(LuaChunk chunk, HashMap<String, Double> chunkParams) {
        List<Node> lds = createWrapper.block.getChildren(LocalDeclare.class);

        // find 2 referenced params
        for (Node node : lds) {
            LocalDeclare ld = (LocalDeclare)node;

            Expression rhs = ld.exprs.exprs.get(0);

            if (rhs instanceof Variable) {
                Variable var = (Variable)rhs;

                if (var.name.symbol().equals(cacheName)) {
                    String sid = var.suffixes.get(0).expOrName.line();

                    // found number of args
                    if (chunkParams.containsKey(sid)) {
                        double value = chunkParams.get(sid);

                        chunk.numParams = value;

                        chunkParams.remove(sid);
                    }
                }
            }
            else if (rhs instanceof Number) {
                String srcValue = ((Number)rhs).toString();

                // found number of upvals
                if (chunkParams.containsKey(srcValue)) {
                    double value = chunkParams.get(srcValue);

                    chunk.numUpVals = value;

                    chunkParams.remove(srcValue);
                }
            }

            if (chunkParams.size() == 1) {
                break;
            }
        }

        // last contender is max stack size
        chunk.maxStackSize = chunkParams.entrySet().iterator().next().getValue();
    }

    private LuaChunk loadChunks() {
        List<Node> children = decodeChunk.block.getChildren();

        LuaChunk chunk = new LuaChunk();

        // keep track of variables
        HashMap<String, LuaValue> vals = new HashMap<>();

        // max stack size, num upvals, num args
        HashMap<String, Double> chunkParams = new HashMap<>();

        // emulate lua code
        for (int i = 0; i < children.size(); i++) {
            Node node = children.get(i);

            if (node instanceof LocalDeclare) {
                LocalDeclare ld = (LocalDeclare)node;
                if (ld.names.names.size() != 1)
                    throw new RuntimeException("Namelist unsupported in loadChunks");
                String lvalue = ld.names.names.get(0).symbol();
                vals.put(lvalue, evaluateVmExpression(ld.exprs.exprs.get(0)));
            }
            else if (node instanceof Assign) {
                Assign ld = (Assign)node;
                if (ld.vars.vars.size() != 1)
                    throw new RuntimeException("Namelist unsupported in loadChunks");

                // consume expression
                LuaValue v = evaluateVmExpression(ld.exprs.exprs.get(0));

                if (v.type != LuaValue.Type.NUMBER) {
                    throw new RuntimeException("LuaValue expected number while loading chunk");
                }

                Variable vr = (Variable)ld.vars.vars.get(0);
                String sid = vr.suffixes.get(0).expOrName.line();
                chunkParams.put(sid, v._nv);

            }
            else if (node instanceof ForStep) {
                // determine if we are loading constants, debug info, or instructions
                ForStep stmt = (ForStep)node;

                String vtoName = ((Variable)stmt.condition).symbol();
                int init = 1;
                int to = (int)vals.get(vtoName)._nv;

                if (stmt.block.getChildren(If.class).size() > 0) {
                    // loading constants

                    Block block = stmt.block;

                    List<Node> cc = block.getChildren();

                    for (int j = init; j <= to; j++) {
                        double key = -1;
                        for (int k = 0; k < cc.size(); k++) {
                            Node n = cc.get(k);

                            if (n instanceof LocalDeclare) {
                                LuaValue v = evaluateVmExpression(((LocalDeclare)n).exprs.exprs.get(0));
                                if (v.type == LuaValue.Type.NUMBER) {
                                    key = v._nv;
                                }
                            }
                            else if (n instanceof If) {
                                BinaryExpression e = (BinaryExpression)((If)n).ifstmt.first;

                                if (key == ((Number)e.right).value) {
                                    Assign assign = (Assign)((If)n).ifstmt.second.stmts.get(0);
                                    LuaValue v = evaluateVmExpression(assign.exprs.exprs.get(0));

                                    chunk.constants.add(v);
                                    //System.out.println("-- Constant: " + v);
                                    break;
                                }
                            }
                        }
                    }
                }
                else if (stmt.block.stmts.size() == 1 && stmt.block.stmts.get(0).line().contains(decodeChunkName)) {
                    // loading prototypes

                    for (int j = init; j <= to; j++) {
                        chunk.prototypes.add(loadChunks());
                    }
                }
                else if (stmt.block.stmts.size() == 1 && stmt.block.stmts.get(0).line().contains(getDwordName)) {
                    // loading debug info

                    for (int j = init; j <= to; j++) {
                        chunk.debugLines.add(get_dword());
                    }
                }
                else {
                    // loading instructions

                    // get key used by get_instr

                    FunctionCall call = (FunctionCall)stmt.block.first(FunctionCall.class);
                    String varName = call.nameAndArgs.get(0).args.symbol();
                    double key = 0.0;

                    if (varName.equals(key1Name)) {
                        key = key1;
                    }
                    else if (varName.equals(key2Name)) {
                        key = key2;
                    }
                    else {
                        throw new RuntimeException("Unknown encryption key used by get_instruction");
                    }

                    for (int j = init; j <= to; j++) {
                        double insnData = get_instruction(key);
                        VMInstruction insn = new VMInstruction();

                        for (Node stmtOps : stmt.block.stmts) {
                            if (stmtOps instanceof Assign) {
                                Assign assign = (Assign)stmtOps;

                                String idx = ((Variable)assign.vars.vars.get(0)).suffixes.get(0).expOrName.line();

                                if (!(assign.exprs.exprs.get(0) instanceof FunctionCall))
                                    continue;

                                FunctionCall getBitsCall = (FunctionCall)assign.exprs.exprs.get(0);

                                double ix = Double.parseDouble(((ExprList)getBitsCall.nameAndArgs.get(0).args).exprs.get(1).line());
                                double jx = Double.parseDouble(((ExprList)getBitsCall.nameAndArgs.get(0).args).exprs.get(2).line());

                                if (!getBitsCall.varOrExp.symbol().equals(getBitsName)) {
                                    throw new RuntimeException("Expected get bits call here");
                                }

                                if (idx.equals(aIdx)) {
                                    insn.A = get_bits(insnData, ix, jx);
                                }
                                else if (idx.equals(bIdx)) {
                                    insn.B = get_bits(insnData, ix, jx);
                                }
                                else if (idx.equals(cIdx)) {
                                    insn.C = get_bits(insnData, ix, jx);
                                }
                                else if (idx.equals(bxIdx)) {
                                    insn.Bx = get_bits(insnData, ix, jx);
                                    insn.sBx = insn.Bx - 131071.0;
                                }
                                else if (opcodeIdxs.contains(idx)) {
                                    // Lua code indices into table, so we add 1 to the opcode
                                    int val = (int)get_bits(insnData, ix, jx);
                                    insn.opcode = opcodeToInstructionMapping.get(val + 1);
                                    insn.opcodeNum = val;
                                    if (insn.opcode == null) {
                                        throw new RuntimeException("Bad operand");
                                    }
                                }
                            }
                        }

                        handleRedirectionHandlers(insn);
                        chunk.insns.add(insn);
                    }
                }
            }
            else if (node instanceof FunctionCall) {
                evaluateVmExpression((FunctionCall)node);
            }
        }

        populateChunkParams(chunk, chunkParams);

        return chunk;
    }

    private void getInstructionAndStringDecryptionKeys() {
        List<Node> children = vmRun.block.getChildren();

        int idx = -1;

        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) == decodeChunk) {
                idx = i;
                break;
            }
        }

        if (idx == -1)
            throw new RuntimeException("Could not find string and instruction decryption keys in VM err1");

        LocalDeclare l1 = (LocalDeclare)children.get(idx - 2);
        LocalDeclare l2 = (LocalDeclare)children.get(idx - 1);

        if (l1.names.names.size() != 1 || l2.names.names.size() != 1)
            throw new RuntimeException("Could not find string and instruction decryption keys in VM err2");

        key1Name = l1.names.names.get(0).symbol();
        key2Name = l2.names.names.get(0).symbol();

        String f1 = l1.exprs.exprs.get(0).line();
        String f2 = l2.exprs.exprs.get(0).line();

        if (!f1.equals(getByteName + "()") || !f2.equals(getByteName + "()"))
            throw new RuntimeException("Could not find string and instruction decryption keys in VM err3");

        key1 = get_byte();
        key2 = get_byte();
    }

    private void dumpHandlerInfo() {
        for (Map.Entry entry : instructionToOpcodeMapping.entrySet()) {
            System.out.println("-- OPCODE " + entry.getValue() + "=" + entry.getKey().toString());
            Function fn = vmHandlerMapping.get(vmOpcodeDispatcherMapping.get(entry.getValue()));

            Block block = new Block();
            block.stmts.add(fn);
            ASTSourceGenerator gen = new ASTSourceGenerator(block);
            // dont confuse opcodes with table entries
            System.out.println(gen.generate());
        }

        System.out.println();
    }

    private int getDecodeChunkConstantStatementIdx() {
        int idx = -1;
        int max = 0;

        for (int i = 0; i < decodeChunk.block.stmts.size(); i++) {
            Statement stmt = decodeChunk.block.stmts.get(i);

            if (stmt instanceof ForStep) {
                int count = stmt.count(If.class);

                if (count > max) {
                    count = max;
                    idx = i;
                }
            }
        }

        return idx;
    }

    private void getDecodeChunkConstantIndices() {
        int constantStmtIdx = getDecodeChunkConstantStatementIdx();

        ForStep constantStep = (ForStep)decodeChunk.block.stmts.get(constantStmtIdx);

        If stmt = (If)constantStep.block.first(If.class);
        Variable var = (Variable)stmt.ifstmt.second.first(Variable.class);
        constantIdx = var.suffixes.get(0).expOrName.line();

        Assign last = (Assign)constantStep.block.stmts.get(constantStep.block.stmts.size() - 1);
        constantTableIdx = ((Variable)last.first(Variable.class)).suffixes.get(0).expOrName.line();
    }

    private void getDecodeChunkDebugIndices() {
        for (Node stmt : decodeChunk.block.getChildren(ForStep.class)) {
            List<Node> children = ((ForStep)stmt).block.getChildren();

            if (children.size() == 1) {
                FunctionCall call = (FunctionCall)children.get(0).first(FunctionCall.class);
                if (call.varOrExp.line().equals(getDwordName)) {
                    Assign assign = (Assign)children.get(0);
                    debugTableIdx = ((Variable)assign.first(Variable.class)).suffixes.get(0).expOrName.line();
                }
            }
        }
    }

    private void getDecodeChunkInstructionIndicies() {
        for (Node stmt : decodeChunk.block.getChildren(ForStep.class)) {
            Block block = (Block)((ForStep)stmt).block;
            FunctionCall call = (FunctionCall)block.first(FunctionCall.class);
            if (call.varOrExp.line().equals(getInstructionName)) {
                List<Node> assign = block.getChildren(Assign.class);

                for (Node s : assign) {
                    FunctionCall gbitsCall = (FunctionCall)s.first(FunctionCall.class);
                    String idx = ((Variable)s.first(Variable.class)).suffixes.get(0).expOrName.line();

                    if (gbitsCall != null && gbitsCall.varOrExp.line().equals(getBitsName)) {
                        for (NameAndArgs nargs : gbitsCall.nameAndArgs) {
                            ExprList list = (ExprList)nargs.args;
                            double from = ((Number)list.exprs.get(1)).value;
                            double to = ((Number)list.exprs.get(2)).value;
                            int size = (int)(to - from + 1);

                            if (size == 6) {
                                opcodeIdxs.add(idx);
                            }
                            else if (size == 8){
                                aIdxs.add(idx);
                            }
                            else if (size == 9) {
                                bcIdxs.add(idx);
                            }
                            else if (size == 18) {
                                bxIdxs.add(idx);
                            }
                        }
                    }
                    else {
                        instructionTableIdx = idx;
                    }
                }
            }
        }
    }

    private void getDecodeChunkPrototypeIndicies() {
        for (Node stmt : decodeChunk.block.getChildren(ForStep.class)) {
            List<Node> children = ((ForStep)stmt).block.getChildren();

            if (children.size() == 1) {
                FunctionCall call = (FunctionCall)children.get(0).first(FunctionCall.class);
                if (call.varOrExp.line().equals(decodeChunkName)) {
                    Assign assign = (Assign)children.get(0);
                    prototypeTableIdx = ((Variable)assign.first(Variable.class)).suffixes.get(0).expOrName.line();
                }
            }
        }
    }

    private void getDecodeChunkData() {
        getDecodeChunkConstantIndices();
        getDecodeChunkDebugIndices();
        getDecodeChunkInstructionIndicies();
        getDecodeChunkPrototypeIndicies();
    }

    private void luraphDecodeString() {
        boolean dup = false;
        byte repeat = 0;

        bytePos = 0;

        for (int i = 0; i < luraphVmString.length(); i += 2) {
            int high = Character.digit(luraphVmString.charAt(i), 16);
            int low = Character.digit(luraphVmString.charAt(i + 1), 16);

            byte b = (byte)((high << 4) + low);

            // luraph constants
            if (luraphVmString.charAt(i + 1) == 71) {
                dup = true;
                repeat = (byte)high;
            }
            else {
                if (dup) {
                    dup = false;
                    for (int j = 0; j < repeat; j++) {
                        luraphBytes.add(b);
                    }
                }
                else {
                    luraphBytes.add(b);
                }
            }
        }
    }

    private double get_byte() {
        int b = luraphBytes.get(bytePos++) & 0xFF;

        return (double)b;
    }

    private double get_dword() {
        double a = get_byte(), b = get_byte(), c = get_byte(), d = get_byte();

        return ((((d * 1.6777216E7) + (c * 65536.0)) + (b * 256.0)) + a);
    }

    private double modulus(double value, double mod) {
        double val = value % mod;

        if (val < 0.0) {
            val = val + mod;
        }

        return val;
    }

    private double get_bits(double value, double i) {
        double mask = Math.pow(2.0, i - 1.0);

        double m = mask + mask;

        double val = modulus(value, m);

        if (mask <= val)
            return 1.0;
        else
            return 0.0;
    }

    private double get_bits(double value, double i, double j) {
        double it = 0.0;
        double res = 0.0;

        for (double k = i; k <= j; k += 1.0) {
            res = res + Math.pow(2.0, it) * get_bits(value, k);
            it += 1.0;
        }

        return res;
    }

    private double get_float64() {
        double a = get_dword(), b = get_dword();

        if (a == 0.0 && b == 0.0) {
            return 0.0;
        }

        return ((((-2.0 * get_bits(b, 32.0)) + 1.0) * (Math.pow(2.0, (get_bits(b, 21.0, 31.0) - 1023.0)))) *
                ((((get_bits(b, 1.0, 20.0) * 4.294967296E9) + a) / 4.503599627370496E15) + 1.0));
    }

    // always the same
    private double get_instruction(double key) {
        double arr1[] = { get_byte(), get_byte(), get_byte(), get_byte() };
        double keys[] = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };

        for (int i = 0; i < 8; i++) {
            keys[i] = get_bits(key, i + 1);
        }

        double arr2[] = { 0.0, 0.0, 0.0, 0.0 };

        for (int i = 0; i < 4; i++) {
            double a = 0.0, b = 0.0;

            for (int j = 0; j < 8; j++) {
                double c = get_bits(arr1[i], j + 1);

                if (keys[j] == 1.0) {
                    if (c == 1.0)
                        c = 0.0;
                    else
                        c = 1.0;
                }

                a = a + Math.pow(2.0, b) * c;
                b = b + 1.0;
            }

            arr2[i] = a;
        }

        return ((((arr2[3] * 1.6777216E7) + (arr2[2] * 65536.0)) + (arr2[1] * 256.0)) + arr2[0]);
    }

    private String get_string(double key) {
        StringBuilder sb = new StringBuilder();

        int stringSize = (int)get_dword();

        double[] arr0 = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

        for (int i = 0; i < 8; i++) {
            arr0[i] = get_bits(key, i + 1.0);
        }

        double raw[] = new double[stringSize];

        for (int i = 0; i < stringSize; i++) {
            raw[i] = get_byte();
        }

        for (int i = 0; i < stringSize; i++) {
            double a = 0.0, b = 0.0;

            for (int j = 0; j < 8; j++) {
                double c = get_bits(raw[i], j + 1);

                if (arr0[j] == 1.0) {
                    if (c == 1.0)
                        c = 0.0;
                    else
                        c = 1.0;
                }

                a = a + Math.pow(2.0, b) * c;
                b = b + 1.0;
            }

            sb.append((char)a);
        }

        return sb.toString();
    }

    private void getInstructionOpcodeIndices() throws RuntimeException {
        // USE RETURN! its ALWAYS present!!!
        if (!instructionToOpcodeMapping.containsKey(VMOp.RETURN)) {
            throw new RuntimeException("VM does not contain RETURN instruction. Critical error here... All Lua bytecode HAS at least ONE return!");
        }


        Function fn = purifyHandler(vmHandlerMapping.get(
                vmOpcodeDispatcherMapping.get(instructionToOpcodeMapping.get(VMOp.RETURN))));

        // get first if else stmt

        String varA = null, varB = null;

        List<Node> stmts = fn.block.getChildren();

        for (Node node : stmts) {
            if (!(node instanceof If))
                continue;

            If stmt = (If)node;

            if (stmt.elsestmt == null)
                continue;

            Assign assign = (Assign)stmt.elsestmt.stmts.get(0);
            BinaryExpression _Aexpr = (BinaryExpression)assign.exprs.exprs.get(0);

            BinaryExpression _Bexpr = (BinaryExpression)_Aexpr.left;

            Variable _A = (Variable)_Bexpr.left;

            Variable _B = (Variable)_Bexpr.right;

            varA = _A.line();
            varB = _B.line();
        }

        if (varA == null || varB == null) {
            // try to search for A + (B - 2) in outer scope
            for (Node node : stmts) {
                if (!(node instanceof LocalDeclare))
                    continue;

                LocalDeclare stmt = (LocalDeclare) node;

                if (stmt.exprs.exprs.size() == 1 && stmt.exprs.exprs.get(0) instanceof BinaryExpression) {
                    BinaryExpression expr1 = (BinaryExpression)stmt.exprs.exprs.get(0);
                    if (expr1.operator == BinaryExpression.Operator.SUB && expr1.right instanceof Number) {
                        if (((Number)expr1.right).value == 2.0) {
                            BinaryExpression expr2 = (BinaryExpression)expr1.left;
                            Variable _A = (Variable)expr2.left;

                            Variable _B = (Variable)expr2.right;

                            varA = _A.line();
                            varB = _B.line();

                        }
                    }
                }
            }
        }

        if (varA == null || varB == null)
            throw new RuntimeException("Could not find A or B idx");

        // extract A, B, C, Bx, sBx
        for (int i = 0; i < 5; i++) {
            LocalDeclare decl = (LocalDeclare)stmts.get(i);

            String str = decl.line();

            Expression expr = decl.exprs.exprs.get(0);

            if (expr instanceof Variable) {
                // A, B, C, Bx

                String idx = ((Variable) decl.exprs.exprs.get(0)).suffixes.get(0).expOrName.line();

                if (str.contains(varA)) {
                    aIdx = idx;
                } else if (str.contains(varB)) {
                    bIdx = idx;
                }
                else if (bcIdxs.contains(idx)) {
                    cIdx = idx;
                }
                else if (bxIdxs.contains(idx)) {
                    bxIdx = idx;
                }
            }
            else if (expr instanceof BinaryExpression) {
                // sBx
                BinaryExpression e = (BinaryExpression)expr;
                String idx = ((Variable)e.left).suffixes.get(0).expOrName.line();

                sbxIdx = idx;
            }
        }

        if (!sbxIdx.equals(bxIdx))
            throw new RuntimeException("sbxIdx should be equal to bxIdx");

    }

    private<K, V> boolean hasDuplicateValues(HashMap<K, V> map) {
        List<V> list = new ArrayList<V>();

        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (list.contains(entry.getValue())) {
                return false;
            }
            list.add(entry.getValue());
        }

        return false;
    }

    private void setupOpcodeToInstructionMapping() {
        for (HashMap.Entry<VMOp, Integer> entry : instructionToOpcodeMapping.entrySet()) {
            opcodeToInstructionMapping.put(entry.getValue(), entry.getKey());
        }
    }

    private void verifyHandlers() throws RuntimeException {
        if (instructionToOpcodeMapping.size() != vmOpcodeDispatcherMapping.size()) {
            throw new RuntimeException("Inconsistent opcode to opcode dispatcher sizes");
        }

        if (vmOpcodeDispatcherMapping.size() != vmHandlerMapping.size()) {
            throw new RuntimeException("Inconsistent opcode dispatcher to handler sizes");
        }

        if (hasDuplicateValues(vmOpcodeDispatcherMapping)) {
            throw new RuntimeException("Duplicates in opcode dispatcher");
        }

        if (hasDuplicateValues(vmHandlerMapping)) {
            throw new RuntimeException("Duplicates in handlers");
        }
    }

    // create purified representantion of handler removing all redirection if checks
    private Function purifyHandler(Function handler) {
        Function purified = handler.clone();

        ListIterator<Statement> it = purified.block.stmts.listIterator();
        while (it.hasNext()) {
            Statement stmt = it.next();

            if (stmt instanceof If) {
                FunctionCall call = (FunctionCall)stmt.first(FunctionCall.class);

                if (call != null && call.varOrExp.symbol().equals(vmOpcodeDispatcherTableName)) {
                    it.remove();
                }
            }
        }

        return purified;
    }

    private boolean matchAssign(Assign assign, String lhsVarName, String rhsVarName) {
        if (assign.vars.vars.size() == 1 && assign.exprs.exprs.size() == 1) {
            Variable lhs = (Variable)assign.vars.vars.get(0);
            Expression rhs = assign.exprs.exprs.get(0);
            if (rhs instanceof Variable) {
                if ((lhs.symbol().equals(lhsVarName) || lhsVarName.isEmpty()) &&
                        rhs.symbol().equals(rhsVarName) || rhsVarName.isEmpty()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matchAssign(Assign assign, String lhsVarName, Literal rhsLiteral) {
        if (assign.vars.vars.size() == 1 && assign.exprs.exprs.size() == 1) {
            Variable lhs = (Variable)assign.vars.vars.get(0);
            Expression rhs = assign.exprs.exprs.get(0);
            if (rhs instanceof Literal) {
                if ((lhs.symbol().equals(lhsVarName) || lhsVarName.isEmpty()) &&
                        rhs.line().equals(rhsLiteral.line())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matchBinOp(BinaryExpression expr, BinaryExpression.Operator op, String lhsVarName, Literal rhs) {
        if (expr.operator == op && expr.left instanceof Variable && expr.right instanceof Literal) {
            Variable var = (Variable)expr.left;
            Literal lit = (Literal)expr.right;

            if (var.symbol().equals(lhsVarName) || lhsVarName.isEmpty()) {
                if (rhs.line().equals(lit.line())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matchBinOp(BinaryExpression expr, BinaryExpression.Operator op, String lhsVarName, String rhsVarName) {
        if (expr.operator == op && expr.left instanceof Variable && expr.right instanceof Variable) {
            Variable var = (Variable)expr.left;
            Variable var2 = (Variable)expr.right;

            if (var.symbol().equals(lhsVarName) || lhsVarName.isEmpty()) {
                if (var2.symbol().equals(rhsVarName) || rhsVarName.isEmpty()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matchUnOp(UnaryExpression expr, UnaryExpression.Operator op, String name) {
        if (expr.op == op && expr.expr instanceof Variable) {
            Variable var = (Variable)expr.expr;

            if (var.symbol().equals(name) || name.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private boolean matchLoadConstantStack(If stmt) {
        if (stmt.elseifstmt.size() != 0)
            return false;

        if (stmt.ifstmt.first instanceof BinaryExpression &&
                matchBinOp((BinaryExpression)stmt.ifstmt.first, BinaryExpression.Operator.GT, "", new Number(255))) {
            if (stmt.ifstmt.second.stmts.size() == 1 && stmt.ifstmt.second.stmts.get(0) instanceof Assign) {
                if (matchAssign((Assign)stmt.ifstmt.second.stmts.get(0), "", constantsName)) {
                    if (stmt.elsestmt != null && stmt.elsestmt.stmts.size() == 1 && stmt.elsestmt.stmts.get(0) instanceof Assign) {
                        return matchAssign((Assign)stmt.elsestmt.stmts.get(0), "", stackName);
                    }
                }
            }

        }

        return false;
    }

    private boolean identifyMove(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof Assign) {
            Assign assign = (Assign)fn.block.stmts.get(5);
            if (matchAssign(assign, stackName, stackName)) {
                instructionToOpcodeMapping.put(VMOp.MOVE, opcodeIndex);
                return true;
            }
        }
        return false;
    }

    private boolean identifyLoadk(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof Assign) {
            Assign assign = (Assign)fn.block.stmts.get(5);
            if (matchAssign(assign, stackName, constantsName)) {
                instructionToOpcodeMapping.put(VMOp.LOADK, opcodeIndex);
                return true;
            }
        }
        return false;
    }

    private boolean identifyLoadBool(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 7 && fn.block.stmts.get(6) instanceof If && fn.block.stmts.get(5) instanceof Assign) {
            If stmt = (If)fn.block.stmts.get(6);
            Assign assign = (Assign)fn.block.stmts.get(5);

            boolean match1 = false, match2 = false;

            if (assign.exprs.exprs.size() == 1 && assign.exprs.exprs.get(0) instanceof BinaryExpression) {
                if (assign.vars.vars.get(0).symbol().equals(stackName)) {
                    if (matchBinOp((BinaryExpression)assign.exprs.exprs.get(0), BinaryExpression.Operator.NEQ, "", new Number(0.0))) {
                        match1 = true;
                    }
                }
            }

            if (stmt.elsestmt == null && stmt.elseifstmt.size() == 0 && stmt.ifstmt.first instanceof BinaryExpression) {
                BinaryExpression binOp = (BinaryExpression)stmt.ifstmt.first;
                if (matchBinOp(binOp, BinaryExpression.Operator.NEQ, "", new Number(0.0))) {
                    match2  = true;
                }
            }

            if (match1 && match2) {
                instructionToOpcodeMapping.put(VMOp.LOADBOOL, opcodeIndex);
                return true;
            }
        }
        return false;
    }

    private boolean identifyLoadNil(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof ForStep) {
            ForStep stmt = (ForStep)fn.block.stmts.get(5);
            if (stmt.step == null && stmt.block.stmts.size() == 1 && stmt.block.stmts.get(0) instanceof Assign) {
                if (matchAssign((Assign)stmt.block.stmts.get(0), stackName, new Nil())) {
                    instructionToOpcodeMapping.put(VMOp.LOADNIL, opcodeIndex);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean identifyGetUpVal(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof Assign) {
            Assign assign = (Assign)fn.block.stmts.get(5);
            if (matchAssign(assign, stackName, upvaluesName)) {
                instructionToOpcodeMapping.put(VMOp.GETUPVAL, opcodeIndex);
                return true;
            }
        }
        return false;
    }

    private boolean identifyGetGlobal(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 8 && fn.block.stmts.get(7) instanceof Assign) {
            If stmt = (If)fn.block.first(If.class);

            if (stmt != null && stmt.ifstmt.first instanceof BinaryExpression) {
                BinaryExpression expr = (BinaryExpression)stmt.ifstmt.first;

                if (matchBinOp(expr, BinaryExpression.Operator.EQ, "", new Number(100000))) {
                    Assign assign = (Assign)fn.block.stmts.get(7);

                    if (matchAssign(assign, stackName, environmentName)) {
                        instructionToOpcodeMapping.put(VMOp.GETGLOBAL, opcodeIndex);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean identifyGetTable(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 7 && fn.block.stmts.get(6) instanceof Assign && fn.block.stmts.get(5) instanceof If) {
            If stmt = (If)fn.block.stmts.get(5);
            Assign assign = (Assign)fn.block.stmts.get(6);

            if (matchLoadConstantStack(stmt) && matchAssign(assign, stackName, stackName)) {
                instructionToOpcodeMapping.put(VMOp.GETTABLE, opcodeIndex);
                return true;
            }
        }
        return false;
    }

    private boolean identifySetGlobal(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 8 && fn.block.stmts.get(7) instanceof Assign) {
            If stmt = (If)fn.block.first(If.class);

            if (stmt != null && stmt.ifstmt.first instanceof BinaryExpression) {
                BinaryExpression expr = (BinaryExpression)stmt.ifstmt.first;

                if (matchBinOp(expr, BinaryExpression.Operator.EQ, "", new Number(100000))) {
                    Assign assign = (Assign)fn.block.stmts.get(7);

                    if (matchAssign(assign, environmentName, stackName)) {
                        instructionToOpcodeMapping.put(VMOp.SETGLOBAL, opcodeIndex);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean identifySetUpVal(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof Assign) {
            Assign assign = (Assign)fn.block.stmts.get(5);
            if (matchAssign(assign, upvaluesName, stackName)) {
                instructionToOpcodeMapping.put(VMOp.SETUPVAL, opcodeIndex);
                return true;
            }
        }
        return false;
    }

    private boolean identifySetTable(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 8 && fn.block.stmts.get(7) instanceof Assign && fn.block.stmts.get(6) instanceof If && fn.block.stmts.get(5) instanceof If) {
            if (matchLoadConstantStack((If)fn.block.stmts.get(5)) && matchLoadConstantStack((If)fn.block.stmts.get(6))) {
                if (matchAssign((Assign)fn.block.stmts.get(7), stackName, "")) {
                    instructionToOpcodeMapping.put(VMOp.SETTABLE, opcodeIndex);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean identifyNewTable(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof Assign) {
            Assign assign = (Assign)fn.block.stmts.get(5);
            if (assign.vars.vars.size() == 1 && assign.vars.vars.get(0).symbol().equals(stackName)) {
                if (assign.exprs.exprs.size() == 1 && assign.exprs.exprs.get(0) instanceof TableConstructor) {
                    instructionToOpcodeMapping.put(VMOp.NEWTABLE, opcodeIndex);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean identifySelf(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 9 && fn.block.stmts.get(8) instanceof Assign &&
                fn.block.stmts.get(7) instanceof Assign && fn.block.stmts.get(6) instanceof If &&
                fn.block.stmts.get(5) instanceof Assign) {

            Assign assign0 = (Assign)fn.block.stmts.get(5);
            If stmt = (If)fn.block.stmts.get(6);
            Assign assign1 = (Assign)fn.block.stmts.get(7);
            Assign assign2 = (Assign)fn.block.stmts.get(8);

            if (!matchLoadConstantStack(stmt))
                return false;

            if (!matchAssign(assign2, stackName, "") || !matchAssign(assign1, stackName, ""))
                return false;

            if (!matchAssign(assign0, "", stackName))
                return false;

            if (assign1.vars.vars.get(0) instanceof Variable) {
                Variable var = (Variable)assign1.vars.vars.get(0);
                if (var.suffixes.get(0).expOrName instanceof BinaryExpression) {
                    BinaryExpression expr = (BinaryExpression)var.suffixes.get(0).expOrName;
                    if (matchBinOp(expr, BinaryExpression.Operator.ADD, "", new Number(1))) {
                        instructionToOpcodeMapping.put(VMOp.SELF, opcodeIndex);
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private boolean identifyAdd(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 8 &&
                fn.block.stmts.get(5) instanceof If &&
                fn.block.stmts.get(6) instanceof If &&
                fn.block.stmts.get(7) instanceof Assign) {

            If s1 = (If)fn.block.stmts.get(5);
            If s2 = (If)fn.block.stmts.get(6);
            Assign s3 = (Assign)fn.block.stmts.get(7);

            if (!matchLoadConstantStack(s1) || !matchLoadConstantStack(s2))
                return false;

            if (s3.vars.vars.size() == 1 && s3.exprs.exprs.size() == 1 && s3.exprs.exprs.get(0) instanceof BinaryExpression) {
                BinaryExpression expr = (BinaryExpression)s3.exprs.exprs.get(0);
                if (matchBinOp(expr, BinaryExpression.Operator.ADD, "", "")) {
                    instructionToOpcodeMapping.put(VMOp.ADD, opcodeIndex);
                    return true;
                }
            }

        }
        return false;
    }

    private boolean identifySub(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 8 &&
                fn.block.stmts.get(5) instanceof If &&
                fn.block.stmts.get(6) instanceof If &&
                fn.block.stmts.get(7) instanceof Assign) {

            If s1 = (If)fn.block.stmts.get(5);
            If s2 = (If)fn.block.stmts.get(6);
            Assign s3 = (Assign)fn.block.stmts.get(7);

            if (!matchLoadConstantStack(s1) || !matchLoadConstantStack(s2))
                return false;

            if (s3.vars.vars.size() == 1 && s3.exprs.exprs.size() == 1 && s3.exprs.exprs.get(0) instanceof BinaryExpression) {
                BinaryExpression expr = (BinaryExpression)s3.exprs.exprs.get(0);
                if (matchBinOp(expr, BinaryExpression.Operator.SUB, "", "")) {
                    instructionToOpcodeMapping.put(VMOp.SUB, opcodeIndex);
                    return true;
                }
            }

        }
        return false;
    }

    private boolean identifyMul(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 8 &&
                fn.block.stmts.get(5) instanceof If &&
                fn.block.stmts.get(6) instanceof If &&
                fn.block.stmts.get(7) instanceof Assign) {

            If s1 = (If)fn.block.stmts.get(5);
            If s2 = (If)fn.block.stmts.get(6);
            Assign s3 = (Assign)fn.block.stmts.get(7);

            if (!matchLoadConstantStack(s1) || !matchLoadConstantStack(s2))
                return false;

            if (s3.vars.vars.size() == 1 && s3.exprs.exprs.size() == 1 && s3.exprs.exprs.get(0) instanceof BinaryExpression) {
                BinaryExpression expr = (BinaryExpression)s3.exprs.exprs.get(0);
                if (matchBinOp(expr, BinaryExpression.Operator.MUL, "", "")) {
                    instructionToOpcodeMapping.put(VMOp.MUL, opcodeIndex);
                    return true;
                }
            }

        }
        return false;
    }

    private boolean identifyDiv(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 8 &&
                fn.block.stmts.get(5) instanceof If &&
                fn.block.stmts.get(6) instanceof If &&
                fn.block.stmts.get(7) instanceof Assign) {

            If s1 = (If)fn.block.stmts.get(5);
            If s2 = (If)fn.block.stmts.get(6);
            Assign s3 = (Assign)fn.block.stmts.get(7);

            if (!matchLoadConstantStack(s1) || !matchLoadConstantStack(s2))
                return false;

            if (s3.vars.vars.size() == 1 && s3.exprs.exprs.size() == 1 && s3.exprs.exprs.get(0) instanceof BinaryExpression) {
                BinaryExpression expr = (BinaryExpression)s3.exprs.exprs.get(0);
                if (matchBinOp(expr, BinaryExpression.Operator.REAL_DIV, "", "")) {
                    instructionToOpcodeMapping.put(VMOp.DIV, opcodeIndex);
                    return true;
                }
            }

        }
        return false;
    }

    private boolean identifyMod(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 8 &&
                fn.block.stmts.get(5) instanceof If &&
                fn.block.stmts.get(6) instanceof If &&
                fn.block.stmts.get(7) instanceof Assign) {

            If s1 = (If)fn.block.stmts.get(5);
            If s2 = (If)fn.block.stmts.get(6);
            Assign s3 = (Assign)fn.block.stmts.get(7);

            if (!matchLoadConstantStack(s1) || !matchLoadConstantStack(s2))
                return false;

            if (s3.vars.vars.size() == 1 && s3.exprs.exprs.size() == 1 && s3.exprs.exprs.get(0) instanceof BinaryExpression) {
                BinaryExpression expr = (BinaryExpression)s3.exprs.exprs.get(0);
                if (matchBinOp(expr, BinaryExpression.Operator.MOD, "", "")) {
                    instructionToOpcodeMapping.put(VMOp.MOD, opcodeIndex);
                    return true;
                }
            }

        }
        return false;
    }

    private boolean identifyPow(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 8 &&
                fn.block.stmts.get(5) instanceof If &&
                fn.block.stmts.get(6) instanceof If &&
                fn.block.stmts.get(7) instanceof Assign) {

            If s1 = (If)fn.block.stmts.get(5);
            If s2 = (If)fn.block.stmts.get(6);
            Assign s3 = (Assign)fn.block.stmts.get(7);

            if (!matchLoadConstantStack(s1) || !matchLoadConstantStack(s2))
                return false;

            if (s3.vars.vars.size() == 1 && s3.exprs.exprs.size() == 1 && s3.exprs.exprs.get(0) instanceof BinaryExpression) {
                BinaryExpression expr = (BinaryExpression)s3.exprs.exprs.get(0);
                if (matchBinOp(expr, BinaryExpression.Operator.POW, "", "")) {
                    instructionToOpcodeMapping.put(VMOp.POW, opcodeIndex);
                    return true;
                }
            }

        }
        return false;
    }

    private boolean identifyUnm(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof Assign) {
            Assign assign = (Assign)fn.block.stmts.get(5);
            if (assign.exprs.exprs.size() == 1 && assign.vars.vars.size() == 1 &&
                    assign.vars.vars.get(0) instanceof Variable && assign.exprs.exprs.get(0) instanceof UnaryExpression) {

                Variable var = (Variable)assign.vars.vars.get(0);
                UnaryExpression expr = (UnaryExpression)assign.exprs.exprs.get(0);

                if (var.symbol().equals(stackName) && matchUnOp(expr, UnaryExpression.Operator.MINUS, "")) {
                    instructionToOpcodeMapping.put(VMOp.UNM, opcodeIndex);
                    return true;
                }

            }
        }
        return false;
    }

    private boolean identifyNot(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof Assign) {
            Assign assign = (Assign)fn.block.stmts.get(5);
            if (assign.exprs.exprs.size() == 1 && assign.vars.vars.size() == 1 &&
                    assign.vars.vars.get(0) instanceof Variable && assign.exprs.exprs.get(0) instanceof UnaryExpression) {

                Variable var = (Variable)assign.vars.vars.get(0);
                UnaryExpression expr = (UnaryExpression)assign.exprs.exprs.get(0);

                if (var.symbol().equals(stackName) && matchUnOp(expr, UnaryExpression.Operator.NOT, "")) {
                    instructionToOpcodeMapping.put(VMOp.NOT, opcodeIndex);
                    return true;
                }

            }
        }
        return false;
    }

    private boolean identifyLen(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof Assign) {
            Assign assign = (Assign)fn.block.stmts.get(5);
            if (assign.exprs.exprs.size() == 1 && assign.vars.vars.size() == 1 &&
                    assign.vars.vars.get(0) instanceof Variable && assign.exprs.exprs.get(0) instanceof UnaryExpression) {

                Variable var = (Variable)assign.vars.vars.get(0);
                UnaryExpression expr = (UnaryExpression)assign.exprs.exprs.get(0);

                if (var.symbol().equals(stackName) && matchUnOp(expr, UnaryExpression.Operator.HASHTAG, "")) {
                    instructionToOpcodeMapping.put(VMOp.LEN, opcodeIndex);
                    return true;
                }

            }
        }
        return false;
    }

    private boolean identifyConcat(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 8 && fn.block.stmts.get(7) instanceof Assign && fn.block.stmts.get(6) instanceof ForStep) {
            if (matchAssign((Assign)fn.block.stmts.get(7), stackName, "")) {
                Node node = ((ForStep) fn.block.stmts.get(6)).block.first(BinaryExpression.class);
                if (node != null && ((BinaryExpression)node).operator == BinaryExpression.Operator.STRCAT) {
                    instructionToOpcodeMapping.put(VMOp.CONCAT, opcodeIndex);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean identifyJump(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof Assign) {
            Assign assign = (Assign)fn.block.stmts.get(5);
            if (assign.vars.vars.size() == 1 && assign.exprs.exprs.size() == 1 &&
                    assign.vars.vars.get(0) instanceof Variable && assign.exprs.exprs.get(0) instanceof BinaryExpression) {

                BinaryExpression expr = (BinaryExpression)assign.exprs.exprs.get(0);

                if (expr.operator == BinaryExpression.Operator.ADD) {
                    instructionToOpcodeMapping.put(VMOp.JUMP, opcodeIndex);
                    return true;
                }

            }
        }
        return false;
    }

    private boolean identifyEq(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 9 && fn.block.stmts.get(6) instanceof If && fn.block.stmts.get(7) instanceof If
                && fn.block.stmts.get(8) instanceof If) {

            If if1 = (If)fn.block.stmts.get(6);
            If if2 = (If)fn.block.stmts.get(7);
            If if3 = (If)fn.block.stmts.get(8);

            if (matchLoadConstantStack(if1) && matchLoadConstantStack(if2)) {
                if (if3.ifstmt.first instanceof BinaryExpression) {
                    BinaryExpression expr1 = (BinaryExpression)if3.ifstmt.first;
                    if (expr1.left instanceof BinaryExpression && expr1.right instanceof Variable) {
                        BinaryExpression expr2 = (BinaryExpression)expr1.left;

                        if (expr1.operator == BinaryExpression.Operator.NEQ && expr2.operator == BinaryExpression.Operator.EQ) {
                            instructionToOpcodeMapping.put(VMOp.EQ, opcodeIndex);
                            return true;
                        }
                    }
                }
            }

        }
        else if (fn.block.stmts.size() == 8 && fn.block.stmts.get(5) instanceof If && fn.block.stmts.get(6) instanceof If
                && fn.block.stmts.get(7) instanceof If) {

            If if1 = (If)fn.block.stmts.get(5);
            If if2 = (If)fn.block.stmts.get(6);
            If if3 = (If)fn.block.stmts.get(7);

            if (matchLoadConstantStack(if1) && matchLoadConstantStack(if2)) {
                if (if3.ifstmt.first instanceof BinaryExpression) {
                    BinaryExpression expr1 = (BinaryExpression)if3.ifstmt.first;
                    if (expr1.left instanceof BinaryExpression && expr1.right instanceof BinaryExpression) {
                        BinaryExpression expr2 = (BinaryExpression)expr1.left;

                        if (expr1.operator == BinaryExpression.Operator.NEQ && expr2.operator == BinaryExpression.Operator.EQ) {
                            instructionToOpcodeMapping.put(VMOp.EQ, opcodeIndex);
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    private boolean identifyLt(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 9 && fn.block.stmts.get(6) instanceof If && fn.block.stmts.get(7) instanceof If
                && fn.block.stmts.get(8) instanceof If) {

            If if1 = (If)fn.block.stmts.get(6);
            If if2 = (If)fn.block.stmts.get(7);
            If if3 = (If)fn.block.stmts.get(8);

            if (matchLoadConstantStack(if1) && matchLoadConstantStack(if2)) {
                if (if3.ifstmt.first instanceof BinaryExpression) {
                    BinaryExpression expr1 = (BinaryExpression)if3.ifstmt.first;
                    if (expr1.left instanceof BinaryExpression && expr1.right instanceof Variable) {
                        BinaryExpression expr2 = (BinaryExpression)expr1.left;

                        if (expr1.operator == BinaryExpression.Operator.NEQ && expr2.operator == BinaryExpression.Operator.LT) {
                            instructionToOpcodeMapping.put(VMOp.LT, opcodeIndex);
                            return true;
                        }
                    }
                }
            }

        }
        else if (fn.block.stmts.size() == 8 && fn.block.stmts.get(5) instanceof If && fn.block.stmts.get(6) instanceof If
                && fn.block.stmts.get(7) instanceof If) {

            If if1 = (If)fn.block.stmts.get(5);
            If if2 = (If)fn.block.stmts.get(6);
            If if3 = (If)fn.block.stmts.get(7);

            if (matchLoadConstantStack(if1) && matchLoadConstantStack(if2)) {
                if (if3.ifstmt.first instanceof BinaryExpression) {
                    BinaryExpression expr1 = (BinaryExpression)if3.ifstmt.first;
                    if (expr1.left instanceof BinaryExpression && expr1.right instanceof BinaryExpression) {
                        BinaryExpression expr2 = (BinaryExpression)expr1.left;

                        if (expr1.operator == BinaryExpression.Operator.NEQ && expr2.operator == BinaryExpression.Operator.LT) {
                            instructionToOpcodeMapping.put(VMOp.LT, opcodeIndex);
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    // todo: need to refactor this so i dont depend on stmt size
    private boolean identifyLte(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 9 && fn.block.stmts.get(6) instanceof If && fn.block.stmts.get(7) instanceof If
                && fn.block.stmts.get(8) instanceof If) {

            If if1 = (If)fn.block.stmts.get(6);
            If if2 = (If)fn.block.stmts.get(7);
            If if3 = (If)fn.block.stmts.get(8);

            if (matchLoadConstantStack(if1) && matchLoadConstantStack(if2)) {
                if (if3.ifstmt.first instanceof BinaryExpression) {
                    BinaryExpression expr1 = (BinaryExpression)if3.ifstmt.first;
                    if (expr1.left instanceof BinaryExpression && expr1.right instanceof Variable) {
                        BinaryExpression expr2 = (BinaryExpression)expr1.left;

                        if (expr1.operator == BinaryExpression.Operator.NEQ && expr2.operator == BinaryExpression.Operator.LTE) {
                            instructionToOpcodeMapping.put(VMOp.LTE, opcodeIndex);
                            return true;
                        }
                    }
                }
            }

        }
        else if (fn.block.stmts.size() == 8 && fn.block.stmts.get(5) instanceof If && fn.block.stmts.get(6) instanceof If
                && fn.block.stmts.get(7) instanceof If) {

            If if1 = (If)fn.block.stmts.get(5);
            If if2 = (If)fn.block.stmts.get(6);
            If if3 = (If)fn.block.stmts.get(7);

            if (matchLoadConstantStack(if1) && matchLoadConstantStack(if2)) {
                if (if3.ifstmt.first instanceof BinaryExpression) {
                    BinaryExpression expr1 = (BinaryExpression)if3.ifstmt.first;
                    if (expr1.left instanceof BinaryExpression && expr1.right instanceof BinaryExpression) {
                        BinaryExpression expr2 = (BinaryExpression)expr1.left;

                        if (expr1.operator == BinaryExpression.Operator.NEQ && expr2.operator == BinaryExpression.Operator.LTE) {
                            instructionToOpcodeMapping.put(VMOp.LTE, opcodeIndex);
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    private boolean identifyTest(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof If) {
            If stmt = (If)fn.block.stmts.get(5);

            if (stmt.elseifstmt.size() == 0 && stmt.elsestmt == null) {
                if (stmt.ifstmt.first instanceof BinaryExpression) {
                    BinaryExpression expr1 = (BinaryExpression)stmt.ifstmt.first;
                    if (expr1.operator != BinaryExpression.Operator.EQ)
                        return false;
                    if (expr1.left instanceof Variable && ((Variable)expr1.left).symbol().equals(stackName)) {
                        if (expr1.right instanceof BinaryExpression) {
                            BinaryExpression expr2 = (BinaryExpression)expr1.right;
                            if (expr2.operator == BinaryExpression.Operator.EQ && expr2.right instanceof Number) {
                                Number n = (Number)expr2.right;
                                if (n.value == 0.0 && stmt.ifstmt.second.stmts.size() == 1) {
                                    instructionToOpcodeMapping.put(VMOp.TEST, opcodeIndex);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean identifyTestSet(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 6 && fn.block.stmts.get(5) instanceof If) {
            If stmt = (If)fn.block.stmts.get(5);

            if (stmt.elseifstmt.size() == 0 && stmt.elsestmt != null) {
                if (stmt.ifstmt.first instanceof BinaryExpression) {
                    BinaryExpression expr1 = (BinaryExpression)stmt.ifstmt.first;
                    if (expr1.operator != BinaryExpression.Operator.EQ)
                        return false;
                    if (expr1.left instanceof Variable && ((Variable)expr1.left).symbol().equals(stackName)) {
                        if (expr1.right instanceof BinaryExpression) {
                            BinaryExpression expr2 = (BinaryExpression)expr1.right;
                            if (expr2.operator == BinaryExpression.Operator.EQ && expr2.right instanceof Number) {
                                Number n = (Number)expr2.right;
                                if (n.value == 0.0 && stmt.ifstmt.second.stmts.size() == 1) {
                                    instructionToOpcodeMapping.put(VMOp.TESTSET, opcodeIndex);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private int countIfNeq1(Function fn) {
        int count = 0;
        List<Node> children = fn.block.getChildren(If.class);

        for (Node node : children) {
            Expression expr1 = ((If)node).ifstmt.first;

            if (expr1 instanceof BinaryExpression) {
                BinaryExpression binexpr = (BinaryExpression)expr1;
                if (binexpr.operator == BinaryExpression.Operator.NEQ && binexpr.right instanceof Number) {
                    if (((Number)binexpr.right).value == 1.0) {
                        ++ count;
                    }
                }
            }
        }

        return count;
    }

    private boolean identifyCall(Function fn, int opcodeIndex) {
        If stmt = (If)fn.block.first(If.class);

        if (stmt != null) {
            FunctionCall call = (FunctionCall)stmt.first(FunctionCall.class);

            if (call != null && call.varOrExp.symbol().equals(handleReturnName)) {
                // handle return doesnt have a return
                if (fn.block.ret == null) {
                    instructionToOpcodeMapping.put(VMOp.CALL, opcodeIndex);
                    return true;
                }
            }
        }

        if (countIfNeq1(fn) == 2) {
            instructionToOpcodeMapping.put(VMOp.CALL, opcodeIndex);
            return true;
        }

        return false;
    }

    private boolean identifyTailCall(Function fn, int opcodeIndex) {
        If stmt = (If)fn.block.first(If.class);

        if (stmt != null) {
            FunctionCall call = (FunctionCall)stmt.first(FunctionCall.class);

            if (call != null && call.varOrExp.symbol().equals(handleReturnName)) {
                // handle return doesnt have a return
                if (fn.block.ret != null) {
                    instructionToOpcodeMapping.put(VMOp.TAILCALL, opcodeIndex);
                    return true;
                }
            }
        }

        if (countIfNeq1(fn) == 1) {
            instructionToOpcodeMapping.put(VMOp.TAILCALL, opcodeIndex);
            return true;
        }

        return false;
    }

    private boolean identifyReturn(Function fn, int opcodeIndex) {
        if (fn.block.ret != null) {
            for (Node node : fn.block.getChildren()) {
                if (node.first(Return.class) != null) {
                    instructionToOpcodeMapping.put(VMOp.RETURN, opcodeIndex);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean identifyForLoop(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 9 && fn.block.stmts.get(8) instanceof If) {
            If stmt = (If)fn.block.stmts.get(8);

            if (stmt.elsestmt == null & stmt.elseifstmt.size() == 1) {
                instructionToOpcodeMapping.put(VMOp.FORLOOP, opcodeIndex);
                return true;
            }
        }
        return false;
    }

    private boolean identifyForPrep(Function fn, int opcodeIndex) {
        FunctionCall call = (FunctionCall)fn.block.first(FunctionCall.class);

        if (call != null && call.varOrExp.symbol().equals("assert")) {
            instructionToOpcodeMapping.put(VMOp.FORPREP, opcodeIndex);
            return true;
        }

        return false;
    }

    private boolean identifyTForLoop(Function fn, int opcodeIndex) {
        for (Node node : fn.block.getChildren()) {
            BinaryExpression expr = (BinaryExpression)node.first(BinaryExpression.class);

            if (expr != null && expr.operator == BinaryExpression.Operator.NEQ && expr.right instanceof Nil) {
                instructionToOpcodeMapping.put(VMOp.TFORLOOP, opcodeIndex);
                return true;
            }
        }

        return false;
    }

    private boolean identifySetList(Function fn, int opcodeIndex) {
        if (fn.block.stmts.size() == 8 && fn.block.stmts.get(5) instanceof LocalDeclare) {
            LocalDeclare decl = (LocalDeclare)fn.block.stmts.get(5);
            if (decl.exprs.exprs.get(0) instanceof BinaryExpression) {
                BinaryExpression expr = (BinaryExpression)decl.exprs.exprs.get(0);
                if (expr.operator == BinaryExpression.Operator.MUL && expr.right instanceof Number) {
                    Number num = (Number)expr.right;
                    if (num.value == 50) {
                        instructionToOpcodeMapping.put(VMOp.SETLIST, opcodeIndex);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean identifyClose(Function fn, int opcodeIndex) {
        ForStep stmt1 = (ForStep)fn.block.first(ForStep.class);
        if (stmt1 != null) {
            ForIn stmt2 = (ForIn)stmt1.block.first(ForIn.class);
            if (stmt2 != null && stmt2.exprs.exprs.get(0).symbol().equals("next")) {
                instructionToOpcodeMapping.put(VMOp.CLOSE, opcodeIndex);
                return true;
            }
        }
        return false;
    }

    private boolean identifyClosure(Function fn, int opcodeIndex) {
        FunctionCall call = (FunctionCall)fn.block.first(FunctionCall.class);

        if (call != null && call.varOrExp.symbol().equals("setmetatable")) {
            instructionToOpcodeMapping.put(VMOp.CLOSURE, opcodeIndex);
            return true;
        }

        return false;
    }

    private boolean identifyVarArg(Function fn, int opcodeIndex) {
        /*
        ForStep stmt = (ForStep)fn.first(ForStep.class);

        if (stmt != null) {
            If s = (If)stmt.first(If.class);
            if (s != null && s.elsestmt != null && s.ifstmt.first instanceof BinaryExpression) {
                BinaryExpression expr = (BinaryExpression)s.ifstmt.first;
                if (expr.operator == BinaryExpression.Operator.LTE && expr.right instanceof Variable) {
                    Variable var = (Variable)expr.right;
                    if (var.symbol().equals(varargszName)) {
                        instructionToOpcodeMapping.put(VMOp.VARARG, opcodeIndex);
                        return true;
                    }
                }
            }
        }

        return false;*/

        if (new ASTSourceGenerator(fn.block).generate().contains(varargszName)) {
            instructionToOpcodeMapping.put(VMOp.VARARG, opcodeIndex);
            return true;
        }

        return false;
    }

    private void identifyVmHandler(Function purified, int opcodeIndex) {
        if (identifyMove(purified, opcodeIndex))
            return;
        if (identifyLoadk(purified, opcodeIndex))
            return;
        if (identifyLoadBool(purified, opcodeIndex))
            return;
        if (identifyLoadNil(purified, opcodeIndex))
            return;
        if (identifyGetUpVal(purified, opcodeIndex))
            return;
        if (identifyGetGlobal(purified, opcodeIndex))
            return;
        if (identifyGetTable(purified, opcodeIndex))
            return;
        if (identifySetGlobal(purified, opcodeIndex))
            return;
        if (identifySetUpVal(purified, opcodeIndex))
            return;
        if (identifySetTable(purified, opcodeIndex))
            return;
        if (identifyNewTable(purified, opcodeIndex))
            return;
        if (identifySelf(purified, opcodeIndex))
            return;
        if (identifyAdd(purified, opcodeIndex))
            return;
        if (identifySub(purified, opcodeIndex))
            return;
        if (identifyMul(purified, opcodeIndex))
            return;
        if (identifyDiv(purified, opcodeIndex))
            return;
        if (identifyMod(purified, opcodeIndex))
            return;
        if (identifyPow(purified, opcodeIndex))
            return;
        if (identifyUnm(purified, opcodeIndex))
            return;
        if (identifyNot(purified, opcodeIndex))
            return;
        if (identifyLen(purified, opcodeIndex))
            return;
        if (identifyConcat(purified, opcodeIndex))
            return;
        if (identifyJump(purified, opcodeIndex))
            return;
        if (identifyEq(purified, opcodeIndex))
            return;
        if (identifyLt(purified, opcodeIndex))
            return;
        if (identifyLte(purified, opcodeIndex))
            return;
        if (identifyTest(purified, opcodeIndex))
            return;
        if (identifyTestSet(purified, opcodeIndex))
            return;
        if (identifyCall(purified, opcodeIndex))
            return;
        if (identifyTailCall(purified, opcodeIndex))
            return;
        if (identifyReturn(purified, opcodeIndex))
            return;
        if (identifyForLoop(purified, opcodeIndex))
            return;
        if (identifyForPrep(purified, opcodeIndex))
            return;
        if (identifyTForLoop(purified, opcodeIndex))
            return;
        if (identifySetList(purified, opcodeIndex))
            return;
        if (identifyClose(purified, opcodeIndex))
            return;
        if (identifyClosure(purified, opcodeIndex))
            return;
        if (identifyVarArg(purified, opcodeIndex))
            return;

        Block block = new Block();
        block.stmts.add(purified);
        ASTSourceGenerator gen = new ASTSourceGenerator(block);

        System.out.println(gen.generate());

        throw new RuntimeException("Above printed handler could not be identified");
    }

    private void identifyVmHandlers() {
        for (Map.Entry element : vmOpcodeDispatcherMapping.entrySet()) {
            int key = (int)element.getKey();
            int value = (int)element.getValue();
            Function fn = vmHandlerMapping.get(value);
            Function purified = purifyHandler(fn);
            identifyVmHandler(purified, key);
        }
    }

    private class RenamerPass1 extends ASTOptimizerBase {
        private void renameCoreFunctions(Function node) {
            if (node.name != null) {
                String symbol = node.name.symbol();

                switch(symbol) {
                    default:
                        break;
                    case "func0":
                        mgr.symbols.add(symbol, node, vmRunName);
                        vmRun = node;
                        break;
                    case "func1":
                        mgr.symbols.add(symbol, node, getByteName);
                        getByte = node;
                        break;
                    case "func2":
                        mgr.symbols.add(symbol, node, getDwordName);
                        getDword = node;
                        break;
                    case "func3":
                        mgr.symbols.add(symbol, node, getBitsName);
                        getBits = node;
                        break;
                    case "func4":
                        mgr.symbols.add(symbol, node, getFloat64Name);
                        getFloat64 = node;
                        break;
                    case "func5":
                        mgr.symbols.add(symbol, node, getInstructionName);
                        getInstruction = node;
                        break;
                    case "func6":
                        mgr.symbols.add(symbol, node, getStringName);
                        getString = node;
                        break;
                    case "func7":
                        mgr.symbols.add(symbol, node, decodeChunkName);
                        decodeChunk = node;
                        break;
                    case "func8":
                        mgr.symbols.add(symbol, node, createWrapperName);
                        createWrapper = node;
                        break;
                    case "func9":
                        mgr.symbols.add(symbol, node, vmRunFuncName);
                        vmRunFunc = node;
                        break;
                }
            }
        }

        @Override
        public Node visit(Function node) {
            renameCoreFunctions(node);

            return node;
        }
    }

    private class RenamerPass2 extends ASTOptimizerBase {

        private void renameCreateWrapper(Function node) {
            mgr.symbols.add(node.params.names.get(0).symbol(), null, cacheName);
            mgr.symbols.add(node.params.names.get(1).symbol(), null, environmentName);
            mgr.symbols.add(node.params.names.get(2).symbol(), null, upvaluesName);

            for (Node declare : node.block.getChildren(LocalDeclare.class)) {
                FunctionCall call = (FunctionCall)declare.first(FunctionCall.class);
                if (call != null && call.varOrExp.line().equals("setmetatable")) {
                    LuaString var = (LuaString)declare.first(LuaString.class);
                    mgr.symbols.add(var.symbol(), null, constantsName);
                }
            }
        }

        private void renameVmLocalsAndBias(LocalDeclare decl) {
            LuaString var = (LuaString)decl.first(LuaString.class);

            if (decl.exprs.exprs.size() == 0) {
                mgr.symbols.add(var.symbol(), null, vmOpcodeDispatcherTableName);
            }
            else if (decl.exprs.first(TableConstructor.class) != null) {
                mgr.symbols.add(var.symbol(), null, vmHandlerTableName);
            }
            else if (decl.exprs.first(Number.class) != null) {
                mgr.symbols.add(var.symbol(), null, biasName);
            }
        }

        private void renameVmRunFunc(Function node) {
            boolean setEnv = false, setVarArgsz = false;

            for (Node declare : node.block.getChildren(LocalDeclare.class)) {
                FunctionCall call = (FunctionCall)declare.first(FunctionCall.class);

                if (call != null && call.varOrExp.line().equals("getfenv")) {
                    LuaString var = (LuaString)declare.first(LuaString.class);
                    mgr.symbols.add(var.symbol(), null, environmentName);
                    setEnv = true;
                }

                UnaryExpression unop = (UnaryExpression)declare.first(UnaryExpression.class);

                if (unop != null && unop.op == UnaryExpression.Operator.HASHTAG) {
                    LuaString var = (LuaString)declare.first(LuaString.class);
                    mgr.symbols.add(var.symbol(), null, varargszName);
                    setVarArgsz = true;
                }

                if (setEnv && setVarArgsz) {
                    break;
                }
            }

            ForStep stmt = (ForStep)node.block.getChildren(ForStep.class).get(0);

            Variable stack = (Variable)stmt.block.first(If.class).first(Assign.class).first(Variable.class);
            mgr.symbols.add(stack.symbol(), null, stackName);

            List<Node> children;

            Function handleRet = (Function)node.block.getChildren(Function.class).get(0);
            mgr.symbols.add(handleRet.name.symbol(), null, handleReturnName);

            children = node.block.getChildren();

            // Old

            /*
            int handleRetIdx = -1;
            for (int i = 0; i < children.size(); i++) {
                if (children.get(i) instanceof Function && children.get(i) == handleRet) {
                    handleRetIdx = i;
                    break;
                }
            }

            renameVmLocalsAndBias((LocalDeclare) children.get(handleRetIdx + 1));
            renameVmLocalsAndBias((LocalDeclare) children.get(handleRetIdx + 2));
            renameVmLocalsAndBias((LocalDeclare) children.get(handleRetIdx + 3));
            */

            // New

            int handleRetIdx = -1;
            for (int i = 0; i < children.size() - 1; i++) {
                if ((children.get(i) instanceof Function || children.get(i) instanceof ForStep) && children.get(i + 1) instanceof LocalDeclare) {
                    handleRetIdx = i;
                    break;
                }
            }

            renameVmLocalsAndBias((LocalDeclare) children.get(handleRetIdx + 1));
            renameVmLocalsAndBias((LocalDeclare) children.get(handleRetIdx + 2));
            renameVmLocalsAndBias((LocalDeclare) children.get(handleRetIdx + 3));

        }

        @Override
        public Node visit(Block node) {
            if (node.parent != null && node.parent instanceof Function) {
                Function func = (Function)node.parent;

                if (func == createWrapper) {
                    renameCreateWrapper(createWrapper);
                }
                else if (func == vmRunFunc) {
                    renameVmRunFunc(vmRunFunc);
                }
            }

            return node;
        }
    }

    private class RenamerPass3 extends ASTOptimizerBase {
        private void initVmHandlers(LocalDeclare decl) {
            TableConstructor ctor = (TableConstructor)decl.first(TableConstructor.class);

            for (int i = 0; i < ctor.entries.size(); i++) {
                Pair<Expression, Expression> p = ctor.entries.get(i);

                if (p.second instanceof Function) {
                    populateVmHandler(i + 1, (Function)p.second);
                }
            }
        }

        private void populateVmHandler(int index, Function function) {
            vmHandlerMapping.put(index, function);
        }

        @Override
        public Node visit(Assign node) {
            Variable var = (Variable)node.first(Variable.class);

            if (var.symbol().equals(vmHandlerTableName)) {
                double index = ((Number)var.suffixes.get(0).expOrName).value;

                Function function = (Function)node.first(Function.class);

                if (function != null) {
                    populateVmHandler((int)index, function);
                }
            }
            else if (var.symbol().equals(vmOpcodeDispatcherTableName)) {
                TableConstructor ctor = (TableConstructor)node.first(TableConstructor.class);

                if (ctor != null) {
                    for (int i = 0; i < ctor.entries.size(); i++) {
                        Variable v = (Variable) ctor.entries.get(i).second;

                        vmOpcodeDispatcherMapping.put(i + 1,
                                (int) Double.parseDouble(v.suffixes.get(0).expOrName.line()));
                    }
                }
            }

            return node;
        }

        @Override
        public Node visit(LocalDeclare node) {
            LuaString str = (LuaString)node.first(LuaString.class);

            if (str.symbol().equals(vmHandlerTableName)) {
                initVmHandlers(node);
            }

            return node;
        }

        @Override
        public Node visit(LuaString node) {
            if (node.value.startsWith("\"LPH|")) {
                luraphVmString = node.value.substring(5, node.value.length() - 1);
            }

            return node;
        }
    }
}
