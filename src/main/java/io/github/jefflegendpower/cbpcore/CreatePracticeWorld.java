package io.github.jefflegendpower.cbpcore;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.*;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.UUID;

public class CreatePracticeWorld {

    private final SlimePlugin plugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");

    private CreatePracticeWorld() {}

    public static CreatePracticeWorld getInstance() {
        return new CreatePracticeWorld();
    }

    public SlimeWorld clone(SlimeWorld world) {
        final SlimeWorld[] cloneWorld = new SlimeWorld[1];
        String worldName = world.getName();
//        Bukkit.getScheduler().runTaskAsynchronously(CBPCore.getPlugin(CBPCore.class), () -> {
        try {
            SlimePropertyMap propertyMap = new SlimePropertyMap();
            propertyMap.setString(SlimeProperties.DIFFICULTY, "easy");
            propertyMap.setBoolean(SlimeProperties.ALLOW_MONSTERS, false);
            propertyMap.setBoolean(SlimeProperties.ALLOW_ANIMALS, false);
            propertyMap.setBoolean(SlimeProperties.PVP, true);

            SlimeLoader loader = plugin.getLoader("file");

            SlimeWorld slimeWorld = plugin.loadWorld(loader, worldName, true, propertyMap);
            cloneWorld[0] = slimeWorld.clone(worldName + UUID.randomUUID());

//                Bukkit.getScheduler().runTask(CBPCore.getPlugin(CBPCore.class), () -> {
            try {
                plugin.generateWorld(cloneWorld[0]);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
//                });

        } catch (CorruptedWorldException | NewerFormatException | UnknownWorldException | IllegalArgumentException | IOException | WorldInUseException e) {
                e.printStackTrace();
        }
//        });

        return cloneWorld[0];
    }

    public SlimeWorld getWorldFromString(String world) {
        try {
            // Converting string literal to SlimeWorld
            return plugin.loadWorld(plugin.getLoader("file"), world, true, new SlimePropertyMap());
        } catch (CorruptedWorldException | NewerFormatException | WorldInUseException | UnknownWorldException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
