package io.github.redstoneparadox.buildsystem.sources;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;

public class SourceSet {
	public String name = "";

	public List<File> getSources() {
		String userDir = System.getProperty("user.dir");
		Path root = Path.of(userDir + "/src/" + name);
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**.java");

		try {
			return Files.walk(root)
					.filter(Files::isRegularFile)
					.filter(matcher::matches)
					.map(Path::toFile)
					.toList();
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
