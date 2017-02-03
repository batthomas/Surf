package ch.batthomas.surf.command;

import ch.batthomas.surf.Surf;
import java.sql.SQLException;
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
            try {
                player.sendMessage(plugin.getPrefix() + "Noch " + plugin.getLevelCalculator().getDistanceToLevel(player) + " Kills bis zum nächsten Level");
                player.sendMessage(plugin.getPrefix() + "Level Fortschritt:");
                player.sendMessage(plugin.getPrefix() + "Dein Level: " + plugin.getLevelCalculator().getLevel(player));
            } catch (SQLException ex) {
                Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        return false;
    }

}
