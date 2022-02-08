package io.github.redstoneparadox.buildsystem.impl.repository;

import io.github.redstoneparadox.buildsystem.buildscript.Repository;

public class MavenRepository implements Repository {
	private String name = "";
	private String url = "";

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getUrl() {
		return url;
	}
}
