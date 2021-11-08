package io.github.jefflegendpower.cbpcore.modes.arena;

import io.github.jefflegendpower.cbpcore.config.Config;
import io.github.jefflegendpower.cbpcore.events.PlayerLeaveArenaEvent;
import io.github.jefflegendpower.cbpcore.events.PracticeStartEvent;
import io.github.jefflegendpower.cbpcore.items.GUICompass;
import io.github.jefflegendpower.cbpcore.utility.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static io.github.jefflegendpower.cbpcore.items.LeaveSword.getLeaveSword;

public class LeaveArena implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerLeaveArenaEvent leaveArenaEvent = new PlayerLeaveArenaEvent(player);
        Bukkit.getPluginManager().callEvent(leaveArenaEvent);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent event) {
        Player player = event.getPlayer();
        player.teleport(new Config().getSpawn());
        InventoryUtils.setItem(player, 0, GUICompass.getGUICompass());
        ArenaManager arenaManager = ArenaManager.getInstance();
        Arena arena = arenaManager.getArenaFromPlayer(player);
        if (arena == null) return;
        arenaManager.deleteArena(arena);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void giveLeaveSword(PracticeStartEvent event) {
        InventoryUtils.setItem(event.getPlayer(), 8, getLeaveSword());
    }
}
