package ch.batthomas.surf.util;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author batthomas
 */
public class Kit {

    private String name;
    private final Map<String, ItemStack> items;

    public Kit(String name) {
        this.name = name;
        items = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, ItemStack> getItems() {
        return items;
    }

    public ItemStack getItem(String key) {
        return items.get(key);
    }

    public void addItem(String slot, ItemStack item) {
        items.put(slot, item);
    }

    public void removeItem(String slot) {
        items.remove(slot);
    }
}
