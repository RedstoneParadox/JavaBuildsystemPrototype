package io.github.redstoneparadox.buildsystem;

import io.github.redstoneparadox.buildsystem.compilation.Compiler;
import io.github.redstoneparadox.buildsystem.compilation.JarBuilder;
import io.github.redstoneparadox.buildsystem.sources.SourceSet;

public class Main {
	public static void main(String[] args) {
		var sourceSet = new SourceSet("test");
		var compiler = new Compiler();
		var builder = new JarBuilder(sourceSet.name());

		var sources = sourceSet.getSources();
		compiler.compile(sources);
		builder.build(sources);
	}
}
