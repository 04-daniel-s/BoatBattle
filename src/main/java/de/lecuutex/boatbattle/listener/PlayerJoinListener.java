package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.scheduler.LobbyScheduler;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamsEnum;
import de.lecuutex.boatbattle.utils.GameHandler;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import de.lecuutex.boatbattle.utils.inventory.Itembuilder;
import de.lecuutex.boatbattle.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextFormat;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.Range;
import org.w3c.dom.Text;

/**
 * A class created by yi.dnl - 19.04.2022 / 21:36
 */

public class PlayerJoinListener implements Listener {

    private final GameHandler gameHandler = BoatBattle.getInstance().getGameHandler();

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        event.setJoinMessage("");
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(1024);

        if (gameHandler.getPlayers().containsKey(player.getUniqueId().toString())) {
            MinigamePlayer minigamePlayer = gameHandler.getPlayer(player);

            if (minigamePlayer.getTeamsEnum() != TeamsEnum.RANDOM && BoatBattle.getInstance().getTeamHandler().get(minigamePlayer.getTeamsEnum()).getTeamSheep().isAlive()) {
                minigamePlayer.spawn();
            } else {
                gameHandler.setSpectator(player);
            }
        } else {
            gameHandler.addPlayer(player);
        }

        if (gameHandler.getGameState() == GameStateEnum.LOBBY) {
            event.setJoinMessage(Utils.PREFIX + player.getName() + " §eist dem Server beigetreten");

            player.getInventory().setItem(0, new Itembuilder(Material.BOOK).setDisplayName("§cKit Auswahl").build());
            player.getInventory().setItem(8, new Itembuilder(Material.RED_DYE).setDisplayName("§cZurück zur Lobby").build());
            player.getInventory().setItem(4, new Itembuilder(Material.WHITE_WOOL).setDisplayName("§cTeam Auswahl").build());
        }
    }
}
