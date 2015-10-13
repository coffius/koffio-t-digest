lazy val root = (project in file(".")).
  settings(
    name := "koffio-t-digest",
    version := "0.0.1",
    scalaVersion := "2.11.7"
  )

libraryDependencies ++= Seq(
  "com.tdunning" % "t-digest" % "3.1",
  "org.apache.commons" % "commons-math3" % "3.5",
  "com.madhukaraphatak" %% "java-sizeof" % "0.1",
  "com.clearspring.analytics" % "stream" % "2.7.0"
)