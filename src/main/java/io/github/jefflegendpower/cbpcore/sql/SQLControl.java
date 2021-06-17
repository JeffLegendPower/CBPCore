package io.github.jefflegendpower.cbpcore.sql;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLControl extends AbstractSQLControl {

    @Override
    public void createTable() {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS mpp" +
                    "(NAME VARCHAR(100),UUID VARCHAR(100),POINTS INT(100),PRIMARY KEY (NAME))");
            ps.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM mpp WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();
            results.next();
            if (!exists(uuid)) {
                PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INFO mpp" +
                        " (NAME, UUID) VALUES (?,?)");
                ps2.setString(1, player.getName());
                ps2.setString(2, uuid.toString());
                ps2.executeUpdate();
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM mpp WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet results = ps.executeQuery();
            return results.next();

        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public void addPoints(UUID uuid, int points) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE mpp SET POINTS=? WHERE UUID=?");
            ps.setInt(1, getPoints(uuid) + points);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int getPoints(UUID uuid) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT POINTS FROM mpp WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int points = 0;
            if (rs.next()) {
                points = rs.getInt("POINTS");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
