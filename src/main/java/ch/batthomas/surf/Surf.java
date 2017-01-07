package ch.batthomas.surf;

import ch.batthomas.surf.listener.BlockEventBlocker;
import ch.batthomas.surf.listener.PlayerEventBlocker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author batthomas
 */
public class Surf extends JavaPlugin {

    @Override
    public void onEnable() {
        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BlockEventBlocker(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEventBlocker(), this);
    }
}
