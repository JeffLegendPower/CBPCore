package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.tntjump;

import io.github.jefflegendpower.cbpcore.modes.ArenaItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TNTItem implements ArenaItem {

    @Override
    public ItemStack item() {
        return new ItemStack(Material.TNT);
    }
}
