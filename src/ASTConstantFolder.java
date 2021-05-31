import ASTNodes.*;
import ASTNodes.Number;

public class ASTConstantFolder extends ASTOptimizerBase {
    private Node foldTableLengthOptimization(UnaryExpression node) {
        if (node.op == UnaryExpression.Operator.HASHTAG && (node.expr instanceof TableConstructor)) {
            TableConstructor ctor = (TableConstructor)node.expr;

            boolean canOptimize = true;

            // all entries <null, Literal>
            for (Pair<Expression, Expression> entry : ctor.entries) {
                if (entry.first != null || !(entry.second instanceof Literal)) {
                    canOptimize = false;
                    break;
                }
            }

            if (canOptimize) {
                return new Number(Integer.toString(ctor.entries.size()));
            }
        }

        return node;
    }

    private Node foldNumberNegation(UnaryExpression node) {
        if (node.op == UnaryExpression.Operator.MINUS && (node.expr instanceof Number)) {
            return new Number(((Number) node.expr).value * -1);
        }

        return node;
    }

    private Node foldDoubleNegation(UnaryExpression node) {
        if (node.op == UnaryExpression.Operator.TILDE && (node.expr instanceof UnaryExpression)) {
            UnaryExpression child = (UnaryExpression)node.expr;
            if (child.op == UnaryExpression.Operator.TILDE) {
                return child.expr.clone();
            }
        }
        else if (node.op == UnaryExpression.Operator.NOT && (node.expr instanceof UnaryExpression)) {
            UnaryExpression child = (UnaryExpression)node.expr;
            if (child.op == UnaryExpression.Operator.NOT) {
                return child.expr.clone();
            }
        }

        return node;
    }

    private void swap(BinaryExpression node) {
        Expression temp = node.left;
        node.left = node.right;
        node.right = node.left;
    }

    private boolean isVarBinExp(Expression _node) {
        if (!(_node instanceof BinaryExpression)) {
            return false;
        }

        BinaryExpression node = (BinaryExpression)_node;

        return (!(node.left instanceof Number) && node.right instanceof Number) ||
                (node.left instanceof Number && !(node.right instanceof  Number));
    }

    // c commutative, a associative
    // (0) (A + 1) - 1 --> (A + 1) + (-1)
    //     (A - 1) + 1 --> (A + (-1)) + 1
    // (1) (1 c A) c 1 --> (A c 1) c 1
    // (2) (A a 1) a 1 --> A a (1 a 1)

    // precondition for rewrites: valid rewrite format VarBinExp op Const

    private BinaryExpression rewriteBinExpRule0(BinaryExpression node) {
        if (isVarBinExp(node.left)) {
            BinaryExpression left = (BinaryExpression)node.left;
            if (left.operator == BinaryExpression.Operator.ADD && node.operator == BinaryExpression.Operator.SUB) {
                node.operator = BinaryExpression.Operator.ADD;
                node.right = new Number(((Number)node.right).value * (-1));
                // dont need to clone because (1) or (2) will
            }
            else if (left.operator == BinaryExpression.Operator.SUB && node.operator == BinaryExpression.Operator.ADD) {
                left.operator = BinaryExpression.Operator.ADD;
                left.right = new Number(((Number)left.right).value * (-1));
                // dont need to clone because (1) or (2) will
            }
        }

        return node;
    }

    private BinaryExpression rewriteBinExpRule1(BinaryExpression node) {
        if (isVarBinExp(node.left)) {
            BinaryExpression left = (BinaryExpression)node.left;
            if (left.left instanceof Number && left.isCommutative() && node.operator == left.operator) {
                BinaryExpression cloned = node.clone();
                swap((BinaryExpression)cloned.left);
                return cloned;
            }
        }
        else {
            BinaryExpression right = (BinaryExpression)node.right;
            if (right.right instanceof Number && right.isCommutative() && node.operator == right.operator) {
                BinaryExpression cloned = node.clone();
                swap((BinaryExpression)cloned.right);
                return cloned;
            }
        }

        return node;
    }

