package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

/**
 * A class created by yi.dnl - 24.06.2022 / 18:30
 */

public class BlockDropItemListener implements Listener {

    @EventHandler
    public void onEvent(BlockDropItemEvent event) {
        ConfigManager.getWhitelistedBlocks().stream().filter(v -> v == event.getBlock().getType()).forEach(v -> {
            event.setCancelled(true);
        });
    }
}
