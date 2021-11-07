package io.github.jefflegendpower.cbpcore.modes;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class StopHunger implements Listener {

    @EventHandler
    public void playerLoseHungerEvent(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            event.setCancelled(true);
            if (player.getFoodLevel() < 20)
                player.setFoodLevel(20);
        }
    }
}
