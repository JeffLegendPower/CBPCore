package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.tntjump;

import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.modes.arena.ArenaManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class IgniteTNT implements Listener {

    @EventHandler
    public void onTNTPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.TNT) {
            event.setCancelled(true);
            Player player = event.getPlayer();

            if (player.getItemInHand().getAmount() == 1) player.setItemInHand(new ItemStack(Material.AIR));
            player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);

            TNTPrimed tnt = player.getWorld().spawn(block.getLocation().clone().add(0.5, 0, 0.5), TNTPrimed.class);
            tnt.setFuseTicks(tnt.getFuseTicks()/2);
        }
    }

    @EventHandler
    public void reduceTNTDamage(EntityDamageByEntityEvent event) {
        ArenaManager arenaManager = ArenaManager.getInstance();
        if (event.getEntity() instanceof Player)
            if (arenaManager.getArenaFromPlayer(
                    ((Player) event.getEntity()))
                    .getType() == Mode.TNT_JUMP)
                if (event.getDamager() instanceof TNTPrimed) event.setDamage(event.getDamage()/4);
    }
}
