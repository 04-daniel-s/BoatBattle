package de.lecuutex.boatbattle.minigame.ingame.scheduler;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamHandler;
import de.lecuutex.boatbattle.utils.GameHandler;
import de.lecuutex.boatbattle.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

/**
 * A class created by yi.dnl - 21.04.2022 / 01:17
 */

@Getter
public abstract class GameScheduler {

    private final GameHandler gameHandler = BoatBattle.getInstance().getGameHandler();

    private final TeamHandler teamHandler = BoatBattle.getInstance().getTeamHandler();

    private final int id;

    private final int oldTime;

    @Setter
    private int time;

    public GameScheduler(int time) {
        this.time = time + 1;
        this.oldTime = this.time;

        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(BoatBattle.getInstance(), this::start, 0, 20);
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1));
        setGameState();
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(id);
        end();
        nextScheduler();
    }

    public void sendMessageAtSeconds(String message, Integer... seconds) {
        for (int i = 0; i < seconds.length; i++) {
            if (getTime() == seconds[i]) {
                Bukkit.broadcast(new TextComponent(Utils.PREFIX + message));
                Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1));
            }
        }
    }

    public void resetTimer() {
        setTime(oldTime);
    }

    private void start() {
        if (time != 0) {
            time--;
            run();
        } else {
            cancel();
        }
    }

    public abstract void setGameState();

    public abstract void nextScheduler();

    public abstract void run();

    public abstract void end();
}
