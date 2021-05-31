package ASTNodes;

import java.util.ArrayList;
import java.util.List;

// Does not include suffix function calls
// eg (0):test()[name] = 0
public class FunctionCall extends Expression {
    public Expression varOrExp; // may also be a literal
    public List<NameAndArgs> nameAndArgs = new ArrayList<>();

    // if the function call occurred in a statement grammar
    public boolean isStatement = false;

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(varOrExp);

        for (NameAndArgs it : nameAndArgs) {
            children.add(it);
        }

        return children;
    }

    @Override
    public String line() {
        String s = "";

        s += varOrExp.line();

        s += "(";

        for (NameAndArgs it : nameAndArgs) {
            s += it.line();
        }

        return s + ")";
    }

    @Override
    public String toString() {
        return "call";
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            FunctionCall call = (FunctionCall)obj;

            if (nameAndArgs.size() != call.nameAndArgs.size()) {
                return false;
            }

            for (int i = 0; i < nameAndArgs.size(); i++) {
                if (!nameAndArgs.get(i).matches(call.nameAndArgs.get(i))) {
                    return false;
                }
            }

            return varOrExp.matches(call.varOrExp);
        }

        return false;
    }

    @Override
    public FunctionCall clone() {
        FunctionCall call = new FunctionCall();

        call.varOrExp = varOrExp.clone();

        for (NameAndArgs it : nameAndArgs) {
            call.nameAndArgs.add(it.clone());
        }

        return call;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        varOrExp = (Expression)varOrExp.accept(visitor);

        for (int i = 0; i < nameAndArgs.size(); i++){
            Node node = nameAndArgs.get(i).accept(visitor);

            nameAndArgs.set(i, (NameAndArgs)node);
        }
    }

    @Override
    public Node accept(IASTVisitor visitor) {
        Node replacement = visitor.visit(this);

        if (replacement != this) {
            return replacement;
        }

        acceptChildren(visitor);

        return this;
    }
}
