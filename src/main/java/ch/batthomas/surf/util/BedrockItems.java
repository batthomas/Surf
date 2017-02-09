package ch.batthomas.surf.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

/**
 *
 * @author batthomas
 */
public class BedrockItems {

    private final List<ItemStack> items;

    public BedrockItems() {
        items = new ArrayList<>();
        initArray();
    }

    public ItemStack getRandomItem() {
        Random random = new Random(System.currentTimeMillis());
        return items.get(items.size() < 2 ? 0 : random.nextInt(items.size()));
    }

    private void initArray() {
        items.add(new ItemCreator(Material.EGG).setName("Grenade").setAmount(1).toItemStack());
        items.add(new ItemCreator(Material.GOLDEN_APPLE).setName("Golden Apple").setAmount(1).toItemStack());
        items.add(new ItemCreator(Material.GOLDEN_APPLE, (short) 1).setName("Enchanted Apple").setAmount(1).toItemStack());
        items.add(new ItemCreator(Material.POTION).setName("Strength").setAmount(1).setPotionEffect(PotionType.STRENGTH).toItemStack());
        items.add(new ItemCreator(Material.POTION).setName("Swiftness").setAmount(1).setPotionEffect(PotionType.SPEED).toItemStack());
    }

}
