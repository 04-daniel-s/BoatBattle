package de.lecuutex.boatbattle.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * A class created by yi.dnl - 12.06.2022 / 15:21
 */

public class ProjectileHitListener implements Listener {

    @EventHandler
    public void onEvent(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            arrow.remove();
        }
    }
}
