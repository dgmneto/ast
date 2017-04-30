// Generated from dgmn_rbb3.g4 by ANTLR 4.4
package main;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link dgmn_rbb3Parser}.
 */
public interface dgmn_rbb3Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link dgmn_rbb3Parser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(@NotNull dgmn_rbb3Parser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link dgmn_rbb3Parser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(@NotNull dgmn_rbb3Parser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link dgmn_rbb3Parser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(@NotNull dgmn_rbb3Parser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link dgmn_rbb3Parser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(@NotNull dgmn_rbb3Parser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link dgmn_rbb3Parser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(@NotNull dgmn_rbb3Parser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link dgmn_rbb3Parser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(@NotNull dgmn_rbb3Parser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link dgmn_rbb3Parser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull dgmn_rbb3Parser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link dgmn_rbb3Parser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull dgmn_rbb3Parser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link dgmn_rbb3Parser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(@NotNull dgmn_rbb3Parser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link dgmn_rbb3Parser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(@NotNull dgmn_rbb3Parser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link dgmn_rbb3Parser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull dgmn_rbb3Parser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link dgmn_rbb3Parser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull dgmn_rbb3Parser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link dgmn_rbb3Parser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull dgmn_rbb3Parser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link dgmn_rbb3Parser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull dgmn_rbb3Parser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link dgmn_rbb3Parser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(@NotNull dgmn_rbb3Parser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link dgmn_rbb3Parser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(@NotNull dgmn_rbb3Parser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link dgmn_rbb3Parser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(@NotNull dgmn_rbb3Parser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link dgmn_rbb3Parser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(@NotNull dgmn_rbb3Parser.ClassDeclarationContext ctx);
}