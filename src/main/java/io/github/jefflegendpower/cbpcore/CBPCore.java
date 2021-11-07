package io.github.jefflegendpower.cbpcore;

import com.onarandombox.MultiverseCore.MultiverseCore;
import io.github.jefflegendpower.cbpcore.commands.OpenGUI;
import io.github.jefflegendpower.cbpcore.config.Config;
import io.github.jefflegendpower.cbpcore.items.GUICompass;
import io.github.jefflegendpower.cbpcore.items.LeaveSword;
import io.github.jefflegendpower.cbpcore.modes.StopHunger;
import io.github.jefflegendpower.cbpcore.modes.StopItemDrop;
import io.github.jefflegendpower.cbpcore.modes.arena.BlocksChangedInArena;
import io.github.jefflegendpower.cbpcore.modes.arena.LeaveArena;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.blockin.BlockIn;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.blockin.BlockInStart;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.fireball.FireballJump;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.fireball.FireballJumpStart;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.fireball.LaunchFireball;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.kbclutch.KnockbackClutch;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.kbclutch.KnockbackClutchStart;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.speedclutch.SpeedClutch;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.speedclutch.SpeedClutchStart;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.tntjump.IgniteTNT;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.tntjump.TNTJump;
import io.github.jefflegendpower.cbpcore.modes.bedwarspractice.tntjump.TNTJumpStart;
import io.github.jefflegendpower.cbpcore.spawn.PlayerSpawn;
import io.github.jefflegendpower.cbpcore.spawn.PlayerVoid;
import io.github.jefflegendpower.cbpcore.spawn.RespawnPlayer;
import io.github.jefflegendpower.cbpcore.sql.MySQL;
import io.github.jefflegendpower.cbpcore.sql.SQLControl;
import io.github.jefflegendpower.cbpcore.stats.PlayerJoin;
import io.github.jefflegendpower.cbpcore.stats.StatsSQLControl;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class CBPCore extends JavaPlugin {

    public MySQL SQL;
    public SQLControl statsData;
    private final JavaPlugin core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.SQL = new MySQL(new Config());

        connectDatabase();

        this.statsData = new StatsSQLControl();

        registerEvents();
        registerCommands();
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

    private void registerEvents() {
        registerEvent(new PlayerJoin(this));
        registerEvent(new PlayerVoid());
        registerEvent(new RespawnPlayer());
        registerEvent(new LeaveArena());
        registerEvent(new BlocksChangedInArena());
        registerEvent(new StopItemDrop());
        registerEvent(new StopHunger());
        registerEvent(new PlayerSpawn());

        registerEvent(new FireballJump());
        registerEvent(new FireballJumpStart());
        registerEvent(new LaunchFireball());

        registerEvent(new TNTJumpStart());
        registerEvent(new IgniteTNT());
        registerEvent(new TNTJump());

        registerEvent(new SpeedClutchStart());
        registerEvent(SpeedClutch.getInstance());

        registerEvent(new KnockbackClutchStart());
        registerEvent(KnockbackClutch.getInstance());

        registerEvent(new BlockInStart());
        registerEvent(BlockIn.getInstance());

        registerEvent(new GUICompass());
        registerEvent(new LeaveSword());

        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(io.github.jefflegendpower.cbpcore.traits.OpenGUI.class).
                withName("openpracticegui"));
    }

    private void registerCommands() {
        this.getCommand("start").setExecutor(new OpenGUI());
    }

    private void registerEvent(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
