package io.github.jefflegendpower.cbpcore.spawn;

import com.sk89q.worldedit.WorldEdit;
import io.github.jefflegendpower.cbpcore.config.Config;
import io.github.jefflegendpower.cbpcore.events.PlayerFallInVoidEvent;
import io.github.jefflegendpower.cbpcore.modes.arena.LeaveArena;
import io.github.jefflegendpower.cbpcore.modes.arena.Arena;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import io.github.jefflegendpower.cbpcore.utility.InventoryUtils;
import io.github.jefflegendpower.cbpcore.utility.WorldEditUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class RespawnPlayer implements Listener {

    private List<Player> playersInVoid = new ArrayList<>();

    @EventHandler
    public void onPlayerVoid(PlayerFallInVoidEvent event) {

        ArenaManager arenaManager = ArenaManager.getInstance();

        Player player = event.getPlayer();
        Arena arena = arenaManager.getArenaFromPlayer(player);

        playersInVoid.add(player);
        if (arena == null) player.teleport(Bukkit.getWorld(new Config().getConfig().getString("Spawn.world")).
                getHighestBlockAt(new Config().getSpawn()).getLocation());
        else {
            player.teleport(arena.getSpawnLocation());
            InventoryUtils.clearInventory(player);
            InventoryUtils.giveItems(player, arena.getRelatedItems());
            InventoryUtils.setItem(player, 8, LeaveArena.leaveSword());

            for (Block block : arena.getPlayerPlacedBlocks())
                block.setType(Material.AIR);

            WorldEditUtils worldEditUtils = new WorldEditUtils(WorldEdit.getInstance().getEditSessionFactory());
//            Location location = arena.getLocation();

//            switch (arena.getType()) {
//                case TNT_JUMP: worldEditUtils.loadSchematic(new Config().getTemplateWorld("tnt_jump"), location); break;
//                case FIREBALL_JUMP: worldEditUtils.loadSchematic(new Config().getTemplateWorld("fireball_jump"), location); break;
//                case SPEED_CLUTCH: worldEditUtils.loadSchematic(new Config().getTemplateWorld("speed_clutch"), location); break;
//                case KNOCKBACK_CLUTCH: worldEditUtils.loadSchematic(new Config().getTemplateWorld("knockback_clutch"), location); break;
//                case BLOCK_IN: worldEditUtils.loadSchematic(new Config().getTemplateWorld("block_in"), location); break;
//            }
        }
        resetEntities(player);
    }

    @EventHandler
    public void preventFallDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (playersInVoid.contains(player)) {
                event.setCancelled(true);
                player.setHealth(player.getMaxHealth());
                playersInVoid.remove(player);
            }
        }
    }

    private void resetEntities(Player player) {
        for (Entity entity : player.getNearbyEntities(25, 25, 25))
            if (!(entity instanceof Player)) entity.remove();
    }
}
