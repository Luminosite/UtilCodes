package priv.L.parser.sql

import java.util.Scanner

import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}
import research.parser.{SqlBaseLexer, SqlBaseParser}

object Program {
  private def getParsedData(input: String): (ANTLRInputStream, SqlBaseParser) = {
    val inputStream = new ANTLRInputStream(input)
    val lexer = new SqlBaseLexer(inputStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new SqlBaseParser(tokenStream)
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
          val ast = new SqlParser(inputStream).visitSingleStatement(cst)
          println(s":${ast.getStruct}")
        } catch {
          case ex: Exception =>
            println(s"get Exception: ${ex.getMessage} for $input")
        }
      }
    }
  }

}
