package testBase.example

import priv.L.parser.bsiSql.sql.{BsiSqlParser, Program}
import testBase.utils.testUtils.UTTrait

/**
  * Created by kunfu on 2018-01-12.
  */
class ParserTest extends UTTrait{
  "Parser" should "parse join sql correctly" in {
    val input = "myView = select * from table_a a join table_b b on a.a1 = b.bi where a.a2 > 0"
    val (inputStream, parser) = Program.getParsedData(input)
    val cst = parser.singleStatement()
    val ast = new BsiSqlParser(inputStream).visitSingleStatement(cst)
    println(s":${ast.get.getStruct}")
  }

  "Parser" should "parse simple sql correctly" in {
    val input = "myView = select * from table_a where a2 > 0"

    val (inputStream, parser) = Program.getParsedData(input)

    val cst = parser.singleStatement()
    val ast = new BsiSqlParser(inputStream).visitSingleStatement(cst)
    println(s":${ast.get.getStruct}")
  }

  "Parser" should "parse normal sql correctly" in {
    val input = "select k1, k2, k3 from table_a where a2 > 0"

    val (inputStream, parser) = Program.getParsedData(input)

    val cst = parser.singleStatement()
    val ast = new BsiSqlParser(inputStream).visitSingleStatement(cst)

    ast.get.getStruct shouldBe "normalStatement(sql: select k1, k2, k3 from table_a where a2 > 0)"
  }
}
