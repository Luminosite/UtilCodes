name := "UtilCodes"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

antlr4Settings

antlr4Version in Antlr4 := "4.5.3"

antlr4PackageName in Antlr4 := Some("research.parser")
antlr4GenListener in Antlr4 := true // default: true
antlr4GenVisitor in Antlr4 := true // default: false

lazy val showD = taskKey[Unit]("show")
showD := {
  println((javaSource in Antlr4).value)
  println((antlr4PackageName in Antlr4).value)
}

resolvers ++= Seq(
  Resolver.defaultLocal,
  Resolver.mavenLocal,
  // make sure default maven local repository is added... Resolver.mavenLocal has bugs.
  "Local Maven Repository" at Path.userHome.asFile.toURI.toURL + "/.m2/repository"
)

parallelExecution in Test := false

checksums in update := Nil
/// Assembly
// skip test in assembly
test in assembly := {}

// do not include scala libraries
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

// do not include scapegoat jars
assemblyExcludedJars in assembly := {
  val cp = (fullClasspath in assembly).value
  cp filter { cp =>
    cp.data.getName.startsWith("scalac-scapegoat-plugin") || cp.data.getName.startsWith("scaldi")
  }
}

// pack setting
// Enable plugin and automatically find def main(args:Array[String]) methods from the classpath
fork := true
packAutoSettings

// testing
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "com.holdenkarau" %% "spark-testing-base" % "2.0.0_0.6.0" % "test"
libraryDependencies += "org.hibernate" % "hibernate-core" % "3.6.10.Final"

libraryDependencies += "org.powermock" % "powermock-module-junit4-rule" % "1.5.1" % "test"
libraryDependencies += "org.powermock" % "powermock-api-mockito" % "1.5.1" % "test"
libraryDependencies += "org.powermock" % "powermock-classloading-xstream" % "1.5.1" % "test"
libraryDependencies += "org.mockito" % "mockito-all" % "1.9.5" % "test"
libraryDependencies += "junit" % "junit" % "4.11" % "test"

