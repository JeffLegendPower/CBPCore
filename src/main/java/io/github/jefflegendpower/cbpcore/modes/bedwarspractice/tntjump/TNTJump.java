package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.tntjump;

import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.modes.PlayerHitGoldBlock;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class TNTJump extends PlayerHitGoldBlock implements Listener {



    @EventHandler
    public void onPlayerHitGoldBlock(PlayerMoveEvent event) {
        playerHitGoldBlockLogic(event, Mode.TNT_JUMP, new TNTItem());
    }
}
