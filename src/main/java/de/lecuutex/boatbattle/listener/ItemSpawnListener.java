package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

/**
 * A class created by yi.dnl - 09.08.2022 / 16:32
 */

public class ItemSpawnListener implements Listener {
    @EventHandler
    public void onEvent(ItemSpawnEvent event) {
        if (!ConfigManager.getWhitelistedBlocks().contains(event.getEntity().getItemStack().getType())) {
            if (event.getEntity().getItemStack().getType() == Material.APPLE || event.getEntity().getItemStack().getType() == Material.NETHER_STAR) return;
            event.setCancelled(true);
        }
    }
}
