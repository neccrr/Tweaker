package dev.necr.tweaker.commands;

import cloud.commandframework.arguments.parser.ParserParameters;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.meta.CommandMeta;
import com.google.common.reflect.ClassPath;
import dev.necr.tweaker.Tweaker;

import cloud.commandframework.Command;
import cloud.commandframework.CommandTree;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.minecraft.extras.MinecraftHelp;
import cloud.commandframework.paper.PaperCommandManager;

import dev.necr.tweaker.commands.main.MainCommand;
import dev.necr.tweaker.modules.misc.Misc;
import dev.necr.tweaker.utils.Utils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

import lombok.Getter;

import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

public class CommandManager {

    @Getter
    private PaperCommandManager<CommandSender> commandManager;
    @Getter
    private final Tweaker plugin;
    @Getter
    private final Command.Builder<CommandSender> builder;
    @Getter
    private final MinecraftHelp<CommandSender> minecraftHelp;
    @Getter
    private final AnnotationParser<CommandSender> annotationParser;

    public CommandManager(Tweaker plugin) {
        this.plugin = plugin;

        Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction = CommandExecutionCoordinator.simpleCoordinator();
        Function<CommandSender, CommandSender> mapperFunction = Function.identity();
        try {
            this.commandManager = new PaperCommandManager<>(
                    plugin,
                    executionCoordinatorFunction,
                    mapperFunction,
                    mapperFunction
            );
        } catch (Exception e) {
            this.plugin.getLogger().severe("Failed to initialize the Command Manager");
            e.printStackTrace();
        }

        this.builder = this.commandManager.commandBuilder("tweaker", "tweak");

        // registers the custom help command
        BukkitAudiences bukkitAudiences = BukkitAudiences.create(plugin);
        this.minecraftHelp = new MinecraftHelp<>(
                "/tweaker help",
                bukkitAudiences::sender,
                this.commandManager
        );

        if (this.commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            this.commandManager.registerAsynchronousCompletions();
        }

        // registers the annotation parser
        Function<ParserParameters, CommandMeta> commandMetaFunction = it ->
                CommandMeta.simple()
                        .with(CommandMeta.DESCRIPTION, it.get(StandardParameters.DESCRIPTION, "No description"))
                        .build();

        this.annotationParser = new AnnotationParser<>(
                this.commandManager,
                CommandSender.class,
                commandMetaFunction
        );

        // initializes the default command
        commandManager.command(builder
                .meta(CommandMeta.DESCRIPTION, "The main command")
                .handler(commandContext -> {
                    CommandSender sender = commandContext.getSender();
                    String query = commandContext.getOrDefault("query", null);
                    if (query == null) {
                        sender.sendMessage(Utils.getPluginDescription());
                        return;
                    }
                    this.getMinecraftHelp().queryCommands(query, sender);
                })
        );

        this.initMainCommand();
    }

    public void initCommands(Class<?> clazz) {
        plugin.getLogger().info("Loading and registering " + clazz.getName() + "commands");
        try {
            ClassPath classPath = ClassPath.from(plugin.getClass().getClassLoader());
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(clazz.getPackage().getName() + ".commands")) {
                try {
                    Class<?> commandClass = Class.forName(classInfo.getName());
                    this.parseAnnotationCommands(commandClass.getDeclaredConstructor().newInstance());
                    plugin.getLogger().info("Registered " + commandClass.getName() + " commands!");
                } catch (Exception e) {
                    plugin.getLogger().severe("Failed loading command class: " + classInfo.getName());
                    e.printStackTrace();
                }
            }

            plugin.getLogger().info("Registered " + clazz.getName() + " commands!");
        } catch (IOException e) {
            plugin.getLogger().severe("Failed loading " + clazz.getName() + "command classes!");
            e.printStackTrace();
        }
    }

    private void initMainCommand() {
        Class<?> clazz = MainCommand.class;

        plugin.getLogger().info("Loading and registering " + clazz.getName() + " command...");

        try {
            this.parseAnnotationCommands(clazz.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            plugin.getLogger().severe("Failed loading command class: " + clazz.getName());
            e.printStackTrace();
        }

        plugin.getLogger().info("Registered " + clazz.getName() + " commands!");
    }

    private void parseAnnotationCommands(Object... clazz) {
        Arrays.stream(clazz).forEach(this.annotationParser::parse);
    }

}