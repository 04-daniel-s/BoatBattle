package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import de.lecuutex.boatbattle.utils.GameHandler;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.stream.Collectors;


/**
 * A class created by yi.dnl - 18.06.2022 / 12:34
 */

public class AsyncChatListener implements Listener {

    private final GameHandler gameHandler = BoatBattle.getInstance().getGameHandler();

    @EventHandler
    public void onEvent(AsyncChatEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();
        String message = ((TextComponent) event.message()).content();

        if (gameHandler.getGameState() == GameStateEnum.LOBBY) {
            for (Player spectator : Bukkit.getOnlinePlayers().stream().filter(p -> gameHandler.getPlayer(p).getPlayerStateEnum() == PlayerStateEnum.SPECTATOR).toList()) {
                spectator.sendMessage("§7" + player.getName() + " §7| " + message);
            }
        } else {
            Bukkit.broadcastMessage(gameHandler.getPlayerTeam(player).getName().substring(0, 2) + player.getName() + " §7| " + message);
        }
    }
}
