package ch.batthomas.surf.util;

import ch.batthomas.surf.Surf;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class LevelCalculator {

    private final Surf plugin;
    private final Map<Player, Integer> levels;

    public LevelCalculator(Surf plugin) {
        this.plugin = plugin;
        levels = new HashMap<>();
    }

    public void calculateLevel(Player player) throws SQLException {
        int kills = Integer.parseInt(plugin.getStatsQuery().getStats(player.getUniqueId(), "kills"));
        if (kills != 0) {
            int currentAmount = 0;
            for (int i = 1; i < 101; i++) {
                currentAmount += (int) Math.pow(i, 2);
                if (currentAmount > kills) {
                    if (levels.get(player) != null) {
                        int oldlevel = levels.get(player);
                        levels.put(player, i - 1);
                        System.err.println(levels.get(player) + " " + oldlevel);
                        if (oldlevel != levels.get(player)) {
                            player.sendMessage(plugin.getPrefix() + "Du hast ein neues Level erreicht");
                        }
                        break;
                    }
                    levels.put(player, i - 1);
                    break;
                }
            }
        } else {
            levels.put(player, 1);
        }
    }

    public int getDistanceToLevel(Player player) throws SQLException {
        int kills = Integer.parseInt(plugin.getStatsQuery().getStats(player.getUniqueId(), "kills"));
        int currentAmount = 0;
        for (int i = 1; i < 101; i++) {
            currentAmount += (int) Math.pow(i, 2);
            if (currentAmount > kills) {
                return currentAmount - kills;
            }
        }
        return 0;
    }

    public int getLevel(Player player) throws SQLException {
        if (levels.get(player) == null || levels.get(player) == 0) {
            calculateLevel(player);
        }
        return levels.get(player);

    }
}
