package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import de.lecuutex.boatbattle.utils.GameHandler;
import de.lecuutex.boatbattle.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

/**
 * A class created by yi.dnl - 26.05.2022 / 11:56
 */

public class PlayerDeathListener implements Listener {

    private final GameHandler gameHandler = BoatBattle.getInstance().getGameHandler();

    @EventHandler
    public void onEvent(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        event.setDeathMessage("");

        if (gameHandler.getPlayerInCombat().containsKey(player.getUniqueId().toString())) {
            Player killer = Bukkit.getPlayer(UUID.fromString((gameHandler.getPlayerInCombat().get(player.getUniqueId().toString()))));
            player.sendMessage(Utils.PREFIX + "Du wurdest von §e" + killer.getName() + "§7 getötet!");
            killer.sendMessage(Utils.PREFIX + "Du hast §e" + player.getName() + " §7getötet!");
        } else {
            player.sendMessage(Utils.PREFIX + "Du bist gestorben!");
        }

        if (gameHandler.getGameState() != GameStateEnum.INGAME) {
            event.setCancelled(true);
            return;
        }

        MinigamePlayer minigamePlayer = gameHandler.getPlayer(event.getPlayer());

        Bukkit.getScheduler().scheduleSyncDelayedTask(BoatBattle.getInstance(), () -> {
            event.getPlayer().spigot().respawn();
        }, 1);

        if (BoatBattle.getInstance().getGameHandler().isTest()) {
            player.setAllowFlight(true);
            player.setFlying(true);
        }

        if (!BoatBattle.getInstance().getTeamHandler().get(minigamePlayer.getTeamsEnum()).getTeamSheep().isAlive()) {
            gameHandler.setSpectator(event.getPlayer());
            return;
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(BoatBattle.getInstance(), minigamePlayer::spawn, 1);
    }
}
