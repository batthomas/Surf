package ch.batthomas.surf.listener;

import ch.batthomas.surf.Surf;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author batthomas
 */
public class JoinListener implements Listener{
    
    private final Surf plugin;
    
    public JoinListener(Surf plugin){
        this.plugin  = plugin;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        try {
            plugin.getStatsQuery().addPlayer(e.getPlayer());
        } catch (SQLException ex) {
            Logger.getLogger(JoinListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
