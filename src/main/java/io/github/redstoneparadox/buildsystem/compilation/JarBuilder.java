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
	private final String sourceSet;

	public JarBuilder(String sourceSet) {
		this.sourceSet = sourceSet;
	}

	public void build(List<File> sources) {
		var classes = sources.stream().map(File::getPath).map(s -> s.substring(0, s.length() - 4) + "class").toList();
		var manifest = new Manifest();

		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

		try {
			clean();

			JarOutputStream target = new JarOutputStream(new FileOutputStream("run/output.jar"), manifest);
			for (String s : classes) {
				addToJar(new File(s), target);
				move(s);
			}
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
		var root = userDir + "/src/" + sourceSet + "/java/";

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

	private void move(String sourcePath) throws IOException {
		String destinationPath = sourcePath.replace("\\src\\","\\run\\classes\\");
		File source = new File(sourcePath);
		File destination = new File(destinationPath);

		// var flag = destination.mkdirs();

		source.renameTo(destination);
	}
}
