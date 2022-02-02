package io.github.redstoneparadox.buildscript

class DependenciesContext {
    val dependencies: MutableList<String> = mutableListOf()

    @Suppress("unused")
    fun dependency(dependency: String) {
        dependencies.add(dependency)
    }
}