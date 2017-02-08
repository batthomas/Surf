package ch.batthomas.surf.level;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class LevelBenefits {

    private final LevelManager levelmanager;
    private final Map<Integer, Integer> cooldown;

    public LevelBenefits(LevelManager levelmanager) {
        this.levelmanager = levelmanager;
        cooldown = new HashMap<>();
        initArrays();
    }

    public int getCooldown(Player player) throws SQLException {
        int level = levelmanager.getLevel(player);
        int levelrange = level < 4 ? 0 : (int) Math.floor(level / 5) * 5 - 1;
        return cooldown.get(levelrange);
    }

    private void initArrays() {
        int level = 4;
        int seconds = 60;
        cooldown.put(0, seconds);
        for (int amount = 1; amount <= 20; amount++) {
            if (amount <= 5) {
                seconds -= 5;
            } else if (amount <= 10) {
                seconds -= 3;
            } else if (amount <= 15) {
                seconds -= 2;
            } else if (amount <= 20) {
                seconds -= 1;
            }
            cooldown.put(level, seconds);
            level += 5;
        }
    }
}
