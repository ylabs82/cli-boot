plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "cli-boot-build"

include("cli-boot-project:cli-boot")
include("cli-boot-project:cli-boot-autoconfigure")

include("cli-boot-tests:cli-boot-integration-tests:cli-boot-loader-tests:cli-boot-loader-tests-app")
