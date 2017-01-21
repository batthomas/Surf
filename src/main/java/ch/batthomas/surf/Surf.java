package ch.batthomas.surf;

import ch.batthomas.surf.constant.ConfigConstant;
import ch.batthomas.surf.database.KitQuery;
import ch.batthomas.surf.database.MySQLConnector;
import ch.batthomas.surf.listener.BlockEventBlocker;
import ch.batthomas.surf.listener.PlayerEventBlocker;
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

    @Override
    public void onEnable() {
        registerEvents();
        prepareConfig();
        connectMySQL();
    }

    @Override
    public void onDisable() {

    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BlockEventBlocker(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEventBlocker(), this);
    }

    public void prepareConfig() {
        try {
            ConfigConstant cons = new ConfigConstant();
            cons.initializeContent();
            config = new ConfigHelper("config", cons, this);
        } catch (IOException ex) {
            Logger.getLogger(Surf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connectMySQL() {
        try {
            mysql = new MySQLConnector(this);
            kq = new KitQuery(mysql);
            mysql.connect();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(Surf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
