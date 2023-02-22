plugins {
    id("project.publishing-conventions")
}

repositories {
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    compileOnly(libs.spigot)
}