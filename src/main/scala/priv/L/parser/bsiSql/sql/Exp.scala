package priv.L.parser.bsiSql.sql

abstract class Exp {
  def getStruct: String
}

case class Assignment(viewName: String, sql: String, tables: Tables) extends Exp {
  override def getStruct: String = s"assignment(view_name: $viewName, sql: $sql, tables: ${tables.getStruct})"
}

case class NormalStatement(sql: String) extends Exp{
  override def getStruct: String = s"normalStatement(sql: $sql)"
}

case class Table(name: String, db: Option[String], alias: Option[String]) extends Exp{
  override def getStruct: String =
    s"table(name:${db.map(n=>s"$n.").getOrElse("")}$name${alias.map(a=>s", alias: $a").getOrElse("")})"
}

case class Tables(tables: List[Table]) extends Exp {
  override def getStruct: String = s"Tables(${tables.map(_.getStruct).mkString(", ")})"
}
