package ch.batthomas.surf.listener;

import ch.batthomas.surf.Surf;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

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
        try {
            Player player = e.getPlayer();
            plugin.getWorldManager().teleportPlayer(player);
            plugin.getStatsQuery().addPlayer(player);
            player.getInventory().clear();
            plugin.getKitManager().applyKit(plugin.getKitManager().getCurrentKit());
            player.sendMessage(plugin.getPrefix() + "Das aktuelle Kit heisst " + plugin.getKitManager().getCurrentKit().getName());
            e.setJoinMessage(null);
        } catch (SQLException ex) {
            Logger.getLogger(JoinListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
