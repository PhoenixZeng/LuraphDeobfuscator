package LuaVM;

import java.util.Arrays;

public class VM {
    public static boolean isVariableTargetBranch(VMOp opcode) {
        return opcode == VMOp.JUMP || opcode == VMOp.FORPREP ||opcode == VMOp.FORLOOP;
    }

    public static boolean isConstantTargetBranch(VMOp opcode) {
        VMOp ops[] = {
                VMOp.LOADBOOL, VMOp.EQ, VMOp.LT, VMOp.LTE, VMOp.TEST, VMOp.TESTSET, VMOp.TFORLOOP,
        };

        return Arrays.asList(ops).contains(opcode);
    }

    public static boolean isOperandUsed(VMOperand operand, VMOpType type) {
        switch(type) {
            case A:
                return operand == VMOperand.A;

            case AB:
                return operand == VMOperand.A || operand == VMOperand.B;

            case AC:
                return operand == VMOperand.A || operand == VMOperand.C;

            case ABx:
                return operand == VMOperand.A || operand == VMOperand.Bx;

            case AsBx:
                return operand == VMOperand.A || operand == VMOperand.sBx;

            case ABC:
                return operand == VMOperand.A || operand == VMOperand.B || operand == VMOperand.C;

            case sBx:
                return operand == VMOperand.sBx;
        }

        return false;
    }

    public static boolean isBitsUsed(VMOperand operand, VMOpType type) {

        switch(type) {
            case A:
                return operand == VMOperand.A;

            case AB:
                return operand != VMOperand.C;

            case AC:
                return operand != VMOperand.B;

            case ABx:
            case AsBx:
            case ABC:
                return true;

            case sBx:
                return operand != VMOperand.A;
        }

        return false;
    }

    public static VMOpType getOpType(VMOp type) throws RuntimeException {
        switch (type) {
            case MOVE:
            case LOADNIL:
            case GETUPVAL:
            case SETUPVAL:
            case UNM:
            case NOT:
            case LEN:
            case RETURN:
            case VARARG:
                return VMOpType.AB;

            case LOADK:
            case GETGLOBAL:
            case SETGLOBAL:
            case CLOSURE:
                return VMOpType.ABx;

            case LOADBOOL:
            case GETTABLE:
            case SETTABLE:
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case MOD:
            case POW:
            case CONCAT:
            case CALL:
            case TAILCALL:
            case SELF:
            case EQ:
            case LT:
            case LTE:
            case TESTSET:
            case NEWTABLE:
            case SETLIST:
                return VMOpType.ABC;

            case JUMP:
                return VMOpType.sBx;

            case TEST:
            case TFORLOOP:
                return VMOpType.AC;

            case FORPREP:
            case FORLOOP:
                return VMOpType.AsBx;

            case CLOSE:
                return VMOpType.A;

        }

        throw new RuntimeException("Invalid VMOp provided");
    }
}
