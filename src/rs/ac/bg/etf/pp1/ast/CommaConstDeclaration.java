// generated with ast extension for cup
// version 0.8
// 31/0/2020 19:32:26


package rs.ac.bg.etf.pp1.ast;

public class CommaConstDeclaration extends CommaConstDecl {

    private String constId;
    private Const Const;

    public CommaConstDeclaration (String constId, Const Const) {
        this.constId=constId;
        this.Const=Const;
        if(Const!=null) Const.setParent(this);
    }

    public String getConstId() {
        return constId;
    }

    public void setConstId(String constId) {
        this.constId=constId;
    }

    public Const getConst() {
        return Const;
    }

    public void setConst(Const Const) {
        this.Const=Const;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Const!=null) Const.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Const!=null) Const.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Const!=null) Const.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CommaConstDeclaration(\n");

        buffer.append(" "+tab+constId);
        buffer.append("\n");

        if(Const!=null)
            buffer.append(Const.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CommaConstDeclaration]");
        return buffer.toString();
    }
}
