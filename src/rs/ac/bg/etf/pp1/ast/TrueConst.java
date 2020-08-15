// generated with ast extension for cup
// version 0.8
// 31/0/2020 19:32:26


package rs.ac.bg.etf.pp1.ast;

public class TrueConst extends Const {

    private String constant;

    public TrueConst (String constant) {
        this.constant=constant;
    }

    public String getConstant() {
        return constant;
    }

    public void setConstant(String constant) {
        this.constant=constant;
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
        buffer.append("TrueConst(\n");

        buffer.append(" "+tab+constant);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TrueConst]");
        return buffer.toString();
    }
}
