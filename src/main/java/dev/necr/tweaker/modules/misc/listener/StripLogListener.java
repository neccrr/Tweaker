package dev.necr.tweaker.modules.misc.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class StripLogListener implements Listener {

    @EventHandler
    public void onLogStrip(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Block clickedBlock = event.getClickedBlock();
            ItemStack item = event.getItem();

            if (clickedBlock == null || item == null) {
                return;
            }

            if (clickedBlock.getType().toString().toLowerCase().contains("stripped") && item.getType().toString().toLowerCase().contains("axe")) {
                event.setCancelled(true);

                event.getPlayer().swingMainHand();
                clickedBlock.setType(getUnStrippedLogType(clickedBlock.getType()));
            }

        }
    }

    public Material getUnStrippedLogType(Material strippedLogType) {
        switch (strippedLogType) {
            case STRIPPED_ACACIA_LOG -> {
                return Material.ACACIA_LOG;
            }
            case STRIPPED_BIRCH_LOG -> {
                return Material.BIRCH_LOG;
            }
            case STRIPPED_DARK_OAK_LOG -> {
                return Material.DARK_OAK_LOG;
            }
            case STRIPPED_JUNGLE_LOG -> {
                return Material.JUNGLE_LOG;
            }
            case STRIPPED_OAK_LOG -> {
                return Material.OAK_LOG;
            }
            case STRIPPED_SPRUCE_LOG -> {
                return Material.SPRUCE_LOG;
            }
            case STRIPPED_CHERRY_LOG -> {
                return Material.CHERRY_LOG;
            }
            case STRIPPED_CRIMSON_STEM -> {
                return Material.CRIMSON_STEM;
            }
            case STRIPPED_WARPED_STEM -> {
                return Material.WARPED_STEM;
            }
            default -> {
                return strippedLogType;
            }
        }
    }
}
