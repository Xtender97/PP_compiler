// generated with ast extension for cup
// version 0.8
// 31/0/2020 19:32:26


package rs.ac.bg.etf.pp1.ast;

public class NullConst extends Const {

    private String nullconst;

    public NullConst (String nullconst) {
        this.nullconst=nullconst;
    }

    public String getNullconst() {
        return nullconst;
    }

    public void setNullconst(String nullconst) {
        this.nullconst=nullconst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NullConst(\n");

        buffer.append(" "+tab+nullconst);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NullConst]");
        return buffer.toString();
    }
}
