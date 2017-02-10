package ch.batthomas.surf.manager;

import ch.batthomas.surf.Surf;
import ch.batthomas.surf.util.ConfigHelper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class WorldManager {

    private final Map<World, ConfigHelper> worlds;
    private World currentWorld;

    private final Surf plugin;

    public WorldManager(Surf plugin) throws IOException {
        this.plugin = plugin;
        worlds = new HashMap<>();
        searchConfigs();
    }

    private void searchConfigs() throws IOException {
        File root = new File(plugin.getDataFolder() + "/worlds");
        if (!root.exists()) {
            root.mkdirs();
        }
        for (File file : root.listFiles()) {
            if (file.isFile()) {
                if (new File(Bukkit.getWorldContainer() + "/" + file.getName().split("\\.")[0]).exists()) {
                    World world = Bukkit.createWorld(new WorldCreator(file.getName().split("\\.")[0]));
                    if (world != null) {
                        System.err.println("DEBUG - Worldname: " + world.getName());
                        worlds.put(world, new ConfigHelper("worlds/" + world.getName(), plugin));
                    }
                }
            }
        }
    }

    public void nextWorld() {
        if (currentWorld != null) {
            worlds.remove(currentWorld);
        }
        Random random = new Random(System.currentTimeMillis());
        List<World> list = new ArrayList<>(worlds.keySet());
        worlds.remove(currentWorld);
        currentWorld = list.get(list.size() == 1 ? 0 : random.nextInt(list.size() - 1));
    }

    public void teleportPlayer(Player player) {
        player.teleport(new Location(currentWorld, getFromConfig("spawn.x"), getFromConfig("spawn.y"), getFromConfig("spawn.z"), getFromConfig("spawn.yaw"), getFromConfig("spawn.pitch")));
    }

    public void teleportPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(new Location(currentWorld, getFromConfig("spawn.x"), getFromConfig("spawn.y"), getFromConfig("spawn.z"), getFromConfig("spawn.yaw"), getFromConfig("spawn.pitch")));
        }
    }

    public Location getSpawnLocation() {
        return new Location(currentWorld, getFromConfig("spawn.x"), getFromConfig("spawn.y"), getFromConfig("spawn.z"), getFromConfig("spawn.yaw"), getFromConfig("spawn.pitch"));
    }

    public float getFromConfig(String path) {
        ConfigHelper config = worlds.get(currentWorld);
        return Float.parseFloat(config.getConfig().getString(path));
    }
}
