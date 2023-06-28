package dev.necr.tweaker.modules.misc.commands;

import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import dev.necr.tweaker.api.CommandClass;
import dev.necr.tweaker.utils.Utils;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class BadOmenCommand extends CommandClass {

    @CommandMethod("badomen give")
    @CommandDescription("Gives the player Bad Omen")
    public void giveBadOmenCommand(final @NonNull Player sender) {
        if (!Utils.checkPermission(sender, "badomen.give")) {
            return;
        }

        sender.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, Integer.MAX_VALUE, 0, false, false));
    }

    @CommandMethod("badomen remove")
    @CommandDescription("Removes Bad Omen from the player")
    public void removeBadOmenCommand(final @NonNull Player sender) {
        if (!Utils.checkPermission(sender, "badomen.remove")) {
            return;
        }

        sender.removePotionEffect(PotionEffectType.BAD_OMEN);
    }

}