    private BinaryExpression rewriteBinExpRule2(BinaryExpression node) {
        if (isVarBinExp(node.left)) {
            BinaryExpression left = (BinaryExpression)node.left;
            if (left.right instanceof Number && left.operator == node.operator) {
                BinaryExpression cloned = node.clone();
                BinaryExpression clonedLeft = (BinaryExpression)cloned.left;
                Expression temp = cloned.right;
                cloned.right = clonedLeft.left;
                clonedLeft.left = temp;
                return cloned;
            }
        }
        else {
            BinaryExpression right = (BinaryExpression)node.right;
            if (right.left instanceof Number && right.operator == node.operator) {
                BinaryExpression cloned = node.clone();
                BinaryExpression clonedRight = (BinaryExpression)cloned.right;
                Expression temp = cloned.left;
                cloned.left = clonedRight.right;
                clonedRight.right = temp;
                return cloned;
            }
        }

        return node;
    }

    private BinaryExpression rewriteBinExp(BinaryExpression node) {
        if (isVarBinExp(node.left) && node.right instanceof Number ||
                isVarBinExp(node.right) && node.left instanceof Number) {

            // (0)
            node = rewriteBinExpRule0(node);

            // (1)
            node = rewriteBinExpRule1(node);

            // (2)
            node = rewriteBinExpRule2(node);

        }

        return node;
    }

    // are we going to do LOGICAL and BITWISE here?
    private Node foldNumberBinaryExpression(BinaryExpression node) {
        if (node.left instanceof Number && node.right instanceof Number) {
            double left = ((Number) node.left).value;
            double right = ((Number) node.right).value;

            switch (node.operator) {
                default:
                    break;
                case POW:
                    return new Number(Math.pow(left, right));
                case MUL:
                    return new Number(left * right);
                case INTEGER_DIV:
                    return new Number(Math.floor(left / right));
                case REAL_DIV:
                    return new Number(left / right);
                case MOD:
                    return new Number(left % right);
                case ADD:
                    return new Number(left + right);
                case SUB:
                    return new Number(left - right);
                case LT:
                    return left < right ? new True() : new False();
                case GT:
                    return left > right ? new True() : new False();
                case LTE:
                    return left <= right ? new True() : new False();
                case GTE:
                    return left >= right ? new True() : new False();
                case NEQ:
                    return left != right ? new True() : new False();
                case EQ:
                    return left == right ? new True() : new False();
            }
        }

        return node;
    }

    private boolean isFoldableVariableName(String name) {
        return ConstantData.isConstant(name);
    }

    private Node foldVariableName(Variable var) {
        if (var.name instanceof LuaString) {
            if (var.suffixes.size() == 0 && isFoldableVariableName(var.line())) {
                return new LuaString(var.line());
            }
            else {
                boolean tailCall = false;

                for (Suffix s : var.suffixes) {
                    if (s.nameAndArgs.size() != 0) {
                        tailCall = true;
                        break;
                    }
                }

                if (!tailCall && isFoldableVariableName(var.line())) {
                    return new LuaString(var.line());
                }
            }
        }

        return var;
    }

    private Node foldTableUnpack(TableConstructor ctor) {
        if (ctor.entries.size() == 1) {
            Pair<Expression, Expression> p = ctor.entries.get(0);

            if (p.first == null && p.second instanceof FunctionCall) {
                FunctionCall call = (FunctionCall)p.second;

                if (call.varOrExp.line().equals("unpack") & call.nameAndArgs.size() == 1 &&
                        call.nameAndArgs.get(0).name == null &&
                        call.nameAndArgs.get(0).args instanceof ExprList) {

                    ExprList list = (ExprList)call.nameAndArgs.get(0).args;

                    if (list.exprs.size() == 3 && list.exprs.get(0) instanceof TableConstructor) {
                        TableConstructor a1 = (TableConstructor)list.exprs.get(0);

                        if (a1.entries.size() == 0) {
                            return new TableConstructor();
                        }
                    }
                }
            }
        }

        return  ctor;
    }

    @Override
    public Node visit(Variable node) {
        return foldVariableName(node);
    }

    @Override
    public Node visit(UnaryExpression node) {
        Node replaced = foldTableLengthOptimization(node);

        if (node != replaced) {
            return replaced;
        }

        replaced = foldNumberNegation(node);

        if (node != replaced) {
            return replaced;
        }

        replaced = foldDoubleNegation(node);

        return replaced;
    }

    @Override
    public Node visit(BinaryExpression node) {
        // rewriting is safe eg can continue optimizing after
        node = rewriteBinExp(node);

        Node replaced = foldNumberBinaryExpression(node);

        return replaced;
    }

    @Override
    public Node visit(TableConstructor node) {
        return foldTableUnpack(node);
    }
}
