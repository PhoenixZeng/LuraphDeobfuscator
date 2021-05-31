import ASTNodes.*;
import ASTNodes.Number;

import java.util.Stack;

// beautifier at https://goonlinetools.com/lua-beautifier/

public class ASTSourceGenerator {
    private Block root;
    private StringBuilder sb;
    private int depth;

    public ASTSourceGenerator(Node root) {
        this.root = (Block)root;
    }

    public String generate() {
        sb = new StringBuilder();

        depth = 0;

        visitBlock((Block)root);

        return sb.toString();
    }

    private void indent() {
        for (int i = 0; i < depth; i++) {
            sb.append("  ");
        }
    }

    private void newline()   {
        sb.append(System.lineSeparator());
    }

    private void visitBlock(Block block) {
        for (Statement stmt : block.stmts) {
            buildSource(stmt);
        }
    }

    private void gen(String s) {
        indent();
        sb.append(s);
        newline();
    }

    private void genBlock(Block block, boolean end) {
        newline();
        ++depth;
        visitBlock(block);
        if (block.ret != null) {
            gen(block.ret.line());
        }
        --depth;
        if (end) {
            gen("end");
            newline();
        }
    }

    private void genExprList(ExprList list) {
        for (int i = 0; i < list.exprs.size(); i++) {
            Expression expr = list.exprs.get(i);

            if (expr instanceof Function) {
                sb.append(expr.line());
                genBlock(((Function)expr).block, true);
            }
            else if (expr instanceof FunctionCall) {
                FunctionCall call = (FunctionCall)expr;

                sb.append(call.varOrExp.line());

                sb.append("(");

                boolean nested = false;

                for (NameAndArgs it : call.nameAndArgs) {
                    if (it.name == null && it.args instanceof ExprList) {
                        nested = true;
                        genExprList((ExprList)it.args);
                    }
                }

                sb.append(")");
            }
            else if (expr instanceof TableConstructor) {
                TableConstructor ctor = (TableConstructor)expr;

                sb.append("{ ");

                for (int j = 0; j < ctor.entries.size(); j++) {
                    Pair<Expression, Expression> entry = ctor.entries.get(j);

                    if (entry.first != null) {
                        sb.append("[" + entry.first.line() + "] = ");
                    }

                    if (entry.second != null) {
                        if (entry.second instanceof Function) {
                            Function fn = (Function)entry.second;
                            ExprList temp = new ExprList();
                            temp.exprs.add(fn);
                            genExprList(temp);
                        }
                        else {
                            sb.append(entry.second.line());
                        }
                    }

                    if (j < ctor.entries.size() - 1) {
                        sb.append(", ");
                    }
                }

                sb.append(" }");
            }
            else {
                sb.append(expr.line());
            }

            if (i < list.exprs.size() - 1) {
                sb.append(", ");
            }
        }
    }

    private void genConditional(String token, Pair<Expression, Block> p) {
        indent();
        sb.append(token + " ");
        sb.append(p.first.line());
        sb.append(" then");
        genBlock(p.second, false);
    }

    private void buildSource(Node node) {
        if (node instanceof Assign) {
            Assign stmt = (Assign)node;

            indent();
            sb.append(stmt.vars.line() + " = ");

            genExprList(stmt.exprs);
        }
        else if (node instanceof LocalDeclare) {
            LocalDeclare stmt = (LocalDeclare)node;

            indent();
            sb.append("local " + stmt.names.line());

            if (stmt.exprs.exprs.size() > 0) {
                sb.append(" = ");
                genExprList(stmt.exprs);
            }
        }
        else if (node instanceof Do) {
            gen(node.line());
            genBlock(((Do)node).block, true);
        }
        else if (node instanceof While) {
            gen(node.line());
            genBlock(((While)node).block, true);
        }
        else if (node instanceof Repeat) {
            indent();
            sb.append(node.line());
            genBlock(((Repeat)node).block, false);
            gen("until " + ((Repeat)node).expr.line());
        }
        else if (node instanceof If) {
            If stmt = (If)node;
            genConditional("if", stmt.ifstmt);

            for (Pair<Expression, Block> p : stmt.elseifstmt) {
                genConditional("elseif", p);
            }

            if (stmt.elsestmt != null) {
                indent();
                sb.append("else");
                genBlock(stmt.elsestmt, false);
            }

            gen("end");
        }
        else if (node instanceof ForStep) {
            ForStep stmt = (ForStep) node;
            gen(stmt.line());
            genBlock(stmt.block, true);

        }
        else if (node instanceof ForIn) {
            ForIn stmt = (ForIn) node;
            gen(stmt.line());
            genBlock(stmt.block, true);

        }
        else if (node instanceof Function) {
            Function stmt = (Function) node;
            gen(stmt.line());
            genBlock(stmt.block, true);
        }
        else {
            indent();
            sb.append(node.line());
            newline();
        }

        newline();

    }

}
