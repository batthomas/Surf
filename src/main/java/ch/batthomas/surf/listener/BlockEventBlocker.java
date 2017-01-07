package ch.batthomas.surf.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;

/**
 *
 * @author batthomas
 */
public class BlockEventBlocker implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBurn(BlockBurnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onCanBuild(BlockCanBuildEvent e) {
        e.setBuildable(false);
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFade(BlockFadeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onForm(BlockFormEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onGrow(BlockGrowEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onIgnite(BlockIgniteEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onMultiPlace(BlockMultiPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onSpread(BlockSpreadEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onCauldronChange(CauldronLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onLeaveDecay(LeavesDecayEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onNoteblock(NotePlayEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        e.setCancelled(true);
    }
}
