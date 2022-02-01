package io.github.redstoneparadox.buildsystem.defaultplugin.buildscript;

import io.github.redstoneparadox.buildsystem.api.buildscript.BuildscriptProvider;
import io.github.redstoneparadox.buildsystem.sources.SourceSet;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JSBuildscriptProvider implements BuildscriptProvider {
	private final JavaOptions javaOptions = new JavaOptions();
	private final HashMap<String, Object> sourceSets = new HashMap<>();

	@Override
	public String getExtension() {
		return "js";
	}

	@Override
	public void run(File buildscriptFile) {
		Context cx = Context.enter();
		Scriptable scope = cx.initSafeStandardObjects();
		String buildscript = loadScript(buildscriptFile);

		if (buildscript.isBlank() || buildscript.isEmpty()) return;

		try {
			Object wrappedJavaOptions = Context.javaToJS(javaOptions, scope);
			Object wrappedSourceSets = Context.javaToJS(sourceSets, scope);

			ScriptableObject.putProperty(scope, "java", wrappedJavaOptions);
			ScriptableObject.putProperty(scope, "sources", wrappedSourceSets);

			cx.evaluateString(scope, buildscript, "buildscript.js", 1, null);

			for (Map.Entry<String, Object> entry: sourceSets.entrySet()) {
				Object fObject = entry.getValue();

				if (fObject instanceof Function f) {
					SourceSetOptions ops = new SourceSetOptions();
					Object[] args = {
							Context.javaToJS(ops, scope)
					};

					f.call(cx, scope, scope, args);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<SourceSet> getSourceSets() {
		return null;
	}

	public static String loadScript(File file) {
		if (file.canRead()) {
			try {
				return Files.readString(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
		}

		return "";
	}
}
