package dev.necr.tweaker.utils;

import dev.necr.tweaker.Tweaker;
import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@UtilityClass
@SuppressWarnings({"unused", "UnstableApiUsage"})
public class Utils {

    private final Tweaker plugin = Tweaker.getInstance();

    /**
     * Gets the plugin description
     */
    public String getPluginDescription() {
        return StringUtils.colorizeText("&eThis server is running &bTweaker &b" + plugin.getPluginMeta().getVersion() + " &eby &b" + plugin.getPluginMeta().getAuthors());
    }

    /**
     * see {@link Utils#checkPermission(CommandSender, String, boolean, boolean, String)}
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean checkPermission(CommandSender sender, String permission) {
        return Utils.checkPermission(sender, permission, false, false, null);
    }

    /**
     * see {@link Utils#checkPermission(CommandSender, String, boolean, boolean, String)}
     */
    @SuppressWarnings({"BooleanMethodIsAlwaysInverted"})
    public boolean checkPermission(CommandSender sender, boolean others, String permission) {
        return Utils.checkPermission(sender, permission, others, false, null);
    }

    /**
     * checks if the command target has a certain permission
     *
     * @param target         the target
     * @param permission     the permission
     * @param others         is the target executing this to other player(s)?
     * @param showPermission should the permission deny message show the required permission?
     * @param command        the command, if set to null then the permission deny message will show the executed command.
     * @return see {@link CommandSender#hasPermission(String)}
     */
    public boolean checkPermission(CommandSender target, String permission, boolean others, boolean showPermission, @Nullable String command) {
        permission = "tweaker." + permission.toLowerCase();
        boolean silent = false;

        if (others) {
            permission += ".others";
        }

        if (target.hasPermission(permission)) {
            return true;
        }

        if (!silent) {
            if (showPermission) {
                if (command != null) {
                    target.sendMessage(StringUtils.colorizeText("&cYou don't have the required permission &l" + permission + " to do &l" + command + "&c!"));
                } else {
                    target.sendMessage(StringUtils.colorizeText("&cYou don't have the required permission &l" + permission + " to do that!"));
                }
            } else {
                if (command != null) {
                    target.sendMessage(StringUtils.colorizeText("&cYou don't have the required permission to do &l" + command + "&c!"));
                } else {
                    target.sendMessage(StringUtils.colorizeText("&cYou don't have the required permission to do that!"));
                }
            }
        } else {
            target.sendMessage(StringUtils.colorizeText("Unknown command! Type \"/help\" for help!"));
        }

        plugin.getLogger().info(StringUtils.colorizeText("Player &l" + target.getName() + " &rattempted to run something but lacks &l" + permission + " &rpermission."));
        for (final Player currentPlayer : plugin.getServer().getOnlinePlayers()) {
            if (currentPlayer.hasPermission("bungeecore.operator")) {
                currentPlayer.sendMessage(StringUtils.colorizeText("&bPlayer &e&l" + target.getName() + " &battempted to run something but lacks &l&e" + permission + " &bpermission."));
            }
        }

        return false;
    }
}