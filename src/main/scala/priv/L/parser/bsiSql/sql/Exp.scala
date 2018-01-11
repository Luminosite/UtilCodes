package priv.L.parser.bsiSql.sql

abstract class Exp {
  def getStruct: String
}

case class Assignment(viewName: String, sql: String) extends Exp {
  override def getStruct: String = s"assignment(view_name: $viewName, sql: $sql)"
}

case class NormalStatement(sql: String) extends Exp{
  override def getStruct: String = s"normalStatement(sql: $sql)"
}
