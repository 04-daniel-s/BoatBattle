package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * A class created by yi.dnl - 25.06.2022 / 09:12
 */

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onEvent(ServerListPingEvent event) {
        event.setMotd(BoatBattle.getInstance().getGameHandler().getGameState().getMotd());
    }
}
