package de.lecuutex.boatbattle.commands;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.utils.Utils;
import de.lecuutex.boatbattle.utils.enums.MessageEnum;
import de.lecuutex.boatbattle.utils.enums.PermissionsEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A class created by yi.dnl - 22.04.2022 / 00:39
 */

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if (!player.hasPermission(PermissionsEnum.ADMIN.getPermission())) {
            Utils.sendMessage(player, MessageEnum.NO_PERMISSIONS);
            return true;
        }

        BoatBattle.getInstance().getGameHandler().setTest(true);
        BoatBattle.getInstance().getLobbyScheduler().end();
        return true;
    }
}
