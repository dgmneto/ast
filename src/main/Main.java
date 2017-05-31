package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import ast.Program;
import symboltable.Method;
import symboltable.SymbolTable;
import symboltable.Variable;
import visitor.BuildSymbolTableVisitor;
import visitor.PrettyPrintVisitor;
import visitor.TypeCheckVisitor;

public class Main {
	public static void main(String args[]) throws IOException{
		String file = "src/teste.txt";
		if(args.length > 0)
			file = args[0];
		InputStream stream = new FileInputStream(file);
		ANTLRInputStream input = new ANTLRInputStream(stream);
		dgmn_rbb3Lexer lexer = new dgmn_rbb3Lexer(input);
		CommonTokenStream token = new CommonTokenStream(lexer);
		dgmn_rbb3Parser parser = new dgmn_rbb3Parser(token);
		ParseTree pt = parser.goal();
		Visitor visitor = new Visitor();
		Program prog = (Program) visitor.visit(pt);
		BuildSymbolTableVisitor stVis = new BuildSymbolTableVisitor();
		prog.accept(stVis);
		testST(stVis.getSymbolTable());
		prog.accept(new TypeCheckVisitor(stVis.getSymbolTable()));
	}
	
	public static void testST(SymbolTable st) {
		for(Object i: st.hashtable.keySet()) {
			symboltable.Class c = st.getClass((String)i);
			System.out.printf("%s:\n", c.getId());
			for(Object j: c.globals.keySet()) {
				Variable v = c.getVar((String)j);
				System.out.printf("  %s\n", v.id());
			}
			for(Object j: c.methods.keySet()) {
				Method m = c.getMethod((String)j);
				System.out.printf("  %s(", m.getId());
				boolean first = true;
				for(Variable v: m.params) {
					if(!first) {
						System.out.print(", ");
					} else first = false;
					System.out.printf("%s", v.id());
				}
				System.out.println("):");
				for(Object k: m.vars.keySet()) {
					Variable v = m.getVar((String)k);
					System.out.printf("    %s\n", v.id());
				}
			}
		}
	}
}
