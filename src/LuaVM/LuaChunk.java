package LuaVM;

import java.util.ArrayList;
import java.util.List;

public class LuaChunk {
    public List<VMInstruction> insns = new ArrayList<>();
    public List<LuaValue> constants = new ArrayList<>();
    public List<LuaChunk> prototypes = new ArrayList<>();
    public List<Double> debugLines = new ArrayList<>();

    public double numUpVals;
    public double numParams;
    public double maxStackSize;
    public double isVarArgFlag;

    public int lineDefined;

    public void print() {
        System.out.println("DUMPING CHUNK");
        System.out.println(toString());
        System.out.println("END CHUNK");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("#UPVALUES = " + numUpVals);
        sb.append(System.lineSeparator());

        sb.append("#PARAMS = " + numParams);
        sb.append(System.lineSeparator());

        sb.append("#MAXSTACKSIZE = " + maxStackSize);
        sb.append(System.lineSeparator());

        sb.append("#CONSTANTS = " + constants.size());
        sb.append(System.lineSeparator());

        sb.append("#INSTRUCTIONS = " + insns.size());
        sb.append(System.lineSeparator());

        sb.append("#PROTOTYPES = " + prototypes.size());
        sb.append(System.lineSeparator());

        sb.append(System.lineSeparator());

        for (int i = 0; i < constants.size(); i++) {
            sb.append("Constant " + i + " = " + constants.get(i).toString() + System.lineSeparator());
        }

        sb.append(System.lineSeparator());

        for (int i = 0; i < insns.size(); i++) {
            sb.append("[" + i + "] " + insns.get(i).toString() + System.lineSeparator());
        }

        sb.append(System.lineSeparator());

        for (int i = 0; i < prototypes.size(); i++) {
            sb.append("[CHUNK " + i + "]");
            sb.append(System.lineSeparator());
            sb.append(prototypes.get(i).toString());
        }

        return sb.toString();
    }
}
