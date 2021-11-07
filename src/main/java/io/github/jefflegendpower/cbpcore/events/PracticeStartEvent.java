package io.github.jefflegendpower.cbpcore.events;

import io.github.jefflegendpower.cbpcore.modes.Mode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PracticeStartEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled = false;
    private final Mode gameMode;
    private final Player player;

    public PracticeStartEvent(Mode gameMode, Player player) {
        this.gameMode = gameMode;
        this.player = player;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }

    public Mode getMode() {
        return gameMode;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
