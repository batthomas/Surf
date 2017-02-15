package ch.batthomas.surf.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class StatsQuery {

    private final MySQLConnector mysql;

    public StatsQuery(MySQLConnector mysql) {
        this.mysql = mysql;
    }

    public void addPlayer(Player player) throws SQLException {
        String query = "INSERT IGNORE INTO surf_stats (uuid, kills, deaths) VALUES (?, 0, 0)";
        PreparedStatement statement = mysql.getConnection().prepareStatement(query);
        statement.setString(1, player.getUniqueId().toString());
        mysql.executeUpdate(statement);
    }

    public void addStats(UUID uuid, String mode, int amount) throws SQLException {
        switch (mode) {
            case "kills":
            case "deaths":
                int added = Integer.parseInt(getStats(uuid, mode)) + amount;
                String query = "UPDATE surf_stats SET " + mode + "=? WHERE uuid=?";
                PreparedStatement statement = mysql.getConnection().prepareStatement(query);
                statement.setString(1, mode);
                statement.setString(2, uuid.toString());
                mysql.executeUpdate(statement);
                break;
            default:
                break;
        }
    }

    public String getStats(UUID uuid, String mode) throws SQLException {
        switch (mode) {
            case "kills":
            case "deaths":
                String query = "SELECT " + mode + " FROM surf_stats WHERE uuid=?";
                PreparedStatement statement = mysql.getConnection().prepareStatement(query);
                statement.setString(1, uuid.toString());
                ResultSet rs = mysql.executeQuery(statement);
                if (rs.first()) {
                    return rs.getString(1);
                }
                return "0";
            default:
                return "0";
        }
    }

}
