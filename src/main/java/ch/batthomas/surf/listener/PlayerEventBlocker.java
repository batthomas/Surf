package ch.batthomas.surf.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

/**
 *
 * @author batthomas
 */
public class PlayerEventBlocker implements Listener {
    
    @EventHandler
    public void onAchievement(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onManipulate(PlayerArmorStandManipulateEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onShear(PlayerShearEntityEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }
}
