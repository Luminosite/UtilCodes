package priv.L.parser.bsiSql.sql

import java.util.Scanner

import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}
import research.parser.{BsiSqlBaseLexer, BsiSqlBaseParser}

object Program {
  def getParsedData(input: String): (ANTLRInputStream, BsiSqlBaseParser) = {
    val inputStream = new ANTLRInputStream(input)
    val lexer = new BsiSqlBaseLexer(inputStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new BsiSqlBaseParser(tokenStream)
    (inputStream, parser)
  }

  def main(args: Array[String]): Unit = {
    val scan = new Scanner(System.in)
    var continue = true
    while (continue) {
      print(">")
      val input = scan.nextLine()
      if(input.trim.toLowerCase()==":q"){
        continue = false
      } else {
        val (inputStream, parser) = getParsedData(input)

        try{
          val cst = parser.singleStatement()
          val ast = new BsiSqlParser(inputStream).visitSingleStatement(cst)
          println(s":${ast.get.getStruct}")
        } catch {
          case ex: Exception =>
            println(s"get Exception: ${ex.getMessage} for $input")
            ex.printStackTrace()
        }
      }
    }
  }

}
