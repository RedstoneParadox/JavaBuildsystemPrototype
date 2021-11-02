package io.github.redstoneparadox.buildsystem.impl.buildscript;

import io.github.redstoneparadox.buildsystem.api.buildscript.BuildscriptProvider;
import io.github.redstoneparadox.buildsystem.api.buildscript.BuildscriptProviderRegistry;

import java.util.HashMap;
import java.util.Map;

public class BuildscriptProviderRegistryImpl implements BuildscriptProviderRegistry {
	private final Map<String, BuildscriptProvider> providerMap = new HashMap<>();

	@Override
	public void register(BuildscriptProvider provider) {
		providerMap.put(provider.getExtension(), provider);
	}

	public BuildscriptProvider get(String extension) {
		return providerMap.get(extension);
	}
}
