package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.speedclutch;

import io.github.jefflegendpower.cbpcore.modes.ArenaItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BlockItem implements ArenaItem {

    @Override
    public ItemStack item() {
        return new ItemStack(Material.WOOL, 64);
    }
}
