package io.github.jefflegendpower.cbpcore.stats;

import io.github.jefflegendpower.cbpcore.sql.SQLControl;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StatsSQLControl extends SQLControl {

    @Override
    public void createTable() {
        new Thread(() -> {
            try {
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerstats" +
                        "(NAME VARCHAR(100),UUID VARCHAR(100),POINTS INT(100),PRIMARY KEY (NAME))");
                ps.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    public void addPlayer(Player player) {
        new Thread(() -> {
            try {
                UUID uuid = player.getUniqueId();
                if (!exists(uuid)) {
                    PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO playerstats" +
                            " (NAME,UUID) VALUES (?,?)");
                    ps.setString(1, player.getName());
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                }

            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    private boolean exists(UUID uuid) {

        ExecutorService threadpool = Executors.newCachedThreadPool();
        Future<Boolean> booleanFuture = threadpool.submit(() -> {
            try {
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM playerstats WHERE UUID=?");
                ps.setString(1, uuid.toString());

                ResultSet results = ps.executeQuery();
                return results.next();

            } catch (SQLException exception) {
                exception.printStackTrace();
                return false;
            }
        });
        try {
            return booleanFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
//        try {
//            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM playerstats WHERE UUID=?");
//            ps.setString(1, uuid.toString());
//
//            ResultSet results = ps.executeQuery();
//            return results.next();
//
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//
//            return false;
//        }
    }
}
