plugins {
    id("buildlogic.java-library-conventions")
}

description = "CLI Boot"

dependencies {
    implementation(project(":cli-boot-project:cli-boot-autoconfigure"))
}
