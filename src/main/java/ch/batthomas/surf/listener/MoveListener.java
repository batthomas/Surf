package ch.batthomas.surf.listener;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.util.Cooldown;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

/**
 *
 * @author batthomas
 */
public class MoveListener implements Listener {

    private final Cooldown cooldown;
    private final Surf plugin;

    public MoveListener(Surf plugin) {
        cooldown = new Cooldown();
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getLocation().getBlock().getType() == Material.STATIONARY_WATER || player.getLocation().getBlock().getType() == Material.WATER) {
            player.setVelocity(new Vector(player.getLocation().getDirection().getX() * 1.8D, 3.0D, player.getLocation().getDirection().getZ() * 1.8D));
        } else if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.BEDROCK) {
            if (cooldown.getCooldown(player) == 0) {
                try {
                    cooldown.addPlayer(player, plugin.getLevelManager().getBenefits().getCooldown(player));
                    player.sendMessage(plugin.getPrefix() + "Du hast eine Überraschung erhalten");
                } catch (SQLException ex) {
                    Logger.getLogger(MoveListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                player.sendMessage(plugin.getPrefix() + "Du musst noch " + cooldown.getCooldown(player) + " Sekunden warten");
            }
        }
    }
}
