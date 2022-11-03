package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.lobby.KitItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

/**
 * A class created by yi.dnl - 20.04.2022 / 01:02
 */

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onEvent(InventoryClickEvent event) {
        if (BoatBattle.getInstance().getGameHandler().getGameState() != GameStateEnum.INGAME) event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        MinigamePlayer minigamePlayer = BoatBattle.getInstance().getGameHandler().getPlayer(player);

        if (event.getClickedInventory() != event.getWhoClicked().getInventory()) {
            for (KitItem item : minigamePlayer.getKit().getItems()) {
                if (event.getCursor() != null && event.getCursor().hasItemMeta()) {
                    if (event.getCursor().getItemMeta().getDisplayName().equals(item.getName())) {
                        event.setCancelled(!item.isDispensable());
                    }
                }
            }
        } else if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
            if (event.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY) return;
            for (KitItem item : minigamePlayer.getKit().getItems()) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(item.getName())) {
                    event.setCancelled(!item.isDispensable());
                }
            }
        }
    }
}
