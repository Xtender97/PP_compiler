package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import com.sun.org.apache.xalan.internal.xsltc.dom.ArrayNodeListIterator;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean errorDetected = false;
	int printCallCount = 0;
	int varDeclCount = 0;
	int nVars;
	private Struct var_type = Tab.noType;

	Logger log = Logger.getLogger(getClass());

	public boolean nameAlreadyExists(String name) {
		if (Tab.currentScope().findSymbol(name) == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean passed() {
		return !errorDetected;
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

//DEKLARACIJA PROGRAMA
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
		report_info("Pronasao sam ime programa i mnogo sam pametan ", null);
	}

	public void visit(Program program) {
		if (Tab.find("main") == Tab.noObj) {
			report_error("Program ne sadrzi main() funkciju", program);
		} else {
			nVars = Tab.currentScope.getnVars();
			Tab.chainLocalSymbols(program.getProgName().obj);
			Tab.closeScope();
			report_info("Obradio sam program " + program.getProgName().obj.getName(), null);
		}
	}

//DEKLARACIJA PROMENLJIVIH

	public void visit(VarDeclaration varDecl) {
		if (!nameAlreadyExists(varDecl.getVarId())) {
			Tab.insert(Obj.Var, varDecl.getVarId(), varDecl.getType().struct);
			varDeclCount++;
			report_info("Deklarisana promenljiva " + varDecl.getVarId(), varDecl);
		} else {
			report_error("Ime promenljive je vec iskorisceno - ime: " + varDecl.getVarId(), varDecl);
		}

	}

	public void visit(CommaVarDeclaration varDecl) {
		if (!nameAlreadyExists(varDecl.getVarId())) {
			Tab.insert(Obj.Var, varDecl.getVarId(), var_type);
			varDeclCount++;
			report_info("Deklarisana promenljiva u nizu " + varDecl.getVarId(), varDecl);
		} else {
			report_error("Ime promenljive je vec iskorisceno - ime: " + varDecl.getVarId(), varDecl);

		}
	}

	public void visit(FormalVarDecl varDecl) {
		if (!nameAlreadyExists(varDecl.getVarId())) {
			Tab.insert(Obj.Var, varDecl.getVarId(), var_type);
			varDeclCount++;
			report_info("Deklarisana promenljiva kao argumet " + varDecl.getVarId(), varDecl);
		} else {
			report_error("Ime promenljive je vec iskorisceno - ime: " + varDecl.getVarId(), varDecl);

		}
	}

//DEKLARACIJA TIPA

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
				report_info("Pronadjen tip " + type.getTypeName(), type);
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
				type.struct = Tab.noType;
			}
		}
		var_type = type.struct;
	}

//DEKLARACIJE NIZOVA

	public void visit(VarArrayDecl varDecl) {
		if (!nameAlreadyExists(varDecl.getVarId())) {
			Tab.insert(Obj.Var, varDecl.getVarId(), new Struct(Struct.Array, var_type));
			varDeclCount++;
			report_info("Deklarisana promenljiva " + varDecl.getVarId() + "[] ", varDecl);
		} else {
			report_error("Ime promenljive je vec iskorisceno - ime: " + varDecl.getVarId(), varDecl);

		}
	}

	public void visit(CommaArrayVarDeclaration varDecl) {
		if (!nameAlreadyExists(varDecl.getVarId())) {

			Tab.insert(Obj.Var, varDecl.getVarId(), new Struct(Struct.Array, var_type));
			varDeclCount++;
			report_info("Deklarisana promenljiva " + varDecl.getVarId() + "[]", varDecl);
		} else {
			report_error("Ime promenljive je vec iskorisceno - ime: " + varDecl.getVarId(), varDecl);

		}
	}

	public void visit(FormalArrayDecl varDecl) {
		if (!nameAlreadyExists(varDecl.getVarId())) {

			Tab.insert(Obj.Var, varDecl.getVarId(), new Struct(Struct.Array, var_type));
			varDeclCount++;
			report_info("Deklarisana promenljiva u argumentu  " + varDecl.getVarId() + "[]", varDecl);
		} else {
			report_error("Ime promenljive je vec iskorisceno - ime: " + varDecl.getVarId(), varDecl);

		}
	}

