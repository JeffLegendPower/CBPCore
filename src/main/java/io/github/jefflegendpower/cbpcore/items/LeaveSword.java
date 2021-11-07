package io.github.jefflegendpower.cbpcore.items;

import io.github.jefflegendpower.cbpcore.events.PlayerLeaveArenaEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LeaveSword implements Listener {

    public static ItemStack getLeaveSword() {
        ItemStack leaveSword = new ItemStack(Material.IRON_SWORD);
        ItemMeta itemMeta = leaveSword.getItemMeta();
        itemMeta.setDisplayName("Leave");

        List<String> lore = new ArrayList<>();
        lore.add("Block to leave!");
        itemMeta.setLore(lore);

        leaveSword.setItemMeta(itemMeta);
        return leaveSword;
    }

    @EventHandler
    public void onPlayerBlockSword(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && player.getItemInHand().isSimilar(getLeaveSword())) {
            PlayerLeaveArenaEvent leaveArenaEvent = new PlayerLeaveArenaEvent(player);
            Bukkit.getPluginManager().callEvent(leaveArenaEvent);
        }
    }
}
