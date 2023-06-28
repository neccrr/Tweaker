package dev.necr.tweaker.modules.respawngui;

import dev.necr.tweaker.Tweaker;
import dev.necr.tweaker.modules.respawngui.listeners.PlayerRespawnListener;

public class RespawnGUI {

    private final Tweaker plugin;

    public RespawnGUI(Tweaker plugin) {
        this.plugin = plugin;

        this.initialize();
    }

    private void initialize() {
        this.plugin.getLogger().info("Initializing RespawnGUI module...");

        plugin.getServer().getPluginManager().registerEvents(new PlayerRespawnListener(plugin), plugin);

        this.plugin.getLogger().info("RespawnGUI module initialized!");
    }
}
