package my.mjba.pluginHider;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PluginHiderCommand implements CommandExecutor {
    private final PluginHider plugin;

    public PluginHiderCommand(PluginHider plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        if (sender instanceof Player player) {
            if (!plugin.hasAdminPermission(player)) {
                sender.sendMessage("§cYou don't have permission to use this command!");
                return true;
            }
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadPlugin();
                sender.sendMessage("§aPluginHider configuration reloaded!");
                break;
            case "toggle":
                boolean newState = !plugin.isPluginEnabled();
                plugin.setPluginEnabled(newState);
                sender.sendMessage("§aPluginHider " + (newState ? "enabled" : "disabled") + "!");
                break;
            case "status":
                sender.sendMessage("§aPluginHider Status:");
                sender.sendMessage("§eEnabled: " + (plugin.isPluginEnabled() ? "Yes" : "No"));
                break;
            default:
                sendHelp(sender);
                break;
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§aPluginHider Commands:");
        sender.sendMessage("§e/pluginhider reload - Reload the configuration");
        sender.sendMessage("§e/pluginhider toggle - Toggle plugin hiding");
        sender.sendMessage("§e/pluginhider status - Show current status");
        sender.sendMessage("§eAlias: /plhide");
    }
} 