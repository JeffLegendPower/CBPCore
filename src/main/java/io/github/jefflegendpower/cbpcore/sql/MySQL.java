package io.github.jefflegendpower.cbpcore.sql;

import io.github.jefflegendpower.cbpcore.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL implements Runnable {

    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    private Connection connection;

    public MySQL(Config config) {
        this.host = getSQLInfo("host", config);
        this.port = getSQLInfo("port", config);
        this.database = getSQLInfo("database", config);
        this.username = getSQLInfo("username", config);
        this.password = getSQLInfo("password", config);
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected())
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" +
                    port + "/" + database + "?useSSL=false", username, password);
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private String getSQLInfo(String path, Config config) {

        if (config.getConfig().getString("SQL." + path) == null)
            return "";
        return config.getConfig().getString("SQL." + path);
    }

    @Override
    public void run() {
        new Thread(() -> {
            System.out.println("helo");
        }).start();
    }

    public void start() {

    }
}
