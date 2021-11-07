package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.speedclutch;

import io.github.jefflegendpower.cbpcore.CBPCore;
import io.github.jefflegendpower.cbpcore.events.PlayerFallInVoidEvent;
import io.github.jefflegendpower.cbpcore.events.PlayerLeaveArenaEvent;
import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.modes.arena.Arena;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.ActionBarTimer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

public class SpeedClutch implements Listener {

    private static final SpeedClutch instance = new SpeedClutch();
    private Map<Player, Long> speedClutchPlayers = new HashMap<>();

    private final ArenaManager arenaManager = ArenaManager.getInstance();

    private SpeedClutch() {}

    public static SpeedClutch getInstance() {
        return instance;
    }

    @EventHandler
    public void playerStartRun(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (arenaManager.getArenaFromPlayer(player) == null) return;
        if (arenaManager.getArenaFromPlayer(player).getType() != Mode.SPEED_CLUTCH) return;

        if (speedClutchPlayers.get(player) != null) return;

        if (event.getFrom().getBlockZ() != event.getTo().getBlockZ() || event.getFrom().getBlockX() != event.getTo().getBlockX()) {
            speedClutchPlayers.put(player, System.currentTimeMillis());

            new ActionBarTimer().actionBarTimer(player, speedClutchPlayers).runTaskTimer(
                    CBPCore.getPlugin(CBPCore.class), 0, 0);
        }
    }

    @EventHandler
    public void playerEndRun(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Arena arena = arenaManager.getArenaFromPlayer(player);

        if (arena == null) return;
        if (arena.getType() != Mode.SPEED_CLUTCH) return;
        if (speedClutchPlayers.get(player) == null) return;
        if (player.getLocation().clone().subtract(0, 1, 0).getBlock().getType() != Material.GOLD_BLOCK) return;

        long time = System.currentTimeMillis() - speedClutchPlayers.get(player);
        speedClutchPlayers.remove(player);
        player.sendMessage(time/1000 + " seconds!");

        for (Block block : arena.getPlayerPlacedBlocks())
            block.setType(Material.AIR);

        player.setNoDamageTicks(40);
//        player.teleport(arenaManager.getArenaFromPlayer(player).getLocation());
    }

    @EventHandler
    public void playerDeath(PlayerFallInVoidEvent event) {
        Player player = event.getPlayer();
        Arena arena = arenaManager.getArenaFromPlayer(player);

        if (arena == null) return;
        if (arena.getType() != Mode.SPEED_CLUTCH) return;

        speedClutchPlayers.remove(player);
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent event) {
        speedClutchPlayers.remove(event.getPlayer());
    }
}
