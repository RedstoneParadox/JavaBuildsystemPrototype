package io.github.redstoneparadox.buildsystem.buildscript;

public interface Repository {
	public void setName(String name);
	String getName();
	public void setUrl(String url);
	String getUrl();
}
