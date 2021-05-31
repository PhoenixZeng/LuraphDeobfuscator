import ASTNodes.*;
import ASTNodes.Number;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// WARNING: Replacing blocks will mess up symbol tables for renamers.
// Do not replace blocks for renamer routines;

// Optimizations may result in incorrect code
// their only purpose is to simplify LUA for structural matching purposes

public class ASTOptimizerMgr {
    private List<ASTOptimizerBase> optimizers = new ArrayList<>();
    private boolean renamer;
    private Block root;
    private int optimizationCount;

    public SymbolTable symbols;

    public ASTOptimizerMgr(Node root) {
        renamer = false;
        this.root = (Block)root;
    }

    public void addOptimizer(ASTOptimizerBase optimizer) {
        optimizers.add(optimizer);
        optimizer.setMgr(this);
    }

    public void addRenamer(ASTOptimizerBase renamer) {
        optimizers.add(renamer);
        renamer.setMgr(this);

        this.renamer = true;
    }

    // returns number of optimizations applied
    public int optimizeOneIter() {
        optimizationCount = 0;

        // Root symbol table is not associated with any block and should be empty
        symbols = new SymbolTable(null);

        // run optimizations
        root.accept(new Visitor());

        // run renamer
        if (renamer) {
            root.accept(new Renamer());
        }

        return optimizationCount;
    }

    // fixed point algorithm
    public void optimize() {
        while (optimizeOneIter() > 0);
    }

    // Rename symbols
    private class Renamer extends ASTVisitor {
        @Override
        public void enterBlock(Block block) {
            symbols = symbols.getScope(block);
        }

        @Override
        public void exitBlock(Block block) {
            symbols = symbols.parent;
        }

        @Override
        public Node visit(Block node) {
            if (node.parent instanceof Function) {
                Function fn = (Function)node.parent;

                for (LuaString arg : fn.params.names) {
                    SymbolTable.VarInfo info = symbols.getScopedVariable(arg.symbol());

                    if (info != null && info.rename != null) {
                        arg.value = info.rename;
                    }
                }
            }
            else if (node.parent instanceof ForStep) {
                ForStep stmt = (ForStep)node.parent;

                SymbolTable.VarInfo info = symbols.getScopedVariable(stmt.iteratorName.symbol());

                if (info != null && info.rename != null) {
                    stmt.iteratorName.value = info.rename;
                }
            }
            else if (node.parent instanceof ForIn) {
                ForIn stmt = (ForIn)node.parent;

                for (LuaString name : stmt.names.names) {
                    SymbolTable.VarInfo info = symbols.getScopedVariable(name.symbol());

                    if (info != null && info.rename != null) {
                        name.value = info.rename;
                    }
                }
            }

            return node;
        }

        @Override
        public Node visit(Variable node) {
            if (node.name instanceof LuaString) {
                SymbolTable.VarInfo info = symbols.getScopedVariable(node.symbol());

                if (info != null && info.rename != null) {
                    ((LuaString)node.name).value = info.rename;
                }
            }

            return node;
        }

        @Override
        public Node visit(LuaString node) {
            SymbolTable.VarInfo info = symbols.getScopedVariable(node.symbol());

            if (info != null && info.rename != null) {
                node.value = info.rename;
            }

            return node;
        }
    }

    private class Visitor extends ASTVisitor {
        private void setSymbol(String name, Expression value) {
            symbols.getScopedVariable(name).setValue(value);
        }

        @Override
        public void enterBlock(Block block) {
            symbols = symbols.createChild(block);
        }

        @Override
        public void exitBlock(Block block) {
            symbols = symbols.parent;
        }

        @Override
        public Node visit(Assign node) {
            // Invalidate constants
            for (int i = 0; i < node.vars.vars.size(); i++) {
                Node n = node.vars.vars.get(i);

                SymbolTable.VarInfo info = symbols.getScopedVariable(n.symbol());

                if (info != null) {
                    setSymbol(n.symbol(), null);
                }
            }

            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            for (int i = 0; i < node.vars.vars.size(); i++) {
                Node n = node.vars.vars.get(i);

                SymbolTable.VarInfo info = symbols.getScopedVariable(n.symbol());

                if (info == null) {
                    symbols.addGlobal(n.symbol(), null);
                }

                if (i < node.exprs.exprs.size()) {
                    setSymbol(n.symbol(), null);
                }
            }

            return node;
        }

        @Override
        public Node visit(BinaryExpression node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Block node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            if (node.parent instanceof Function) {
                Function fn = (Function)node.parent;

                for (LuaString arg : fn.params.names) {
                    symbols.add(arg.symbol(), null);
                }
            }
            else if (node.parent instanceof ForStep) {
                ForStep stmt = (ForStep)node.parent;

                symbols.add(stmt.iteratorName.value, null);
            }
            else if (node.parent instanceof ForIn) {
                ForIn stmt = (ForIn)node.parent;

                for (LuaString name : stmt.names.names) {
                    symbols.add(name.value, null);
                }
            }

            return node;
        }

        @Override
        public Node visit(Break node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Do node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Expression node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(ExprList node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(False node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(ForIn node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(ForStep node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Function node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            if (node.name != null) {
                symbols.add(node.name.value, node);
            }

            return node;
        }

        @Override
        public Node visit(FunctionCall node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(GoTo node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(If node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Label node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Literal node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(LocalDeclare node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            for (int i = 0; i < node.names.names.size(); i++) {
                symbols.add(node.names.names.get(i).symbol(), null);

                if (i < node.exprs.exprs.size()) {
                    setSymbol(node.names.names.get(i).symbol(), node.exprs.exprs.get(i));
                }
            }

            return node;
        }

        @Override
        public Node visit(LuaString node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(NameAndArgs node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(NameList node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Nil node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Node node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Number node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Pair node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Repeat node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Return node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Suffix node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(TableConstructor node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(True node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(UnaryExpression node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(Variable node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(VarList node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }

        @Override
        public Node visit(While node) {
            for (ASTOptimizerBase optimizer : optimizers) {
                Node replaced = optimizer.visit(node);

                if (replaced != node) {
                    ++optimizationCount;
                    return replaced;
                }
            }

            return node;
        }
    }

}
