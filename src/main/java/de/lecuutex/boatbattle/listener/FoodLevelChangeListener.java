package de.lecuutex.boatbattle.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * A class created by yi.dnl - 20.04.2022 / 22:54
 */

public class FoodLevelChangeListener implements Listener {
    @EventHandler
    public void onEvent(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
