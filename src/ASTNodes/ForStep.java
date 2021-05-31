package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class ForStep extends Statement{
    public LuaString iteratorName;
    public Expression init;
    public Expression condition;
    public Expression step; // optional
    public Block block;

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(iteratorName);
        children.add(init);
        children.add(condition);

        if (step != null) {
            children.add(step);
        }

        children.add(block);

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            ForStep stmt = (ForStep)obj;

            if (step == null && stmt.step != null || step != null && stmt.step == null) {
                return false;
            }

            if (step != null && !step.matches(stmt.step)) {
                return false;
            }

            return iteratorName.matches(stmt.iteratorName) &&
                    init.matches(stmt.init) &&
                    condition.matches(stmt.condition) &&
                    block.matches(stmt.block);

        }

        return false;
    }

    @Override
    public String line() {
        String s = "for ";

        s += iteratorName + " = " + init.line() + ", " + condition.line();

        if (step != null) {
            s += ", " + step.line();
        }

        s += " do";

        return s;
    }

    @Override
    public ForStep clone() {
        ForStep stmt = new ForStep();

        stmt.iteratorName = iteratorName.clone();
        stmt.init = init.clone();
        stmt.condition = condition.clone();

        if (step != null) {
            stmt.step = step.clone();
        }

        stmt.block = block.clone();
        stmt.block.parent = stmt;

        return stmt;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        iteratorName = (LuaString)iteratorName.accept(visitor);
        init = (Expression)init.accept(visitor);
        condition = (Expression)condition.accept(visitor);

        if (step != null) {
            step = (Expression)step.accept(visitor);
        }

        block = (Block)block.accept(visitor);
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
