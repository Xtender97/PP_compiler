package rs.ac.bg.etf.pp1;
	import rs.ac.bg.etf.pp1.ast.CommaArrayVarDeclaration;
import rs.ac.bg.etf.pp1.ast.CommaVarDeclaration;
import rs.ac.bg.etf.pp1.ast.VarArrayDecl;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VarDeclaration;
import rs.ac.bg.etf.pp1.ast.VarDeclarationList;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

	public class CounterVisitor extends VisitorAdaptor {

		protected int count;
		
		public int getCount(){
			return count;
		}		
		public static class VarCounter extends CounterVisitor{
			
			public void visit(VarDeclaration varDecl){
				count++;
			}
			public void visit(VarArrayDecl varDecl){
				count++;
			}
			public void visit(CommaVarDeclaration varDecl){
				count++;
			}
			public void visit(CommaArrayVarDeclaration varDecl){
				count++;
			}
			
		}
	}

