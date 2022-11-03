package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * A class created by yi.dnl - 12.06.2022 / 13:29
 */

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        MinigamePlayer minigamePlayer = BoatBattle.getInstance().getGameHandler().getPlayer(player);

        if (minigamePlayer.getPlayerStateEnum() == PlayerStateEnum.SPECTATOR) event.setCancelled(true);
    }
}
