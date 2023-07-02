package dev.necr.tweaker.modules.hologram.commands;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.suggestions.Suggestions;
import cloud.commandframework.context.CommandContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.necr.tweaker.api.CommandClass;
import dev.necr.tweaker.modules.hologram.data.PlayerData;
import dev.necr.tweaker.utils.StringUtils;
import dev.necr.tweaker.utils.Utils;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings({"unused"})
public class HologramCommand extends CommandClass {

    private static final String STORAGE_FILE = "hologram_data.json";
    private Map<String, PlayerData> hologramData;  // Map to store hologram name and player data

    public HologramCommand() {
        loadHologramData();  // Load existing hologram data from storage
    }

    @CommandMethod("createhologram <name> <message>")
    @CommandDescription("Creates a hologram")
    public void createHologram(final @NonNull Player sender, final @NonNull @Argument(value = "name", description = "The hologram name") String name, final @NonNull @Argument(value = "message", description = "The text") @Greedy String message) {
        if (!Utils.checkPermission(sender, "hologram.create")) {
            return;
        }

        PlayerData playerData = hologramData.getOrDefault(sender.getUniqueId().toString(), new PlayerData(sender.getName(), sender.getUniqueId().toString(), new ArrayList<>()));
        List<String> holograms = playerData.getHolograms();

        // Check if the player has reached the hologram limit
        if (holograms.size() >= 2) {
            sender.sendMessage(StringUtils.colorize("&cYou have reached the maximum limit of holograms (2)."));
            return;
        }

        // Check if the hologram name already exists
        if (holograms.contains(name)) {
            sender.sendMessage(StringUtils.colorize("&cA hologram with that name already exists."));
            return;
        }

        holograms.add(name);
        playerData.setHolograms(holograms);
        hologramData.put(sender.getUniqueId().toString(), playerData);  // Store the hologram name and player data

        saveHologramData();  // Save the updated hologram data to storage

        DHAPI.createHologram(name, sender.getEyeLocation(), true, Collections.singletonList(message));  // Create the hologram

        sender.sendMessage(StringUtils.colorize("&aYou created a hologram with the message: " + message));
    }

    @CommandMethod("deletehologram <name>")
    @CommandDescription("Deletes a hologram")
    public void deleteHologram(final @NonNull Player sender, final @NonNull @Argument(value = "name", description = "The hologram name", suggestions = "ownedHologramList") String name) {
        if (!Utils.checkPermission(sender, "hologram.delete")) {
            return;
        }

        PlayerData playerData = hologramData.get(sender.getUniqueId().toString());
        if (playerData != null) {
            List<String> holograms = playerData.getHolograms();
            if (holograms.contains(name)) {
                holograms.remove(name);
                playerData.setHolograms(holograms);
                hologramData.put(sender.getUniqueId().toString(), playerData);  // Store the updated player data
                saveHologramData();  // Save the updated hologram data to storage

                DHAPI.removeHologram(name);

                sender.sendMessage(StringUtils.colorize(StringUtils.colorize("&aYou deleted the hologram: " + name)));
            } else {
                sender.sendMessage(StringUtils.colorize(StringUtils.colorize("&cYou don't own a hologram with that name.")));
            }
        } else {
            sender.sendMessage(StringUtils.colorize("You don't own any holograms."));

        }

    }

    @CommandMethod("listHologram [target]")
    @CommandDescription("Lists all holograms owned by a player")
    public void listHologram(final @NonNull Player sender, final @NonNull @Argument(value = "target", description = "The target player", defaultValue = "self", suggestions = "players") String targetName) {
        if (!Utils.checkPermission(sender, "hologram.list")) {
            return;
        }

        if (sender.hasPermission("tweaker.hologram.list.others")) {
            TargetsCallback targets = this.getTargets(sender, targetName);
            if (targets.notifyIfEmpty()) {
                sender.sendMessage(StringUtils.colorize("&cNo targets found!"));
                return;
            }
            if (targets.size() > 1) {
                sender.sendMessage(StringUtils.colorize("&cYou can only check one player at a time!"));
                return;
            }

            targets.stream().findFirst().ifPresent(target -> {
                PlayerData playerData = hologramData.get(target.getUniqueId().toString());
                if (playerData != null) {
                    List<String> holograms = playerData.getHolograms();
                    if (holograms.isEmpty()) {
                        sender.sendMessage(StringUtils.colorize("&cThat player doesn't own any holograms."));
                        return;
                    }

                    sender.sendMessage(StringUtils.colorize("&aHolograms owned by " + target.getName() + ":"));
                    holograms.forEach(hologram -> sender.sendMessage(StringUtils.colorize("&a- " + hologram)));
                } else {
                    sender.sendMessage(StringUtils.colorize("&cThat player doesn't own any holograms."));
                }
            });

            return;
        }

        PlayerData playerData = hologramData.get(sender.getUniqueId().toString());
        if (playerData != null) {
            List<String> holograms = playerData.getHolograms();
            if (holograms.isEmpty()) {
                sender.sendMessage(StringUtils.colorize("&cYou don't own any holograms."));
                return;
            }

            sender.sendMessage(StringUtils.colorize("&aHolograms owned by " + sender.getName() + ":"));
            holograms.forEach(hologram -> sender.sendMessage(StringUtils.colorize("&a- " + hologram)));
        } else {
            sender.sendMessage(StringUtils.colorize("&cYou don't own any holograms."));
        }
    }

    @CommandMethod("edithologram <name> <message>")
    @CommandDescription("Edits a hologram")
    public void editHologram(final @NonNull Player sender, final @NonNull @Argument(value = "name", description = "The hologram name", suggestions = "ownedHologramList") String name, final @NonNull @Argument(value = "message", description = "The new message") String message) {
        if (!Utils.checkPermission(sender, "hologram.edit")) {
            return;
        }

        PlayerData playerData = hologramData.get(sender.getUniqueId().toString());
        if (playerData != null) {
            List<String> holograms = playerData.getHolograms();
            if (holograms.contains(name)) {
                Location holoLocation = Objects.requireNonNull(DHAPI.getHologram(name)).getLocation();

                DHAPI.removeHologram(name);
                DHAPI.createHologram(name, holoLocation, true, Collections.singletonList(message));

                sender.sendMessage(StringUtils.colorize("&aYou edited the hologram: " + name));
            } else {
                sender.sendMessage(StringUtils.colorize("&cYou don't own a hologram with that name."));
            }
        } else {
            sender.sendMessage(StringUtils.colorize("You don't own any holograms."));
        }
    }

    private void loadHologramData() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(STORAGE_FILE)));
            Gson gson = new Gson();
            Type hologramDataType = new TypeToken<HashMap<String, PlayerData>>() {}.getType();
            hologramData = gson.fromJson(json, hologramDataType);
        } catch (IOException e) {
            hologramData = new HashMap<>();
        }
    }

    private void saveHologramData() {
        try (FileWriter fileWriter = new FileWriter(STORAGE_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(hologramData);
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Suggestions("ownedHologramList")
    public List<String> ownedHologramList(CommandContext<Player> context, String current) {
        PlayerData playerData = hologramData.get(context.getSender().getUniqueId().toString());
        if (playerData != null) {
            return playerData.getHolograms().stream().filter(hologram -> hologram.startsWith(current)).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}

