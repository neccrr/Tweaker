package dev.necr.tweaker.modules.playerhologram;

import dev.necr.tweaker.modules.Module;
import dev.necr.tweaker.modules.playerhologram.commands.HologramCommand;

public class PlayerHologram extends Module {

    @Override
    public void init() {
        plugin.getLogger().info("Initializing Hologram module...");

        commandManager.initCommand(HologramCommand.class);

        plugin.getLogger().info("Hologram module initialized!");
    }
}
