enablePlugins(ScalaJSPlugin)

name := "scalajs-react-toggle"

version := "0.1.0"

scalaVersion := "2.11.7"

val scalaJSReactVersion = "0.10.4"

val scalaCssVersion = "0.4.0-SNAPSHOT"

val reactJSVersion = "0.14.4"

libraryDependencies ++= Seq(
  "com.github.japgolly.scalajs-react" %%% "core" % scalaJSReactVersion,
  "com.github.japgolly.scalajs-react" %%% "extra" % scalaJSReactVersion,
  "com.github.japgolly.scalacss" %%% "core" % scalaCssVersion,
  "com.github.japgolly.scalacss" %%% "ext-react" % scalaCssVersion
)

jsDependencies ++= Seq(
  "org.webjars.npm" % "react"     % reactJSVersion / "react-with-addons.js" commonJSName "React"    minified "react-with-addons.min.js",
  "org.webjars.npm" % "react-dom" % reactJSVersion / "react-dom.js"         commonJSName "ReactDOM" minified "react-dom.min.js" dependsOn "react-with-addons.js"

)


persistLauncher := true

persistLauncher in Test := false

skip in packageJSDependencies := false


crossTarget in (Compile, fullOptJS) := file("js")

crossTarget in (Compile, fastOptJS) := file("js")

crossTarget in (Compile, packageJSDependencies) := file("js")

crossTarget in (Compile, packageScalaJSLauncher) := file("js")

crossTarget in (Compile, packageMinifiedJSDependencies) := file("js")

artifactPath in (Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value /
  ((moduleName in fastOptJS).value + "-opt.js"))



scalacOptions ++= Seq("-feature", "-deprecation", "-language:postfixOps")