package io.github.redstoneparadox.buildscript

import io.github.redstoneparadox.buildsystem.sources.SourceSet
import kotlinx.coroutines.runBlocking
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.*
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver
import kotlin.script.experimental.jvm.JvmDependency
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    fileExtension = "creamer.kts",
    compilationConfiguration = BuildscriptDefConfiguration::class
)
abstract class BuildscriptDef {
    val java = JavaConfiguration()
    val sourceSets: MutableList<SourceSet> = mutableListOf()
    val dependenciesContext: DependenciesContext = DependenciesContext()
    val repositoriesContext: RepositoriesContext = RepositoriesContext()

    @Suppress("unused")
    fun repositories(consumer: RepositoriesContext.() -> Unit) {
        consumer.invoke(repositoriesContext)
    }

    @Suppress("unused")
    fun dependencies(consumer: DependenciesContext.() -> Unit) {
        consumer.invoke(dependenciesContext)
    }

    @Suppress("unused")
    fun sourceSet(consumer: SourceSet.() -> Unit) {
        val sourceSet = SourceSet()
        consumer.invoke(sourceSet)
        sourceSets.add(sourceSet)
    }
}

object BuildscriptDefConfiguration: ScriptCompilationConfiguration({
    defaultImports(DependsOn::class, kotlin.script.experimental.dependencies.Repository::class)
    jvm {
        dependenciesFromCurrentContext(wholeClasspath = true)
    }
    refineConfiguration {
        onAnnotations(DependsOn::class, kotlin.script.experimental.dependencies.Repository::class, handler = ::handleBuildscript)
    }

})

private val resolver = CompoundDependenciesResolver(FileSystemDependenciesResolver(), MavenDependenciesResolver())

fun handleBuildscript(context: ScriptConfigurationRefinementContext): ResultWithDiagnostics<ScriptCompilationConfiguration> {
    val annotations = context.collectedData?.get(ScriptCollectedData.collectedAnnotations)?.takeIf { it.isNotEmpty() }
        ?: return context.compilationConfiguration.asSuccess()
    return runBlocking {
        resolver.resolveFromScriptSourceAnnotations(annotations)
    }.onSuccess {
        context.compilationConfiguration.with {
            dependencies.append(JvmDependency(it))
        }.asSuccess()
    }
}