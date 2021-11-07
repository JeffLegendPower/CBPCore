package io.github.jefflegendpower.cbpcore.modes.arena;

import io.github.jefflegendpower.cbpcore.CBPCore;
import io.github.jefflegendpower.cbpcore.modes.Mode;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    private static ArenaManager instance;
    private CBPCore plugin;
    private World practiceWorld;
    private List<Arena> arenaList;

    private ArenaManager(CBPCore plugin, World practiceWorld) {
        this.plugin = plugin;
        this.practiceWorld = practiceWorld;
        this.arenaList = new ArrayList<>();
    }

    public static ArenaManager getInstance() {
        if (instance == null)
            instance = new ArenaManager(CBPCore.getPlugin(CBPCore.class), Bukkit.getWorld("PracticeSpace"));
        return instance;
    }

    public Arena getNewArena(Mode mode, Player player, String templateWorld, ItemStack... relatedItems) {
        return new Arena(practiceWorld, mode, player, templateWorld, relatedItems);
    }

    public void createArena(Arena arena) {
        arena.createArena();
        arenaList.add(arena);
    }

    public void deleteArena(Arena arena) {
        arena.getPlayer().getInventory().clear();
        arena.removeArena();
        arenaList.remove(arena);
    }

    public List<Arena> getArenaList() {
        return arenaList;
    }

    public List<Arena> getArenaList(Mode arenaType) {
        List<Arena> arenas = new ArrayList<>();
        for (Arena arena : arenaList)
            if (arena.getType() == arenaType)
                arenas.add(arena);
        return arenas;
    }

    public Arena getArenaFromPlayer(Player player) {
        for (Arena arena : arenaList)
            if (arena.getPlayer().equals(player)) return arena;

        return null;
    }
}
