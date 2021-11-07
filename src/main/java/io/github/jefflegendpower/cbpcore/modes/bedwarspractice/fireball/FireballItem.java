package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.fireball;

import io.github.jefflegendpower.cbpcore.modes.ArenaItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FireballItem implements ArenaItem {

    @Override
    public ItemStack item() {

        ItemStack fireball = new ItemStack(Material.FIREBALL);

        ItemMeta itemMeta = fireball.getItemMeta();
        itemMeta.setDisplayName("Fireball");
        fireball.setItemMeta(itemMeta);

        return fireball;
    }
}
