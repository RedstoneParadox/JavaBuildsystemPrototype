package io.github.redstoneparadox.buildsystem.api.buildscript;

import io.github.redstoneparadox.buildsystem.impl.buildscript.BuildscriptProviderRegistryImpl;

public interface BuildscriptProviderRegistry {
	BuildscriptProviderRegistry INSTANCE = new BuildscriptProviderRegistryImpl();

	static void registerProvider(BuildscriptProvider provider) {
		INSTANCE.register(provider);
	}

	void register(BuildscriptProvider provider);
}
