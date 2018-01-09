package priv.L.parser

import java.util.Scanner

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import research.parser.{MathLexer, MathParser}

object Program {
  def main(args: Array[String]): Unit = {
    val scan = new Scanner(System.in)
    var continue = true
    while (continue) {
      print(">")
      val input = scan.nextLine()
      if(input.trim.toLowerCase()==":q"){
        continue = false
      } else {
        val inputStream = CharStreams.fromString(input)
        val lexer = new MathLexer(inputStream)
        val tokenStream = new CommonTokenStream(lexer)
        val parser = new MathParser(tokenStream)

        try{
          val cst = parser.compileUnit()
          val ast = new MyCstVisitor().visitCompileUnit(cst)
//          println(s":${ast.getString}")
          println(s"=${(new MyAstVisitor).visit(ast)}")
        } catch {
          case ex: Exception =>
            println(s"get Exception: ${ex.getMessage} for $input")
        }
      }
    }
  }

}
