package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.tntjump;

import io.github.jefflegendpower.cbpcore.config.Config;
import io.github.jefflegendpower.cbpcore.events.PracticeStartEvent;
import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.modes.Start;
import io.github.jefflegendpower.cbpcore.modes.arena.Arena;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import io.github.jefflegendpower.cbpcore.utility.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class TNTJumpStart implements Start {

    @Override
    @EventHandler
    public void onRoundStart(PracticeStartEvent event) {
        if (event.getMode() != Mode.TNT_JUMP) return;

        ArenaManager arenaManager = ArenaManager.getInstance();

        Player player = event.getPlayer();
        Arena arena = arenaManager.getArenaFromPlayer(player);

        if (arena != null)
            arenaManager.deleteArena(arena);
        Arena TNTArena = ArenaManager.getInstance().getNewArena(Mode.TNT_JUMP, event.getPlayer(), new Config().getTemplateWorld("tnt_jump"), new TNTItem().item());
        arenaManager.createArena(TNTArena);

        player.teleport(TNTArena.getSpawnLocation());

        InventoryUtils.giveItems(player, new TNTItem().item());
    }
}
