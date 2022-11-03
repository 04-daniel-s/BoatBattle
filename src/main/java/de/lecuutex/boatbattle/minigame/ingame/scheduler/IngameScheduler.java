package de.lecuutex.boatbattle.minigame.ingame.scheduler;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.teams.Team;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamsEnum;
import de.lecuutex.boatbattle.utils.Utils;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class created by yi.dnl - 21.04.2022 / 01:29
 */

public class IngameScheduler extends GameScheduler {

    public IngameScheduler(int time) {
        super(time);
    }

    @Override
    public void setGameState() {
        getGameHandler().setGameState(GameStateEnum.INGAME);
    }

    @Override
    public void nextScheduler() {
        new EndScheduler((Integer) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.RESTART_COUNTDOWN));
    }

    List<Team> teamsAlive = new ArrayList<>();

    @Override
    public void run() {
        int ingamePlayers = getGameHandler().getPlayers().values().stream().filter(p -> p.getPlayerStateEnum() != PlayerStateEnum.SPECTATOR).toList().size();
        if (ingamePlayers < 1) Bukkit.getServer().shutdown();

        teamsAlive = getTeamHandler().getTeams().values().stream().filter(t -> getGameHandler().getPlayersInTeam(t.getTeamsEnum()).stream().filter(p -> p.getPlayerStateEnum() != PlayerStateEnum.SPECTATOR).toList().size() > 0).collect(Collectors.toList());
        if (teamsAlive.size() == 1 && !getGameHandler().isTest()) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                Utils.sendMessage(onlinePlayer, "Das Spiel hat " + teamsAlive.get(0).getTeamsEnum().getName() + " §7gewonnen!");
            }
            cancel();
        }
    }

    @Override
    public void end() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.setAllowFlight(true);
            onlinePlayer.setFlying(true);

            for (Player player : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(BoatBattle.getInstance(), player);
                player.showPlayer(BoatBattle.getInstance(), onlinePlayer);
            }

            if (teamsAlive.size() > 1) {
                Utils.sendMessage(onlinePlayer, "Das Spiel ist unentschieden ausgegangen!");
            }
        }
    }
}
