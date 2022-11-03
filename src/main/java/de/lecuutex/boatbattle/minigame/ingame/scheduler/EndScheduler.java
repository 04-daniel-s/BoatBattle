package de.lecuutex.boatbattle.minigame.ingame.scheduler;

import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import org.bukkit.Bukkit;

/**
 * A class created by yi.dnl - 21.04.2022 / 01:29
 */

public class EndScheduler extends GameScheduler {

    public EndScheduler(int time) {
        super(time);
    }

    @Override
    public void setGameState() {
        getGameHandler().setGameState(GameStateEnum.END);
    }

    @Override
    public void nextScheduler() {
    }

    @Override
    public void run() {
        sendMessageAtSeconds("§cDer Server startet in §e" + getTime() + "§c Sekunden neu!", 15, 10, 5, 4, 3, 2, 1);
    }

    @Override
    public void end() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"restart");
    }
}