//DEKLARACIJE KONSTANTI

	public void visit(ConstDecl constDecl) {
		if (!nameAlreadyExists(constDecl.getConstId())) {
			Obj obj = Tab.insert(Obj.Con, constDecl.getConstId(), var_type);
			obj.setAdr(constDecl.getConst().obj.getAdr());
			if (var_type.getKind() == constDecl.getConst().obj.getType().getKind()) {
				report_info("Deklarisana constanta " + constDecl.getConstId() + " vrednoscu: "
						+ constDecl.getConst().obj.getName(), constDecl);

			} else {
				report_error("Greska pri deklaraciji konstante - Pogresan TIP", constDecl);
			}
		} else {
			report_error("Ime constante je vec iskorisceno - ime: " + constDecl.getConstId(), constDecl);
		}

	}

	public void visit(CommaConstDeclaration constDecl) {
		if (!nameAlreadyExists(constDecl.getConstId())) {
			Obj obj = Tab.insert(Obj.Con, constDecl.getConstId(), var_type);
			obj.setAdr(constDecl.getConst().obj.getAdr());
			if (var_type.getKind() == constDecl.getConst().obj.getType().getKind()) {
				report_info("Deklarisana constanta u nizu " + constDecl.getConstId() + " vrednoscu: "
						+ constDecl.getConst().obj.getName(), constDecl);
			} else {
				report_error("Greska pri deklaraciji konstante u nizu - Pogresan TIP", constDecl);
			}
		} else {
			report_error("Ime constante je vec iskorisceno - ime: " + constDecl.getConstId(), constDecl);

		}

	}

// VREDNOST KONSTANTI

	public void visit(NumConst numConst) {
		numConst.obj = new Obj(Obj.Con, numConst.getConstant().toString(), new Struct(Struct.Int));
		numConst.obj.setAdr(numConst.getConstant());// dodavanje vrednosti za generisanje koda
		report_info("Pronadjena numericka CONSTANTA ", numConst);
	}

	public void visit(CharConst numConst) {
		numConst.obj = new Obj(Obj.Con, numConst.getConstant(), new Struct(Struct.Char));
		numConst.obj.setAdr(numConst.getConstant().charAt(1));
		report_info("Pronadjena char CONSTANTA", numConst);
	}

	public void visit(TrueConst numConst) {
		numConst.obj = new Obj(Obj.Con, numConst.getConstant(), new Struct(Struct.Bool));
		numConst.obj.setAdr(1);
		report_info("Pronadjena bool CONSTANTA", numConst);
	}

	public void visit(FalseConst numConst) {
		numConst.obj = new Obj(Obj.Con, numConst.getConstant(), new Struct(Struct.Bool));
		numConst.obj.setAdr(0);
		report_info("Pronadjena bool CONSTANTA", numConst);
	}

	public void visit(NullConst nullptr) {
		nullptr.obj = new Obj(Obj.Con, nullptr.getNullconst(), new Struct(Struct.Class));
		report_info("Pronadjena null CONSTANTA", nullptr);
	}

