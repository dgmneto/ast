package main;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

import ast.*;
import main.dgmn_rbb3Parser.ClassDeclarationContext;
import main.dgmn_rbb3Parser.ExpressionContext;
import main.dgmn_rbb3Parser.GoalContext;
import main.dgmn_rbb3Parser.IdentifierContext;
import main.dgmn_rbb3Parser.MainClassContext;
import main.dgmn_rbb3Parser.MethodDeclarationContext;
import main.dgmn_rbb3Parser.StatementContext;
import main.dgmn_rbb3Parser.TypeContext;
import main.dgmn_rbb3Parser.VarDeclarationContext;

public class Visitor extends AbstractParseTreeVisitor<Object> implements dgmn_rbb3Visitor<Object> {

	@Override
	public Object visitGoal(GoalContext ctx) {
		MainClass m = (MainClass) this.visit(ctx.mainClass());
		ClassDeclList cl = new ClassDeclList();
		for(ClassDeclarationContext t: ctx.classDeclaration())
			cl.addElement((ClassDecl) this.visit(t));
		return new Program(m, cl);
	}

	@Override
	public Object visitMainClass(MainClassContext ctx) {
		Identifier i1 = (Identifier) this.visit(ctx.identifier(0)), i2 = (Identifier) this.visit(ctx.identifier(1));
		Statement s = (Statement) this.visit(ctx.statement());
		return new MainClass(i1, i2, s);
	}

	@Override
	public Object visitClassDeclaration(ClassDeclarationContext ctx) {
		ClassDecl ret = null;
		switch(ctx.identifier().size()) {
			case 1: {
				Identifier i = (Identifier) this.visit(ctx.identifier(0));
				VarDeclList vl = new VarDeclList();
				MethodDeclList ml = new MethodDeclList();
				for(VarDeclarationContext t: ctx.varDeclaration())
					vl.addElement((VarDecl) this.visit(t));
				for(MethodDeclarationContext t: ctx.methodDeclaration())
					ml.addElement((MethodDecl) this.visit(t));
				ret = new ClassDeclSimple(i, vl, ml);
				break;
			}
			case 2: {
				Identifier i = (Identifier) this.visit(ctx.identifier(0)), j = (Identifier) this.visit(ctx.identifier(1));
				VarDeclList vl = new VarDeclList();
				MethodDeclList ml = new MethodDeclList();
				for(VarDeclarationContext t: ctx.varDeclaration())
					vl.addElement((VarDecl) this.visit(t));
				for(MethodDeclarationContext t: ctx.methodDeclaration())
					ml.addElement((MethodDecl) this.visit(t));
				ret = new ClassDeclExtends(i, j, vl, ml);
				break;
			}
		}
		return ret;
	}

	@Override
	public Object visitVarDeclaration(VarDeclarationContext ctx) {
		Type t = (Type) this.visit(ctx.type());
		Identifier i = (Identifier) this.visit(ctx.identifier());
		return new VarDecl(t, i);
	}

	@Override
	public Object visitMethodDeclaration(MethodDeclarationContext ctx) {
		Type t = (Type) this.visit(ctx.type(0));
		Identifier i = (Identifier) this.visit(ctx.identifier(0));
		FormalList fl = new FormalList();
		for(int k = 1; k < ctx.type().size(); ++k)
			fl.addElement(new Formal((Type) this.visit(ctx.type(k)), (Identifier) this.visit(ctx.identifier(k))));
		VarDeclList vl = new VarDeclList();
		for(VarDeclarationContext d: ctx.varDeclaration())
			vl.addElement((VarDecl) this.visit(d));
		StatementList sl = new StatementList();
		for(StatementContext d: ctx.statement())
			sl.addElement((Statement) this.visit(d));
		Exp e = (Exp) this.visit(ctx.expression());
		return new MethodDecl(t, i, fl, vl, sl, e);
	}

	@Override
	public Object visitType(TypeContext ctx) {
		Type ret = null;
		if(ctx.identifier() != null) {
			ret = new IdentifierType(ctx.identifier().getText());
		} else if(ctx.getChild(0).getText().equals("boolean")) {
			ret = new BooleanType();
		} else if(ctx.getChildCount() == 1) {
			ret = new IntegerType();
		} else {
			ret = new IntArrayType();
		}
		return ret;
	}

