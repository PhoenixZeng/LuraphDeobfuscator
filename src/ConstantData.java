import java.util.Arrays;

public class ConstantData {
    public static boolean isConstant(String name) {
        String[] foldableNames = {
                "assert",
                "select",
                "tonumber",
                "unpack",
                "pcall",
                "setfenv",
                "setmetatable",
                "type",
                "getfenv",
                "tostring",
                "error",
                "string.sub",
                "string.byte",
                "string.char",
                "string.rep",
                "string.gsub",
                "string.match"
        };

        return Arrays.asList(foldableNames).contains(name);
    }
}
