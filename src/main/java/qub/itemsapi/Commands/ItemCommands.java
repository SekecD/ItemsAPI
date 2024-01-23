package qub.itemsapi.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import qub.itemsapi.ItemsAPI;

public class ItemCommands implements CommandExecutor {

    private final ItemsAPI plugin;

    public ItemCommands(ItemsAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("customitems")) {
            if (args.length == 2 && args[0].equalsIgnoreCase("change")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    String localizationKey = args[1].toLowerCase();

                    if (localizationKey.equals("ru") || localizationKey.equals("en")) {
                        // Устанавливаем локализацию игрока
                        plugin.getConfigManager().setPlayerLocalizationKey(player, localizationKey);
                        sender.sendMessage("Локализация сменена на " + localizationKey + ". Перезайдите.");

                        // Получаем команду для выполнения
                        String commandKey = "command_" + localizationKey.toLowerCase();
                        String commandToExecute = plugin.getConfig().getString("menus.menu1.items.0." + commandKey);

                        if (commandToExecute != null && !commandToExecute.isEmpty()) {
                            // Выполняем команду
                            plugin.getServer().dispatchCommand(sender, commandToExecute);
                        } else {
                            sender.sendMessage("Команда не найдена для текущей локализации.");
                        }
                    } else {
                        sender.sendMessage("Такой локализации пока что нет. (ru/en) ");
                    }
                } else {
                    sender.sendMessage("Эта команда только для игроков");
                }
                return true;
            } else if (args.length == 0) {
                sender.sendMessage("ver 1.02");
                return true;
            }
            sender.sendMessage("неправильный аргумент");
            return true;
        }
        return false;
    }
}
