package io.github.jefflegendpower.cbpcore.modes;

import io.github.jefflegendpower.cbpcore.events.PracticeStartEvent;
import org.bukkit.event.Listener;

public interface Start extends Listener {

    void onRoundStart(PracticeStartEvent event);
}
