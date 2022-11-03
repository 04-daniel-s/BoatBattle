package de.lecuutex.boatbattle.commands;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A class created by yi.dnl - 21.04.2022 / 02:27
 */

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if (BoatBattle.getInstance().getGameHandler().getGameState() == GameStateEnum.LOBBY || BoatBattle.getInstance().getGameHandler().isTest()) {
            BoatBattle.getInstance().getGuiManager().getKitGUI().open(player);
        }

        return true;
    }
}
