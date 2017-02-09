package ch.batthomas.surf.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 *
 * @author batthomas
 */
public class ItemCreator {

    private final ItemStack item;

    public ItemCreator(Material material) {
        item = new ItemStack(material);
    }

    public ItemCreator(Material material, short data) {
        item = new ItemStack(material, 1, data);
    }

    public ItemCreator setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemCreator setName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator setPotionEffect(PotionType type) {
        if (item.getType() == Material.POTION || item.getType() == Material.SPLASH_POTION || item.getType() == Material.LINGERING_POTION) {
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            meta.setBasePotionData(new PotionData(type, false, true));
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemStack toItemStack() {
        return item;
    }
}
