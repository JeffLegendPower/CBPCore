package io.github.jefflegendpower.cbpcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerFallInVoidEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Player player;

    public PlayerFallInVoidEvent(Player player) {
        this.player = player;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
