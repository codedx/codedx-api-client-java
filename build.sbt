lazy val root = (project in file(".")).
  settings(
    organization := "com.codedx",
    name := "codedx-api-client-java",
    version := "3.5.0-SNAPSHOT",
    scalaVersion := "2.11.4",
    crossPaths := false,
    scalacOptions ++= Seq("-feature"),
    javacOptions in compile ++= Seq("-Xlint:deprecation"),
    publishArtifact in (Compile, packageDoc) := false,
    resolvers += Resolver.mavenLocal,
    libraryDependencies ++= Seq(
      "io.swagger" % "swagger-annotations" % "1.5.15",
      "com.squareup.okhttp" % "okhttp" % "2.7.5",
      "com.squareup.okhttp" % "logging-interceptor" % "2.7.5",
      "com.google.code.gson" % "gson" % "2.8.1",
      "org.threeten" % "threetenbp" % "1.3.5" % "compile",
      "io.gsonfire" % "gson-fire" % "1.8.0" % "compile",
      "junit" % "junit" % "4.12" % "test",
      "com.novocode" % "junit-interface" % "0.10" % "test"
    )
  )
  .settings(publishingSettings: _*)

lazy val publishingSettings = Seq(
   publishMavenStyle := true,
   publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if(isSnapshot.value)
         Some("snapshots" at nexus + "content/repositories/snapshots")
      else
         Some("releases" at nexus + "service/local/staging/deploy/maven2")
   },
   publishArtifact in Test := false,
   pomIncludeRepository := { _ => false },
   pomExtra :=
      <url>https://github.com/codedx/codedx-api-client-java</url>
         <description>Client SDK for Code Dx API</description>
         <scm>
            <connection>scm:git:git@github.com:codedx/codedx-api-client-java.git</connection>
            <developerConnection>scm:git:git@github.com:codedx/codedx-api-client-java.git</developerConnection>
            <url>https://github.com/codedx/codedx-api-client-java</url>
         </scm>

         <licenses>
            <license>
               <name>Apache License, Version 2.0</name>
               <url>https://www.apache.org/licenses/LICENSE-2.0</url>
               <distribution>repo</distribution>
            </license>
         </licenses>

         <developers>
            <developer>
               <name>Code Dx</name>
               <email>support@codedx.com</email>
               <organization>Code Dx</organization>
               <organizationUrl>https://codedx.com</organizationUrl>
            </developer>
         </developers>
)