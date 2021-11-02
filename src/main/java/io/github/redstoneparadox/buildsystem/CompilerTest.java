package io.github.redstoneparadox.buildsystem;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class CompilerTest {
	public static void main(String[] args) {
		var dir = System.getProperty("user.dir");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		var result = compiler.run(null, null, null,
				dir + "/src/main/java/io/github/redstoneparadox/buildsystem/CompilerTest.java");

		System.out.println("Compile result code = " + result);
	}
}
