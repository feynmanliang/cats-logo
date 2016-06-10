lazy val commonSettings = Seq(
  organization := "com.feynmanliang",
  version := "0.1.0",
  scalaVersion := "2.11.7"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "cats-logo",
    libraryDependencies += "org.typelevel" %% "cats" % "0.6.0"
  )

