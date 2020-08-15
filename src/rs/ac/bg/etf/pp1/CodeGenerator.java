package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;

public class CodeGenerator extends VisitorAdaptor {
	int mainPC;

	public int getMainPc() {
		return mainPC;

	}

	// FACTORS
	public void visit(ConstFactor cnst) {
		if (cnst.getParent().getParent().getClass() == NegExpr.class) {
			cnst.obj.setAdr(-cnst.obj.getAdr());
			Code.load(cnst.obj);
			System.out.println("Const factor load NEG");
		} else {
			Code.load(cnst.obj);
			System.out.println("Const factor load");
		}

	}

	public void visit(DesignatorFactor des) {
		if (des.getParent().getParent().getClass() == NegExpr.class) {
			Code.load(des.getDesignator().obj);
			Code.put(Code.const_m1);
			Code.put(Code.mul);
			System.out.println("Designator factor load NEG");
		} else {
			Code.load(des.getDesignator().obj);
			System.out.println("Designator factor load POS");
		}

	}

	public void visit(NewListArray newlist) {
		Code.put(Code.newarray);
		if (newlist.getType().struct.getKind() == Struct.Char) {
			Code.put(0);
		} else {
			Code.put(1);
		}
		System.out.println("New");

	}


	public void visit(ArrayID id) {
		Code.load(id.obj);
		System.out.println("ARRAY ID");
	}

	// DESIGNATOR STATEMENTS

	public void visit(EqualDesignator stmnt) {
		Code.store(stmnt.getDesignator().obj);
		System.out.println("Equal Designator store");
	}

	public void visit(PlusDesignator stmnt) {
		if (stmnt.getDesignator().getClass() == ArrayDesignator.class) {
			Code.put(Code.dup2);
			Code.load(stmnt.getDesignator().obj);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.store(stmnt.getDesignator().obj);
		} else {
			Code.load(stmnt.getDesignator().obj);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.store(stmnt.getDesignator().obj);
		}
		System.out.println("PLUS PLUS load store");
	}

	public void visit(MinusDesignator stmnt) {
		if (stmnt.getDesignator().getClass() == ArrayDesignator.class) {
			Code.put(Code.dup2);
			Code.load(stmnt.getDesignator().obj);
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.store(stmnt.getDesignator().obj);
		} else {
			Code.load(stmnt.getDesignator().obj);
			Code.loadConst(1);
			Code.put(Code.sub);
			Code.store(stmnt.getDesignator().obj);
		}
		System.out.println("MINUS MINUS load store");
	}

	public void visit(FuncCallStatementNoArg funcCall) {
		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	// LISTE

	public void visit(MultiplicationList mul) {
		Code.put(mul.getMulOp().obj.getAdr());
		System.out.println("Mul list seting op");
	}

	public void visit(AdditionList add) {
		Code.put(add.getAddOp().obj.getAdr());
		System.out.println("Add list setting op");
	}

	// OPERATORI

	public void visit(Plus op) {
		op.obj = new Obj(Obj.NO_VALUE, "plus", Tab.noType);
		op.obj.setAdr(Code.add);
		System.out.println("add op");
	}

	public void visit(Minus op) {
		op.obj = new Obj(Obj.NO_VALUE, "minus", Tab.noType);
		op.obj.setAdr(Code.sub);
		System.out.println("sub op");
	}

	public void visit(Mul op) {
		op.obj = new Obj(Obj.NO_VALUE, "mul", Tab.noType);
		op.obj.setAdr(Code.mul);
		System.out.println("mul op");
	}

	public void visit(Div op) {
		op.obj = new Obj(Obj.NO_VALUE, "div", Tab.noType);
		op.obj.setAdr(Code.div);
		System.out.println("div op");
	}

	public void visit(Mod op) {
		op.obj = new Obj(Obj.NO_VALUE, "minus", Tab.noType);
		op.obj.setAdr(Code.rem);
		System.out.println("Mod op");
	}

	// STATEMENTS

	public void visit(PrintStatementOneArg printStmt) {
		if (printStmt.getExpr().obj.getType().getKind() == Tab.intType.getKind()) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else {
			if (printStmt.getExpr().obj.getType().getKind() == Struct.Bool) {
				if (printStmt.getExpr().obj.getAdr() == 1) {
					Code.loadConst('t');
					Code.loadConst(2);
					Code.put(Code.bprint);
					Code.loadConst('r');
					Code.loadConst(1);
					Code.put(Code.bprint);
					Code.loadConst('u');
					Code.loadConst(1);
					Code.put(Code.bprint);
					Code.loadConst('e');
					Code.loadConst(1);
					Code.put(Code.bprint);
				} else {
					Code.loadConst('f');
					Code.loadConst(2);
					Code.put(Code.bprint);
					Code.loadConst('a');
					Code.loadConst(1);
					Code.put(Code.bprint);
					Code.loadConst('l');
					Code.loadConst(1);
					Code.put(Code.bprint);
					Code.loadConst('s');
					Code.loadConst(1);
					Code.put(Code.bprint);
					Code.loadConst('e');
					Code.loadConst(1);
					Code.put(Code.bprint);

				}
			} else {
				Code.loadConst(2);
				Code.put(Code.bprint);
			}
		}
		System.out.println("Print!!!");
	}

	public void visit(PrintStatementTwoArg printStmt) {
		if (printStmt.getExpr().obj.getType().getKind() == Tab.intType.getKind()) {
			Code.loadConst(printStmt.getBroj());
			Code.put(Code.print);
		} else {
			if (printStmt.getExpr().obj.getType().getKind() == Struct.Bool) {
				if (printStmt.getExpr().obj.getAdr() == 1) {
					Code.loadConst('t');
					Code.loadConst(printStmt.getBroj() - 3);
					Code.put(Code.bprint);
					Code.loadConst('r');
					Code.loadConst(1);
					Code.put(Code.bprint);
					Code.loadConst('u');
					Code.loadConst(1);
					Code.put(Code.bprint);
					Code.loadConst('e');
					Code.loadConst(1);
					Code.put(Code.bprint);
				} else {
					Code.loadConst('f');
					Code.loadConst(printStmt.getBroj() - 3);
					Code.put(Code.bprint);
					Code.loadConst('a');
					Code.loadConst(1);
					Code.put(Code.bprint);
					Code.loadConst('l');
					Code.loadConst(1);
					Code.put(Code.bprint);
					Code.loadConst('s');
					Code.loadConst(1);
					Code.put(Code.bprint);
					Code.loadConst('e');
					Code.loadConst(1);
					Code.put(Code.bprint);

				}
			} else {
				Code.loadConst(printStmt.getBroj());
				Code.put(Code.bprint);
			}
		}
		System.out.println("Print sa 2 argumenta!");
	}

	public void visit(ReadStatement stmnt) {
		if (stmnt.getDesignator().obj.getType().getKind() == Struct.Char) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
			
		}
		Code.store(stmnt.getDesignator().obj);
		System.out.println("Read");
	}
	
//FUNCTION DECL
	
	public void visit(MethodName methodTypeName) {
		if ("main".equalsIgnoreCase(methodTypeName.getMethName())) {
			mainPC = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		SyntaxNode methodNode = methodTypeName.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		// Generate the entry
		Code.put(Code.enter);
		Code.put(0);
		Code.put(varCnt.getCount());// namestano da radi

	}

	public void visit(MethodDeclNoVar methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(MethodDeclWithVar meth) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

}
