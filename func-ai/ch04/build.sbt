
lazy val root = (project in file(".")).
  settings(
    name := "ch04",
    version := "1.0",
    scalaVersion := "2.11.7"
  )


unmanagedJars in Compile +=
  Attributed.blank(
    file(scala.util.Properties.javaHome) / "/lib/jfxrt.jar")

jfxSettings
JFX.artifactBaseNameValue <<= name
//JFX.mainClass := Some("ch04.TTTGra")
//JFX.mainClass := Some("ch04.TTTMinMax")
JFX.mainClass := Some("ch04.TTTab")
