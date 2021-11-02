package io.github.redstoneparadox.buildsystem.api.buildscript;

import io.github.redstoneparadox.buildsystem.sources.SourceSet;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

public interface BuildscriptProvider {
	/**
	 * Runs the buildscript for the project.
	 *
	 * @param projectDirectory project directory
	 */
	void run(File projectDirectory);

	/**
	 * Gets all the source sets
	 *
	 * @return A collection of all source sets
	 * specified by the build script.
	 */
	Collection<SourceSet> getSourceSets();
}
