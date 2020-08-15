// generated with ast extension for cup
// version 0.8
// 31/0/2020 19:32:26


package rs.ac.bg.etf.pp1.ast;

public class FormalPars extends FormPars {

    private FormalParsList FormalParsList;

    public FormalPars (FormalParsList FormalParsList) {
        this.FormalParsList=FormalParsList;
        if(FormalParsList!=null) FormalParsList.setParent(this);
    }

    public FormalParsList getFormalParsList() {
        return FormalParsList;
    }

    public void setFormalParsList(FormalParsList FormalParsList) {
        this.FormalParsList=FormalParsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormalParsList!=null) FormalParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormalParsList!=null) FormalParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormalParsList!=null) FormalParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalPars(\n");

        if(FormalParsList!=null)
            buffer.append(FormalParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalPars]");
        return buffer.toString();
    }
}
