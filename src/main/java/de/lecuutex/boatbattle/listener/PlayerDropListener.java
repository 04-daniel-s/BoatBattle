package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.lobby.KitItem;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * A class created by yi.dnl - 20.04.2022 / 00:39
 */

public class PlayerDropListener implements Listener {
    @EventHandler
    public void onEvent(PlayerDropItemEvent event) {
        if (BoatBattle.getInstance().getGameHandler().getGameState() != GameStateEnum.INGAME) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        MinigamePlayer minigamePlayer = BoatBattle.getInstance().getGameHandler().getPlayer(player);

        for (KitItem item : minigamePlayer.getKit().getItems()) {
            if(event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(item.getName())) {
                event.setCancelled(!item.isDispensable());
            }
        }
    }
}
