package priv.L.parser

abstract class ExpNode {def getString: String}

abstract class InfixNode(left: ExpNode, right: ExpNode) extends ExpNode {
  val op: String
  override def getString: String = s"cal[${left.toString}, $op, ${right.toString}]"
}

case class AddNode(left: ExpNode, right: ExpNode) extends InfixNode(left, right){val op = "+"}
case class SubNode(left: ExpNode, right: ExpNode) extends InfixNode(left, right){val op = "-"}
case class DivNode(left: ExpNode, right: ExpNode) extends InfixNode(left, right){val op = "*"}
case class MulNode(left: ExpNode, right: ExpNode) extends InfixNode(left, right){val op = "/"}

case class NegateNode(inner: ExpNode) extends ExpNode {def getString: String = s"[-${inner.getString}]"}

case class FunNode(function1: (Double) => Double, node: ExpNode) extends ExpNode {
  override def getString: String = s"Func[${function1.toString()}, ${node.getString}]"}

case class NumberNode(value: Double) extends ExpNode {
  override def getString: String = s"$value"}
