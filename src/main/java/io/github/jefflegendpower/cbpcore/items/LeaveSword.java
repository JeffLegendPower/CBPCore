package io.github.jefflegendpower.cbpcore.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LeaveSword {

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
}
