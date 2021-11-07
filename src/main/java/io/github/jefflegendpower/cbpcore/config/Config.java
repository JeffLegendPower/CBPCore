package io.github.jefflegendpower.cbpcore.config;

import io.github.jefflegendpower.cbpcore.CBPCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private final CBPCore plugin;

    private FileConfiguration config;

    public Config() {
        this.plugin = CBPCore.getPlugin(CBPCore.class);
        this.config = plugin.getConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        plugin.saveDefaultConfig();
    }

    public Location getSpawn() {
        World world = Bukkit.getWorld(config.getString("Spawn.world"));

        double x, y, z;
        x = config.getDouble("Spawn.x");
        y = config.getDouble("Spawn.y");
        z = config.getDouble("Spawn.z");

        return new Location(world, x, y, z);
    }

    public String getTemplateWorld(String alias) {
        return this.config.getString("Template." + alias + ".name");
    }

    public Location getSpawnLocation(String alias, World world) {
        String[] spawnLocationSplit = this.config.getString("Template." + alias + ".spawn_loc").split(", ");
        double spawnX, spawnY, spawnZ;
        float yaw, pitch;

        Location spawnLocation;

        try {
            spawnX = Double.parseDouble(spawnLocationSplit[0]);
            spawnY = Double.parseDouble(spawnLocationSplit[1]);
            spawnZ = Double.parseDouble(spawnLocationSplit[2]);

            spawnLocation = new Location(world, spawnX, spawnY, spawnZ);
            if (spawnLocationSplit.length >= 5) {
                yaw = Float.parseFloat(spawnLocationSplit[3]);
                pitch = Float.parseFloat(spawnLocationSplit[4]);
                spawnLocation.setYaw(yaw);
                spawnLocation.setPitch(pitch);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("invalid spawn location '" + alias + "'");
        }

        return spawnLocation;
    }
}
