package dev.necr.tweaker;

import dev.necr.tweaker.commands.CommandManager;
import dev.necr.tweaker.modules.Module;
import dev.necr.tweaker.modules.ModuleManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public final class Tweaker extends JavaPlugin {

    @Getter
    private static Tweaker instance;

    @Getter
    private CommandManager commandManager;
    @Getter
    private ModuleManager moduleManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        long millis = System.currentTimeMillis();

        instance = this;

        this.commandManager = new CommandManager(this);

        this.moduleManager = new ModuleManager(this);
        this.moduleManager.initModules();

        this.getLogger().info(this.getPluginMeta().getName() + this.getPluginMeta().getDescription() + " loaded in " + (System.currentTimeMillis() - millis) + "ms!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("Starting shutdown process...");

        this.getLogger().info("GoodBye!");
    }
}
