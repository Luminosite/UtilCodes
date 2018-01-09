name := "IntegratedSqlParser"

version := "1.0"

scalaVersion := "2.11.8"

antlr4Settings

antlr4Version in Antlr4 := "4.7"

antlr4PackageName in Antlr4 := Some("research.parser")
antlr4GenListener in Antlr4 := true // default: true
antlr4GenVisitor in Antlr4 := true // default: false

lazy val showD = taskKey[Unit]("show")
showD := {
  println((javaSource in Antlr4).value)
  println((antlr4PackageName in Antlr4).value)
  println(streams.value.cacheDirectory)
}
