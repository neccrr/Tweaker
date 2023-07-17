package dev.necr.tweaker;

import dev.necr.tweaker.commands.CommandManager;
import dev.necr.tweaker.hooks.LuckPermsHook;
import dev.necr.tweaker.modules.ModuleManager;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public final class Tweaker extends JavaPlugin {

    @Getter
    private static Tweaker instance;

    @Getter
    private BukkitAudiences audience;

    @Getter
    private LuckPerms luckPermsProvider;
    @Getter
    private LuckPermsHook luckPerms;

    @Getter
    private CommandManager commandManager;
    @Getter
    private ModuleManager moduleManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        long millis = System.currentTimeMillis();

        instance = this;

        audience = BukkitAudiences.create(this);

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            this.luckPermsProvider = provider.getProvider();

            this.luckPerms = new LuckPermsHook(this, luckPermsProvider);

            this.getLogger().info("LuckPerms hooked!");
        } else {
            this.getLogger().info("");
            this.getLogger().severe("WARNING! Unable to hook into LuckPerms!");
            this.getLogger().severe("Make sure you installed LuckPerms correctly!");
            this.getLogger().severe("This plugin won't work properly without LuckPerms!");
            this.getLogger().severe("https://luckperms.net/download");
            this.getLogger().info("");
        }

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
