package qub.itemsapi.Managers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import qub.itemsapi.ItemsAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemManager {

    private final ItemsAPI plugin;

    public ItemManager(ItemsAPI plugin) {
        this.plugin = plugin;
    }

    public void giveItems(Player player) {
        ConfigurationSection itemsConfig = plugin.getConfig().getConfigurationSection("menus.menu1.items");
        if (itemsConfig != null) {
            for (String itemId : itemsConfig.getKeys(false)) {
                int slot = itemsConfig.getInt(itemId + ".slot");
                ItemStack item = createCustomItem(itemId, player);
                player.getInventory().setItem(slot, item);
            }
        }
    }

    public ItemStack createCustomItem(String itemId, Player player) {
        ConfigurationSection itemConfig = plugin.getConfig().getConfigurationSection("menus.menu1.items." + itemId);
        if (itemConfig == null) {
            plugin.getLogger().warning("Ошибка '" + itemId + "'");
            return null;
        }

        try {
            Material material = Material.matchMaterial(itemConfig.getString("material"));
            if (material == null) {
                throw new IllegalArgumentException("Ошибка '" + itemId + "'");
            }

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            String localizationKey = "ru";
            if (player != null) {
                localizationKey = getPlayerLocalizationKey(player);
            }

            String nameKey = "menu_title_" + localizationKey;
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemConfig.getString(nameKey)));

            List<String> lore = itemConfig.getStringList("lore_" + localizationKey);
            List<String> formattedLore = new ArrayList<>();
            for (String line : lore) {
                formattedLore.add(ChatColor.translateAlternateColorCodes('&', line));
            }

            meta.setLore(formattedLore);

            if ("ru".equals(localizationKey)) {
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(plugin, "commands"),
                        PersistentDataType.STRING,
                        "cc open menuru"
                );
            } else if ("en".equals(localizationKey)) {
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(plugin, "commands"),
                        PersistentDataType.STRING,
                        "cc open menuen"
                );
            }

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
            meta.setUnbreakable(true);

            item.setItemMeta(meta);
            return item;
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Ошибка '" + itemId + "'");
        }

        return null;
    }

    public String getPlayerLocalizationKey(Player player) {
        // Ваша логика определения локализации игрока
        return "ru";
    }
}
