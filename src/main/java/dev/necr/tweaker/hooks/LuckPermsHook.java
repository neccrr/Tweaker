package dev.necr.tweaker.hooks;

import dev.necr.tweaker.Tweaker;
import dev.necr.tweaker.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class LuckPermsHook {

    private final Tweaker plugin;

    private final LuckPerms luckPerms;

    public String getPrefix(Player player) {
        CachedMetaData playerMetaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        String prefix = playerMetaData.getPrefix();

        return StringUtils.colorize(Objects.requireNonNullElse(prefix, "&7"));
    }

    public String getSuffix(Player player) {
        CachedMetaData playerMetaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        String suffix = playerMetaData.getSuffix();

        return StringUtils.colorize(Objects.requireNonNullElse(suffix, "&7"));
    }

    public String getGroupDisplayName(Player player) {
        CachedMetaData playerMetaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        String playerGroup = (playerMetaData.getPrimaryGroup() != null) ? playerMetaData.getPrimaryGroup() : "default";

        Group group = luckPerms.getGroupManager().getGroup(playerGroup);
        assert group != null;
        String groupDisplayName = group.getFriendlyName();

        return StringUtils.colorize(groupDisplayName);
    }

    public void setCustomPrefix(Player player, String prefix) {
        luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            // Remove all other prefixes the user had before.
            user.data().clear(NodeType.PREFIX::matches);

            // Find the highest priority of their other prefixes
            // We need to do this because they might inherit a prefix from a parent group,
            // and we want the prefix we set to override that!
            Map<Integer, String> inheritedPrefixes = user.getCachedData().getMetaData(QueryOptions.nonContextual()).getPrefixes();
            int priority = inheritedPrefixes.keySet().stream().mapToInt(i -> i + 10).max().orElse(10);

            // Create a node to add to the player.
            Node node = PrefixNode.builder(prefix, priority).build();

            // Add the node to the user.
            user.data().add(node);
        });
    }

    public void clearPrefix(Player player) {
        luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            // Remove all other prefixes the user had before.
            user.data().clear(NodeType.PREFIX::matches);
        });
    }

}
