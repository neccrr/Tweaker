package dev.necr.tweaker.modules.hologram;

import dev.necr.tweaker.modules.Module;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

public class Hologram extends Module {

    @Override
    public void init() {
        commandManager.initCommands(this.getClass());
    }
}
