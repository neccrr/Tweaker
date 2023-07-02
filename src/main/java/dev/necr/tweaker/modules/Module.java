package dev.necr.tweaker.modules;

import dev.necr.tweaker.Tweaker;
import dev.necr.tweaker.commands.CommandManager;

public abstract class Module {

    protected Tweaker plugin = Tweaker.getInstance();

    protected CommandManager commandManager = plugin.getCommandManager();

    public abstract void init();

}
