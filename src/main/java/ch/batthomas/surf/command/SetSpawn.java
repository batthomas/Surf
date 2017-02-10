package ch.batthomas.surf.command;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.constant.WorldConfigConstant;
import ch.batthomas.surf.util.ConfigHelper;
import ch.batthomas.surf.util.GameState;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class SetSpawn implements CommandExecutor {

    private final Surf plugin;
    private ConfigHelper config;

    public SetSpawn(Surf plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED + "Nur Spieler koennen diesen Command ausführen!");
            return false;
        }
        Player player = (Player) cs;
        if (cmd.getName().equalsIgnoreCase("setspawn") && player.hasPermission("surf.setup")) {
            if (plugin.getState() == GameState.SETUP) {
                setFullConfig(player);
                return true;
            } else {
                player.sendMessage(plugin.getPrefix() + "Dieser Command funktioniert nur im Setupmode");
            }
        }

        return false;
    }

    private void setFullConfig(Player player) {
        World world = player.getWorld();
        try {
            config = new ConfigHelper("/worlds/" + player.getWorld().getName(), new WorldConfigConstant(), plugin);
            config.setObject("mapinfo.name", world.getName());
            config.setObject("spawn.x", player.getLocation().getX());
            config.setObject("spawn.y", player.getLocation().getY());
            config.setObject("spawn.z", player.getLocation().getZ());
            config.setObject("spawn.yaw", player.getLocation().getYaw());
            config.setObject("spawn.pitch", player.getLocation().getPitch());
            config.setObject("safezone.y", player.getLocation().getY());
            player.sendMessage(plugin.getPrefix() + "Du hast den Spawn erfolgreich gesetzt");
        } catch (IOException ex) {
            Logger.getLogger(SetSpawn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
