package io.github.jefflegendpower.cbpcore.spawn;

import io.github.jefflegendpower.cbpcore.events.PlayerFallInVoidEvent;
import io.github.jefflegendpower.cbpcore.modes.Mode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerVoid implements Listener {

    @EventHandler
    public void onPlayerFallInVoid(PlayerMoveEvent event) {
        if (event.getTo().getY() <= 60) {
            Player player = event.getPlayer();
            PlayerFallInVoidEvent voidEvent = new PlayerFallInVoidEvent(player);
            Bukkit.getPluginManager().callEvent(voidEvent);
        }
    }

}
