package net.koutachan.discordchatsyncbotfabric.util;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public class ConfigManager {
    private final Map<String, String> config;

    public ConfigManager(Path path) {
        this.config = FileUtils.getFileLine(path);
    }

    public ConfigManager(String sources, Path destination) {
        FileUtils.copyFromResources(sources, destination);
        this.config = FileUtils.getFileLine(destination);
    }

    public ConfigManager(String sources, File destination) {
        FileUtils.copyFromResources(sources, destination.toPath());
        this.config = FileUtils.getFileLine(destination.toPath());
    }

    public String getString(String key) {
        return config.get(key);
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(config.get(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(config.get(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(config.get(key));
    }

    public long getLong(String key) {
        return Long.parseLong(config.get(key));
    }

    public Object get(String key) {
        return config.get(key);
    }
}
