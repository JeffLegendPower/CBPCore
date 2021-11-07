package io.github.jefflegendpower.cbpcore.spawn;

import io.github.jefflegendpower.cbpcore.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerSpawn implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        player.setNoDamageTicks(40);
        player.teleport(Bukkit.getWorld(new Config().getConfig().getString("Spawn.world")).getHighestBlockAt(new Config().getSpawn()).getLocation());
    }

}
