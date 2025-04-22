package my.mjba.pluginHider;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public final class PluginHider extends JavaPlugin implements Listener {
    private boolean enabled;
    private boolean useCustomMessages;
    private String pluginsListMessage;
    private static final String ADMIN_PERMISSION = "pluginhider.admin";
    private static final String VIEW_PERMISSION = "pluginhider.view";

    private String translateColors(String text) {
        return text.replace("&", "§");
    }

    @Override
    public void onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig();
        
        // Load configuration
        loadConfig();
        
        // Register events
        getServer().getPluginManager().registerEvents(this, this);
        
        // Register command
        PluginHiderCommand commandExecutor = new PluginHiderCommand(this);
        getCommand("pluginhider").setExecutor(commandExecutor);
        
        getLogger().info("PluginHider has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("PluginHider has been disabled!");
    }

    private void loadConfig() {
        FileConfiguration config = getConfig();
        enabled = config.getBoolean("enabled", true);
        
        // Load message settings
        useCustomMessages = config.getBoolean("messages.custom-enabled", false);
        if (useCustomMessages) {
            pluginsListMessage = translateColors(config.getString("messages.plugins-list", "&fPlugins (0): "));
        } else {
            pluginsListMessage = "§fPlugins (0): ";
        }
    }

    public void reloadPlugin() {
        reloadConfig();
        loadConfig();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTabComplete(TabCompleteEvent event) {
        if (!enabled) return;
        
        String buffer = event.getBuffer().toLowerCase();
        if (!buffer.startsWith("/plugins") && !buffer.startsWith("/pl")) return;
        
        if (event.getSender() instanceof Player) {
            Player player = (Player) event.getSender();
            if (!hasViewPermission(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerCommand(ServerCommandEvent event) {
        if (!enabled) return;
        
        String command = event.getCommand().toLowerCase();
        if (command.equals("plugins") || command.equals("pl")) {
            event.setCancelled(true);
            event.getSender().sendMessage(pluginsListMessage);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (!enabled) return;
        
        String command = event.getMessage().toLowerCase();
        if (command.startsWith("/plugins") || command.startsWith("/pl")) {
            Player player = event.getPlayer();
            if (!hasViewPermission(player)) {
                event.setCancelled(true);
                player.sendMessage(pluginsListMessage);
            }
        }
    }

    private boolean hasViewPermission(Player player) {
        return player.isOp() || player.hasPermission(VIEW_PERMISSION);
    }

    public boolean hasAdminPermission(Player player) {
        return player.isOp() || player.hasPermission(ADMIN_PERMISSION);
    }

    public boolean isPluginEnabled() {
        return enabled;
    }

    public void setPluginEnabled(boolean enabled) {
        this.enabled = enabled;
        getConfig().set("enabled", enabled);
        saveConfig();
    }

    public String getPluginsListMessage() {
        return pluginsListMessage;
    }
}
