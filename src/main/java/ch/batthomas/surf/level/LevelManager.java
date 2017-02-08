package ch.batthomas.surf.level;

import ch.batthomas.surf.Surf;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class LevelManager {

    private final Surf plugin;
    private final Map<UUID, Integer> levels;
    private LevelBenefits benefits;

    public LevelManager(Surf plugin) {
        this.plugin = plugin;
        levels = new HashMap<>();
        benefits = new LevelBenefits(this);
    }

    public void calculateLevel(UUID uuid) throws SQLException {
        int kills = Integer.parseInt(plugin.getStatsQuery().getStats(uuid, "kills"));
        if (kills != 0) {
            int currentAmount = 0;
            for (int i = 1; i < 101; i++) {
                currentAmount += (int) Math.pow(i, 2);
                if (currentAmount > kills) {
                    if (levels.get(uuid) != null) {

                        levels.put(uuid, i - 1);
                        break;
                    }
                    levels.put(uuid, i - 1);
                    break;
                }
            }
        } else {
            levels.put(uuid, 1);
        }
    }

    public void calculateLevel(Player player) throws SQLException {
        int kills = Integer.parseInt(plugin.getStatsQuery().getStats(player.getUniqueId(), "kills"));
        if (kills != 0) {
            int currentAmount = 0;
            for (int i = 1; i < 101; i++) {
                currentAmount += (int) Math.pow(i, 2);
                if (currentAmount > kills) {
                    if (levels.get(player.getUniqueId()) != null) {
                        int oldlevel = levels.get(player.getUniqueId());
                        levels.put(player.getUniqueId(), i - 1);
                        if (oldlevel != levels.get(player.getUniqueId())) {
                            player.sendMessage(plugin.getPrefix() + "Du hast ein neues Level erreicht");
                        }
                        break;
                    }
                    levels.put(player.getUniqueId(), i - 1);
                    break;
                }
            }
        } else {
            levels.put(player.getUniqueId(), 1);
        }
    }

    public int getDistanceToLevel(UUID uuid) throws SQLException {
        int kills = Integer.parseInt(plugin.getStatsQuery().getStats(uuid, "kills"));
        int currentAmount = 0;
        for (int i = 1; i < 101; i++) {
            currentAmount += (int) Math.pow(i, 2);
            if (currentAmount > kills) {
                return currentAmount - kills;
            }
        }
        return 0;
    }

    public double getPercentToLevel(UUID uuid) throws SQLException {
        double kills = Integer.parseInt(plugin.getStatsQuery().getStats(uuid, "kills"));
        double currentAmount = 0;
        for (int i = 1; i < 101; i++) {
            currentAmount += (int) Math.pow(i, 2);
            if (currentAmount > kills) {
                return Double.parseDouble(new DecimalFormat("##.##").format(kills / currentAmount * 100));
            }
        }
        return 0;
    }

    public int getLevel(Player player) throws SQLException {
        if (levels.get(player.getUniqueId()) == null || levels.get(player.getUniqueId()) == 0) {
            calculateLevel(player);
        }
        return levels.get(player.getUniqueId());
    }

    public int getLevel(UUID uuid) throws SQLException {
        if (levels.get(uuid) == null || levels.get(uuid) == 0) {
            calculateLevel(uuid);
        }
        return levels.get(uuid);
    }

    public LevelBenefits getBenefits() {
        return benefits;
    }
}
