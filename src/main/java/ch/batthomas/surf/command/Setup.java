package ch.batthomas.surf.command;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.util.GameState;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class Setup implements CommandExecutor {
    
    private final Surf plugin;
    
    public Setup(Surf plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED + "Nur Spieler koennen diesen Command ausführen!");
            return false;
        }
        Player player = (Player) cs;
        if (cmd.getName().equalsIgnoreCase("setup")) {
            if (player.hasPermission("surf.setup")) {
                if (args != null && args.length > 0 && args[0] != null) {
                    switch (args[0]) {
                        case "goto":
                            if (args.length == 2 && args[1] != null) {
                                teleportToMap(player, args[1]);
                            } else {
                                player.sendMessage(plugin.getPrefix() + "Bitte nutze einen gültigen Command");
                            }
                            break;
                        default:
                            player.sendMessage(plugin.getPrefix() + "Bitte nutze einen gültigen Command");
                    }
                } else {
                    if (plugin.getState() == GameState.INGAME) {
                        for (Player other : Bukkit.getOnlinePlayers()) {
                            if (!other.hasPermission("surf.setup")) {
                                other.kickPlayer("Der Wartungsmodus wurde eingeschalten, du wurdest gekickt.");
                            }
                        }
                        plugin.setState(GameState.SETUP);
                        Bukkit.getScheduler().cancelTask(plugin.getGameScheduler().getTaskId());
                        player.setGameMode(GameMode.CREATIVE);
                        player.getInventory().clear();
                        Bukkit.broadcastMessage(plugin.getPrefix() + "Der Setupmodus wurde eingeschalten");
                        return true;
                    } else {
                        player.kickPlayer("Der Setupmode wurde ausgeschalten, der Server startet neu");
                        Bukkit.shutdown();
                        return false;
                    }
                }
            } else {
                player.sendMessage(plugin.getPrefix() + "Du hast keine Rechte für diesen Command");
            }
        }
        return false;
    }
    
    private void teleportToMap(Player player, String name) {
        File file = new File(Bukkit.getWorldContainer().getPath() + "/" + name);
        if (file.exists()) {
            World world = Bukkit.createWorld(new WorldCreator(name));
            player.teleport(world.getSpawnLocation());
            player.sendMessage(plugin.getPrefix() + "Du wurdest erfolgreich zur Map " + name + " teleportiert");
        } else {
            player.sendMessage(plugin.getPrefix() + "Diese Map existiert nicht");
        }
    }
}
