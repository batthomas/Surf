package ch.batthomas.surf;

import ch.batthomas.surf.constant.ConfigConstant;
import ch.batthomas.surf.database.KitQuery;
import ch.batthomas.surf.database.MySQLConnector;
import ch.batthomas.surf.database.StatsQuery;
import ch.batthomas.surf.listener.BlockEventBlocker;
import ch.batthomas.surf.listener.GameListener;
import ch.batthomas.surf.listener.JoinListener;
import ch.batthomas.surf.listener.PlayerEventBlocker;
import ch.batthomas.surf.manager.KitManager;
import ch.batthomas.surf.scheduler.GameScheduler;
import ch.batthomas.surf.util.ConfigHelper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author batthomas
 */
public class Surf extends JavaPlugin {

    private ConfigHelper config;
    private MySQLConnector mysql;

    private KitQuery kq;
    private StatsQuery sq;

    private KitManager km;

    private String prefix;

    @Override
    public void onEnable() {
        prefix = "§b§lSurf §r§8| §7";
        prepareConfig();
        connectMySQL();
        registerEvents();
        registerManagers();
        startSchedulers();
    }

    @Override
    public void onDisable() {

    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BlockEventBlocker(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEventBlocker(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
    }

    private void registerManagers() {
        try {
            km = new KitManager(this);
        } catch (SQLException ex) {
            Logger.getLogger(Surf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareConfig() {
        try {
            ConfigConstant cons = new ConfigConstant();
            cons.initializeContent();
            config = new ConfigHelper("config", cons, this);
        } catch (IOException ex) {
            Logger.getLogger(Surf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connectMySQL() {
        try {
            mysql = new MySQLConnector(this);
            kq = new KitQuery(mysql);
            sq = new StatsQuery(mysql);
            mysql.connect();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(Surf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startSchedulers() {
        GameScheduler gs = new GameScheduler(this);
        gs.start();
    }

    public StatsQuery getStatsQuery() {
        return sq;
    }

    public KitQuery getKitQuery() {
        return kq;
    }

    public KitManager getKitManager() {
        return km;
    }

    public String getPrefix() {
        return prefix;
    }
}