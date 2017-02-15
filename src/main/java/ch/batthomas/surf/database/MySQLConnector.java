package ch.batthomas.surf.database;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.util.ConfigHelper;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import org.bukkit.Bukkit;

/**
 *
 * @author batthomas
 */
public class MySQLConnector {

    private final String username;
    private final String password;
    private final String database;
    private final String host;
    private final int port;
    private Connection connection;

    private final Surf plugin;

    public MySQLConnector(Surf plugin) throws IOException {
        ConfigHelper config = new ConfigHelper("config", plugin);
        username = config.getConfig().getString("MySQL.username");
        password = config.getConfig().getString("MySQL.password");
        database = config.getConfig().getString("MySQL.database");
        host = config.getConfig().getString("MySQL.host");
        port = Integer.parseInt(config.getConfig().getString("MySQL.port"));
        this.plugin = plugin;
    }

    public void connect() throws SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        }
    }

    public void disconnect() throws SQLException {
        if (isConnected()) {
            connection.close();
        }
    }

    public ResultSet executeQuery(PreparedStatement statement) {
        if (isConnected()) {
            ExecutorService exe = Executors.newCachedThreadPool();
            Future<ResultSet> future = exe.submit(() -> {
                try {
                    return statement.executeQuery();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            });
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void executeUpdate(PreparedStatement statement) {
        if (isConnected()) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    statement.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }
}
