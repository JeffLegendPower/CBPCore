package io.github.jefflegendpower.cbpcore.items;

import io.github.jefflegendpower.cbpcore.GUIs.PracticeGUI;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUICompass implements Listener {

    public static ItemStack getGUICompass() {
        ItemStack guiCompass = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = guiCompass.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addEnchant(Enchantment.LUCK, 1, false);
        itemMeta.setDisplayName("Start");
        guiCompass.setItemMeta(itemMeta);

        return guiCompass;
    }

    // Opens the PracticeGUI when right clicked with the compass in hand
    @EventHandler
    public void openGUI(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
            if (event.getPlayer().getItemInHand().isSimilar(getGUICompass()))
                PracticeGUI.INVENTORY.open(event.getPlayer());
    }
}
