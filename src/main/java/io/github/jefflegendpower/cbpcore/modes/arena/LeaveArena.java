package io.github.jefflegendpower.cbpcore.modes.arena;

import io.github.jefflegendpower.cbpcore.config.Config;
import io.github.jefflegendpower.cbpcore.events.PlayerLeaveArenaEvent;
import io.github.jefflegendpower.cbpcore.events.PracticeStartEvent;
import io.github.jefflegendpower.cbpcore.modes.arena.Arena;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import io.github.jefflegendpower.cbpcore.utility.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LeaveArena implements Listener {

    @EventHandler
    public void onPlayerBlockSword(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && player.getItemInHand().isSimilar(leaveSword())) {
            PlayerLeaveArenaEvent leaveArenaEvent = new PlayerLeaveArenaEvent(player);
            Bukkit.getPluginManager().callEvent(leaveArenaEvent);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerLeaveArenaEvent leaveArenaEvent = new PlayerLeaveArenaEvent(player);
        Bukkit.getPluginManager().callEvent(leaveArenaEvent);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent event) {
        Player player = event.getPlayer();

//        player.teleport(new Location(Bukkit.getWorld("world"), 0, 70, 0));
        player.teleport(new Config().getSpawn());
        ArenaManager arenaManager = ArenaManager.getInstance();
        Arena arena = arenaManager.getArenaFromPlayer(player);
        if (arena == null) return;
        arenaManager.deleteArena(arena);
    }

    public static ItemStack leaveSword() {
        ItemStack leaveSword = new ItemStack(Material.IRON_SWORD);
        ItemMeta itemMeta = leaveSword.getItemMeta();
        itemMeta.setDisplayName("Leave");

        List<String> lore = new ArrayList<>();
        lore.add("Block to leave!");
        itemMeta.setLore(lore);

        leaveSword.setItemMeta(itemMeta);
        return leaveSword;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void giveLeaveSword(PracticeStartEvent event) {
        InventoryUtils.setItem(event.getPlayer(), 8, leaveSword());
    }
}
