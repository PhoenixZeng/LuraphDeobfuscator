import java.util.ArrayList;
import java.util.List;

import ASTNodes.*;

public class SymbolTable {
    public SymbolTable parent;
    public List<SymbolTable> children = new ArrayList<>();
    public List<VarInfo> vars = new ArrayList<>();
    public Block block;

    public SymbolTable createChild(Block block) {
        SymbolTable child = new SymbolTable(block);
        child.parent = this;
        children.add(child);
        return child;
    }

    public SymbolTable getScope(Block block) {
        if (this.block == block) {
            return this;
        }

        for (SymbolTable child : children) {
            if (child.block == block) {
                return child;
            }
        }

        return null;
    }

    public SymbolTable(Block block) {
        this.block = block;
    }

    public void add(String name, Expression value) {
        add(name, value, null);
    }

    public void add(String name, Expression value, String rename) {
        if (!vars.contains(name)) {
            VarInfo info = new VarInfo(name, value);
            info.rename = rename;
            vars.add(info);
        }
    }

    public SymbolTable getRoot() {
        SymbolTable root = this;

        while (root.parent != null) {
            root = root.parent;
        }

        return root;
    }

    public void addGlobal(String name, Expression value) {
        getRoot().add(name, value);
    }

    public VarInfo getScopedVariable(String name) {
        for (VarInfo var : vars) {
            if (var.name.equals(name)) {
                return var;
            }
        }

        return parent == null ? null : parent.getScopedVariable(name);
    }

    public class VarInfo {
        String name;
        Expression value;
        String rename;

        public VarInfo(String name, Expression value) {
            this.name = name;
            this.value = value;
        }

        public void setValue(Expression value) {
            this.value = value;
        }

        public boolean isConstant() {
            if (value != null && value instanceof LuaString) {
                return ConstantData.isConstant(((LuaString)value).value);
            }

            return false;
        }
    }
}
