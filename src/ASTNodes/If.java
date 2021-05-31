package ASTNodes;

import java.util.ArrayList;
import java.util.List;

public class If extends Statement {
    public Pair<Expression, Block> ifstmt;
    public List<Pair<Expression, Block>> elseifstmt = new ArrayList<>();
    public Block elsestmt;

    // cannot be represented on a line, call line() on members

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();

        children.add(ifstmt);

        for (Pair<Expression, Block> p : elseifstmt) {
            children.add(p);
        }

        if (elsestmt != null) {
            children.add(elsestmt);
        }

        return children;
    }

    @Override
    public boolean matches(Node obj) {
        if (super.matches(obj)) {
            If stmt = (If)obj;

            if (elsestmt == null && stmt.elsestmt != null || elsestmt != null && stmt.elsestmt == null) {
                return false;
            }

            if(elsestmt != null && !elsestmt.matches(stmt.elsestmt)) {
                return false;
            }

            if (!ifstmt.matches(stmt.ifstmt)) {
                return false;
            }

            if (elseifstmt.size() != stmt.elseifstmt.size()) {
                return false;
            }

            for (int i = 0; i < elseifstmt.size(); i++) {
                if (!elseifstmt.get(i).matches(stmt.elseifstmt.get(i))) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public If clone() {
        If stmt = new If();

        stmt.ifstmt = ifstmt.clone();

        for (Pair<Expression, Block> p : elseifstmt) {
            stmt.elseifstmt.add(p.clone());
        }

        if (elsestmt != null) {
            stmt.elsestmt = elsestmt.clone();
        }

        return stmt;
    }

    @Override
    public void acceptChildren(IASTVisitor visitor) {
        ifstmt = (Pair)ifstmt.accept(visitor);

        for (int i = 0; i < elseifstmt.size(); i++) {
            Node node = elseifstmt.get(i).accept(visitor);

            if (node != elseifstmt) {
                elseifstmt.set(i, (Pair<Expression, Block>)node);
            }
        }

        if (elsestmt != null) {
            elsestmt = (Block) elsestmt.accept(visitor);
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
