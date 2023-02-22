plugins {
    id("project.common-conventions")
    `maven-publish`
}

publishing {
    repositories {
    }
    publications {
        create<MavenPublication>("maven") {
            artifactId = "${rootProject.name}-${project.name}"
            from(components["java"])
        }
    }
}