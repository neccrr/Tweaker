package dev.necr.tweaker.modules.misc.commands;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import dev.necr.tweaker.api.CommandClass;
import dev.necr.tweaker.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class HeadCommand extends CommandClass {

    @CommandMethod("head|kepala [player]")
    @CommandDescription("Get a player's head")
    public void head(final @NotNull Player sender, final @Nullable @Argument(value = "player", description = "The prefix to set") Player target) {
        ItemStack skull;
        skull = getSkull(Objects.requireNonNullElse(target, sender));
        placeSkull(sender, skull);
    }

    private ItemStack getSkull(Player player) {
        // Code to get the player's skull, e.g., by using a SkullMeta with the player's name.
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skull.setItemMeta(skullMeta);

        return skull;
    }

    private void placeSkull(Player player, ItemStack skull) {
        PlayerInventory inventory = player.getInventory();
        // Check if the main hand slot is empty.
        if (inventory.getItemInMainHand().getType() == Material.AIR) {
            inventory.setItemInMainHand(skull);
            return;
        }

        // Check if the hotbar has an empty slot.
        for (int i = 0; i <= 8; i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                inventory.setItem(i, skull);
                return;
            }
        }

        // Check if the inventory has an empty slot.
        for (int i = 9; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                inventory.setItem(i, skull);
                return;
            }
        }

        // If no space is available, send a warning to the player.
        player.sendMessage(StringUtils.colorize("&cInventory kamu penuh! Tolong bersihkan inventorymu sebelum mengeksekusi command itu lagi."));
    }
}
