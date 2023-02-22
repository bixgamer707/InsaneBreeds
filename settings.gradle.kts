rootProject.name = "InsaneBreeds"

arrayOf("api", "plugin").forEach {
    includePrefixed(":$it")
}

fun includePrefixed(name: String) {
    val kebabName = name.replace(":", "-")
    val path = name.replace(':', '/')

    include(kebabName)
    project(":$kebabName").projectDir = file(path)
}