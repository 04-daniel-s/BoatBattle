package de.lecuutex.boatbattle.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.Collections;

/**
 * A class created by yi.dnl - 19.04.2022 / 14:40
 */

public class TabCompleteListener implements Listener {
    @EventHandler
    public void onEvent(TabCompleteEvent event) {
        if (!event.isCommand()) return;

        if ("/config reload".startsWith(event.getBuffer())) {
            event.setCompletions(Collections.singletonList("reload"));
        }
    }
}
