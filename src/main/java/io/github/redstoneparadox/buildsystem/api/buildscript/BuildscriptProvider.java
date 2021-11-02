package io.github.redstoneparadox.buildsystem.api.buildscript;

import io.github.redstoneparadox.buildsystem.sources.SourceSet;

import java.io.File;
import java.util.Collection;

public interface BuildscriptProvider {
	/**
	 * Gets the file extension supported by
	 * this buildscript provider.
	 *
	 * @return The file extension.
	 */
	String getExtension();

	/**
	 * Runs the buildscript for the project.
	 *
	 * @param buildscript The buildscript file.
	 */
	void run(File buildscript);

	/**
	 * Gets all the source sets
	 *
	 * @return A collection of all source sets
	 * specified by the build script.
	 */
	Collection<SourceSet> getSourceSets();
}
