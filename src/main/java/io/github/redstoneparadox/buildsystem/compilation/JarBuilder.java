package io.github.redstoneparadox.buildsystem.compilation;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarBuilder {
	public JarBuilder() {

	}

	public void build(List<File> sources, String sourceSetName) {
		var classes = sources
				.stream()
				.map(File::getPath)
				.map(s -> s.substring(0, s.length() - 4) + "class")
				.map(s -> s.replace("\\src\\" + sourceSetName + "\\java\\","\\run\\classes\\"))
				.map(File::new)
				.toList();
		var manifest = new Manifest();

		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

		try {
			JarOutputStream target = new JarOutputStream(new FileOutputStream("run/output.jar"), manifest);
			for (File file : classes) {
				addToJar(file, target);
			}
			target.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clean() throws IOException {
		File runDir = new File("run");
		FileUtils.deleteDirectory(runDir);
		runDir.mkdir();
	}

	private void addToJar(File source, JarOutputStream target) throws IOException {
		BufferedInputStream in = null;
		var userDir = System.getProperty("user.dir");
		var root = userDir + "/run/classes/";

		try
		{
			if (source.isDirectory())
			{
				throw new Exception("Whoops!");
			}

			JarEntry entry = new JarEntry(source.getPath().substring(root.length()));
			entry.setTime(source.lastModified());
			target.putNextEntry(entry);
			in = new BufferedInputStream(new FileInputStream(source));

			byte[] buffer = new byte[1024];
			while (true)
			{
				int count = in.read(buffer);
				if (count == -1)
					break;
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		} catch (Exception e) {
			e.printStackTrace();
		} finally
		{
			if (in != null)
				in.close();
		}
	}
}
