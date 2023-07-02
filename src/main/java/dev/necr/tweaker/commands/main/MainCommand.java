package dev.necr.tweaker.commands.main;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.specifier.Greedy;
import dev.necr.tweaker.api.CommandClass;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings({"unused"})
public class MainCommand extends CommandClass {

    @CommandMethod("tweaker help|? [query]")
    @CommandDescription("Shows the help page")
    public void helpCommand(final @NonNull CommandSender sender, final @Nullable @Argument(value = "query", description = "The subcommand or the help page") @Greedy String query) {
        plugin.getCommandManager().getMinecraftHelp().queryCommands(query == null ? "" : query, sender);
    }
}
