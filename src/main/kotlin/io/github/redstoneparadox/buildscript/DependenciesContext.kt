package io.github.redstoneparadox.buildscript

class DependenciesContext {
    val dependencies: MutableList<String> = mutableListOf();

    fun dependency(dependency: String) {
        dependencies.add(dependency)
    }
}