package dev.necr.tweaker.modules.respawngui;

import dev.necr.tweaker.modules.Module;
import dev.necr.tweaker.modules.respawngui.listeners.PlayerRespawnListener;

public class RespawnGUI extends Module {

    @Override
    public void init() {
        plugin.getLogger().info("Initializing RespawnGUI module...");

        plugin.getServer().getPluginManager().registerEvents(new PlayerRespawnListener(plugin), plugin);

        plugin.getLogger().info("RespawnGUI module initialized!");
    }
}
