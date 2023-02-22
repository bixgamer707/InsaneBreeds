plugins {
    id("project.common-conventions")
    alias(libs.plugins.shadow)
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://maven.enginehub.org/repo/")
	maven("https://jitpack.io")
}

dependencies {
    api(project(":-api"))
    compileOnly(libs.spigot)
    compileOnly(libs.itemsAdder)
    compileOnly(libs.oraxen)
    compileOnly(libs.vault)
    compileOnly(libs.placeholder)
}

tasks {
    shadowJar {
        archiveBaseName.set("InsaneBreeds")
        destinationDirectory.set(file("$rootDir/bin/"))
        minimize()
    }

    clean {
        delete("${rootDir}/bin/")
    }
}