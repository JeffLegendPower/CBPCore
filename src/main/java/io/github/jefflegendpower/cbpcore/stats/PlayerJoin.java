package io.github.jefflegendpower.cbpcore.stats;

import io.github.jefflegendpower.cbpcore.CBPCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    CBPCore plugin;

    public PlayerJoin(CBPCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        StatsSQLControl sqlControl = new StatsSQLControl();
        sqlControl.addPlayer(event.getPlayer());
    }
}

