package ch.batthomas.surf.command;

import ch.batthomas.surf.Surf;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class NextRound implements CommandExecutor {

    private final Surf plugin;
    
    public NextRound(Surf plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED + "Nur Spieler koennen diesen Command ausführen!");
            return false;
        }
        Player player = (Player) cs;
        if(cmd.getName().equalsIgnoreCase("nextround")){
            if(player.hasPermission("ttt.nextround")){
                plugin.getGameScheduler().nextRound();
                player.sendMessage(plugin.getPrefix() + "Du hast eine neue Runde gestartet");
                return true;
            }else{
                player.sendMessage(plugin.getPrefix() + "Du hast keine Rechte für diesen Command");
            }
        }
        return false;
    }
}
