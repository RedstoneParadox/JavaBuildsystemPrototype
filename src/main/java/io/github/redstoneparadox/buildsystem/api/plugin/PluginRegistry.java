package io.github.redstoneparadox.buildsystem.api.plugin;

public interface PluginRegistry {
	PluginRegistry INSTANCE = null;

	static void registerPlugin(Plugin plugin) {
		INSTANCE.register(plugin);
	}

	void register(Plugin plugin);
}
