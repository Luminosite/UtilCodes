package testBase.example

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import priv.L.parser.bsiSql.sql.BsiSqlParser
import research.parser.{BsiSqlBaseLexer, BsiSqlBaseParser}
import testBase.utils.testUtils.UTTrait

/**
  * Created by kunfu on 2018-01-12.
  */
class ParserTest extends UTTrait{
  "Parser" should "parse join sql correctly" in {
    val input = "myView = select * from table_a a join table_b b on a.a1 = b.bi where a.a2 > 0"

    val inputStream = CharStreams.fromString(input)
    val lexer = new BsiSqlBaseLexer(inputStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new BsiSqlBaseParser(tokenStream)

    val cst = parser.singleStatement()
    val ast = new BsiSqlParser(inputStream).visitSingleStatement(cst)
    println(s":${ast.get.getStruct}")
  }

  "Parser" should "parse simple sql correctly" in {
    val input = "myView = select * from table_a where a2 > 0"

    val inputStream = CharStreams.fromString(input)
    val lexer = new BsiSqlBaseLexer(inputStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new BsiSqlBaseParser(tokenStream)

    val cst = parser.singleStatement()
    val ast = new BsiSqlParser(inputStream).visitSingleStatement(cst)
    println(s":${ast.get.getStruct}")
  }

  "Parser" should "parse normal sql correctly" in {
    val input = "select k1, k2, k3 from table_a where a2 > 0"

    val inputStream = CharStreams.fromString(input)
    val lexer = new BsiSqlBaseLexer(inputStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new BsiSqlBaseParser(tokenStream)

    val cst = parser.singleStatement()
    val ast = new BsiSqlParser(inputStream).visitSingleStatement(cst)

    ast.get.getStruct shouldBe "normalStatement(sql: select k1, k2, k3 from table_a where a2 > 0)"
  }
}
