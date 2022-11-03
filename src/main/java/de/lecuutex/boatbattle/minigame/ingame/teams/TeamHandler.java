package de.lecuutex.boatbattle.minigame.ingame.teams;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A class created by yi.dnl - 23.04.2022 / 17:48
 */

@Getter
public class TeamHandler {
    private final HashMap<TeamsEnum, Team> teams = new HashMap<>();

    public TeamHandler() {
        teams.put(TeamsEnum.BLUE, new TeamBlue());
        teams.put(TeamsEnum.RED, new TeamRed());
    }

    public Team get(TeamsEnum teamsEnum) {
        if (teams.containsKey(teamsEnum)) {
            return teams.get(teamsEnum);
        }
        return null;
    }

    public Team getTeam(TeamsEnum teamsEnum) {
        return teams.get(teamsEnum);
    }

    public List<MinigamePlayer> getPlayersInTeam(TeamsEnum teamsEnum) {
        Collection<MinigamePlayer> players = BoatBattle.getInstance().getGameHandler().getPlayers().values();
        return players.stream().filter(p -> p.getTeamsEnum() == teamsEnum).filter(p -> Bukkit.getPlayer(UUID.fromString(p.getUuid())) != null).collect(Collectors.toList());
    }
}
