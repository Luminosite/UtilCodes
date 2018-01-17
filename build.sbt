name := "IntegratedSqlParser"

version := "1.0"

scalaVersion := "2.11.8"

antlr4Settings

antlr4Version in Antlr4 := "4.5.3"

antlr4PackageName in Antlr4 := Some("research.parser")
antlr4GenListener in Antlr4 := true // default: true
antlr4GenVisitor in Antlr4 := true // default: false

// testing
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "com.holdenkarau" %% "spark-testing-base" % "2.0.0_0.6.0" % "test"
libraryDependencies += "org.hibernate" % "hibernate-core" % "3.6.10.Final"

libraryDependencies += "org.powermock" % "powermock-module-junit4-rule" % "1.5.1" % "test"
libraryDependencies += "org.powermock" % "powermock-api-mockito" % "1.5.1" % "test"
libraryDependencies += "org.powermock" % "powermock-classloading-xstream" % "1.5.1" % "test"
libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5" % "test"
libraryDependencies += "junit" % "junit" % "4.11" % "test"

lazy val showD = taskKey[Unit]("show")
showD := {
  println((javaSource in Antlr4).value)
  println((antlr4PackageName in Antlr4).value)
  println(streams.value.cacheDirectory)
}
