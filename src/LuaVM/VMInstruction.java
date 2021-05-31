/**
 * Lua instructions which change the PC
 *
 * Only JMP, FORPREP, and FORLOOP will be considered official branch instructions, because all other branches are followed by a JMP
 * NOTE: ^ Turns out to not be true. Lua VM bytecode can generate LOADBOOL insns in sequence for example: local x = y > 100
 *
 * Each basic block will have
 *
 * LOADBOOL A B C R(A) := (Bool)B; if (C) PC++
 *
 * JMP sBx PC += sBx
 *
 * EQ A B C if ((RK(B) == RK(C)) ~= A) then PC++
 *
 * LT A B C if ((RK(B) < RK(C)) ~= A) then PC++
 *
 * LE A B C if ((RK(B) <= RK(C)) ~= A) then PC++
 *
 * TEST A C if not (R(A) <=> C) then PC++
 *
 * TESTSET A B C if (R(B) <=> C) then R(A) := R(B) else PC++
 *
 * FORPREP A sBx R(A) -= R(A+2); PC += sBx
 *
 * FORLOOP A sBx
 *               R(A) += R(A+2)
 *               if R(A) <= R(A+1) then {
 *                  PC += sBx; R(A+3) = R(A)
 *               }
 *
 * TFORLOOP A C
 *               R(A+3), ... ,R(A+2+C) := R(A)(R(A+1), R(A+2));
 *               if R(A+3) ~= nil then {
 *                  R(A+2) = R(A+3);
 *               } else {
 *                  PC++;
 *              }
 */

// TFORLOOP: for in
// FORPREP, FORLOOP for i = a, b, c


package LuaVM;

import java.util.Arrays;

public class VMInstruction {
    public VMOp opcode;
    public int opcodeNum; // value encoded into instruction
    public double A, B, C, sBx, Bx;

    public double getValue(VMOperand operand) {
        switch (operand) {
            case A:
                return A;
            case B:
                return B;
            case C:
                return C;
            case sBx:
                return sBx;
            case Bx:
                return Bx;
        }

        throw new RuntimeException("Invalid operand");
    }

    public boolean isVariableTargetBranch() {
        return VM.isVariableTargetBranch(opcode);
    }

    public boolean isConstantTargetBranch() {
        return VM.isConstantTargetBranch(opcode);
    }

    public boolean isBranch() {
        return isVariableTargetBranch() || isConstantTargetBranch();
    }

    public int getExitTargetPC(int PC) {
        if (isVariableTargetBranch()) {
            return (int)sBx + PC;
        }
        else if (isConstantTargetBranch()) {
            return PC + 1;
        }
        else {
            throw new RuntimeException("getExitTargetPC called on non branch instruction");
        }
    }

    @Override
    public String toString() {
        String s = opcode.toString();

        VMOpType type = VM.getOpType(opcode);

        if (VM.isOperandUsed(VMOperand.A, type)) {
             s += " " + A;
        }

        if (VM.isOperandUsed(VMOperand.B, type)) {
            s += " " + B;
        }

        if (VM.isOperandUsed(VMOperand.C, type)) {
            s += " " + C;
        }

        if (VM.isOperandUsed(VMOperand.Bx, type)) {
            s += " " + Bx;
        }

        if (VM.isOperandUsed(VMOperand.sBx, type)) {
            s += " " + sBx;
        }

        return s;
    }
}
