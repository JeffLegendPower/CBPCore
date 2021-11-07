package io.github.jefflegendpower.cbpcore.modes;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class StopItemDrop implements Listener {

    @EventHandler
    public void onPlayerItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
