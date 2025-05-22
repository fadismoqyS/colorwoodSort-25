name := "ColorwoodSort"
version := "1.0"
scalaVersion := "3.6.4"

// Source-Verzeichnis anpassen
Compile / scalaSource := baseDirectory.value / "src" / "src" / "main" / "scala"
Test / scalaSource := baseDirectory.value / "src" / "src" / "test" / "scala"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"

Compile / mainClass := Some("de.htwg.se.colorwoodSort.Main")