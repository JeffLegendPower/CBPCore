package io.github.jefflegendpower.cbpcore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {

    private final JavaPlugin plugin;

    private FileConfiguration config;

    protected Config(JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        plugin.saveDefaultConfig();
    }


}
