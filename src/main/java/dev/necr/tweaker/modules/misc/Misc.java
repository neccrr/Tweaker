package dev.necr.tweaker.modules.misc;

import dev.necr.tweaker.modules.Module;

public class Misc extends Module {

    @Override
    public void init() {
        commandManager.initCommands(this.getClass());
    }

}
