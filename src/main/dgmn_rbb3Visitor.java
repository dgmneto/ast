// Generated from dgmn_rbb3.g4 by ANTLR 4.4
package main;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link dgmn_rbb3Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface dgmn_rbb3Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link dgmn_rbb3Parser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(@NotNull dgmn_rbb3Parser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link dgmn_rbb3Parser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(@NotNull dgmn_rbb3Parser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link dgmn_rbb3Parser#goal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoal(@NotNull dgmn_rbb3Parser.GoalContext ctx);
	/**
	 * Visit a parse tree produced by {@link dgmn_rbb3Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull dgmn_rbb3Parser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link dgmn_rbb3Parser#mainClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainClass(@NotNull dgmn_rbb3Parser.MainClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link dgmn_rbb3Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull dgmn_rbb3Parser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link dgmn_rbb3Parser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(@NotNull dgmn_rbb3Parser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link dgmn_rbb3Parser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(@NotNull dgmn_rbb3Parser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link dgmn_rbb3Parser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(@NotNull dgmn_rbb3Parser.ClassDeclarationContext ctx);
}