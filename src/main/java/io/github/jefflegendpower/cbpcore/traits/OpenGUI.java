package io.github.jefflegendpower.cbpcore.traits;

import io.github.jefflegendpower.cbpcore.GUIs.PracticeGUI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.event.EventHandler;

public class OpenGUI extends Trait {

    public OpenGUI() {
        super("openpracticegui");
    }

    @EventHandler
    public void click(NPCRightClickEvent event) {
        PracticeGUI.INVENTORY.open(event.getClicker());
    }
}
