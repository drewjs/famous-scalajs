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

object ApplicationBuild extends Build with UniversalKeys {

  val scalajsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

  override def rootProject = Some(sitejvm)

  val sharedSrcDir = "shared"

  lazy val sitejvm = Project(
    id = "sitejvm",
    base = file("sitejvm"))
  .enablePlugins(play.PlayScala)
  .settings(sitejvmSettings: _*)
  .aggregate(sitejs)

  lazy val sitejs = Project(
    id = "sitejs",
    base = file("sitejs"))
  .enablePlugins(ScalaJSPlugin)
  .settings(sitejsSettings: _*)

  lazy val shared = Project(
    id = "shared",
    base = file(sharedSrcDir)
  ) settings (sharedScalaSettings: _*)

  lazy val sitejvmSettings =
    Seq(
      name := "stab-project",
      version := Versions.app,
      scalaVersion := Versions.scala,
      //scalajsOutputDir := (classDirectory in Compile).value / "public" / "javascripts",
      scalajsOutputDir := (crossTarget in Compile).value / "classes" / "public" / "javascripts",
      compile in Compile <<= (compile in Compile) dependsOn (fastOptJS in (sitejs, Compile)) dependsOn copySourceMapsTask,
      dist <<= dist dependsOn (fullOptJS in (sitejs, Compile)),
      //stage <<= stage dependsOn (fullOptJS in (sitejs, Compile)),
      libraryDependencies ++= Dependencies.sitejvm.value,
      EclipseKeys.skipParents in ThisBuild := false,
      commands += preStartCommand
    ) ++ (
      Seq(packageScalaJSLauncher, fastOptJS, fullOptJS) map { packageJSKey =>
        crossTarget in (sitejs, Compile, packageJSKey) := scalajsOutputDir.value
      }
    ) ++ sharedDirectorySettings

  lazy val sitejsSettings = Seq(
      name := "sitejs-example",
      version := Versions.app,
      scalaVersion := Versions.scala,
      persistLauncher := false,
      persistLauncher in Test := false,
      relativeSourceMaps := true,
      libraryDependencies ++= Dependencies.sitejs.value
    ) ++ sharedDirectorySettings

  lazy val sharedScalaSettings =
    Seq(
      name := "shared",
      EclipseKeys.skipProject := true,
      libraryDependencies ++= Dependencies.shared.value
    )

  lazy val sharedDirectorySettings = Seq(
    unmanagedSourceDirectories in Compile += new File((file(".") / sharedSrcDir / "src" / "main" / "scala").getCanonicalPath),
    unmanagedSourceDirectories in Test += new File((file(".") / sharedSrcDir / "src" / "test" / "scala").getCanonicalPath),
    unmanagedResourceDirectories in Compile += file(".") / sharedSrcDir / "src" / "main" / "resources",
    unmanagedResourceDirectories in Test += file(".") / sharedSrcDir / "src" / "test" / "resources"
  )

  val copySourceMapsTask = Def.task {
    val scalaFiles = (Seq(shared.base, sitejs.base) ** ("*.scala")).get
    for (scalaFile <- scalaFiles) {
      val target = new File((classDirectory in Compile).value, scalaFile.getPath)
      IO.copyFile(scalaFile, target)
    }
  }

  // Use reflection to rename the 'start' command to 'play-start'
  Option(play.Play.playStartCommand.getClass.getDeclaredField("name")) map { field =>
    field.setAccessible(true)
    field.set(playStartCommand, "play-start")
  }

  // The new 'start' command optimises the JS before calling the Play 'start' renamed 'play-start'
  val preStartCommand = Command.args("start", "<port>") { (state: State, args: Seq[String]) =>
    Project.runTask(fullOptJS in (sitejs, Compile), state)
    state.copy(remainingCommands = ("play-start " + args.mkString(" ")) +: state.remainingCommands)
  }
}

object Dependencies {
  val shared = Def.setting(Seq())

  val sitejvm = Def.setting(shared.value ++ Seq(
    "org.webjars" %% "webjars-play" % "2.3.0",
    "org.webjars" % "fastclick" % "1.0.2",
    "org.webjars" % "modernizr" % "2.7.1",
    "org.webjars" % "jquery" % "2.1.1",
    "org.webjars" % "foundation" % "5.4.0",
    "org.webjars" % "font-awesome" % "4.3.0-1",
    "com.lihaoyi" %% "scalatags" % Versions.scalatags,
    "com.lihaoyi" %% "upickle" % Versions.upickle,
    "com.lihaoyi" %% "autowire" % Versions.autowire,
    "com.lihaoyi" %% "scalarx" % Versions.scalaRx,
    "com.vmunier" %% "play-scalajs-sourcemaps" % Versions.playScalajsSourcemaps
  )) 

  val sitejs = Def.setting(shared.value ++ Seq(
    "com.lihaoyi" %%%! "scalatags" % Versions.scalatags,
    "com.lihaoyi" %%%! "scalarx" % Versions.scalaRx,
    "com.lihaoyi" %%%! "upickle" % Versions.upickle,
    "com.lihaoyi" %%%! "autowire" % Versions.autowire,
    "org.scala-js" %%%! "scalajs-dom" % Versions.scalajsDom
  ))
}

object Versions {
  val app = "0.1.0-SNAPSHOT"
  val playScalajsSourcemaps = "0.1.0"
  val scala = "2.11.4"
  val scalajsDom = "0.7.0"
  val scalaRx = "0.2.7-RC1"
  val scalatags = "0.4.3-RC1"
  val upickle = "0.2.6-RC1"
  val autowire = "0.2.4-RC1"
}
