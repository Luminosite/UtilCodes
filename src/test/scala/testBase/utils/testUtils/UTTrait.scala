package testBase.utils.testUtils

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.hibernate.jdbc.util.BasicFormatterImpl
//import org.hibernate.engine.jdbc.internal.BasicFormatterImpl
import org.scalatest.{FlatSpec, Matchers, Outcome}

/**
  * Created by chufang on 12/30/15.
  */
trait UTTrait extends FlatSpec with Matchers{
//  def withFixture(test: Any): Outcome = ???
  val sqlFormatter = new BasicFormatterImpl()
  def assertEqualSql(sql1: String, sql2: String) {
    val formattedSQL = sqlFormatter.format(sql1);
    val formattedSQL2 = sqlFormatter.format(sql2);
    formattedSQL shouldBe formattedSQL2
  }
  protected def readFromResourceFile(file: String): String = {
    getClass.getResource(file).getPath
    val stream = getClass.getResourceAsStream(file)
    val conf = scala.io.Source.fromInputStream(stream).getLines.mkString("\n")
    conf
  }
  protected def getResourceFilePath(file: String): String = {
    getClass.getResource(file).getPath
  }
}
