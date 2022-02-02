package io.github.redstoneparadox.buildscript

class RepositoriesContext {
    val repositories: MutableList<Repository> = mutableListOf();

    @Suppress("unused")
    fun repository(repositorySetup: Repository.() -> Unit) {
        val repo = Repository()
        repo.repositorySetup()
        repositories.add(repo)
    }
}