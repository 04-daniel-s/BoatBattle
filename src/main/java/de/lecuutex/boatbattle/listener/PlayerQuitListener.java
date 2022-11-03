package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamsEnum;
import de.lecuutex.boatbattle.utils.GameHandler;
import de.lecuutex.boatbattle.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * A class created by yi.dnl - 20.04.2022 / 00:59
 */

public class PlayerQuitListener implements Listener {

    private final GameHandler gameHandler = BoatBattle.getInstance().getGameHandler();

    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (gameHandler.getPlayerTeam(player) == TeamsEnum.RANDOM) {
            gameHandler.removePlayer(player);
        }

        event.setQuitMessage(Utils.PREFIX + player.getName() + " §chat den Server verlassen");
    }
}
