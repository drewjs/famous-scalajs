import sbt._
import Keys._
import play.PlayScala
import play.Play._
import com.typesafe.sbt.packager.universal.UniversalKeys
import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import org.scalajs.sbtplugin.cross.{CrossProject, CrossType}
import sbt.Project.projectToRef
import playscalajs._
import playscalajs.PlayScalaJS.autoImport._
import playscalajs.ScalaJSPlay.autoImport._
import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.web.SbtWeb.autoImport._
import com.typesafe.sbt.gzip.Import._
import com.typesafe.sbt.web.pipeline.Pipeline

object Versions {
  val app = "0.1.0-SNAPSHOT"
  val scala = "2.11.5"
  val scalajsDom = "0.8.0"
  val scalaRx = "0.2.7"
  val scalatags = "0.4.5"
  val upickle = "0.2.6"
  val autowire = "0.2.4"
}

object ApplicationBuild extends Build with UniversalKeys {
  
  lazy val clients = Seq(sitejs)

  override def rootProject = Some(sitejvm)

  lazy val sitejvm = (project in file("sitejvm")).settings(
    scalaVersion := Versions.scala,
    scalaJSProjects := clients,
    pipelineStages := Seq(scalaJSProd, gzip),
    libraryDependencies ++= Seq(
      "org.webjars" %% "webjars-play" % "2.3.0",
      "org.webjars" % "fastclick" % "1.0.2",
      "org.webjars" % "modernizr" % "2.7.1",
      "org.webjars" % "jquery" % "2.1.1",
      "org.webjars" % "foundation" % "5.4.0",
      "org.webjars" % "font-awesome" % "4.3.0-1",
      "com.lihaoyi" %% "scalatags" % Versions.scalatags,
      "com.lihaoyi" %% "scalarx" % Versions.scalaRx,
      "com.lihaoyi" %% "upickle" % Versions.upickle,
      "com.lihaoyi" %% "autowire" % Versions.autowire,
      "com.vmunier" %% "play-scalajs-scripts" % "0.1.0",
      "org.webjars" % "jquery" % "1.11.1"
    ),
    EclipseKeys.skipParents in ThisBuild := false
  )
  .enablePlugins(PlayScala,SbtWeb)
  .aggregate(clients.map(projectToRef): _*)
  .dependsOn(sitejvmShared)

  lazy val sitejs = (project in file("sitejs")).settings(
    scalaVersion := Versions.scala,
    persistLauncher := false,
    persistLauncher in Test := false,
    sourceMapsDirectories += sitejsShared.base / "..",
    unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value),
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "scalatags" % Versions.scalatags,
      "com.lihaoyi" %%% "scalarx" % Versions.scalaRx,
      "com.lihaoyi" %%% "upickle" % Versions.upickle,
      "com.lihaoyi" %%% "autowire" % Versions.autowire,
      "org.scala-js" %%% "scalajs-dom" % Versions.scalajsDom
    )
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSPlay)
  .dependsOn(sitejsShared)

  lazy val shared = (
    crossProject.crossType(CrossType.Pure) in file("shared")
  )
  .settings(scalaVersion := Versions.scala)
  .jsConfigure(_ enablePlugins ScalaJSPlay)
  .jsSettings(sourceMapsBase := baseDirectory.value / "..")

  lazy val sitejvmShared = shared.jvm
  lazy val sitejsShared = shared.js

  // loads the jvm project at sbt startup
  onLoad in Global := (Command.process("project sitejvm", _: State)) compose (onLoad in Global).value
}
