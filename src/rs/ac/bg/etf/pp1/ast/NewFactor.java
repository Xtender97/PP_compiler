// generated with ast extension for cup
// version 0.8
// 31/0/2020 19:32:27


package rs.ac.bg.etf.pp1.ast;

public class NewFactor extends Factor {

    private NewList NewList;

    public NewFactor (NewList NewList) {
        this.NewList=NewList;
        if(NewList!=null) NewList.setParent(this);
    }

    public NewList getNewList() {
        return NewList;
    }

    public void setNewList(NewList NewList) {
        this.NewList=NewList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NewList!=null) NewList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NewList!=null) NewList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NewList!=null) NewList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NewFactor(\n");

        if(NewList!=null)
            buffer.append(NewList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NewFactor]");
        return buffer.toString();
    }
}
