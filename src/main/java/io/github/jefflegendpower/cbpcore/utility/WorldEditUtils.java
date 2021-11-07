package io.github.jefflegendpower.cbpcore.utility;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionFactory;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.world.DataException;
import io.github.jefflegendpower.cbpcore.CBPCore;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;

public class WorldEditUtils {

    private EditSessionFactory editSessionFactory;

    public WorldEditUtils(EditSessionFactory editSessionFactory) {
        this.editSessionFactory = editSessionFactory;
    }

    private void loadArea(World world, File file, Vector origin) throws DataException, IOException, MaxChangedBlocksException {
        EditSession editSession = editSessionFactory.getEditSession(
                (com.sk89q.worldedit.world.World) new BukkitWorld(world), 999999999);
        MCEditSchematicFormat.getFormat(file).load(file).paste(editSession, origin, false);
        new Vector();
    }

    public void loadSchematic(String schematicName, Location location) {
        CBPCore plugin = CBPCore.getPlugin(CBPCore.class);
        World world = location.getWorld();

        File file = new File(plugin.getDataFolder().getAbsolutePath() + "/" + schematicName);

        Vector vector = new Vector(location.getX(), location.getY(), location.getZ());
        try {
            this.loadArea(world, file, vector);
        } catch (DataException | MaxChangedBlocksException | IOException e) {
            e.printStackTrace();
        }
    }
}
