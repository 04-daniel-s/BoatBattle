package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamsEnum;
import de.lecuutex.boatbattle.utils.GameHandler;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * A class created by yi.dnl - 26.05.2022 / 11:34
 */

public class PlayerMoveListener implements Listener {

    private final GameHandler gameHandler = BoatBattle.getInstance().getGameHandler();

    @EventHandler
    public void onEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Location mapCenter = ((Location) ConfigManager.get(ConfigEnum.LOCATIONS, PlaceholderEnum.MAP_CENTER_SPAWNPOINT)).clone();
        mapCenter.setY(player.getLocation().getY());
        int radius = (Integer) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.AREA_LEAVING_FORBIDDEN_RADIUS);

        if (mapCenter.distance(event.getTo().toBlockLocation()) > radius) {
            if (gameHandler.getGameState() == GameStateEnum.INGAME) {
                MinigamePlayer minigamePlayer = gameHandler.getPlayer(player);
                TeamsEnum teamsEnum = minigamePlayer.getTeamsEnum();

                Location location = (minigamePlayer.getPlayerStateEnum() == PlayerStateEnum.SPECTATOR || teamsEnum == TeamsEnum.RANDOM) ?
                        (Location) ConfigManager.get(ConfigEnum.LOCATIONS, PlaceholderEnum.MAP_CENTER_SPAWNPOINT) :
                        (Location) (teamsEnum == TeamsEnum.BLUE ?
                                ConfigManager.get(ConfigEnum.LOCATIONS, PlaceholderEnum.TEAM_BLUE_SPAWNPOINT) :
                                ConfigManager.get(ConfigEnum.LOCATIONS, PlaceholderEnum.TEAM_RED_SPAWNPOINT));
                player.teleport(location);
            }
        }

        if (player.getLocation().getY() <= -120) {
            if (gameHandler.getGameState() == GameStateEnum.INGAME && gameHandler.getPlayer(player).getPlayerStateEnum() == PlayerStateEnum.INGAME) {
                player.setHealth(0);
                return;
            }

            player.teleport((Location) ConfigManager.get(ConfigEnum.LOCATIONS, PlaceholderEnum.MAP_CENTER_SPAWNPOINT));
        }
    }
}
