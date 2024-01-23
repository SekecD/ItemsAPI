package qub.itemsapi.Managers;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import qub.itemsapi.ItemsAPI;

public class ConfigManager {

    private final ItemsAPI plugin;

    public ConfigManager(ItemsAPI plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
    }

    public ConfigurationSection getMenuItemsConfig(String menuKey) {
        ConfigurationSection menusConfig = plugin.getConfig().getConfigurationSection("menus");
        if (menusConfig != null) {
            return menusConfig.getConfigurationSection(menuKey + ".items");
        }
        return null;
    }

    public String getPlayerLocalizationKey(Player player) {
        ConfigurationSection playerLocalizationSection = plugin.getConfig().getConfigurationSection("player_localizations");
        if (playerLocalizationSection != null) {
            return playerLocalizationSection.getString(player.getUniqueId().toString(), "ru");
        }
        return "ru";
    }

    public void setPlayerLocalizationKey(Player player, String localizationKey) {
        ConfigurationSection playerLocalizationSection = plugin.getConfig().getConfigurationSection("player_localizations");
        if (playerLocalizationSection == null) {
            playerLocalizationSection = plugin.getConfig().createSection("player_localizations");
        }
        playerLocalizationSection.set(player.getUniqueId().toString(), localizationKey);
        plugin.saveConfig();
    }
}
