package dev.necr.tweaker.modules.privatemessage.commands;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.specifier.Greedy;
import dev.necr.tweaker.api.CommandClass;
import dev.necr.tweaker.modules.privatemessage.utils.ReplyManager;
import dev.necr.tweaker.utils.StringUtils;
import dev.necr.tweaker.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@SuppressWarnings({"unused"})
public class MessageCommand extends CommandClass {

    @CommandMethod("message|msg|m|whisper|w|tell <target> <message>")
    @CommandDescription("Sends private message to another player")
    public void messageCommand(final @NotNull Player sender, final @NotNull @Argument(value = "target", description = "The target player", suggestions = "players") String targetName, final @NotNull @Argument(value = "message", description = "The message to send") @Greedy String message) {
        Player target = Bukkit.getPlayer(targetName);

        if (!Utils.checkPermission(sender, "privatemessage.message")) {
            return;
        }

        if (sender.getName().equalsIgnoreCase(targetName)) {
            sender.sendMessage(StringUtils.colorize("&cYou cannot send a private message to yourself."));
            return;
        }

        if (target == null) {
            sender.sendMessage(StringUtils.colorize("&cPlayer " + targetName + " not found."));
            return;
        }

        sender.sendMessage(StringUtils.colorize("&b&lTo &r&7" + plugin.getLuckPerms().getPrefix(target) + target.getName() + "&r: " + message));

        target.sendMessage(StringUtils.colorize("&b&lFrom &r&7" + plugin.getLuckPerms().getPrefix(sender) + sender.getName() + "&r: " + message));

        this.reply(sender.getUniqueId(), target.getUniqueId());
    }

    /**
     * Checks if the player/target already exists in the reply data
     * <p>
     * <p>
     * If the player/target already exists in the reply data then
     * it will renew the reply data by removing them from the reply data
     * and add them again together
     * <p>
     * <p>
     * If the player/target is not exists in the reply data then
     * it will add them to the reply data together
     *
     * @param playerUUID The player's UUID
     * @param targetUUID The target's UUID
     */
    private void reply(UUID playerUUID, UUID targetUUID) {
        // Checks if the player is in reply data
        if (!ReplyManager.isInReply(playerUUID)) {
            ReplyManager.add(playerUUID, targetUUID);
        } else {
            ReplyManager.remove(playerUUID);
            ReplyManager.add(playerUUID, targetUUID);
        }

        // Checks if the target is in reply data
        if (!ReplyManager.isInReply(targetUUID)) {
            ReplyManager.add(targetUUID, playerUUID);
        } else {
            ReplyManager.remove(targetUUID);
            ReplyManager.add(targetUUID, playerUUID);
        }
    }
}
