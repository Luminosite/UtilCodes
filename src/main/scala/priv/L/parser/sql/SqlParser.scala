package priv.L.parser.sql

import org.antlr.v4.runtime.{CharStream, ParserRuleContext}
import org.antlr.v4.runtime.misc.Interval
import research.parser.{SqlBaseBaseVisitor, SqlBaseParser}

class SqlParser(stream: CharStream) extends SqlBaseBaseVisitor[Exp]{

  override def visitSingleStatement(ctx: SqlBaseParser.SingleStatementContext): Exp = visit(ctx.statement())

  override def visitQuery(ctx: SqlBaseParser.QueryContext): Exp = {
    val sql = getOriginalString(ctx)
    NormalStatement(sql)
  }

  def getOriginalString(ctx: ParserRuleContext): String = {
    val start = ctx.start.getStartIndex
    val stop = ctx.stop.getStopIndex

    val interval = new Interval(start, stop)
    stream.getText(interval)
  }
}
