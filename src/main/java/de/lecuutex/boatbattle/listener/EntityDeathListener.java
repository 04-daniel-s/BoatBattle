package de.lecuutex.boatbattle.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * A class created by yi.dnl - 08.05.2022 / 18:32
 */

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEvent(EntityDeathEvent event) {
        event.getDrops().clear();
    }
}
