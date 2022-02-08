package io.github.redstoneparadox.buildsystem;

import io.github.redstoneparadox.buildscript.Buildscript;
import io.github.redstoneparadox.buildsystem.buildscript.Repository;
import io.github.redstoneparadox.buildsystem.compilation.Compiler;
import io.github.redstoneparadox.buildsystem.compilation.JarBuilder;
import io.github.redstoneparadox.buildsystem.sources.SourceSet;

public class Main {
	public static void main(String[] args) {
		var buildscript = new Buildscript();

		buildscript.run();

		var repositories = buildscript.getRepositories();
		var dependencies = buildscript.getDependencies();

		for (Repository repo: repositories) {
			var url = repo.getUrl();
		}

		var javaVersion = buildscript.getJavaVersion();
		var sourceSets = buildscript.getSourceSets();
		var compiler = new Compiler();

		compiler.prepare();

		for (SourceSet sourceSet: sourceSets) {
			var builder = new JarBuilder();
			var sources = sourceSet.getSources();

			compiler.compile(javaVersion, sources);
			builder.build(sources, sourceSet.name);
		}
	}
}
