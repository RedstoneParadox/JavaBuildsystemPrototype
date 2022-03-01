package io.github.redstoneparadox.buildsystem;

import io.github.redstoneparadox.buildscript.Buildscript;
import io.github.redstoneparadox.buildsystem.buildscript.Repository;
import io.github.redstoneparadox.buildsystem.compilation.Compiler;
import io.github.redstoneparadox.buildsystem.compilation.JarBuilder;
import io.github.redstoneparadox.buildsystem.sources.SourceSet;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		var buildscript = new Buildscript();
		buildscript.run();

		var in = new Scanner(System.in);

		while (true) {
			String command = in.next();

			if (Objects.equals(command, "build")) {
				build(buildscript, args[0]);
			} else if (Objects.equals(command, "clean")) {
				File runDir = new File(args[0] + "/run");

				try {
					FileUtils.deleteDirectory(runDir);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (Objects.equals(command, "stop")) {
				break;
			} else {
				System.out.println("Command not recognized.");
			}
		}
	}

	private static void build(Buildscript buildscript, String directory) {
		var repositories = buildscript.getRepositories();
		var dependencies = buildscript.getDependencies();

		for (Repository repo: repositories) {
			var url = repo.getUrl();
		}

		var javaVersion = buildscript.getJavaVersion();
		var sourceSets = buildscript.getSourceSets();
		var compiler = new Compiler();

		compiler.prepare(directory);

		for (SourceSet sourceSet: sourceSets) {
			var builder = new JarBuilder();
			var sources = sourceSet.getSources();

			compiler.compile(javaVersion, sources, directory);
			builder.build(sources, sourceSet.name);
		}
	}
}
