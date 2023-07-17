package dev.necr.tweaker.modules.privatemessage;

import dev.necr.tweaker.modules.Module;
import dev.necr.tweaker.modules.privatemessage.commands.MessageCommand;
import dev.necr.tweaker.modules.privatemessage.commands.ReplyCommand;

public class PrivateMessage extends Module {

    @Override
    public void init() {
        plugin.getLogger().info("Initializing PrivateMessage module...");

        this.commandManager.initCommand(MessageCommand.class);
        this.commandManager.initCommand(ReplyCommand.class);

        plugin.getLogger().info("PrivateMessage module initialized!");
    }
}
