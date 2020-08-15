// generated with ast extension for cup
// version 0.8
// 31/0/2020 19:32:26


package rs.ac.bg.etf.pp1.ast;

public class SingleFormalParam extends FormalParsList {

    private FormalDecl FormalDecl;

    public SingleFormalParam (FormalDecl FormalDecl) {
        this.FormalDecl=FormalDecl;
        if(FormalDecl!=null) FormalDecl.setParent(this);
    }

    public FormalDecl getFormalDecl() {
        return FormalDecl;
    }

    public void setFormalDecl(FormalDecl FormalDecl) {
        this.FormalDecl=FormalDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormalDecl!=null) FormalDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalDecl!=null) FormalDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalDecl!=null) FormalDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleFormalParam(\n");

        if(FormalDecl!=null)
            buffer.append(FormalDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleFormalParam]");
        return buffer.toString();
    }
}
