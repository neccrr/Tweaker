package dev.necr.tweaker.commands.main;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.specifier.Greedy;
import dev.necr.tweaker.api.CommandClass;
import dev.necr.tweaker.utils.Utils;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MainCommand extends CommandClass {

    @CommandMethod("tweaker help|? [query]")
    @CommandDescription("Gives the player Bad Omen")
    public void helpCommand(final @NonNull CommandSender sender, final @Nullable @Argument(value = "query", description = "The subcommand or the help page") @Greedy String query) {
        plugin.getCommandManager().getMinecraftHelp().queryCommands(query == null ? "" : query, sender);
    }
}
