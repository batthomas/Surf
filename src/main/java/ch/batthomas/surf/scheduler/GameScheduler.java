package ch.batthomas.surf.scheduler;

import ch.batthomas.surf.Surf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class GameScheduler implements Runnable {

    private int taskid;
    private int time;
    private final Surf plugin;

    public GameScheduler(Surf plugin) {
        this.plugin = plugin;
        time = 1500;
    }

    public void start() {
        taskid = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 20, 20);
    }

    @Override
    public void run() {
        switch (time) {
            case 1500:
            case 1200:
            case 900:
            case 600:
            case 300:
                //TODO : new Map
                plugin.getKitManager().applyKit(plugin.getKitManager().nextKit());
                break;
            case 60:
            case 10:
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(time != 1 ? plugin.getPrefix() + "Der Server startet in " + time + " Sekunden neu" : plugin.getPrefix() + "Der Server startet" + time + " Sekunde");
                }
                break;
            case 0:
                Bukkit.shutdown();
                break;
        }
        time--;
    }

    public int getTaskId() {
        return taskid;
    }

}
