package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import de.lecuutex.boatbattle.utils.enums.config.SheepItems;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

/**
 * A class created by yi.dnl - 12.06.2022 / 15:27
 */

public class VehicleEnterListener implements Listener {
    @EventHandler
    public void onEvent(VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player) {
            Player player = (Player) event.getEntered();

            MinigamePlayer minigamePlayer = BoatBattle.getInstance().getGameHandler().getPlayer(player);
            if (minigamePlayer.getPlayerStateEnum() == PlayerStateEnum.SPECTATOR) {
                event.setCancelled(true);
            }

        } else if (event.getEntered() instanceof Sheep) {
            event.setCancelled(true);
        }
    }
}
