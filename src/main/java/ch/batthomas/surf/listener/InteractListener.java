package ch.batthomas.surf.listener;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.util.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author batthomas
 */
public class InteractListener implements Listener {

    private final Surf plugin;
    private final Cooldown cooldown;

    public InteractListener(Surf plugin) {
        this.plugin = plugin;
        cooldown = new Cooldown();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getMaterial() == Material.EGG) {
                if (cooldown.getCooldown(player) == 0) {
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                    Item item = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.EGG));
                    item.setVelocity(player.getLocation().getDirection().multiply(0.8D));
                    cooldown.addPlayer(player, 3);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        item.getWorld().createExplosion(item.getLocation().getX(), item.getLocation().getY(), item.getLocation().getZ(), 3, false, false);
                        item.remove();
                    }, 20 * 3);
                } else {
                    player.sendMessage(plugin.getPrefix() + "Bitte warte bis du eine neue Granate wirfst");
                }
                e.setCancelled(true);
            }
        }
    }
}
