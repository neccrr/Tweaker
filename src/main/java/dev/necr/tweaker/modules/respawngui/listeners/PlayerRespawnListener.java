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
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
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

    private final String title = "You Died!";

    private final ItemStack respawnItem = new ItemStack(Material.SUGAR_CANE, 1);
    private final ItemStack backItem = new ItemStack(Material.ARROW, 1);
    private final ItemStack warpBaseItem = new ItemStack(Material.CHERRY_PLANKS, 1);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        // this.plugin.getLogger().info("Player " + event.getPlayer().getName() + " has respawned!");

        // Create the inventory
        Inventory inventory = Bukkit.createInventory(null, 27, title);
        // this.plugin.getLogger().info("Created inventory for " + player.getName() + "!");

        // Add items to the inventory
        ItemMeta respawnItemMeta = respawnItem.getItemMeta();
        respawnItemMeta.setDisplayName(StringUtils.colorizeText("&aRespawn"));
        respawnItemMeta.setLore(Collections.singletonList(StringUtils.colorizeText("&7Klik untuk respawn!")));
        respawnItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);

        respawnItem.setItemMeta(respawnItemMeta);

        ItemMeta backItemMeta = backItem.getItemMeta();
        backItemMeta.setDisplayName(StringUtils.colorizeText("&aRespawn dengan \"/back!\""));
        backItemMeta.setLore(Collections.singletonList(StringUtils.colorizeText("&7Klik untuk respawn dengan \"/back\"!")));
        backItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);

        backItem.setItemMeta(backItemMeta);

        ItemMeta warpBaseItemMeta = warpBaseItem.getItemMeta();
        warpBaseItemMeta.setDisplayName(StringUtils.colorizeText("&aRespawn dengan \"/warp base!\""));
        warpBaseItemMeta.setLore(Collections.singletonList(StringUtils.colorizeText("&7Klik untuk respawn dengan \"/warp base\"!")));
        warpBaseItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);

        warpBaseItem.setItemMeta(warpBaseItemMeta);

        inventory.setItem(13, respawnItem);
        inventory.setItem(15, backItem);
        inventory.setItem(11, warpBaseItem);

        // this.plugin.getLogger().info("Added item to inventory for " + player.getName() + "!");


        // Schedule a delayed task using Bukkit Scheduler
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

            // Open the inventory for the player
            player.openInventory(inventory);
            // this.plugin.getLogger().info("Opened inventory for " + player.getName() + "!");

        }, 1L / 2L); // 20 ticks = 1-second delay
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        // Check if the inventory is the same inventory created before
        if (event.getClickedInventory() != null && event.getView().getTitle().equals(title)) {
            // this.plugin.getLogger().info("Player " + player.getName() + " clicked in the inventory!");

            // Check if the clicked item is not null
            if (clickedItem != null) {
                // this.plugin.getLogger().info("Player " + player.getName() + " clicked on an item!");
                // Make the item unmovable
                event.setCancelled(true);

                // Check if the clicked item is the respawn item
                if (clickedItem.getType() == respawnItem.getType()) {
                    // this.plugin.getLogger().info("Player " + player.getName() + " clicked on the respawn item!");

                    // Close the inventory
                    player.closeInventory();
                    // this.plugin.getLogger().info("Closed inventory for " + player.getName() + "!");

                    // Send "RESPAWNED" title to the player
                    player.sendTitle(StringUtils.colorizeText("&aRESPAWNED!"), StringUtils.colorizeText("&eKamu telah respawn!"), 10, 70, 20);

                    return;
                }

                // Check if the clicked item is the back item
                if (clickedItem.getType() == backItem.getType()) {
                    // this.plugin.getLogger().info("Player " + player.getName() + " clicked on the back item!");

                    // Respawn the player with /back
                    player.performCommand("back");
                    // this.plugin.getLogger().info("Player " + player.getName() + " respawned with /back!");

                    // Close the inventory
                    player.closeInventory();
                    // this.plugin.getLogger().info("Closed inventory for " + player.getName() + "!");

                    // Send "RESPAWNED" title to the player
                    player.sendTitle(StringUtils.colorizeText("&aRESPAWNED!"), StringUtils.colorizeText("&eKamu telah respawn menggunakan \"/back\"!"), 10, 70, 20);

                    return;
                }

                // Check if the clicked item is the warp base item
                if (clickedItem.getType() == warpBaseItem.getType()) {
                    // this.plugin.getLogger().info("Player " + player.getName() + " clicked on the warp base item!");

                    // Respawn the player with /warp base
                    player.performCommand("warp base");
                    // this.plugin.getLogger().info("Player " + player.getName() + " respawned with /warp base!");

                    // Close the inventory
                    player.closeInventory();
                    // this.plugin.getLogger().info("Closed inventory for " + player.getName() + "!");

                    // Send "RESPAWNED" title to the player
                    player.sendTitle(StringUtils.colorizeText("&aRESPAWNED!"), StringUtils.colorizeText("&eKamu telah respawn menggunakan \"/warp base\"!"), 10, 70, 20);

                    return;
                }

                // check if player closed the created inventory
                if (event.getAction() == InventoryAction.NOTHING) {
                    // this.plugin.getLogger().info("Player " + player.getName() + " closed the inventory!");

                    // Send "RESPAWNED" title to the player
                    player.sendTitle(StringUtils.colorizeText("&aRESPAWNED!"), StringUtils.colorizeText("&eKamu telah respawn!"), 10, 70, 20);
                }
            }
        }
    }

}