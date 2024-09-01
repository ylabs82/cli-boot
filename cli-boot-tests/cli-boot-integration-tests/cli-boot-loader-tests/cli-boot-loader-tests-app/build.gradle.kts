plugins {
    id("buildlogic.java-application-conventions")
}

application {
    mainClass = "es.ylabs.cliframework.boot.tests.loaderapp.LoaderTestApplication"
}

dependencies {
    implementation(project(":cli-boot-project:cli-boot"))
    implementation(project(":cli-boot-project:cli-boot-autoconfigure"))
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks {
    val standaloneJar = register<Jar>("standaloneJar") {
        dependsOn.addAll(listOf(
            ":cli-boot-project:cli-boot:jar",
            ":cli-boot-project:cli-boot-autoconfigure:jar",
            "compileJava",
            "processResources"
        ))

        archiveClassifier = "standalone"

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest {
            attributes(mapOf("Main-Class" to application.mainClass))
        }

        val sources = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } + sources.output
        from(contents)
    }

    build {
        dependsOn(standaloneJar)
    }
}
