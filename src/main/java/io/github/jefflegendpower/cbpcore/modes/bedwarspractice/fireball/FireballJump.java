package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.fireball;

import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.modes.PlayerHitGoldBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FireballJump extends PlayerHitGoldBlock implements Listener {

    @EventHandler
    public void onPlayerHitGoldBlock(PlayerMoveEvent event) {
        playerHitGoldBlockLogic(event, Mode.FIREBALL_JUMP, new FireballItem());
    }
}