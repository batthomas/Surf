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
import java.util.UUID;
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
public class Level implements CommandExecutor {

    private final Surf plugin;

    public Level(Surf plugin) {
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
            if (cmd.getName().equalsIgnoreCase("level")) {
                if (args != null && args.length > 0) {
                    UUID uuid = plugin.getMojangAPIHelper().getUUID(args[0]);
                    if (uuid != null) {
                        try {
                            player.sendMessage(plugin.getPrefix() + "§l- §6Level von " + args[0] + " §7-");
                            player.sendMessage("           §7● §6Level§7: " + plugin.getLevelManager().getLevel(uuid));
                            player.sendMessage("           §7● §6Fortschritt§7: " + plugin.getLevelManager().getPercentToLevel(uuid) + "%");
                            player.sendMessage("           §7● §6Nächstes Level§7: " + plugin.getLevelManager().getDistanceToLevel(uuid) + " Kills");
                        } catch (SQLException ex) {
                            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                    } else {
                        player.sendMessage(plugin.getPrefix() + "Dieser Spieler wurde nicht gefunden");
                    }
                } else {
                    try {
                        player.sendMessage(plugin.getPrefix() + "§l- §6Level von " + player.getName() + " §7-");
                        player.sendMessage("           §7● §6Level§7: " + plugin.getLevelManager().getLevel(player));
                        player.sendMessage("           §7● §6Fortschritt§7: " + plugin.getLevelManager().getPercentToLevel(player.getUniqueId()) + "%");
                        player.sendMessage("           §7● §6Nächstes Level§7: " + plugin.getLevelManager().getDistanceToLevel(player.getUniqueId()) + " Kills");
                    } catch (SQLException ex) {
                        Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return false;
    }
}
