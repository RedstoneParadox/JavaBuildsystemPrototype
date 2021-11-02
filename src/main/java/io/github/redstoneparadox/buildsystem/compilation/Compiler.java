package io.github.redstoneparadox.buildsystem.compilation;

import io.github.redstoneparadox.buildsystem.util.JvmUtil;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Compiler {
	public void compile(List<File> sources) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8);

		JavaCompiler.CompilationTask task = compiler.getTask(
				null,
				fileManager,
				null,
				Arrays.asList(JvmUtil.compileArgs(16, 16)),
				null,
				fileManager.getJavaFileObjects(sources.toArray(new File[]{}))
		);

		task.call();
	}
}
