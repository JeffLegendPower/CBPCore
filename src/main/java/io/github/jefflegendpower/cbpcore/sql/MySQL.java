package io.github.jefflegendpower.cbpcore.sql;

import io.github.jefflegendpower.cbpcore.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    private Connection connection;

    public MySQL(Config config) {
        this.host = config.getConfig().getString("host");
        this.port = config.getConfig().getString("port");
        this.database = config.getConfig().getString("database");
        this.username = config.getConfig().getString("username");
        if (config.getConfig().getString("password") != null)
            this.password = config.getConfig().getString("password");
        else
            this.password = "";
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
}
