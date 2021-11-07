package io.github.jefflegendpower.cbpcore.modes;

import io.github.jefflegendpower.cbpcore.modes.arena.Arena;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import io.github.jefflegendpower.cbpcore.modes.arena.LeaveArena;
import io.github.jefflegendpower.cbpcore.utility.InventoryUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public abstract class PlayerHitGoldBlock {

    protected void playerHitGoldBlockLogic(PlayerMoveEvent event, Mode mode, ArenaItem arenaItem) {
        ArenaManager arenaManager = ArenaManager.getInstance();
        Player player = event.getPlayer();

        Arena arena = arenaManager.getArenaFromPlayer(player);
        if (arena == null || arena.getType() != mode) return;

        Location blockLocation = player.getLocation().clone().subtract(0, 1, 0);
        for(int x = -1; x<=1; x++) {
            if(blockLocation.clone().add(x, 0, 0).getBlock().getType() == Material.GOLD_BLOCK) break;
            if (x == 1) return;
        }

        Location spawnLoc = arena.getSpawnLocation();
        Location location = spawnLoc.clone().add(-0.5, 0, 2.5);

        int distance = Math.abs(
                (location.getBlockX() - blockLocation.getBlockX()) +
                        (location.getBlockZ() - blockLocation.getBlockZ()));

        player.sendMessage(ChatColor.GOLD + "You have jumped " + distance + " blocks!");
        player.setHealth(player.getMaxHealth());

        player.setNoDamageTicks(40);
        player.teleport(spawnLoc);

        InventoryUtils.giveItems(player, arenaItem.item());
        InventoryUtils.setItem(player, 8, LeaveArena.leaveSword());
    }
}
