package de.lecuutex.boatbattle.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

/**
 * A class created by yi.dnl - 23.04.2022 / 15:55
 */

public class ItemDespawnListener implements Listener {

    @EventHandler
    public void onEvent(ItemDespawnEvent event) {
        event.setCancelled(true);
    }
}
