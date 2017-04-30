package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import ast.Program;
import visitor.PrettyPrintVisitor;

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
		prog.accept(new PrettyPrintVisitor());
	}
}
