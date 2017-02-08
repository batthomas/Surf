package ch.batthomas.surf.util;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 *
 * @author batthomas
 */
public class Cooldown {

    private final Map<Player, Long> players;

    public Cooldown() {
        players = new HashMap<>();
    }

    public void addPlayer(Player player, int seconds) {
        players.put(player, System.currentTimeMillis() / 1000 + seconds);
    }

    public long getCooldown(Player player) {
        if (players.containsKey(player)) {
            if (players.get(player) > System.currentTimeMillis() / 1000) {
                return players.get(player) - System.currentTimeMillis() / 1000;
            }
            players.remove(player);
            return 0;
        }
        return 0;
    }
}
