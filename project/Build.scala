import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "timeconv"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.scala-tools.time" %% "time" % "0.5"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
