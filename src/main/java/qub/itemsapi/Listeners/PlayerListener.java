package qub.itemsapi.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import qub.itemsapi.ItemsAPI;

public class PlayerListener implements Listener {

    private final ItemsAPI plugin;

    public PlayerListener(ItemsAPI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.giveItemsOnJoin(event.getPlayer());
    }
}
