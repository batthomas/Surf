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
        Player player = (Player) cs;
        if (cmd.getName().equalsIgnoreCase("level")) {
            if (args != null && args.length > 0) {
                UUID uuid = lookupUUID(args[0]);
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
        return false;
    }

    private UUID lookupUUID(String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            if (con.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Gson gson = new Gson();
                String rawuuid = gson.fromJson(response.toString(), JsonObject.class).get("id").getAsString();
                String uuid = rawuuid.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5");
                return UUID.fromString(uuid);
            } else {
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Stats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return null;
    }

}
