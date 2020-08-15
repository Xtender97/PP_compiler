// generated with ast extension for cup
// version 0.8
// 31/0/2020 19:32:26


package rs.ac.bg.etf.pp1.ast;

public class CommaArrayVarDeclaration extends CommaVarDecl {

    private String varId;

    public CommaArrayVarDeclaration (String varId) {
        this.varId=varId;
    }

    public String getVarId() {
        return varId;
    }

    public void setVarId(String varId) {
        this.varId=varId;
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
        buffer.append("CommaArrayVarDeclaration(\n");

        buffer.append(" "+tab+varId);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CommaArrayVarDeclaration]");
        return buffer.toString();
    }
}
