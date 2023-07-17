package dev.necr.tweaker.modules.misc.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();

        if (event.getClickedInventory() != null && clickedItem != null) {
            if (clickedItem.getType() == Material.CRAFTING_TABLE && event.getClick().isRightClick()) {
                event.setCancelled(true);

                event.getWhoClicked().closeInventory();
                event.getWhoClicked().openWorkbench(null, true);
            }
        }
    }
}
