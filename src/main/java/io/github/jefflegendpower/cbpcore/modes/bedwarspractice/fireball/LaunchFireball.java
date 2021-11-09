package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.fireball;

import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class LaunchFireball implements Listener {

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        if (ArenaManager.getInstance().getArenaFromPlayer(player).getType() == null ||
                ArenaManager.getInstance().getArenaFromPlayer(player).getType() != Mode.FIREBALL_JUMP) return;

        if (player.getItemInHand().isSimilar(new FireballItem().item())) {
            summonFireball(player);
            event.setCancelled(true);
            if (player.getItemInHand().getAmount() == 1) player.setItemInHand(new ItemStack(Material.AIR));
            player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
        }
    }

    private void summonFireball(Player player) {

        Location summonLocation = player.getEyeLocation().add(player.getLocation().getDirection());
        Fireball fireball = player.getWorld().spawn(summonLocation, Fireball.class);
        Vector direction = player.getLocation().getDirection();
        fireball.setVelocity(direction);
    }
}
