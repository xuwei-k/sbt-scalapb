import SonatypeKeys._

sonatypeSettings

releaseSettings

sbtPlugin := true

scalaVersion := "2.10.4"

organization := "com.trueaccord.scalapb"

profileName := "com.trueaccord"

name := "sbt-scalapb"

addSbtPlugin("com.github.gseitz" % "sbt-protobuf" % "0.4.0")

ReleaseKeys.publishArtifactsAction := PgpKeys.publishSigned.value

// This is the version of the scalaPb compiler and runtime going to be used.
// The version for the *plugin* is in version.sbt.
val scalaPbVersion = "0.5.7"

libraryDependencies ++= Seq(
  "com.trueaccord.scalapb" %% "compilerplugin" % scalaPbVersion
)

pomExtra in ThisBuild := {
  <url>https://github.com/trueaccord/ScalaPB</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:github.com:trueaccord/ScalaPB.git</connection>
    <developerConnection>scm:git:git@github.com:trueaccord/ScalaPB.git</developerConnection>
    <url>github.com/trueaccord/ScalaPB</url>
  </scm>
  <developers>
    <developer>
      <id>thesamet</id>
      <name>Nadav S. Samet</name>
      <url>http://www.thesamet.com/</url>
    </developer>
  </developers>
}

def genVersionFile(out: File, pluginVersion: String): Seq[File] = {
  out.mkdirs()
  val f = out / "Version.scala"
  val w = new java.io.FileOutputStream(f)
  w.write(s"""|// Generated by ScalaPB's build.sbt.
              |
              |package com.trueaccord.scalapb.plugin
              |
              |object Version {
              |  val pluginVersion = "$pluginVersion"
              |  val scalaPbVersion = "$scalaPbVersion"
              |}
              |""".stripMargin.getBytes("UTF-8"))
  w.close()
  Seq(f)
}

sourceGenerators in Compile <+= (sourceManaged in Compile, version in Compile) map {
  (sourceManaged, version) =>
    genVersionFile(sourceManaged, version)
}
