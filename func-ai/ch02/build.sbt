
unmanagedJars in Compile +=
  Attributed.blank(
    file(scala.util.Properties.javaHome) / "/lib/jfxrt.jar")


lazy val root = (project in file(".")).
  settings(
    name := "ch02",
    version := "1.0",
    scalaVersion := "2.11.7"
  )

/*
  jfxSettings
  JFX.artifactBaseNameValue <<= name
  //JFX.mainClass := Some("ch01.CurveMain")
  JFX.mainClass := Some("ch02.Queen")
*/
