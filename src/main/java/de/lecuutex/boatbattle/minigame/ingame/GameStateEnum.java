package de.lecuutex.boatbattle.minigame.ingame;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A class created by yi.dnl - 19.04.2022 / 21:29
 */

@Getter
@AllArgsConstructor
public enum GameStateEnum {
    LOBBY("§aSpielersuche"),
    INGAME("§eIngame"),
    END("§cRestarting");

    private String motd;
}
