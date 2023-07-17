package dev.necr.tweaker.modules.privatemessage.utils;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.UUID;

/**
 * Utilities for managing reply data
 */
@UtilityClass
public class ReplyManager {
    private final HashMap<UUID, UUID> reply = new HashMap<>();

    /**
     * Add the inputted UUID to the reply data
     *
     * @param playerUUID The player's UUID
     * @param targetUUID The target's UUID
     */
    public void add(UUID playerUUID, UUID targetUUID) {
        reply.put(playerUUID, targetUUID);
    }

    /**
     * Remove the inputted UUID from the reply data
     *
     * @param playerUUID The player's UUID
     */
    public void remove(UUID playerUUID) {
        reply.remove(playerUUID);
    }

    /**
     * Checks if the inputted UUID already exists in the reply data
     *
     * @param playerUUID The player's UUID
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isInReply(UUID playerUUID) {
        return reply.containsKey(playerUUID);
    }

    /**
     * Get the inputted UUID from the reply data
     *
     * @param playerUUID The player's UUID
     */
    public UUID get(UUID playerUUID) {
        return reply.get(playerUUID);
    }

}
