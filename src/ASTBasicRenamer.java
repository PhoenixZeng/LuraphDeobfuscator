import ASTNodes.*;

public class ASTBasicRenamer extends ASTOptimizerBase {
    private int varCounter;
    private int funcCounter;

    private String varName = "var";
    private String funcName = "func";

    public ASTBasicRenamer() {
        varCounter = 0;
        funcCounter = 0;
    }

    private void rename(String name) {
        // dont rename keyword
        if(name.equals("...")) {
            return;
        }

        SymbolTable.VarInfo info = mgr.symbols.getScopedVariable(name);

        if (info != null && info.rename == null) {
            if (info.value instanceof Function) {
                info.rename = funcName + funcCounter++;
            }
            else {
                info.rename = varName + varCounter++;
            }
        }
    }

    // Handle function and loop construct renaming
    @Override
    public Node visit(Block node) {
        if (node.parent instanceof Function) {
            Function fn = (Function)node.parent;

            for (LuaString arg : fn.params.names) {
                rename(arg.symbol());
            }
        }
        else if (node.parent instanceof ForStep) {
            ForStep stmt = (ForStep)node.parent;

            rename(stmt.iteratorName.value);
        }
        else if (node.parent instanceof ForIn) {
            ForIn stmt = (ForIn)node.parent;

            for (LuaString name : stmt.names.names) {
                rename(name.value);
            }
        }

        return node;
    }

    @Override
    public Node visit(Variable node) {
        if (node.name instanceof LuaString) {
            rename(node.symbol());
        }

        return node;
    }

    @Override
    public Node visit(LuaString node) {
        rename(node.symbol());

        return node;
    }
}
