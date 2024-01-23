package qub.itemsapi.Listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import qub.itemsapi.ItemsAPI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InventoryListener implements Listener {

    private final ItemsAPI plugin;

    public InventoryListener(ItemsAPI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemInHand = event.getItem();
            if (itemInHand != null && isCustomItem(itemInHand, event.getPlayer())) {
                event.setCancelled(true);

                String localizationKey = plugin.getItemManager().getPlayerLocalizationKey(event.getPlayer());
                List<String> commands = getCommandsFromItem(itemInHand, localizationKey);

                if (!commands.isEmpty()) {
                    for (String command : commands) {
                        plugin.getServer().dispatchCommand(event.getPlayer(), command);
                    }
                } else {
                    plugin.getServer().getConsoleSender().sendMessage("No commands found for localization: " + localizationKey);
                }
            }
        }
    }

    private boolean isCustomItem(ItemStack item, Player player) {
        ConfigurationSection itemsConfig = plugin.getConfig().getConfigurationSection("menus.menu1.items");
        if (itemsConfig != null) {
            for (String itemId : itemsConfig.getKeys(false)) {
                ItemStack customItem = plugin.getItemManager().createCustomItem(itemId, player);
                if (customItem != null && item.isSimilar(customItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<String> getCommandsFromItem(ItemStack item, String localizationKey) {
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, "commands_" + localizationKey);
        String commandsString = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);

        if (commandsString != null && !commandsString.isEmpty()) {
            return Arrays.asList(commandsString.split(";"));
        } else {
            return Collections.emptyList();
        }
    }
}
