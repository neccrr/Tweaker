package dev.necr.tweaker.modules.respawngui.listeners;

import dev.necr.tweaker.Tweaker;
import dev.necr.tweaker.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

@RequiredArgsConstructor
@SuppressWarnings({"deprecation"})
public class PlayerRespawnListener implements Listener {

    private final Tweaker plugin;
    private final Inventory inventory = Bukkit.createInventory(null, 27, "You Died!");

    private final ItemStack respawnItem = new ItemStack(Material.SUGAR_CANE, 1);
    private final ItemStack backItem = new ItemStack(Material.ARROW, 1);
    private final ItemStack warpBaseItem = new ItemStack(Material.CHERRY_PLANKS, 1);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        // Add items to the inventory
        ItemMeta respawnItemMeta = respawnItem.getItemMeta();
        respawnItemMeta.setDisplayName(StringUtils.colorize("&aRespawn"));
        respawnItemMeta.setLore(Collections.singletonList(StringUtils.colorize("&7Klik untuk respawn!")));
        respawnItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);

        respawnItem.setItemMeta(respawnItemMeta);

        ItemMeta backItemMeta = backItem.getItemMeta();
        backItemMeta.setDisplayName(StringUtils.colorize("&aRespawn dengan \"/back!\""));
        backItemMeta.setLore(Collections.singletonList(StringUtils.colorize("&7Klik untuk respawn dengan \"/back\"!")));
        backItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);

        backItem.setItemMeta(backItemMeta);

        ItemMeta warpBaseItemMeta = warpBaseItem.getItemMeta();
        warpBaseItemMeta.setDisplayName(StringUtils.colorize("&aRespawn dengan \"/warp base!\""));
        warpBaseItemMeta.setLore(Collections.singletonList(StringUtils.colorize("&7Klik untuk respawn dengan \"/warp base\"!")));
        warpBaseItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);

        warpBaseItem.setItemMeta(warpBaseItemMeta);

        inventory.setItem(13, respawnItem);
        inventory.setItem(15, backItem);
        inventory.setItem(11, warpBaseItem);



        // Schedule a delayed task using Bukkit Scheduler
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

            // Open the inventory for the player
            player.openInventory(inventory);

        }, 1L / 2L); // 20 ticks = 1-second delay
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        // Check if the inventory is the same inventory created before
        if (event.getClickedInventory() != null && event.getClickedInventory().equals(inventory)) {

            // Check if the clicked item is not null
            if (clickedItem != null) {
                // Make the item unmovable
                event.setCancelled(true);

                // Check if the clicked item is the respawn item
                if (clickedItem.getType() == respawnItem.getType()) {

                    // Close the inventory
                    player.closeInventory();

                    // Send "RESPAWNED" title to the player
                    player.sendTitle(StringUtils.colorize("&aRESPAWNED!"), StringUtils.colorize("&eKamu telah respawn!"), 10, 70, 20);

                    return;
                }

                // Check if the clicked item is the back item
                if (clickedItem.getType() == backItem.getType()) {

                    // Respawn the player with /back
                    player.performCommand("back");

                    // Close the inventory
                    player.closeInventory();

                    // Send "RESPAWNED" title to the player
                    player.sendTitle(StringUtils.colorize("&aRESPAWNED!"), StringUtils.colorize("&eKamu telah respawn menggunakan \"/back\"!"), 10, 70, 20);

                    return;
                }

                // Check if the clicked item is the warp base item
                if (clickedItem.getType() == warpBaseItem.getType()) {

                    // Respawn the player with /warp base
                    player.performCommand("warp base");

                    // Close the inventory
                    player.closeInventory();

                    // Send "RESPAWNED" title to the player
                    player.sendTitle(StringUtils.colorize("&aRESPAWNED!"), StringUtils.colorize("&eKamu telah respawn menggunakan \"/warp base\"!"), 10, 70, 20);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        // Check if the inventory is the same inventory created before
        if (event.getInventory().equals(inventory)) {
            // Send "RESPAWNED" title to the player
            player.sendTitle(StringUtils.colorize("&aRESPAWNED!"), StringUtils.colorize("&eKamu telah respawn!"), 10, 70, 20);
        }
    }

}