package priv.L.parser

import research.parser.MathParser.CompileUnitContext
import research.parser.{MathBaseVisitor, MathLexer, MathParser}

class MyCstVisitor extends MathBaseVisitor[ExpNode]{

  override def visitCompileUnit(ctx: CompileUnitContext): ExpNode = visit(ctx.expr())

  override def visitInfixExpr(ctx: MathParser.InfixExprContext ): ExpNode = {
    val left: ExpNode = visit(ctx.left)
    val right: ExpNode = visit(ctx.right)
    ctx.op.getType match {
      case MathLexer.OP_ADD => AddNode(left, right)
      case MathLexer.OP_DIV => DivNode(left, right)
      case MathLexer.OP_MUL => MulNode(left, right)
      case MathLexer.OP_SUB => SubNode(left, right)
      case n => throw new Exception(s"Unknown operator token number: $n")
    }
  }
  override def visitUnaryExpr(ctx: MathParser.UnaryExprContext ): ExpNode = {
    ctx.op.getType match {
      case MathLexer.OP_ADD => visit(ctx.expr())
      case MathLexer.OP_SUB => NegateNode(visit(ctx.expr()))
      case n => throw new Exception(s"Unknown operator token number: $n")
    }
  }
  override def visitFuncExpr(ctx: MathParser.FuncExprContext ): ExpNode =
    ctx.func.getText match {
      case x if x.toLowerCase() == "sqrt" => FunNode(Math.sqrt, visit(ctx.expr))
      case x if x.toLowerCase() == "exp" => FunNode(Math.exp, visit(ctx.expr))
      case x => throw new Exception(s"Unsupport function: $x")
    }

  override def visitNumberExpr(ctx: MathParser.NumberExprContext ): ExpNode = NumberNode(ctx.value.getText.toDouble)
  override def visitParensExpr(ctx: MathParser.ParensExprContext ): ExpNode = visit(ctx.expr)
}
