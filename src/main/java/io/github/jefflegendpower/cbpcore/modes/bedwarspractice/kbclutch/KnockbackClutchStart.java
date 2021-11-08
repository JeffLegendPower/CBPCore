package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.kbclutch;

import io.github.jefflegendpower.cbpcore.config.Config;
import io.github.jefflegendpower.cbpcore.events.PracticeStartEvent;
import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.modes.Start;
import io.github.jefflegendpower.cbpcore.modes.arena.Arena;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.speedclutch.BlockItem;
import io.github.jefflegendpower.cbpcore.utility.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class KnockbackClutchStart implements Start {

    @Override
    @EventHandler
    public void onRoundStart(PracticeStartEvent event) {
        if (event.getMode() != Mode.KNOCKBACK_CLUTCH) return;

        ArenaManager arenaManager = ArenaManager.getInstance();

        Player player = event.getPlayer();
        Arena arena = arenaManager.getArenaFromPlayer(player);

        if (arena != null)
            arenaManager.deleteArena(arena);
        ItemStack items = new BlockItem().item();
        Arena clutchArena = ArenaManager.getInstance().getNewArena(Mode.KNOCKBACK_CLUTCH, event.getPlayer(), new Config().getTemplateWorld("knockback_clutch"), items);
        arenaManager.createArena(clutchArena);
        InventoryUtils.clearInventory(player);
        InventoryUtils.giveItems(player, clutchArena.getRelatedItems());
    }
}
