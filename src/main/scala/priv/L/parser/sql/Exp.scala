package priv.L.parser.sql

abstract class Exp

case class Equal(viewName: String, rest: Exp) extends Exp
