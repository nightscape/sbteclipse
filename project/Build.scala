import sbt._
import sbt.Keys._
import com.typesafe.sbtscalariform.ScalariformPlugin._
import sbt.ScriptedPlugin._
import sbtrelease.ReleasePlugin._

object Build extends Build {

  lazy val root = Project(
    "sbteclipse",
    file("."),
    aggregate = Seq(sbteclipseCore, sbteclipsePlugin),
    settings = commonSettings ++ Seq(
      publishArtifact := false
    )
  )

  lazy val sbteclipseCore = Project(
    "sbteclipse-core",
    file("sbteclipse-core"),
    settings = commonSettings ++ Seq(
      libraryDependencies ++= Seq(
        Dependencies.Compile.ScalazCore,
        Dependencies.Compile.ScalazEffect
      )
    )
  )

  lazy val sbteclipsePlugin = Project(
    "sbteclipse-plugin",
    file("sbteclipse-plugin"),
    dependencies = Seq(sbteclipseCore),
    settings = commonSettings
  )

  def commonSettings = 
    Defaults.defaultSettings ++ 
    scalariformSettings ++
    scriptedSettings ++
    releaseSettings ++
    Seq(
      organization := "com.typesafe.sbteclipse",
      scalacOptions ++= Seq("-unchecked", "-deprecation"),
      libraryDependencies ++= Seq(
        Dependencies.Test.Specs2,
        Dependencies.Test.ScalaCheck,
        Dependencies.Test.Mockito,
        Dependencies.Test.Hamcrest
      ),
      publishTo <<= isSnapshot(if (_) Some(Classpaths.typesafeSnapshots) else Some(Classpaths.typesafeReleases)),
      sbtPlugin := true,
      publishMavenStyle := false,
      publishArtifact in (Compile, packageDoc) := false,
      publishArtifact in (Compile, packageSrc) := false,
      scriptedLaunchOpts += "-Xmx1024m",
      initialCommands in console := "import com.typesafe.sbteclipse._"
    )

  object Dependencies {

    object Compile {
      val ScalazCore = "org.scalaz" %% "scalaz-core" % "7.0.0-M1" cross CrossVersion.full
      val ScalazEffect = "org.scalaz" %% "scalaz-effect" % "7.0.0-M1" cross CrossVersion.full
    }

    object Test {
      val Specs2 = "org.specs2" %% "specs2" % "1.11" % "test" cross CrossVersion.full
      val ScalaCheck = "org.scalacheck" %% "scalacheck" % "1.9" % "test" cross CrossVersion.full
      val Mockito = "org.mockito" % "mockito-all" % "1.9.0" % "test"
      val Hamcrest = "org.hamcrest" % "hamcrest-all" % "1.1" % "test"
    }
  }
}
