package io.github.redstoneparadox.buildscript

import io.github.redstoneparadox.buildsystem.sources.SourceSet
import java.io.File
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

class Buildscript {
    var def: BuildscriptDef? = null

    fun run() {
        val scriptFile = File("build.creamer.kts")
        def = evaluate(scriptFile)
    }

    fun getJavaVersion(): Int {
        return def?.java?.version ?: 11
    }

    fun getSourceSets(): Collection<SourceSet> {
        return def?.sourceSets ?: emptyList();
    }

    private fun evaluate(scriptFile: File): BuildscriptDef {
        val compilationConfiguration: ScriptCompilationConfiguration = createJvmCompilationConfigurationFromTemplate<BuildscriptDef>()
        val res = BasicJvmScriptingHost().eval(scriptFile.toScriptSource(), compilationConfiguration, null)

        res.reports.forEach {
            if (it.severity > ScriptDiagnostic.Severity.DEBUG) {
                println(" : ${it.message}" + if (it.exception == null) "" else ": ${it.exception}")
            }
        }

        return res.valueOrThrow().returnValue.scriptInstance as BuildscriptDef
    }
}