package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.kbclutch;

import io.github.jefflegendpower.cbpcore.CBPCore;
import io.github.jefflegendpower.cbpcore.events.PlayerFallInVoidEvent;
import io.github.jefflegendpower.cbpcore.events.PlayerLeaveArenaEvent;
import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.modes.arena.Arena;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.ActionBarTimer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class KnockbackClutch implements Listener {

    private static final KnockbackClutch instance = new KnockbackClutch();
    private Map<Player, Long> knockbackClutchPlayers = new HashMap<>();

    private final ArenaManager arenaManager = ArenaManager.getInstance();

    private KnockbackClutch() {}

    public static KnockbackClutch getInstance() {
        return instance;
    }

    @EventHandler
    public void playerStartRun(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (arenaManager.getArenaFromPlayer(player) == null) return;
        if (arenaManager.getArenaFromPlayer(player).getType() != Mode.KNOCKBACK_CLUTCH) return;

        if (knockbackClutchPlayers.get(player) != null) return;

        if (event.getFrom().getBlockZ() != event.getTo().getBlockZ() || event.getFrom().getBlockX() != event.getTo().getBlockX()) {
            knockbackClutchPlayers.put(player, System.currentTimeMillis());

            new ActionBarTimer().actionBarTimer(player, knockbackClutchPlayers).runTaskTimer(
                    CBPCore.getPlugin(CBPCore.class), 0, 0);
            randomKnockback(player, CBPCore.getPlugin(CBPCore.class));
        }
    }

    @EventHandler
    public void playerEndRun(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Arena arena = arenaManager.getArenaFromPlayer(player);

        if (arena == null) return;
        if (arena.getType() != Mode.KNOCKBACK_CLUTCH) return;
        if (knockbackClutchPlayers.get(player) == null) return;
        if (player.getLocation().clone().subtract(0, 1, 0).getBlock().getType() != Material.GOLD_BLOCK) return;

        long time = System.currentTimeMillis() - knockbackClutchPlayers.get(player);
        knockbackClutchPlayers.remove(player);
        player.sendMessage(time/1000 + " seconds!");

        for (Block block : arena.getPlayerPlacedBlocks())
            block.setType(Material.AIR);

        player.setNoDamageTicks(40);
        player.teleport(arenaManager.getArenaFromPlayer(player).getSpawnLocation());
    }

    @EventHandler
    public void playerDeath(PlayerFallInVoidEvent event) {
        Player player = event.getPlayer();
        Arena arena = arenaManager.getArenaFromPlayer(player);

        if (arena == null) return;
        if (arena.getType() != Mode.KNOCKBACK_CLUTCH) return;

        knockbackClutchPlayers.remove(player);
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent event) {
        knockbackClutchPlayers.remove(event.getPlayer());
    }

    private void randomKnockback(Player player, CBPCore plugin) {
        BukkitRunnable runnable = new BukkitRunnable() {

            final Random random = new Random();
            @Override
            public void run() {

                if (knockbackClutchPlayers.get(player) == null) cancel();

                try {
                    float willKnockback = random.nextFloat();
                    if (random.nextBoolean()) willKnockback = willKnockback * -1;

                    if (willKnockback > 0.8 &&
                            player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {

                        float offsetX = random.nextFloat() * 2 - 1;
                        float offsetZ = random.nextFloat() * 2 - 1;

                        Location attacker = player.getLocation().clone().add(offsetX, 0, offsetZ);
                        attacker.setDirection(player.getLocation().subtract(attacker).toVector().normalize());

                        player.damage(1);
                        player.setHealth(player.getMaxHealth());
                        player.setVelocity(attacker.getDirection().multiply(random.nextFloat()).setY(0.3333));
                    }
                } catch (NullPointerException exception) {
                    cancel();
                }
            }
        };

        runnable.runTaskTimer(plugin, 20, 10);
    }
}

