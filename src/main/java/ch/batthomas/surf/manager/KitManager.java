package ch.batthomas.surf.manager;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.util.Kit;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class KitManager {

    private final List<Kit> kits;
    private Kit currentKit;
    private final Surf plugin;

    public KitManager(Surf plugin) throws SQLException {
        kits = plugin.getKitQuery().getKits();
        this.plugin = plugin;
    }

    public Kit nextKit() {
        if (kits != null && !kits.isEmpty()) {
            Random random = new Random(System.currentTimeMillis());
            currentKit = kits.get(kits.size() != 1 ? random.nextInt(kits.size() - 1) : 0);
            kits.remove(currentKit);
            return currentKit;
        } else {
            Bukkit.shutdown();
            return null;
        }
    }

    public void applyKit(Kit kit) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < 9; i++) {
                player.getInventory().setItem(i, kit.getItem("slot" + (i + 1)));
            }
            player.getInventory().setHelmet(kit.getItem("helmet"));
            player.getInventory().setChestplate(kit.getItem("chestplate"));
            player.getInventory().setLeggings(kit.getItem("leggings"));
            player.getInventory().setBoots(kit.getItem("boots"));
        }
    }

    public Kit getCurrentKit() {
        return currentKit;
    }
}
