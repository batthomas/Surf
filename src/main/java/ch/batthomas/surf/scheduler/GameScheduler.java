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
        if (time % 300 == 0) {
            plugin.getKitManager().applyKit(plugin.getKitManager().nextKit());
            Bukkit.broadcastMessage(plugin.getPrefix() + "Das neue Kit heisst " + plugin.getKitManager().getCurrentKit().getName());
        } else if (time / 100 % 3 == 0) {
            String end;
            if (String.valueOf(time).startsWith("1")) {
                end = String.valueOf(time).substring(2);
            } else {
                end = String.valueOf(time).substring(1);
            }
            switch (end) {
                case "10":
                    Bukkit.broadcastMessage(plugin.getPrefix() + "Das Kit wechselt in " + end + " Sekunden");
                    break;
                case "05":
                case "04":
                case "03":
                case "02":
                case "01":

                    Bukkit.broadcastMessage(plugin.getPrefix() + (Integer.parseInt(end) != 01 ? "Das Kit wechselt in " + end.substring(1) + " Sekunden" : "Das Kit wechselt in " + end.substring(1) + " Sekunde"));
                    break;
            }
        } else if (time < 10) {
            Bukkit.broadcastMessage(time != 1 ? plugin.getPrefix() + "Der Server startet in " + time + " Sekunden neu" : plugin.getPrefix() + "Der Server startet" + time + " Sekunde");
        } else if (time == 0) {
            Bukkit.shutdown();
        }
        time--;
    }

    public int getTaskId() {
        return taskid;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void nextRound() {
        if (Math.floor(time / 100) % 3 == 0) {
            time = ((int) time / 100) * 100 + 10;
        } else {
            time = time - 100;
            nextRound();
        }
    }
}
