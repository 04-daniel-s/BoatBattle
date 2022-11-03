package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

/**
 * A class created by yi.dnl - 12.06.2022 / 15:30
 */

public class EntityPickupItemListener implements Listener {

    @EventHandler
    public void onEvent(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        MinigamePlayer minigamePlayer = BoatBattle.getInstance().getGameHandler().getPlayer((Player) event.getEntity());
        if (minigamePlayer.getPlayerStateEnum() == PlayerStateEnum.SPECTATOR) {
            event.setCancelled(true);
        }
    }
}
