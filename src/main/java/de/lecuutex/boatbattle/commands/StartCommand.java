package de.lecuutex.boatbattle.commands;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.MessageEnum;
import de.lecuutex.boatbattle.utils.enums.PermissionsEnum;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import de.lecuutex.boatbattle.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A class created by yi.dnl - 21.04.2022 / 02:15
 */

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if (!player.hasPermission(PermissionsEnum.PREMIUM.getPermission())) {
            Utils.sendMessage(player, MessageEnum.NO_PERMISSIONS);
            return true;
        }

        if (Bukkit.getOnlinePlayers().size() < (int) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.LOBBY_MIN_PLAYER)) {
            Utils.sendMessage(player, MessageEnum.NOT_ENOUGH_PLAYER);
            return true;
        }

        if (BoatBattle.getInstance().getLobbyScheduler().getTime() <= 6) {
            Utils.sendMessage(player, "Das Spiel wurde bereits gestartet!");
            return true;
        }

        BoatBattle.getInstance().getLobbyScheduler().setTime(5 + 1);
        return true;
    }
}
