package io.github.redstoneparadox.buildsystem.compilation;

import io.github.redstoneparadox.buildsystem.util.JvmUtil;
import org.apache.commons.io.FileUtils;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Compiler {
	public void prepare(String projectDirectory) {
		File runDir = new File(projectDirectory + "/run");

		try {
			FileUtils.deleteDirectory(runDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void compile(int javaVersion, List<File> sources, String projectDirectory) {
		File classesDir = new File(projectDirectory + "/run/classes/");
		classesDir.mkdirs();

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8);
		try {
			fileManager.setLocation(StandardLocation.CLASS_OUTPUT, List.of(classesDir));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JavaCompiler.CompilationTask task = compiler.getTask(
				null,
				fileManager,
				null,
				Arrays.asList(JvmUtil.compileArgs(javaVersion, javaVersion)),
				null,
				fileManager.getJavaFileObjects(sources.toArray(new File[]{}))
		);

		task.call();
	}
}
