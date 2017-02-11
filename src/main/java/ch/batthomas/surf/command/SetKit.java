package ch.batthomas.surf.command;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.util.GameState;
import ch.batthomas.surf.util.Kit;
import java.sql.SQLException;
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
public class SetKit implements CommandExecutor {

    private final Surf plugin;

    public SetKit(Surf plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED + "Nur Spieler koennen diesen Command ausführen!");
            return false;
        }
        Player player = (Player) cs;
        if (cmd.getName().equalsIgnoreCase("setkit")) {
            if (player.hasPermission("surf.setup")) {
                if (args.length == 1 && args[0] != null && plugin.getState() == GameState.SETUP) {
                    try {
                        Kit kit = new Kit(args[0]);
                        for (int i = 0; i < 9; i++) {
                            kit.addItem("slot" + (i + 1), player.getInventory().getItem(i));
                        }
                        kit.addItem("helmet", player.getInventory().getHelmet());
                        kit.addItem("chestplate", player.getInventory().getChestplate());
                        kit.addItem("leggings", player.getInventory().getLeggings());
                        kit.addItem("boots", player.getInventory().getBoots());
                        plugin.getKitQuery().addKit(kit);
                        player.sendMessage(plugin.getPrefix() + "Dein Kit wurde erfolgreich erstellt");
                        return true;
                    } catch (SQLException ex) {
                        player.sendMessage(plugin.getPrefix() + "Dein Kit existiert bereits");
                        return false;
                    }
                }
                player.sendMessage(plugin.getPrefix() + "Bitte nutze /setkit <name> im Setupmode");
                return false;
            } else {
                player.sendMessage(plugin.getPrefix() + "Du hast keine Rechte für diesen Command");
            }
        }
        return false;
    }
}
