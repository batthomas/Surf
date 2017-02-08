package ch.batthomas.surf.listener;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.manager.KillstreakManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.util.Vector;

/**
 *
 * @author batthomas
 */
public class GameListener implements Listener {

    private final KillstreakManager ksm;

    private final Surf plugin;

    public GameListener(Surf plugin) {
        this.plugin = plugin;
        ksm = new KillstreakManager(plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        try {
            Player player = e.getEntity();
            Player killer = e.getEntity().getKiller();
            plugin.getStatsQuery().addStats(player.getUniqueId(), "deaths", 1);
            e.getDrops().clear();
            e.setDroppedExp(0);
            e.setDeathMessage(null);
            plugin.getWorldManager().teleportPlayer(player);
            player.spigot().respawn();
            if (killer != null) {
                plugin.getStatsQuery().addStats(killer.getUniqueId(), "kills", 1);
                plugin.getLevelManager().calculateLevel(killer);
                player.sendMessage(plugin.getPrefix() + "Du wurdest von §3" + killer.getName() + " §7getötet!");
                killer.sendMessage(plugin.getPrefix() + "Du hast §3" + player.getName() + " §7getötet!");
                ksm.addKill(killer);
                ksm.removePlayer(player);
            } else {
                player.sendMessage(plugin.getPrefix() + "Du wurdest getötet!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(plugin.getWorldManager().getSpawnLocation());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        ksm.removePlayer(e.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player player = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();
            if (player.getLocation().getY() + 2 >= plugin.getWorldManager().getFromConfig("safezone.y")) {
                if (damager.getLocation().getY() + 2 >= plugin.getWorldManager().getFromConfig("safezone.y")) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
