package io.github.jefflegendpower.cbpcore.modes.arena;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlocksChangedInArena implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ArenaManager arenaManager = ArenaManager.getInstance();
        Arena arena = arenaManager.getArenaFromPlayer(event.getPlayer());
        if (arena == null) return;

        arena.addPlayerPlacedBlock(event.getBlock());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        ArenaManager arenaManager = ArenaManager.getInstance();
        Arena arena = arenaManager.getArenaFromPlayer(event.getPlayer());
        if (arena == null) return;


        arena.addPlayerBrokenBlock(event.getBlock(), event.getBlock().getType());
    }
}
