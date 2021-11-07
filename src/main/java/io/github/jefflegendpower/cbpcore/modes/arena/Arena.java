package io.github.jefflegendpower.cbpcore.modes.arena;

import com.grinderwolf.swm.api.world.SlimeWorld;
import io.github.jefflegendpower.cbpcore.config.Config;
import io.github.jefflegendpower.cbpcore.modes.Mode;
import io.github.jefflegendpower.cbpcore.CreatePracticeWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arena {

    private SlimeWorld slimeWorld;
    private String templateWorld;
    private final World practiceWorld;

    private final Mode type;
    private Location spawnLocation;
    private Player player;
    private ItemStack[] relatedItems;
    private List<org.bukkit.block.Block> playerPlacedBlocks;
    private Map<org.bukkit.block.Block, Material> playerBrokenBlocks;

    protected Arena(World world, Mode type, Player player, String templateWorld, ItemStack... relatedItems) {
        this.practiceWorld = world;
        this.type = type;
        this.player = player;
        this.relatedItems = relatedItems;
        this.playerPlacedBlocks = new ArrayList<>();
        this.playerBrokenBlocks = new HashMap<>();
        this.templateWorld = templateWorld;
    }

    protected void createArena() {
        CreatePracticeWorld create = CreatePracticeWorld.getInstance();

        slimeWorld = create.clone(create.getWorldFromString(templateWorld));

        World bukkitWorld = Bukkit.getWorld(slimeWorld.getName());

        spawnLocation = new Config().getSpawnLocation(type.getAlias(), bukkitWorld);
        while (spawnLocation.getBlock().getType() != Material.AIR || spawnLocation.getBlock().getRelative(BlockFace.UP).getType() != Material.AIR)
            spawnLocation.add(0, 1, 0);

        System.out.println(spawnLocation);
    }

    /** After calling this method you should no longer use this instance */
    protected void removeArena() {
        Bukkit.unloadWorld(slimeWorld.getName(), false);
        player = null;
    }

    protected void addPlayerBrokenBlock(org.bukkit.block.Block block, Material material) {
        playerBrokenBlocks.put(block, material);
    }

    protected void addPlayerPlacedBlock(org.bukkit.block.Block block) {
        playerPlacedBlocks.add(block);
    }

    public Mode getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack[] getRelatedItems() {
        return relatedItems;
    }

    public List<org.bukkit.block.Block> getPlayerPlacedBlocks() {
        return playerPlacedBlocks;
    }

    public Map<org.bukkit.block.Block, Material> getPlayerBrokenBlocks() {
        return playerBrokenBlocks;
    }

    public SlimeWorld getSlimeWorld() {
        return slimeWorld;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }
}
