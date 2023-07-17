package dev.necr.tweaker.modules.playerprefix.commands;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.ProxiedBy;
import cloud.commandframework.annotations.specifier.Greedy;
import dev.necr.tweaker.api.CommandClass;
import dev.necr.tweaker.utils.StringUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetPrefixCommand extends CommandClass {

    @ProxiedBy("reng")
    @CommandMethod("playerprefix setprefix [prefix]")
    @CommandDescription("Sets the player's prefix")
    public void setPrefix(final @NotNull Player sender, final @Nullable @Argument(value = "prefix", description = "The prefix to set") @Greedy String prefix) {
        if (prefix == null) {
            plugin.getLuckPerms().clearPrefix(sender);

            sender.sendMessage(StringUtils.colorize("&aYour prefix has been cleared."));
            return;
        }

        if (prefix.replaceAll("(?i)&[0-9A-FK-OR]", "").length() > 15) {
            sender.sendMessage(StringUtils.colorize("&cYour prefix cannot be longer than 15 characters."));
            return;
        }

        plugin.getLuckPerms().setCustomPrefix(sender, prefix + " ");

        sender.sendMessage(StringUtils.colorize("&aYour prefix has been set to &f" + prefix + "&a."));
    }
}
