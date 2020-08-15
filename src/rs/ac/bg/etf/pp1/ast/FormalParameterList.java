// generated with ast extension for cup
// version 0.8
// 31/0/2020 19:32:26


package rs.ac.bg.etf.pp1.ast;

public class FormalParameterList extends FormalParsList {

    private FormalParsList FormalParsList;
    private FormalDecl FormalDecl;

    public FormalParameterList (FormalParsList FormalParsList, FormalDecl FormalDecl) {
        this.FormalParsList=FormalParsList;
        if(FormalParsList!=null) FormalParsList.setParent(this);
        this.FormalDecl=FormalDecl;
        if(FormalDecl!=null) FormalDecl.setParent(this);
    }

    public FormalParsList getFormalParsList() {
        return FormalParsList;
    }

    public void setFormalParsList(FormalParsList FormalParsList) {
        this.FormalParsList=FormalParsList;
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
        if(FormalParsList!=null) FormalParsList.accept(visitor);
        if(FormalDecl!=null) FormalDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalParsList!=null) FormalParsList.traverseTopDown(visitor);
        if(FormalDecl!=null) FormalDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalParsList!=null) FormalParsList.traverseBottomUp(visitor);
        if(FormalDecl!=null) FormalDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalParameterList(\n");

        if(FormalParsList!=null)
            buffer.append(FormalParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormalDecl!=null)
            buffer.append(FormalDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParameterList]");
        return buffer.toString();
    }
}
