package ast;

import visitor.TypeVisitor;
import visitor.Visitor;

public class Parenthesis extends Exp {
	public Exp e;
	
	public Parenthesis(Exp ae) {
		this.e = ae;
	}
	
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}

}
