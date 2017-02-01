package ch.batthomas.surf.database;

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
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT IGNORE INTO surf_stats (uuid, kills, deaths) VALUES ('").append(player.getUniqueId()).append("', 0, 0)");
        mysql.executeUpdate(sb.toString());
    }

    public void setStats(Player player, String mode, int amount) throws SQLException {
        switch (mode) {
            case "kills":
            case "deaths":
                StringBuilder sb = new StringBuilder();
                sb.append("UPDATE surf_stats SET ").append(mode).append("='").append(amount).append("' WHERE uuid='").append(player.getUniqueId()).append("'");
                mysql.executeUpdate(sb.toString());
                break;
            default:
                break;
        }
    }

    public void addStats(UUID uuid, String mode, int amount) throws SQLException {
        switch (mode) {
            case "kills":
            case "deaths":
                StringBuilder sb = new StringBuilder();
                int added = Integer.parseInt(getStats(uuid, mode)) + amount;
                sb.append("UPDATE surf_stats SET ").append(mode).append("='").append(added).append("' WHERE uuid='").append(uuid).append("'");
                mysql.executeUpdate(sb.toString());
                break;
            default:
                break;
        }
    }

    public String getStats(UUID uuid, String mode) throws SQLException {
        switch (mode) {
            case "kills":
            case "deaths":
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT ").append(mode).append(" FROM surf_stats WHERE uuid='").append(uuid).append("';");
                ResultSet rs = mysql.executeQuery(sb.toString());
                if (rs.first()) {
                    return rs.getString(1);
                }
                return "0";
            default:
                return "0";
        }
    }

}
