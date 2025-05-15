val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "cpolordemo",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.14",
      "org.scalatest" %% "scalatest" % "3.2.14" % Test
    ),
    testFrameworks += new TestFramework("org.scalatest.tools.Framework"),
    coverageEnabled := true,
    coverageFailOnMinimum := false,
    coverageHighlighting := true,
    coverageMinimumStmtTotal := 60,
    coverageMinimumBranchTotal := 60
  )