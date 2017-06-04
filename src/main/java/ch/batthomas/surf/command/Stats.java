package ch.batthomas.surf.command;

import ch.batthomas.surf.Surf;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class Stats implements CommandExecutor {

    private final Surf plugin;

    public Stats(Surf plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED + "Nur Spieler koennen diesen Command ausführen!");
            return false;
        }
        try {
            Player player = (Player) cs;
            if (cmd.getName().equalsIgnoreCase("stats")) {
                if (args != null && args.length > 0) {
                    UUID uuid = plugin.getMojangAPIHelper().getUUID(args[0]);
                    if (uuid != null) {
                        try {
                            player.sendMessage(plugin.getPrefix() + "§l- §6Stats von " + args[0] + " §7-");
                            player.sendMessage("           §7● §6Kills§7: " + plugin.getStatsQuery().getStats(uuid, "kills"));
                            player.sendMessage("           §7● §6Deaths§7: " + plugin.getStatsQuery().getStats(uuid, "deaths"));
                            player.sendMessage("           §7● §6K/D§7: " + calculateKD(uuid));
                        } catch (SQLException ex) {
                            Logger.getLogger(Stats.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        player.sendMessage(plugin.getPrefix() + "Dieser Spieler wurde nicht gefunden");
                    }
                } else {
                    try {
                        player.sendMessage(plugin.getPrefix() + "§l- §6Stats von " + player.getName() + " §7-");
                        player.sendMessage("           §7● §6Kills§7: " + plugin.getStatsQuery().getStats(player.getUniqueId(), "kills"));
                        player.sendMessage("           §7● §6Deaths§7: " + plugin.getStatsQuery().getStats(player.getUniqueId(), "deaths"));
                        player.sendMessage("           §7● §6K/D§7: " + calculateKD(player.getUniqueId()));
                    } catch (SQLException ex) {
                        Logger.getLogger(Stats.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        } catch (IOException ex) {

        }
        return false;
    }

    private double calculateKD(UUID uuid) throws SQLException {
        double kills = Double.parseDouble(plugin.getStatsQuery().getStats(uuid, "kills"));
        double deaths = Double.parseDouble(plugin.getStatsQuery().getStats(uuid, "deaths"));
        return Double.parseDouble(new DecimalFormat("##.##").format(kills / deaths));
    }
}
