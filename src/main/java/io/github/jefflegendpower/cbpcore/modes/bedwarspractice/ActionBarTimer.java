package io.github.jefflegendpower.cbpcore.modes.bedwarspractice;

import io.github.jefflegendpower.cbpcore.utility.ChatUtils;
import io.github.jefflegendpower.cbpcore.utility.InventoryUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class ActionBarTimer {

    public BukkitRunnable actionBarTimer(Player player, Map<Player, Long> players) {

        BukkitRunnable runnable = new BukkitRunnable() {

            @Override
            public void run() {
                if (players.get(player) == null) cancel();

                try {
                    final long timeDifference = System.currentTimeMillis() - players.get(player);

                    String seconds = Long.toString(timeDifference/1000);
                    String milliseconds = Long.toString(timeDifference%1000);

                    ChatUtils.sendActionBar(player, ChatColor.GOLD + seconds + "." + milliseconds);
                } catch (NullPointerException exception) {
                    cancel();
                }
            }
        };
        return runnable;
    }
}
