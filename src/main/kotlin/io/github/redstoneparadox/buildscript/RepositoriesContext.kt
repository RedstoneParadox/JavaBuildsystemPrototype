package io.github.redstoneparadox.buildscript

class RepositoriesContext {
    val repositories: MutableList<String> = mutableListOf();

    fun repository(repository: String) {
        repositories.add(repository)
    }
}