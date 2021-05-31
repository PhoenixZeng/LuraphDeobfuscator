import ASTNodes.*;

public class ASTConstantPropagator extends ASTOptimizerBase {
    private Node replaceVar(Variable node) {
        SymbolTable.VarInfo info = mgr.symbols.getScopedVariable(node.symbol());

        if (info != null && info.isConstant()) {
            return info.value.clone();
        }

        return node;
    }

    @Override
    public Node visit(Assign node) {
        Assign copy = null;

        for (int i = 0; i < node.exprs.exprs.size(); i++) {
            Node expr = node.exprs.exprs.get(i);
            if (expr instanceof Variable) {
                Node replaced = replaceVar((Variable)expr);

                if (copy == null && replaced != expr) {
                    copy = node.clone();
                }

                if (replaced != expr) {
                    copy.exprs.exprs.set(i, (Expression)replaced);
                }
            }
        }

        return copy != null ? copy : node;
    }


    @Override
    public Node visit(LocalDeclare node) {
        LocalDeclare copy = null;

        for (int i = 0; i < node.exprs.exprs.size(); i++) {
            Node expr = node.exprs.exprs.get(i);
            if (expr instanceof Variable) {
                Node replaced = replaceVar((Variable)expr);

                if (copy == null && replaced != expr) {
                    copy = node.clone();
                }

                if (replaced != expr) {
                    copy.exprs.exprs.set(i, (Expression)replaced);
                }
            }
        }

        return copy != null ? copy : node;
    }

    @Override
    public Node visit(BinaryExpression node) {
        BinaryExpression copy = null;

        if (node.left instanceof Variable) {
            Expression expr = (Expression)replaceVar((Variable)node.left);

            if (copy == null && expr != node.left) {
                copy = node.clone();
            }

            if (expr != node.left) {
                copy.left = expr;
            }
        }

        if (node.right instanceof Variable) {
            Expression expr = (Expression)replaceVar((Variable)node.right);

            if (copy == null && expr != node.right) {
                copy = node.clone();
            }

            if (expr != node.right) {
                copy.right = expr;
            }
        }

        return copy != null ? copy : node;
    }

    @Override
    public Node visit(UnaryExpression node) {
        UnaryExpression copy = null;

        if (node.expr instanceof Variable) {
            Expression expr = (Expression)replaceVar((Variable)node.expr);

            if (copy == null && expr != node.expr) {
                copy = node.clone();
            }

            if (expr != node.expr) {
                copy.expr = expr;
            }
        }

        return copy != null ? copy : node;
    }

    @Override
    public Node visit(FunctionCall node) {
        if (node.varOrExp instanceof Variable) {
            Expression expr = (Expression)replaceVar((Variable)node.varOrExp);
            if (expr != node.varOrExp) {
                FunctionCall call = node.clone();
                call.varOrExp = expr;
                return call;
            }
        }

        return node;
    }

    @Override
    public Node visit(ExprList node) {
        ExprList copy = null;

        for (int i = 0; i < node.exprs.size(); i++) {
            Node expr = node.exprs.get(i);
            if (expr instanceof Variable) {
                Node replaced = replaceVar((Variable)expr);

                if (copy == null && replaced != expr) {
                    copy = node.clone();
                }

                if (replaced != expr) {
                    copy.exprs.set(i, (Expression)replaced);
                }
            }
        }

        return copy != null ? copy : node;
    }
}
