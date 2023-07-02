package dev.necr.tweaker.modules;

import dev.necr.tweaker.Tweaker;
import dev.necr.tweaker.modules.hologram.Hologram;
import dev.necr.tweaker.modules.misc.Misc;
import dev.necr.tweaker.modules.respawngui.RespawnGUI;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final Tweaker plugin;
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager(Tweaker plugin) {
        this.plugin = plugin;
    }

    public void initModules() {
        RespawnGUI respawnGUI = new RespawnGUI();
        respawnGUI.init();

        Hologram hologram = new Hologram();
        hologram.init();

        Misc misc = new Misc();
        misc.init();
    }
}
