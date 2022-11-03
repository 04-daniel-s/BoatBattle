package de.lecuutex.boatbattle.commands;

import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.MessageEnum;
import de.lecuutex.boatbattle.utils.enums.PermissionsEnum;
import de.lecuutex.boatbattle.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A class created by yi.dnl - 19.04.2022 / 14:25
 */

public class ConfigCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if (args.length != 1) return true;
        if (!args[0].equalsIgnoreCase("reload")) return true;

        if (!player.hasPermission(PermissionsEnum.ADMIN.getPermission())) {
            Utils.sendMessage(player, MessageEnum.NO_PERMISSIONS);
            return true;
        }

        ConfigManager.loadConfigs();
        return true;
    }
}
