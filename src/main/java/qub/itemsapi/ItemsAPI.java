package qub.itemsapi;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import qub.itemsapi.Commands.ItemCommands;
import qub.itemsapi.Listeners.InventoryListener;
import qub.itemsapi.Listeners.PlayerListener;
import qub.itemsapi.Managers.ConfigManager;
import qub.itemsapi.Managers.ItemManager;

public final class ItemsAPI extends JavaPlugin {

    private ConfigManager configManager;
    private ItemManager itemManager;
    private ItemCommands itemCommands;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        itemManager = new ItemManager(this);
        itemCommands = new ItemCommands(this);

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);

        getCommand("customitems").setExecutor(itemCommands);

        configManager.loadConfig();
    }

    public void giveItemsOnJoin(Player player) {
        itemManager.giveItems(player);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
}
