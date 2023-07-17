package dev.necr.tweaker.modules.misc.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CropTrampleListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    }

    @EventHandler
    public void onMobTrample(EntityInteractEvent event) {
        if (event.getEntity() instanceof Player) {
            return;
        }

        // Make mobs not trample crops
        if (event.getBlock().getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }
}