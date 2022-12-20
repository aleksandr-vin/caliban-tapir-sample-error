// scalafmt: { align.tokens.add = ["<-", "%%", "%", "="] }

resolvers ++= Resolver.sonatypeOssRepos("public")

lazy val akkaVersion                     = "2.6.20"
lazy val catsVersion                     = "2.7.0"
lazy val circeVersion                    = "0.14.1"
lazy val enumeratumQuillVersion          = "1.7.1" // note: integration versions are not linked to the core enumeratum version
lazy val enumeratumVersion               = "1.7.0"
lazy val logBackVersion                  = "1.2.10"
lazy val mammothVersion                  = "1.4.2"
lazy val nettyVersion                    = "4.1.72.Final"
lazy val pureConfigVersion               = "0.17.1"
lazy val scalaCacheVersion               = "1.0.0-M6"
lazy val scalaLoggingVersion             = "3.9.4"
lazy val scalaParallelCollectionsVersion = "0.2.0"
lazy val scalaTestVersion                = "3.2.10"
lazy val spoiwoVersion                   = "2.0.0"
lazy val typesafeConfigVersion           = "1.4.1"
lazy val wiremockVersion                 = "2.32.0"
lazy val jacksonVersion                  = "2.13.3"
lazy val janinoVersion                   = "3.1.6"
lazy val monocleVersion                  = "3.1.0"
lazy val calibanVersion                  = "2.0.2"
lazy val zioVersion                      = "2.0.0"
lazy val zioJsonVersion                  = "0.3.0"
lazy val zioPreludeVersion               = "1.0.0-RC16"
lazy val tapirVersion                    = "1.2.0"
lazy val swaggerUiVersion                = "4.14.2"
lazy val http4sBlazeServerVersion        = "0.23.12"
lazy val sttpVersion                     = "3.8.3"

lazy val apiDeps = Seq(
  "com.softwaremill.sttp.tapir" %% "tapir-core"              % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe"        % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-http4s-server-zio" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-http4s-server"     % tapirVersion,
  "org.http4s"                  %% "http4s-blaze-server"     % http4sBlazeServerVersion,
  "com.github.ghostdogpr"       %% "caliban-tapir"           % calibanVersion
)

lazy val zioDeps = Seq(
  "com.github.ghostdogpr" %% "caliban"  % calibanVersion,
  "dev.zio"               %% "zio"      % zioVersion,
  "dev.zio"               %% "zio-json" % zioJsonVersion
)

lazy val serializationDeps = Seq(
  "com.beachape" %% "enumeratum"           % enumeratumVersion,
  "com.beachape" %% "enumeratum-circe"     % enumeratumVersion,
  "com.beachape" %% "enumeratum-quill"     % enumeratumQuillVersion,
  "io.circe"     %% "circe-core"           % circeVersion,
  "io.circe"     %% "circe-generic"        % circeVersion,
  "io.circe"     %% "circe-parser"         % circeVersion,
  "io.circe"     %% "circe-generic-extras" % circeVersion
)

lazy val loggingDeps = Seq(
  "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
  "ch.qos.logback"     % "logback-classic" % logBackVersion
)

lazy val miscDeps = Seq(
  "org.scala-lang.modules"        %% "scala-parallel-collections" % scalaParallelCollectionsVersion,
  "com.github.pureconfig"         %% "pureconfig"                 % pureConfigVersion,
  "org.typelevel"                 %% "cats-core"                  % catsVersion,
  "dev.optics"                    %% "monocle-core"               % monocleVersion,
  "dev.optics"                    %% "monocle-macro"              % monocleVersion,
  "dev.zio"                       %% "zio-macros"                 % zioVersion,
  "com.softwaremill.sttp.client3" %% "zio"                        % sttpVersion,
  "com.softwaremill.sttp.client3" %% "circe"                      % sttpVersion
)

lazy val root = (project in file("."))
  .settings(
    resolvers += "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots",
    inThisBuild(
      List(
        organization := "com.example",
        scalaVersion := "2.13.10"
      )
    ),
    name := "Example",
    libraryDependencies ++= Seq(
      apiDeps,
      zioDeps,
      serializationDeps,
      loggingDeps
    ).flatten,
    scalacOptions ++= Seq(
      "-language:implicitConversions",
      "-Vimplicits",
      "-Vtype-diffs",
      "-Xlint",
      "-Wconf:cat=lint-byname-implicit:silent",
      "-Wunused:params",
      "-Wunused:nowarn",
      "-Xfatal-warnings",
      "-Ybackend-parallelism",
      "8",
      "-Ymacro-annotations"
    )
  )

Compile / run / fork := true
