package dev.necr.tweaker.modules.playerprefix;

import dev.necr.tweaker.modules.Module;
import dev.necr.tweaker.modules.playerprefix.commands.SetPrefixCommand;

public class PlayerPrefix extends Module {

    @Override
    public void init() {
        this.plugin.getLogger().info("Initializing PlayerPrefix module...");

        this.commandManager.initCommand(SetPrefixCommand.class);

        this.plugin.getLogger().info("PlayerPrefix module initialized!");
    }
}
