package ch.batthomas.surf.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 *
 * @author batthomas
 */
public class InventoryEventBlocker implements Listener {
    
    @EventHandler
    public void onOpen(InventoryOpenEvent e){
        if(e.getView().getType() != InventoryType.PLAYER){
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onDrag(InventoryDragEvent e){
        e.setCancelled(true);
    }
}
