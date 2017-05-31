package visitor;

import symboltable.Method;
import symboltable.SymbolTable;
import ast.And;
import ast.ArrayAssign;
import ast.ArrayLength;
import ast.ArrayLookup;
import ast.Assign;
import ast.Block;
import ast.BooleanType;
import ast.Call;
import ast.ClassDeclExtends;
import ast.ClassDeclSimple;
import ast.False;
import ast.Formal;
import ast.Identifier;
import ast.IdentifierExp;
import ast.IdentifierType;
import ast.If;
import ast.IntArrayType;
import ast.IntegerLiteral;
import ast.IntegerType;
import ast.LessThan;
import ast.MainClass;
import ast.MethodDecl;
import ast.Minus;
import ast.NewArray;
import ast.NewObject;
import ast.Not;
import ast.Plus;
import ast.Print;
import ast.Program;
import ast.This;
import ast.Times;
import ast.True;
import ast.Type;
import ast.VarDecl;
import ast.While;

public class TypeCheckVisitor implements TypeVisitor {

	private SymbolTable symbolTable;
	private symboltable.Class currClass = null;
	private Method currMethod = null;

	public TypeCheckVisitor(SymbolTable st) {
		symbolTable = st;
	}

	// MainClass m;
	// ClassDeclList cl;
	public Type visit(Program n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i1,i2;
	// Statement s;
	public Type visit(MainClass n) {
		n.i1.accept(this);
		currClass = symbolTable.getClass(n.i1.s);
		currMethod = currClass.getMethod("main");
		n.i2.accept(this);
		n.s.accept(this);
		currClass = null;
		currMethod = null;
		return new IdentifierType(n.i1.s);
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclSimple n) {
		n.i.accept(this);
		currClass = symbolTable.getClass(n.i.s);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		currClass = null;
		return new IdentifierType(n.i.s);
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclExtends n) {
		n.i.accept(this);
		n.j.accept(this);
		if(!symbolTable.containsClass(n.j.s)) {
			System.out.printf("Classe %s não declarada\n", n.j.s);
			System.exit(0);
		}
		currClass = symbolTable.getClass(n.i.s);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.elementAt(i).accept(this);
		}
		currClass = null;
		return new IdentifierType(n.i.s);
	}

