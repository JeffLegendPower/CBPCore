package io.github.jefflegendpower.cbpcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerLeaveArenaEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Player player;

    public PlayerLeaveArenaEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
