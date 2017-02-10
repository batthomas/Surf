package ch.batthomas.surf.listener;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.util.GameState;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author batthomas
 */
public class JoinListener implements Listener {

    private final Surf plugin;

    public JoinListener(Surf plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (plugin.getState() == GameState.INGAME) {
            try {
                plugin.getWorldManager().teleportPlayer(player);
                plugin.getStatsQuery().addPlayer(player);
                player.getInventory().clear();
                player.setGameMode(GameMode.SURVIVAL);
                player.setHealth(20.0D);
                plugin.getKitManager().applyKit(plugin.getKitManager().getCurrentKit());
                player.sendMessage(plugin.getPrefix() + "Das aktuelle Kit heisst " + plugin.getKitManager().getCurrentKit().getName());
                e.setJoinMessage(null);
            } catch (SQLException ex) {
                Logger.getLogger(JoinListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (player.hasPermission("surf.setup")) {
                player.getInventory().clear();
                player.setGameMode(GameMode.CREATIVE);
            } else {
                player.kickPlayer(plugin.getPrefix() + "Der Server befindet sich zurzeit im Wartemodus");
            }
        }
    }
}
