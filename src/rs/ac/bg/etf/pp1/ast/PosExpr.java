// generated with ast extension for cup
// version 0.8
// 31/0/2020 19:32:26


package rs.ac.bg.etf.pp1.ast;

public class PosExpr extends Expr {

    private Term Term;
    private AddList AddList;

    public PosExpr (Term Term, AddList AddList) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.AddList=AddList;
        if(AddList!=null) AddList.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public AddList getAddList() {
        return AddList;
    }

    public void setAddList(AddList AddList) {
        this.AddList=AddList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Term!=null) Term.accept(visitor);
        if(AddList!=null) AddList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(AddList!=null) AddList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(AddList!=null) AddList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PosExpr(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddList!=null)
            buffer.append(AddList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PosExpr]");
        return buffer.toString();
    }
}
