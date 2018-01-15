package priv.L.parser.bsiSql.sql

import scala.collection.JavaConversions._

import org.antlr.v4.runtime.misc.Interval
import org.antlr.v4.runtime.{CharStream, ParserRuleContext}
import research.parser.BsiSqlBaseParser.{BsiAssignmentContext, JoinRelationContext}
import research.parser.{BsiSqlBaseBaseVisitor, BsiSqlBaseParser}

class BsiSqlParser(stream: CharStream) extends BsiSqlBaseBaseVisitor[Option[Exp]]{

  override def visitSingleStatement(ctx: BsiSqlBaseParser.SingleStatementContext): Option[Exp] = visit(ctx.extendedStatement())

  override def visitNormalStatement(ctx: BsiSqlBaseParser.NormalStatementContext): Option[Exp] = {
    val sql = getOriginalString(ctx)
    Option(NormalStatement(sql))
  }

  override def visitBsiAssignment(ctx: BsiAssignmentContext): Option[Exp] =  {
    val qtRet = visit(ctx.qt)
    if(qtRet == null) println("null qt ret")
    val tables: Exp = qtRet.getOrElse(Tables(List()))
//    visit(ctx.qo)
    val viewName = getOriginalString(ctx.viewName)
    val sql = getOriginalString(ctx.qt)
    val sqlOrg = getOriginalString(ctx.qo)
    val as = Assignment(viewName, s"$sql $sqlOrg", tables.asInstanceOf[Tables])
    Option(as)
  }

  // sub productions for queryTerm
  override def visitQueryTermDefault(ctx: BsiSqlBaseParser.QueryTermDefaultContext): Option[Exp] = visit(ctx.queryPrimary())

  override def visitSetOperation(ctx: BsiSqlBaseParser.SetOperationContext): Option[Exp] = None

  // sub productions for queryPrimary
  override def visitQueryPrimaryDefault(ctx: BsiSqlBaseParser.QueryPrimaryDefaultContext): Option[Exp] = visit(ctx.querySpecification())

  override def visitOtherQueryPrimary(ctx: BsiSqlBaseParser.OtherQueryPrimaryContext): Option[Exp] = None

  // sub productions for querySpecification
  override def visitNormalQuery(ctx: BsiSqlBaseParser.NormalQueryContext): Option[Exp] = visit(ctx.fromClausePart)

  override def visitSimpleQuery(ctx: BsiSqlBaseParser.SimpleQueryContext): Option[Exp] = None

  // sub productions for fromClause
  override def visitFromParts(ctx: BsiSqlBaseParser.FromPartsContext): Option[Exp] = {
    val tablesList = ctx.relationPart.flatMap(ct =>visit(ct)).map(_.asInstanceOf[Tables])
    val tables = tablesList.flatMap(_.tables).toList
    Option(Tables(tables))
  }

  // sub productions for relation
  override def visitRelationParts(ctx: BsiSqlBaseParser.RelationPartsContext): Option[Exp] = {
    val firstTable = visit(ctx.primaryPart).get.asInstanceOf[Table]
    val list = List(ctx.joinParts.toArray(new Array[JoinRelationContext](0)): _*)
      .flatMap(visit).map(_.asInstanceOf[Table])
    Option(Tables(firstTable :: list))
  }

  // sub productions for joinRelation
  override def visitNormalJoin(ctx: BsiSqlBaseParser.NormalJoinContext): Option[Exp] = visit(ctx.right)

  override def visitNaturalJoin(ctx: BsiSqlBaseParser.NaturalJoinContext): Option[Exp] = visit(ctx.right)

  // sub productions for relationPrimary
  override def visitTableName(ctx: BsiSqlBaseParser.TableNameContext): Option[Exp] = {
    val name = getOriginalString(ctx.identifierPart.table)
    val db = getOptionalOriStr(ctx.identifierPart.db)
    val alias = getOptionalOriStr(ctx.aliasPart)
    Option(Table(name, db, alias))
  }

  override def visitAliasedQuery(ctx: BsiSqlBaseParser.AliasedQueryContext): Option[Exp] = None

  override def visitAliasedRelation(ctx: BsiSqlBaseParser.AliasedRelationContext): Option[Exp] = None

  override def visitInlineTableDefault2(ctx: BsiSqlBaseParser.InlineTableDefault2Context): Option[Exp] = None

  override def visitTableValuedFunction(ctx: BsiSqlBaseParser.TableValuedFunctionContext): Option[Exp] = None

  // util functions
  def getOptionalOriStr(ctx: ParserRuleContext): Option[String] =
    if (ctx == null || (ctx.start.getStartIndex > ctx.stop.getStopIndex)) None else Option(getOriginalString(ctx))

  def getOriginalString(ctx: ParserRuleContext): String = {
    val start = ctx.start.getStartIndex
    val stop = ctx.stop.getStopIndex

    val interval = new Interval(start, stop)
    stream.getText(interval)
  }
}
