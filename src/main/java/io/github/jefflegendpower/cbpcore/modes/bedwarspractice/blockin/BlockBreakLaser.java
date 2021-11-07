package io.github.jefflegendpower.cbpcore.modes.bedwarspractice.blockin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import static java.lang.System.*;

import java.lang.reflect.InvocationTargetException;

public class BlockBreakLaser {

    private void sendBlockBreakPacket(Player player, Location location, int destroyStage) {

        PacketContainer packet = new PacketContainer(
                PacketType.Play.Server.BLOCK_BREAK_ANIMATION);

        packet.getIntegers().write(1, destroyStage);
        packet.getBlockPositionModifier().write(0, new BlockPosition(location.toVector()));

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void makeLaser(Location bedLocation, Player player) {
        Vector direction = player.getLocation().subtract(bedLocation).toVector().setY(0).normalize();
        World world = player.getWorld();
        byte distance = 5;

        for (distance-=0.5; distance >= 0;) {
            Location location = direction.clone().multiply(distance).toLocation(world);

            if (location.getBlock().getType() != Material.AIR) {
                sendBlockBreakPacket(player, location, 1);
            }

            world.playEffect(location, Effect.CRIT, null);
            player.damage(4);
        }
    }
}
