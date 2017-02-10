package ch.batthomas.surf;

import ch.batthomas.surf.command.NextRound;
import ch.batthomas.surf.command.SetSpawn;
import ch.batthomas.surf.command.Setup;
import ch.batthomas.surf.command.Stats;
import ch.batthomas.surf.constant.ConfigConstant;
import ch.batthomas.surf.database.KitQuery;
import ch.batthomas.surf.database.MySQLConnector;
import ch.batthomas.surf.database.StatsQuery;
import ch.batthomas.surf.listener.BlockEventBlocker;
import ch.batthomas.surf.listener.GameListener;
import ch.batthomas.surf.listener.InventoryEventBlocker;
import ch.batthomas.surf.listener.JoinListener;
import ch.batthomas.surf.listener.MoveListener;
import ch.batthomas.surf.listener.PlayerEventBlocker;
import ch.batthomas.surf.manager.KitManager;
import ch.batthomas.surf.manager.WorldManager;
import ch.batthomas.surf.scheduler.GameScheduler;
import ch.batthomas.surf.util.ConfigHelper;
import ch.batthomas.surf.level.LevelManager;
import ch.batthomas.surf.listener.InteractListener;
import ch.batthomas.surf.util.GameState;
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

    private MySQLConnector mysql;

    private KitQuery kq;
    private StatsQuery sq;

    private GameScheduler gs;

    private KitManager km;
    private WorldManager wm;
    private LevelManager lc;

    private String prefix;
    private GameState state;

    @Override
    public void onEnable() {
        prefix = "§b§lSurf §r§8| §7";
        state = GameState.INGAME;
        prepareConfig();
        connectMySQL();
        registerEvents();
        registerManagers();
        startSchedulers();
        registerCommands();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("nextround").setExecutor(new NextRound(this));
        getCommand("stats").setExecutor(new Stats(this));
        getCommand("level").setExecutor(new ch.batthomas.surf.command.Level(this));
        getCommand("setup").setExecutor(new Setup(this));
        getCommand("setspawn").setExecutor(new SetSpawn(this));
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BlockEventBlocker(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEventBlocker(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryEventBlocker(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
        Bukkit.getPluginManager().registerEvents(new MoveListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(this), this);
    }

    private void registerManagers() {
        try {
            km = new KitManager(this);
            km.nextKit();
            lc = new LevelManager(this);
            wm = new WorldManager(this);
            wm.nextWorld();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Surf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareConfig() {
        try {
            ConfigConstant cons = new ConfigConstant();
            cons.initializeContent();
            ConfigHelper config = new ConfigHelper("config", cons, this);
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
        gs = new GameScheduler(this);
        gs.start();
    }

    public StatsQuery getStatsQuery() {
        return sq;
    }

    public KitQuery getKitQuery() {
        return kq;
    }

    public GameScheduler getGameScheduler() {
        return gs;
    }

    public KitManager getKitManager() {
        return km;
    }

    public WorldManager getWorldManager() {
        return wm;
    }

    public LevelManager getLevelManager() {
        return lc;
    }

    public String getPrefix() {
        return prefix;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
