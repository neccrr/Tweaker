package dev.necr.tweaker.modules.misc.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupListener implements Listener {

    @EventHandler
    public void onPlayerPickup(PlayerAttemptPickupItemEvent event) {
        event.getPlayer().sendActionBar(event.getItem().getName() + event.getItem().getItemStack().getAmount());
    }
}
