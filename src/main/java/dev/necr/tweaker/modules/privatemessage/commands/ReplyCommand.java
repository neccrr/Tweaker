package dev.necr.tweaker.modules.privatemessage.commands;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.specifier.Greedy;
import dev.necr.tweaker.api.CommandClass;
import dev.necr.tweaker.modules.privatemessage.utils.ReplyManager;
import dev.necr.tweaker.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ReplyCommand extends CommandClass {

    @CommandMethod("reply|r <message>")
    @CommandDescription("Replies to the last private message")
    public void replyCommand(final @NotNull Player sender, final @NotNull @Argument(value = "message", description = "The message to send") @Greedy String message) {
        Player target = Bukkit.getPlayer(ReplyManager.get(sender.getUniqueId()));

        if (!ReplyManager.isInReply(sender.getUniqueId()) || target == null) {
            sender.sendMessage(StringUtils.colorize("&cYou have no one to reply to."));
            return;
        }

        sender.sendMessage(StringUtils.colorize("&b&lTo &r&7" + plugin.getLuckPerms().getPrefix(target) + target.getName() + "&r: " + message));

        target.sendMessage(StringUtils.colorize("&b&lFrom &r&7" + plugin.getLuckPerms().getPrefix(sender) + sender.getName() + "&r: " + message));
    }
}