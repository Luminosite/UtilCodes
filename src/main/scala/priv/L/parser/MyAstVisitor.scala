package priv.L.parser

import research.parser.MathParser.CompileUnitContext
import research.parser.{MathBaseVisitor, MathLexer, MathParser}

class MyAstVisitor {
  /**
  case class AddNode(left: ExpNode, right: ExpNode) extends InfixNode(left, right){val op = "+"}
case class SubNode(left: ExpNode, right: ExpNode) extends InfixNode(left, right){val op = "-"}
case class DivNode(left: ExpNode, right: ExpNode) extends InfixNode(left, right){val op = "*"}
case class MulNode(left: ExpNode, right: ExpNode) extends InfixNode(left, right){val op = "/"}

case class NegateNode(inner: ExpNode) extends ExpNode {def getString: String = s"[-${inner.getString}]"}

case class FunNode(function1: (Double) => Double, node: ExpNode) extends ExpNode {
  override def getString: String = s"Func[${function1.toString()}, ${node.getString}]"}

case class NumberNode(value: Double) extends ExpNode {
  override def getString: String = s"$value"}
    */
  def visit(node: ExpNode): Double = node match {
    case n if n.isInstanceOf[AddNode] => visit(n.asInstanceOf[AddNode])
    case n if n.isInstanceOf[SubNode] => visit(n.asInstanceOf[SubNode])
    case n if n.isInstanceOf[DivNode] => visit(n.asInstanceOf[DivNode])
    case n if n.isInstanceOf[MulNode] => visit(n.asInstanceOf[MulNode])
    case n if n.isInstanceOf[NegateNode] => visit(n.asInstanceOf[NegateNode])
    case n if n.isInstanceOf[FunNode] => visit(n.asInstanceOf[FunNode])
    case n if n.isInstanceOf[NumberNode] => visit(n.asInstanceOf[NumberNode])
  }
  def visit(node: AddNode): Double = visit(node.left) + visit(node.right)
  def visit(node: SubNode): Double = visit(node.left) - visit(node.right)
  def visit(node: DivNode): Double = visit(node.left) / visit(node.right)
  def visit(node: MulNode): Double = visit(node.left) * visit(node.right)

  def visit(node: NumberNode): Double = node.value
  def visit(node: FunNode): Double = node.function1(visit(node.node))
  def visit(node: NegateNode): Double = - visit(node.inner)
}