	@Override
	public Object visitStatement(StatementContext ctx) {
		Statement ret = null;
		switch(ctx.getChild(0).getText()) {
		case "{": {
			StatementList sl = new StatementList();
			for(StatementContext d: ctx.statement())
				sl.addElement((Statement) this.visit(d));
			ret = new Block(sl);
			break;
		} case "if": {
			Exp e = (Exp) this.visit(ctx.expression(0));
			Statement s1 = (Statement) this.visit(ctx.statement(0)), s2 = (Statement) this.visit(ctx.statement(1));
			ret = new If(e, s1, s2);
			break;
		} case "while": {
			Exp e = (Exp) this.visit(ctx.expression(0));
			Statement s = (Statement) this.visit(ctx.statement(0));
			ret = new While(e, s);
			break;
		} case "System.out.println": {
			Exp e = (Exp) this.visit(ctx.expression(0));
			ret = new Print(e);
			break;
		} default: {
			Identifier i = (Identifier) this.visit(ctx.identifier());
			switch(ctx.expression().size()) {
			case 1: {
				Exp e = (Exp) this.visit(ctx.expression(0));
				ret = new Assign(i, e);
				break;
			} case 2: {
				Exp e1 = (Exp) this.visit(ctx.expression(0)), e2 = (Exp) this.visit(ctx.expression(1));
				ret = new ArrayAssign(i, e1, e2);
				break;
			}
			}
			break;
		}
		}
		return ret;
	}

	@Override
	public Object visitExpression(ExpressionContext ctx) {
		Exp ret = null;
		switch(ctx.getChildCount()) {
		case 1: {
			switch(ctx.getChild(0).getText()) {
			case "true": {
				ret = new True();
				break;
			} case "false": {
				ret = new False();
				break;
			} case "this": {
				ret = new This();
				break;
			} default: {
				if(ctx.getChild(0).getText().charAt(0) >= '0' && ctx.getChild(0).getText().charAt(0) <= '9')
					ret = new IntegerLiteral(Integer.parseInt(ctx.getChild(0).getText()));
				else
					ret = new IdentifierExp(ctx.getChild(0).getText());
				break;
			}
			}
			break;
		} case 2: {
			Exp e = (Exp) this.visit(ctx.expression(0));
			ret = new Not(e);
			break;
		} case 3: {
			switch(ctx.expression().size()) {
			case 1: {
				if(ctx.getChild(0) instanceof ExpressionContext) {
					Exp e = (Exp) this.visit(ctx.expression(0));
					ret = new ArrayLength(e);
				} else {
					Exp e = (Exp) this.visit(ctx.expression(0));
					ret = e;
				}
				break;
			} case 2: {
				Exp e1 = (Exp) this.visit(ctx.expression(0)), e2 = (Exp) this.visit(ctx.expression(1));
				switch(ctx.getChild(1).getText()) {
				case "&": {
					ret = new And(e1, e2);
					break;
				} case "<": {
					ret = new LessThan(e1, e2);
					break;
				} case "+": {
					ret = new Plus(e1, e2);
					break;
				} case "-": {
					ret = new Minus(e1, e2);
					break;
				} case "*": {
					ret = new Times(e1, e2);
					break;
				}
				}
				break;
			}
			}
			break;
		} case 4: {
			if(ctx.expression().size() == 2) {
				Exp e1 = (Exp) this.visit(ctx.expression(0)), e2 = (Exp) this.visit(ctx.expression(1));
				ret = new ArrayLookup(e1, e2);
			} else {
				Identifier i = (Identifier) this.visit(ctx.identifier());
				ret = new NewObject(i);
			}
			break;
		} default: {
			if(ctx.getChild(0) instanceof ExpressionContext) {
				Exp e = (Exp) this.visit(ctx.expression(0));
				Identifier i = (Identifier) this.visit(ctx.identifier());
				ExpList el = new ExpList();
				for(int k = 1; k < ctx.expression().size(); ++k)
					el.addElement((Exp) this.visit(ctx.expression(k)));
				ret = new Call(e, i, el);
			} else {
				Exp e = (Exp) this.visit(ctx.expression(0));
				ret = new NewArray(e);
			}
			break;
		}
		}
		return ret;
	}

	@Override
	public Object visitIdentifier(IdentifierContext ctx) {
		String s = ctx.getText();
		return new Identifier(s);
	}
	
}
