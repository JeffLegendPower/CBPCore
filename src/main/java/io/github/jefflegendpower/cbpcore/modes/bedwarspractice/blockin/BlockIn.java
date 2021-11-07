package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.blockin;

import com.sk89q.worldedit.WorldEdit;
import io.github.jefflegendpower.cbpcore.CBPCore;
import io.github.jefflegendpower.cbpcore.config.Config;
import io.github.jefflegendpower.cbpcore.events.PlayerFallInVoidEvent;
import io.github.jefflegendpower.cbpcore.events.PlayerLeaveArenaEvent;
import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.modes.arena.Arena;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.ActionBarTimer;
import io.github.jefflegendpower.cbpcore.utility.WorldEditUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class BlockIn implements Listener {

    private static BlockIn instance = new BlockIn();
    private ArenaManager arenaManager = ArenaManager.getInstance();

    private Map<Player, Long> blockInPlayers = new HashMap<>();

    private BlockIn() {}

    public static BlockIn getInstance() {
        return instance;
    }

    @EventHandler
    public void playerStartRun(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (arenaManager.getArenaFromPlayer(player) == null) return;
        if (arenaManager.getArenaFromPlayer(player).getType() != Mode.BLOCK_IN) return;

        if (blockInPlayers.get(player) != null) return;

        if (event.getFrom().getBlockZ() != event.getTo().getBlockZ() || event.getFrom().getBlockX() != event.getTo().getBlockX()) {
            blockInPlayers.put(player, System.currentTimeMillis());

            new ActionBarTimer().actionBarTimer(player, blockInPlayers).runTaskTimer(
                    CBPCore.getPlugin(CBPCore.class), 0, 0);
        }
    }

    @EventHandler
    public void playerEndRun(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Arena arena = arenaManager.getArenaFromPlayer(player);

        if (arena == null) return;
        if (arena.getType() != Mode.BLOCK_IN) return;
        if (blockInPlayers.get(player) == null) return;
        if (event.getBlock().getType() != Material.BED_BLOCK) return;

        long time = System.currentTimeMillis() - blockInPlayers.get(player);
        blockInPlayers.remove(player);
        player.sendMessage(time/1000 + " seconds!");

        for (Block block : arena.getPlayerPlacedBlocks())
            block.setType(Material.AIR);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arena.getType() != Mode.BLOCK_IN) cancel();
//                worldEditUtils.loadSchematic(new Config().getTemplateWorld("block_in"), arena.getLocation());

                for (Entity entity : player.getNearbyEntities(25, 25, 25))
                    if (!(entity instanceof Player)) entity.remove();
            }
        }.runTaskLater(CBPCore.getPlugin(CBPCore.class), 1);

//        player.teleport(arenaManager.getArenaFromPlayer(player).getSpawnLocation());
    }

    @EventHandler
    public void playerDeath(PlayerFallInVoidEvent event) {
        Player player = event.getPlayer();
        Arena arena = arenaManager.getArenaFromPlayer(player);

        if (arena == null) return;
        if (arena.getType() != Mode.BLOCK_IN) return;

        blockInPlayers.remove(player);
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent event) {
        blockInPlayers.remove(event.getPlayer());
    }
}
