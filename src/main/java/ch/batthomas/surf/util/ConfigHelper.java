package ch.batthomas.surf.util;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.constant.Constant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author batthomas
 */
public class ConfigHelper {

    private final Surf plugin;

    private final String name;
    private YamlConfiguration config;
    private File file;

    public ConfigHelper(String name, Surf plugin) throws IOException {
        this.name = name;
        this.plugin = plugin;
        createConfig();
    }

    public ConfigHelper(String name, Constant constant, Surf plugin) throws IOException {
        this.name = name;
        this.plugin = plugin;
        createConfig(constant);
    }

    private void createConfig() throws IOException {
        file = new File(plugin.getDataFolder().toString(), name + ".yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void createConfig(Constant constant) throws IOException {
        file = new File(plugin.getDataFolder().toString(), name + ".yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        config = YamlConfiguration.loadConfiguration(file);
        if (file.length() == 0) {
            constant.getContent().keySet().forEach((path) -> {
                config.set((String) path, (String) constant.get((String) path));
            });
            config.save(file);
        }
    }

    public void setObject(String path, Object object) throws IOException {
        config.set(path, object);
        config.save(file);
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
