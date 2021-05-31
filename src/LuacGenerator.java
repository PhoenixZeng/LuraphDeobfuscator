import LuaVM.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

// NOTE: DataOutputStream is BIG Endian

public class LuacGenerator {
    private static void writeInt(DataOutputStream dos, int value) throws IOException {
        byte[] buf = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
        dos.write(buf);
    }

    private static void writeDouble(DataOutputStream dos, double value) throws IOException {
        byte[] buf = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(value).array();
        dos.write(buf);
    }

    private static void writeHeader(DataOutputStream dos) throws IOException {
        // Header signature
        writeInt(dos, 0x61754C1B);

        // Version
        dos.writeByte(0x51);

        // Format
        dos.writeByte(0);

        // Endianness
        dos.writeByte(1);

        // Size of int
        dos.writeByte(4);

        // Size of size_t
        dos.writeByte(4);

        // Size of instruction
        dos.writeByte(4);

        // Size of lua_Number
        dos.writeByte(8);

        // Integral flag
        dos.writeByte(0);
    }

    private static void writeChunk(DataOutputStream dos, LuaChunk chunk, boolean topLevel) throws IOException {
        if (topLevel) {
            writeString(dos, "@devirtualized.lua");
        }
        else {
            writeString(dos, "");
        }

        // Line defs
        writeInt(dos, chunk.lineDefined);
        writeInt(dos, chunk.lineDefined);

        // Upvals
        dos.writeByte((byte)chunk.numUpVals);

        // Params
        dos.writeByte((byte)chunk.numParams);

        // isVararg
        dos.writeByte((byte)chunk.isVarArgFlag);

        // Max stacksize
        dos.writeByte((byte)chunk.maxStackSize);

        writeInstructions(dos, chunk.insns);
        writeConstants(dos, chunk.constants);
        writePrototypes(dos, chunk.prototypes);

        // Source line positions (0 for now)
        writeInt(dos, 0);

        // Locals
        writeInt(dos, 0);

        // Upvalues
        writeInt(dos, 0);
    }

    private static int maskSize(double value, int size) {
        int v = (int)value;
        v = v & ((1 << size) - 1);
        return v;
    }

    private static void writeInstruction(DataOutputStream dos, VMInstruction insn) throws IOException {
        int data = 0;

        data = data | insn.opcode.ordinal();

        switch (VM.getOpType(insn.opcode)) {
            case A:
                data = data | maskSize(insn.A, 8) << 6;
                break;
            case AB:
                data = data | maskSize(insn.A, 8) << 6;
                data = data | maskSize(insn.B, 9) << 23;
                break;
            case AC:
                data = data | maskSize(insn.A, 8) << 6;
                data = data | maskSize(insn.C, 9) << 14;
                break;
            case ABC:
                data = data | maskSize(insn.A, 8) << 6;
                data = data | maskSize(insn.B, 9) << 23;
                data = data | maskSize(insn.C, 9) << 14;
                break;
            case ABx:
                data = data | maskSize(insn.A, 8) << 6;
                data = data | maskSize(insn.Bx, 18) << 14;
                break;
            case AsBx:
                data = data | maskSize(insn.A, 8) << 6;
                data = data | maskSize(insn.sBx + 131071.0, 18) << 14;
                break;
            case sBx:
                data = data | maskSize(insn.sBx + 131071.0, 18) << 14;
                break;
        }

        writeInt(dos, data);
    }

    private static void writeInstructions(DataOutputStream dos, List<VMInstruction> insns) throws IOException {
        writeInt(dos, insns.size());

        for (VMInstruction insn : insns) {
            writeInstruction(dos, insn);
        }
    }

    private static void writeConstant(DataOutputStream dos, LuaValue constant) throws IOException {
        switch(constant.type) {
            case NIL:
                dos.writeByte(0);
                break;

            case BOOLEAN:
                dos.writeByte(1);

                // Data is 1 byte for boolean according to ChunkSpy src
                if (constant._bv) {
                    dos.writeByte(1);
                }
                else {
                    dos.writeByte(0);
                }
                break;

            case NUMBER:
                dos.writeByte(3);
                writeDouble(dos, constant._nv);
                break;

            case STRING:
                dos.writeByte(4);
                writeString(dos, constant._sv);
                break;

            default:
                throw new RuntimeException("Bad constant type in writeConstant");
        }
    }

    private static void writeConstants(DataOutputStream dos, List<LuaValue> constants) throws IOException {
        writeInt(dos, constants.size());

        for (LuaValue constant : constants) {
            writeConstant(dos, constant);
        }
    }

    private static void writePrototypes(DataOutputStream dos, List<LuaChunk> chunks) throws IOException {
        writeInt(dos, chunks.size());

        for (LuaChunk chunk : chunks) {
            writeChunk(dos, chunk, false);
        }
    }

    private static void writeString(DataOutputStream dos, String s) throws IOException {
        writeInt(dos, s.length() + 1);

        for (int i = 0; i < s.length(); i++) {
            dos.writeByte(s.charAt(i));
        }

        dos.writeByte(0);
    }

    public static void write(OutputStream os, LuaChunk chunk) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);

        writeHeader(dos);
        writeChunk(dos, chunk, true);
    }
}
