package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.blockin;

import io.github.jefflegendpower.cbpcore.config.Config;
import io.github.jefflegendpower.cbpcore.events.PracticeStartEvent;
import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.modes.Start;
import io.github.jefflegendpower.cbpcore.modes.arena.Arena;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import io.github.jefflegendpower.cbpcore.utility.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class BlockInStart implements Start {

    @Override
    @EventHandler
    public void onRoundStart(PracticeStartEvent event) {
        if (event.getMode() != Mode.BLOCK_IN) return;

        ArenaManager arenaManager = ArenaManager.getInstance();

        Player player = event.getPlayer();
        Arena arena = arenaManager.getArenaFromPlayer(player);

        if (arena != null)
            arenaManager.deleteArena(arena);

        ItemStack blocks = new ItemStack(Material.WOOL, 64);
        ItemStack pick = new ItemStack(Material.WOOD_PICKAXE);
        ItemStack axe = new ItemStack(Material.WOOD_AXE);

        Arena blockInArena = ArenaManager.getInstance().getNewArena(Mode.BLOCK_IN, event.getPlayer(), new Config().getTemplateWorld("block_in"), blocks, pick, axe);

        arenaManager.createArena(blockInArena);
        InventoryUtils.clearInventory(player);
        InventoryUtils.giveItems(player, blockInArena.getRelatedItems());
        System.out.println("arena loaded");
    }
}
