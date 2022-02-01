package io.github.redstoneparadox.buildsystem.impl.plugin;

import io.github.redstoneparadox.buildsystem.api.plugin.Plugin;
import io.github.redstoneparadox.buildsystem.api.plugin.PluginRegistry;

import java.util.ArrayList;
import java.util.Collection;

public class PluginRegistryImpl implements PluginRegistry {
	private final Collection<Plugin> plugins = new ArrayList<>();

	@Override
	public void register(Plugin plugin) {
		plugins.add(plugin);
	}

	public void setupAll() {
		plugins.forEach(Plugin::setup);
	}
}
