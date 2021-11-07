package io.github.jefflegendpower.cbpcore.utility;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class InventoryUtils {

    public static void giveItems(Player player, ItemStack... items) {
        for (ItemStack item : items) {
            if (player.getInventory().contains(item)) continue;
            player.getInventory().addItem(item);
        }
    }

    public static void setItems(Player player, Map<Integer, ItemStack> itemMap) {
        for (Map.Entry<Integer, ItemStack> itemEntry : itemMap.entrySet()) {
            player.getInventory().setItem(itemEntry.getKey(), itemEntry.getValue());
        }
    }

    public static void setItem(Player player, int index, ItemStack item) {
        player.getInventory().setItem(index, item);
    }

    public static void clearInventory(Player player) {
        player.getInventory().clear();
    }
}
