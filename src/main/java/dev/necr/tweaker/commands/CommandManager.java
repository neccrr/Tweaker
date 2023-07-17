package dev.necr.tweaker.commands;

import cloud.commandframework.Command;
import cloud.commandframework.CommandTree;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.ParserParameters;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.minecraft.extras.MinecraftHelp;
import cloud.commandframework.paper.PaperCommandManager;
import dev.necr.tweaker.Tweaker;
import dev.necr.tweaker.commands.main.MainCommand;
import dev.necr.tweaker.utils.StringUtils;
import dev.necr.tweaker.utils.Utils;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

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
        this.minecraftHelp = new MinecraftHelp<>(
                "/tweaker help",
                plugin.getAudience()::sender,
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
                        plugin.getAudience().sender(sender).sendMessage(Component.text(Utils.getPluginDescription()));
                        plugin.getAudience().sender(sender).sendMessage(Component.text(StringUtils.colorize("&7Use &e/tweaker help&7 to see the list of commands")));
                        return;
                    }
                    this.getMinecraftHelp().queryCommands(query, sender);
                })
        );

        this.initCommand(MainCommand.class);
    }

    /**
     * Initializes a command class
     *
     * @param clazz the command class
     */
    public void initCommand(Class<?> clazz) {
        plugin.getLogger().info("Loading and registering " + clazz.getSimpleName() + " command...");

        try {
            this.parseAnnotationCommands(clazz.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            plugin.getLogger().severe("Failed loading command class: " + clazz.getSimpleName());
            e.printStackTrace();
        }

        plugin.getLogger().info("Registered " + clazz.getSimpleName() + " commands!");
    }

    private void parseAnnotationCommands(Object... clazz) {
        Arrays.stream(clazz).forEach(this.annotationParser::parse);
    }

}