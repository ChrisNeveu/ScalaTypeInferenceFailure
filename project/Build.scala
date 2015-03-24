import sbt._
import Keys._

object BuildSettings {
   
   val buildSettings = Defaults.defaultSettings ++ Seq(
      version      := "0.0.1-SNAPSHOT"
    , scalaVersion := "2.11.6"
    , scalacOptions ++= Seq(
         "-unchecked"
       , "-deprecation"
       , "-feature"
       , "-language:higherKinds"
       , "-language:postfixOps"
       )
    )
}

object ErroneousType extends Build {
   import BuildSettings._

   lazy val root: Project = Project(
      "root"
    , file(".")
    , settings = buildSettings ++ Seq(
         libraryDependencies ++= Seq(
            "org.scalaz" %% "scalaz-core" % "7.1.0"
          )
       )
    )
}