	// Type t;
	// Identifier i;
	public Type visit(VarDecl n) {
		Type te = n.t.accept(this);
		n.i.accept(this);
		return te;
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public Type visit(MethodDecl n) {
		Type te = n.t.accept(this);
		n.i.accept(this);
		currMethod = currClass.getMethod(n.i.s);
		for (int i = 0; i < n.fl.size(); i++) {
			n.fl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		Type ret = n.e.accept(this);
		if(!symbolTable.compareTypes(te, ret)) {
			System.out.printf("Tipo de retorno inválido no método %s\n", n.i.s);
			System.exit(0);
		}
		currMethod = null;
		return te;
	}

	// Type t;
	// Identifier i;
	public Type visit(Formal n) {
		Type te = n.t.accept(this);
		n.i.accept(this);
		return te;
	}
 
	public Type visit(IntArrayType n) {
		return n;
	}

	public Type visit(BooleanType n) {
		return n;
	}

	public Type visit(IntegerType n) {
		return n;
	}

	// String s;
	public Type visit(IdentifierType n) {
		if(!symbolTable.containsClass(n.s)) {
			System.out.printf("A classe %s não foi declarada\n", n.s);
			System.exit(0);
		}
		return n;
	}

	// StatementList sl;
	public Type visit(Block n) {
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.elementAt(i).accept(this);
		}
		return null;
	}

	// Exp e;
	// Statement s1,s2;
	public Type visit(If n) {
		Type ife = n.e.accept(this);
		if(!(ife instanceof BooleanType)) {
			System.out.println("\"if\" deve conter expressão com retorno booleano");
			System.exit(0);
		}
		n.s1.accept(this);
		n.s2.accept(this);
		return null;
	}

	// Exp e;
	// Statement s;
	public Type visit(While n) {
		Type whilee = n.e.accept(this);
		if(!(whilee instanceof BooleanType)) {
			System.out.println("\"while\" deve conter expressão com retorno booleano");
			System.exit(0);
		}
		n.s.accept(this);
		return null;
	}

	// Exp e;
	public Type visit(Print n) {
		Type printe = n.e.accept(this);
		if(printe instanceof IdentifierType) {
			System.out.println("Somente valores literais podem ser impressos");
			System.exit(0);
		}
		return null;
	}

	// Identifier i;
	// Exp e;
	public Type visit(Assign n) {
		n.i.accept(this);
		Type ee = n.e.accept(this);
		if(!symbolTable.compareTypes(symbolTable.getVarType(currMethod, currClass, n.i.s), ee)) {
			System.out.println("Atribuição entre tipos incompatíveis");
			System.exit(0);
		}
		return ee;
	}

	// Identifier i;
	// Exp e1,e2;
	public Type visit(ArrayAssign n) {
		n.i.accept(this);
		if(!(symbolTable.getVarType(currMethod, currClass, n.i.s) instanceof IntArrayType)) {
			System.out.println("Acesso a índice de variável que não é um array");
			System.exit(0);
		}
		Type e1e = n.e1.accept(this);
		if(!(e1e instanceof IntegerType)) {
			System.out.println("O acesso ao array deve ser feito através de um inteiro");
			System.exit(0);
		}
		Type e2e = n.e2.accept(this);
		if(!(e2e instanceof IntegerType)) {
			System.out.println("Atribuição entre tipos incompatíveis");
			System.exit(0);
		}
		return null;
	}

	// Exp e1,e2;
	public Type visit(And n) {
		Type e1e = n.e1.accept(this);
		Type e2e = n.e2.accept(this);
		if(!(e1e instanceof BooleanType) || !(e2e instanceof BooleanType)) {
			System.out.println("Operação entre tipos incompatíveis");
			System.exit(0);
		}
		return new BooleanType();
	}

	// Exp e1,e2;
	public Type visit(LessThan n) {
		Type e1e = n.e1.accept(this);
		Type e2e = n.e2.accept(this);
		if(!(e1e instanceof IntegerType) || !(e2e instanceof IntegerType)) {
			System.out.println("Operação entre tipos incompatíveis");
			System.exit(0);
		}
		return new BooleanType();
	}

	// Exp e1,e2;
	public Type visit(Plus n) {
		Type e1e = n.e1.accept(this);
		Type e2e = n.e2.accept(this);
		if(!(e1e instanceof IntegerType) || !(e2e instanceof IntegerType)) {
			System.out.println("Operação entre tipos incompatíveis");
			System.exit(0);
		}
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(Minus n) {
		Type e1e = n.e1.accept(this);
		Type e2e = n.e2.accept(this);
		if(!(e1e instanceof IntegerType) || !(e2e instanceof IntegerType)) {
			System.out.println("Operação entre tipos incompatíveis");
			System.exit(0);
		}
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(Times n) {
		Type e1e = n.e1.accept(this);
		Type e2e = n.e2.accept(this);
		if(!(e1e instanceof IntegerType) || !(e2e instanceof IntegerType)) {
			System.out.println("Operação entre tipos incompatíveis");
			System.exit(0);
		}
		return new IntegerType();
	}

	// Exp e1,e2;
	public Type visit(ArrayLookup n) {
		Type e1e = n.e1.accept(this);
		Type e2e = n.e2.accept(this);
		if(!(e1e instanceof IntArrayType)) {
			System.out.println("Acesso a índice de variável que não é um array");
			System.exit(0);
		}
		if(!(e2e instanceof IntegerType)) {
			System.out.println("O acesso ao array deve ser feito através de um inteiro");
			System.exit(0);
		}
		return new IntegerType();
	}

	// Exp e;
	public Type visit(ArrayLength n) {
		Type ee = n.e.accept(this);
		if(!(ee instanceof IntArrayType)) {
			System.out.println("O atributo length só é definido para arrays");
			System.exit(0);
		}
		return new IntegerType();
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public Type visit(Call n) {
		Type ee = n.e.accept(this);
		if(!(ee instanceof IdentifierType)) {
			System.out.println("Tipos literais não tem métodos definidos");
			System.exit(0);
		}
		symboltable.Class c = symbolTable.getClass(((IdentifierType)ee).s);
		n.i.accept(this);
		Method m = symbolTable.getMethod(n.i.s, c.getId());
		for (int i = 0; i < n.el.size(); i++) {
			Type ele = n.el.elementAt(i).accept(this);
			if(!symbolTable.compareTypes(m.getParamAt(i).type(), ele)) {
				System.out.printf("O parâmetro de índice %d não é compatível no método %s\n", i, m.getId());
				System.exit(0);
			}
		}
		return m.type();
	}

	// int i;
	public Type visit(IntegerLiteral n) {
		return new IntegerType();
	}

	public Type visit(True n) {
		return new BooleanType();
	}

	public Type visit(False n) {
		return new BooleanType();
	}

	// String s;
	public Type visit(IdentifierExp n) {
		return symbolTable.getVarType(currMethod, currClass, n.s);
	}

	public Type visit(This n) {
		return new IdentifierType(currClass.getId());
	}

	// Exp e;
	public Type visit(NewArray n) {
		Type ee = n.e.accept(this);
		if(!(ee instanceof IntegerType)) {
			System.out.println("Um array deve ser inicializado com um valor inteiro");
			System.exit(0);
		}
		return new IntArrayType();
	}

	// Identifier i;
	public Type visit(NewObject n) {
		n.i.accept(this);
		if(!symbolTable.containsClass(n.i.s)) {
			System.out.printf("A classe %s não foi declarada\n", n.i.s);
			System.exit(0);
		}
		return new IdentifierType(n.i.s);
	}

	// Exp e;
	public Type visit(Not n) {
		Type ee = n.e.accept(this);
		if(!(ee instanceof BooleanType)) {
			System.out.println("Operação entre tipo incompatível");
			System.exit(0);
		}
		return new BooleanType();
	}

	// String s;
	public Type visit(Identifier n) {
		return null;
	}
}
