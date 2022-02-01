package io.github.redstoneparadox.buildscript.kotlin

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
abstract class BuildscriptDef

object BuildscriptDefConfiguration: ScriptCompilationConfiguration({
    defaultImports(DependsOn::class, Repository::class)
    jvm {
        dependenciesFromCurrentContext(wholeClasspath = true)
    }
    refineConfiguration {
        onAnnotations(DependsOn::class, Repository::class, handler = ::handleBuildscript)
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