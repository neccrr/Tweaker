package dev.necr.tweaker.modules;

import dev.necr.tweaker.Tweaker;
import dev.necr.tweaker.modules.misc.Misc;
import dev.necr.tweaker.modules.playerhologram.PlayerHologram;
import dev.necr.tweaker.modules.playerprefix.PlayerPrefix;
import dev.necr.tweaker.modules.privatemessage.PrivateMessage;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final Tweaker plugin;
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager(Tweaker plugin) {
        this.plugin = plugin;
    }

    public void initModules() {
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.init();

        PlayerPrefix playerPrefix = new PlayerPrefix();
        playerPrefix.init();

        PlayerHologram playerHologram = new PlayerHologram();
        playerHologram.init();

        Misc misc = new Misc();
        misc.init();
    }
}
