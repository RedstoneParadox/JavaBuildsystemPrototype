package io.github.redstoneparadox.buildscript

import io.github.redstoneparadox.buildsystem.buildscript.Repository
import io.github.redstoneparadox.buildsystem.impl.repository.MavenRepository

class RepositoriesContext {
    val repositories: MutableList<Repository> = mutableListOf();

    @Suppress("unused")
    fun maven(repositorySetup: Repository.() -> Unit) {
        val repo = MavenRepository()
        repo.repositorySetup()
        repositories.add(repo)
    }
}