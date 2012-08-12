import sbt._
import sbt.Keys._
import com.typesafe.sbtscalariform.ScalariformPlugin._

object Build extends Build {

  lazy val root = Project(
    "sbteclipse",
    file("."),
    settings = commonSettings ++ Seq(
      libraryDependencies ++= Seq(
      )
    )
  )

  def commonSettings = 
    Defaults.defaultSettings ++ 
    scalariformSettings ++
    Seq(
      organization := "com.typesafe.sbteclipse",
      scalaVersion := "2.10.0-M6",
      scalacOptions ++= Seq("-unchecked", "-deprecation"),
      libraryDependencies ++= Seq(
        Dependencies.Test.Specs2,
        Dependencies.Test.ScalaCheck,
        Dependencies.Test.Mockito,
        Dependencies.Test.Hamcrest
      ),
      initialCommands in console := "import com.typesafe.sbteclipse.sbteclipse._"
    )

  object Dependencies {

    object Compile {
      val Config = "com.typesafe" % "config" % "0.5.0"
      val Scalaz = "org.scalaz" %% "scalaz-core" % "7.0.0-M1" cross CrossVersion.full
    }

    object Test {
      val Specs2 = "org.specs2" %% "specs2" % "1.11" % "test" cross CrossVersion.full
      val ScalaCheck = "org.scalacheck" %% "scalacheck" % "1.10.0" % "test" cross CrossVersion.full
      val Mockito = "org.mockito" % "mockito-all" % "1.9.0" % "test"
      val Hamcrest = "org.hamcrest" % "hamcrest-all" % "1.1" % "test"
    }
  }
}
