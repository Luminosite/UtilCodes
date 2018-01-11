package priv.L.parser.bsiSql.sql

import org.antlr.v4.runtime.misc.Interval
import org.antlr.v4.runtime.{CharStream, ParserRuleContext}
import research.parser.BsiSqlBaseParser.BsiAssignmentContext
import research.parser.{BsiSqlBaseBaseVisitor, BsiSqlBaseParser}

class BsiSqlParser(stream: CharStream) extends BsiSqlBaseBaseVisitor[Exp]{

  override def visitSingleStatement(ctx: BsiSqlBaseParser.SingleStatementContext): Exp = visit(ctx.extendedStatement())

  override def visitNormalStatement(ctx: BsiSqlBaseParser.NormalStatementContext): Exp = {
    val sql = getOriginalString(ctx)
    NormalStatement(sql)
  }

  override def visitBsiAssignment(ctx: BsiAssignmentContext): Exp =  {
//    visit(ctx.qt)
//    visit(ctx.qo)
    val viewName = getOriginalString(ctx.viewName)
    val sql = getOriginalString(ctx.qt)
    val sqlOrg = getOriginalString(ctx.qo)
    val as = Assignment(viewName, s"$sql $sqlOrg")
    as
  }

//  override def visitQueryTermDefault(ctx: BsiSqlBaseParser.QueryTermDefaultContext): Exp = super.visitQueryTermDefault(ctx)

  def getOriginalString(ctx: ParserRuleContext): String = {
    val start = ctx.start.getStartIndex
    val stop = ctx.stop.getStopIndex

    val interval = new Interval(start, stop)
    stream.getText(interval)
  }
}
