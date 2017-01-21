package ch.batthomas.surf.database;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.util.ConfigHelper;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public MySQLConnector(Surf plugin) throws IOException {
        ConfigHelper config = new ConfigHelper("config", plugin);
        username = config.getConfig().getString("MySQL.username");
        password = config.getConfig().getString("MySQL.password");
        database = config.getConfig().getString("MySQL.database");
        host = config.getConfig().getString("MySQL.host");
        port = Integer.parseInt(config.getConfig().getString("MySQL.port"));
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

    public ResultSet executeQuery(String query) throws SQLException {
        if (isConnected()) {
            return connection.createStatement().executeQuery(query);
        }
        return null;
    }

    public void executeUpdate(String query) throws SQLException {
        if (isConnected()) {
            connection.createStatement().executeUpdate(query);
        }
    }

    public boolean isConnected() {
        return connection != null;
    }
}
