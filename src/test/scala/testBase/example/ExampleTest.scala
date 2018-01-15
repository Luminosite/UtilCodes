package testBase.example

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import priv.L.parser.bsiSql.sql.BsiSqlParser
import research.parser.{BsiSqlBaseLexer, BsiSqlBaseParser}
import testBase.utils.testUtils.UTTrait

/**
  * Created by kunfu on 2018-01-12.
  */
class ExampleTest extends UTTrait{

  "It" should "be runnable" in {
    val a = "test code"
    a shouldBe "test code"
  }
}