//DEKLARACAIJA METODA/FUNKCIJA

	public void visit(MethodDeclWithVar methDecl) {
		Tab.chainLocalSymbols(methDecl.getMethodName().obj);
		Tab.closeScope();
		report_info("Obradio sam metodu - " + methDecl.getMethodName().getMethName(), methDecl);
	}

	public void visit(MethodDeclNoVar methDecl) {
		Tab.chainLocalSymbols(methDecl.getMethodName().obj);
		Tab.closeScope();
		report_info("Obradio sam metodu - " + methDecl.getMethodName().getMethName(), methDecl);
	}

	public void visit(MethodName methName) {
		if (!nameAlreadyExists(methName.getMethName())) {
			methName.obj = Tab.insert(Obj.Meth, methName.getMethName(), Tab.noType);
			Tab.openScope();
			report_info("Pronasao sam ime metode i otvorio novi SCOPE - " + methName.getMethName(), methName);
		} else {
			report_error("Ime metode vec postoji : - " + methName.getMethName(), methName);
		}
	}

	// FACTOR

	public void visit(DesignatorFactor factor) {
		factor.obj = factor.getDesignator().obj;
	}

	public void visit(ConstFactor factor) {
		factor.obj = factor.getConst().obj;
	}

	public void visit(ExprFactor factor) {
		factor.obj = factor.getExpr().obj;
	}

	public void visit(NewFactor factor) {// ovo ne radi
		factor.obj = new Obj(Obj.NO_VALUE, "new", factor.getNewList().obj.getType());
	}

	public void visit(NewListArray newlist) {
		if (newlist.getExpr().obj.getType().getKind() != Struct.Int) {
			report_error("Izraz u zagradama mora biti tipa int", newlist);
			newlist.obj = new Obj(Obj.NO_VALUE, "error", Tab.noType);
		} else {
			newlist.obj = new Obj(Obj.Elem, "new", new Struct(Struct.Array, newlist.getType().struct));
		}
	}

	public void visit(NoNewList newlist) {
		newlist.obj = new Obj(Obj.Var, "new", newlist.getType().struct);
	}

	public void visit(DesignatorFactorFuncCall factor) {
		factor.obj = factor.getDesignator().obj;
	}

	// DESIGNATOR

	public void visit(ArrayDesignator array) {
		Obj pom = Tab.find(array.getArrayID().getArrayId());
		if (pom == Tab.noObj) {
			report_error("Pristup nedefinisanom nizu " + array.getArrayID().getArrayId(), array);
			array.obj = new Obj(Obj.Var, pom.getName(), Tab.noType);
		} else {
			if (pom.getType().getKind() != Struct.Array) {
				report_error("Pristup promenljivoj koja nije niz " + array.getArrayID().getArrayId(), array);
				array.obj = new Obj(Obj.Var, pom.getName(), Tab.noType);
				array.obj.setAdr(pom.getAdr());

			} else {
				if (array.getExpr().obj.getType().getKind() == Struct.Int) {
					report_info("Pristup nizu " + array.getArrayID().getArrayId(), array);
					array.obj = new Obj(Obj.Elem, pom.getName(), new Struct(pom.getType().getElemType().getKind()));
					array.obj.setAdr(pom.getAdr());
					array.obj.setLevel(pom.getLevel());
				}

				else {
					array.obj = new Obj(Obj.Var, pom.getName(), Tab.noType);
					report_error("Izraz u uglastimm zagradama mora bit tipa INT ", array);
				}
			}
		}
	}
	
	public void visit(ArrayID id) {
		id.obj = Tab.find(id.getArrayId());
	}

	public void visit(SimpleDesignator designator) {
		designator.obj = Tab.find(designator.getVarId());
		if (designator.obj == Tab.noObj) {
			report_error("Pristup nedefinisanoj promenljivoj" + designator.getVarId(), designator);
		} else {
			report_info("Pristup promenljivoj " + designator.getVarId(), designator);
		}
	}

	// EXPRESSION

	public void visit(PosExpr expr) {
		if (expr.getTerm().obj.getType().getKind() == Struct.Int
				&& expr.getAddList().obj.getType().getKind() == Struct.Int) {
			expr.obj = new Obj(Obj.NO_VALUE, "expr", new Struct(Struct.Int));
		} else {
			if (expr.getAddList().obj.getType().getKind() == Tab.noType.getKind()) {
				expr.obj = new Obj(expr.getTerm().obj.getKind(), "expr", expr.getTerm().obj.getType());
				expr.obj.setAdr(expr.getTerm().obj.getAdr());
			} else {
				expr.obj = new Obj(Obj.NO_VALUE, "expr", Tab.noType);
				report_error("Tipovi u izrazu treba da budu tipa INT", expr);

			}
		}
	}

	public void visit(NegExpr expr) {
		if (expr.getTerm().obj.getType().getKind() == Struct.Int
				&& expr.getAddList().obj.getType().getKind() == Struct.Int) {
			expr.obj = new Obj(Obj.NO_VALUE, "expr", new Struct(Struct.Int));
		} else {
			if (expr.getAddList().obj.getType().getKind() == Tab.noType.getKind()) {
				expr.obj = new Obj(expr.getTerm().obj.getKind(), "expr", expr.getTerm().obj.getType());
				expr.obj.setAdr(expr.getTerm().obj.getAdr());
			} else {
				expr.obj = new Obj(Obj.NO_VALUE, "expr", Tab.noType);
				report_error("Tipovi u nega izrazu treba da budu tipa INT", expr);

			}
		}
	}

	// ADDLIST

	public void visit(NoAddList list) {
		list.obj = new Obj(Obj.NO_VALUE, "emptymullist", Tab.noType);
	}

	public void visit(AdditionList list) {
		Obj term = list.getTerm().obj;
		Obj lista = list.getAddList().obj;
		if (term.getType().getKind() == Struct.Int && lista.getType().getKind() == Struct.Int
				|| (term.getType().getKind() == Struct.Int && lista.getType().getKind() == Tab.noType.getKind())) {
			list.obj = new Obj(term.getKind(), "fulladdist", new Struct(Struct.Int));
		} else {
			list.obj = new Obj(Obj.NO_VALUE, "fulladdlist_error", Tab.noType);
			report_error("Operandi sabiranja moraju biti tipa INT", list);
		}
	}

	// TERM
	public void visit(Term term) {
		if (term.getFactor().obj.getType().getKind() == Struct.Int
				&& term.getMulList().obj.getType().getKind() == Struct.Int) {
			term.obj = new Obj(Obj.NO_VALUE, "valid term", new Struct(Struct.Int));
		} else {
			if (term.getMulList().obj.getType().getKind() == Tab.noType.getKind()) {// ako nema mul liste onda ne mora
																					// da bude tipa int jel nema
																					// mnozenja pa zadrzi tip factora
				term.obj = new Obj(term.getFactor().obj.getKind(), "valid term", term.getFactor().obj.getType());
				term.obj.setAdr(term.getFactor().obj.getAdr());
			} else {
				term.obj = new Obj(Obj.NO_VALUE, "error term", Tab.noType);
				report_error("Operator mnozenja mora biti tipa INT", term);
			}
		}
	}

	// MULLIST

	public void visit(NoMulList mullist) {
		mullist.obj = new Obj(Obj.NO_VALUE, "emptymullist", Tab.noType);
	}

	public void visit(MultiplicationList mullist) {
		Obj factor = mullist.getFactor().obj;
		Obj list = mullist.getMulList().obj;
		if ((factor.getType().getKind() == Struct.Int && list.getType().getKind() == Struct.Int)
				|| (factor.getType().getKind() == Struct.Int && list.getType().getKind() == Tab.noType.getKind())) {
			mullist.obj = new Obj(factor.getKind(), "fullmullist", new Struct(Struct.Int));
		} else {
			mullist.obj = new Obj(Obj.NO_VALUE, "fullmullist_error", Tab.noType);
			report_error("Operandi mnozenja moraju biti tipa INT", mullist);
		}
	}

	// DESIGNATOR STATEMENTS

	public void visit(ErrorExpression expr) {
		expr.obj = new Obj(Obj.NO_VALUE, "equal expresion in errorexpr smeni", expr.getExpr().obj.getType());
	}

	public void visit(ErrorStmt expr) {
		expr.obj = new Obj(Obj.NO_VALUE, "error", Tab.noType);
		report_error("GRESKA u izrazu za dodelu vrednosti", expr);
	}

	// fali i error visit metoda da ne bi cepio nullptr

	public void visit(EqualDesignator designatorstmt) {// treba dodati i dodelu null-a tada treba dodati jos jednu if
														// granu
		if (designatorstmt.getErrorExpr().obj.getType().getKind() == designatorstmt.getDesignator().obj.getType()
				.getKind()) {
			if (designatorstmt.getDesignator().obj.getKind() == Obj.Con) {
				report_error("Dodela vrednosti konstanti", designatorstmt);

			} else {
				if (designatorstmt.getErrorExpr().obj.getType().getKind() == Struct.Array) {
					if (designatorstmt.getErrorExpr().obj.getType().getElemType()
							.getKind() == designatorstmt.getDesignator().obj.getType().getElemType().getKind()) {
						report_info("Dodela vrednosti nizu", designatorstmt);
					} else {
						report_error("Tipovi niza prilikom dodele se razlikuju", designatorstmt);
					}
				} else {
					report_info("Dodela vrednosti ", designatorstmt);
				}
			}
		}

		else {
			if (designatorstmt.getErrorExpr().obj.getType().getKind() == Struct.Class
					&& designatorstmt.getDesignator().obj.getType().getKind() == Struct.Array) {
				report_info("Dodela null vrednosti nizu", designatorstmt);
			} else {
				report_error("Dodela razlicitih tipova podataka", designatorstmt);
			}
		}
	}

	public void visit(PlusDesignator designatorstmt) {
		if (designatorstmt.getDesignator().obj.getKind() != Obj.Con
				&& designatorstmt.getDesignator().obj.getType().getKind() == Struct.Int) {
			report_info("Inkrementiranje promenljive", designatorstmt);
		} else {
			report_error("Inkrementiranje constante ili POGRESNOG TIPA", designatorstmt);
		}
	}

	public void visit(MinusDesignator designatorstmt) {
		if (designatorstmt.getDesignator().obj.getKind() == Obj.Var
				&& designatorstmt.getDesignator().obj.getType().getKind() == Struct.Int) {
			report_info("Dekrementiranje promenljive", designatorstmt);
		} else {
			report_error("Deckrementiranje constante ili POGRESNOG TIPA", designatorstmt);
		}
	}

	public void visit(FuncCallStatementWithArg funcall) {
		if (funcall.getDesignator().obj.getKind() != Obj.Meth) {
			report_error(funcall.getDesignator().obj.getName() + "nije funkcija!!", funcall);
		} else {
			report_info("Poziv funkcije " + funcall.getDesignator().obj.getName(), funcall);
		}
	}

	public void visit(FuncCallStatementNoArg funcall) {
		if (funcall.getDesignator().obj.getKind() != Obj.Meth) {
			report_error(funcall.getDesignator().obj.getName() + " nije funkcija!!", funcall);
		} else {
			report_info("Poziv funkcije " + funcall.getDesignator().obj.getName(), funcall);
		}
	}

	// STATEMENTS
	public void visit(PrintStatementOneArg print) {
		printCallCount++;
		if (print.getExpr().obj.getType().getKind() == Struct.Array) {
			report_error("Mogu se ispisati samo tipovi int, char i bool", print);
		} else {
			report_info("Ispis ", print);
		}
	}

	public void visit(PrintStatementTwoArg print) {
		printCallCount++;
		if (print.getExpr().obj.getType().getKind() == Struct.Array) {
			report_error("Mogu se ispisati samo tipovi int, char i bool", print);
		} else {
			report_info("Ispis ", print);
		}
	}

	public void visit(ReadStatement read) {
		if (read.getDesignator().obj.getType().getKind() == Struct.Array) {
			report_error("Ne moze se upisati u niz", read);
		} else {
			if (read.getDesignator().obj.getKind() == Obj.Con) {
				report_error("Ne moze se upisati u konstantu", read);
			} else {
				//read.obj = read.getDesignator().obj;
				report_info("Upis u promenljivu " + read.getDesignator().obj.getName(), read);
			}
		}
	}
}
