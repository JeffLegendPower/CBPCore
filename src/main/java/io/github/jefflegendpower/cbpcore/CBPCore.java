package io.github.jefflegendpower.cbpcore;

import io.github.jefflegendpower.cbpcore.sql.AbstractSQLControl;
import io.github.jefflegendpower.cbpcore.sql.MySQL;
import io.github.jefflegendpower.cbpcore.sql.SQLControl;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class CBPCore extends JavaPlugin {

    public MySQL SQL;
    public AbstractSQLControl data;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.SQL = new MySQL(new Config(this));
        connectDatabase();

        this.data = new SQLControl();
    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }

    private void connectDatabase() {
        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            getLogger().info("Database not connected!");
            getLogger().info("Either there is no database or the login info is incorrect");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        if (SQL.isConnected())
            getLogger().info("Database connected");
    }
}
