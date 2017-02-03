package ch.batthomas.surf.manager;

import ch.batthomas.surf.Surf;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author batthomas
 */
public class KillstreakManager {

    private final Map<Player, Integer> kills;
    private final Surf plugin;

    public KillstreakManager(Surf plugin) {
        kills = new HashMap<>();
        this.plugin = plugin;
    }

    public void addKill(Player player) {
        kills.put(player, kills.get(player) != null ? kills.get(player) + 1 : 1);
        handleKills(player);
    }

    public void removePlayer(Player player) {
        kills.remove(player);
    }

    private void handleKills(Player player) {
        switch (kills.get(player)) {
            case 5:
                Bukkit.broadcastMessage(plugin.getPrefix() + player.getName() + " hat eine 5er Killstreak erreicht");
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 25 * 20, 3));
                break;
            case 10:
                Bukkit.broadcastMessage(plugin.getPrefix() + player.getName() + " hat eine 10er Killstreak erreicht");
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 25 * 20, 3));
                break;
            case 15:
                Bukkit.broadcastMessage(plugin.getPrefix() + player.getName() + " hat eine 15er Killstreak erreicht");
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 25 * 20, 3));
                break;
            case 20:
                Bukkit.broadcastMessage(plugin.getPrefix() + player.getName() + " hat eine 20er Killstreak erreicht");
                for (Player other : Bukkit.getOnlinePlayers()) {
                    if (!player.equals(other)) {
                        other.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 25 * 20, 1));
                    }
                }
                break;
            case 25:
                Bukkit.broadcastMessage(plugin.getPrefix() + player.getName() + " hat eine 25er Killstreak erreicht");
                for (Player other : Bukkit.getOnlinePlayers()) {
                    if (!player.equals(other)) {
                        other.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 25 * 20, 3));
                        other.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 25 * 20, 3));
                    }
                }
                break;
            case 30:
                Bukkit.broadcastMessage(plugin.getPrefix() + player.getName() + " hat eine 30er Killstreak erreicht");
                for (Player other : Bukkit.getOnlinePlayers()) {
                    if (!player.equals(other)) {
                        other.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 25 * 20, 1));
                    }
                }
        }
    }
}
