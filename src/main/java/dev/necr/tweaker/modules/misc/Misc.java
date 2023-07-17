package dev.necr.tweaker.modules.misc;

import dev.necr.tweaker.modules.Module;
import dev.necr.tweaker.modules.misc.commands.HeadCommand;
import dev.necr.tweaker.modules.misc.listener.CropTrampleListener;
import dev.necr.tweaker.modules.misc.listener.InventoryListener;
import dev.necr.tweaker.modules.misc.listener.StripLogListener;

public class Misc extends Module {

    @Override
    public void init() {
        plugin.getLogger().info("Registering Misc module...");

        plugin.getLogger().info("Registering CropTrampleListener...");
        plugin.getServer().getPluginManager().registerEvents(new CropTrampleListener(), plugin);

        plugin.getLogger().info("Registering InventoryListener...");
        plugin.getServer().getPluginManager().registerEvents(new InventoryListener(), plugin);

        plugin.getLogger().info("Registering PlayerRespawnListener...");
        plugin.getServer().getPluginManager().registerEvents(new StripLogListener(), plugin);

        this.commandManager.initCommand(HeadCommand.class);

        plugin.getLogger().info("Misc module initialized!");
    }

}
