package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.fireball;

import io.github.jefflegendpower.cbpcore.config.Config;
import io.github.jefflegendpower.cbpcore.events.PracticeStartEvent;
import io.github.jefflegendpower.cbpcore.items.LeaveSword;
import io.github.jefflegendpower.cbpcore.modes.arena.LeaveArena;
import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.modes.Start;
import io.github.jefflegendpower.cbpcore.modes.arena.Arena;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import io.github.jefflegendpower.cbpcore.utility.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class FireballJumpStart implements Start {

    @Override
    @EventHandler
    public void onRoundStart(PracticeStartEvent event) {
        if (event.getMode() != Mode.FIREBALL_JUMP) return;

        ArenaManager arenaManager = ArenaManager.getInstance();

        Player player = event.getPlayer();
        Arena arena = arenaManager.getArenaFromPlayer(player);

        if (arena != null)
            arenaManager.deleteArena(arena);
        Arena fireballArena = ArenaManager.getInstance().getNewArena(Mode.FIREBALL_JUMP, event.getPlayer(), new Config().getTemplateWorld("fireball_jump"), new FireballItem().item());
        arenaManager.createArena(fireballArena);
        player.teleport(fireballArena.getSpawnLocation());

        InventoryUtils.clearInventory(player);
        InventoryUtils.giveItems(player, new FireballItem().item());
        InventoryUtils.setItem(player, 8, LeaveSword.getLeaveSword());
    }
}
