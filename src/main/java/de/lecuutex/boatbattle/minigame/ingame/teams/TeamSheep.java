package de.lecuutex.boatbattle.minigame.ingame.teams;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.utils.Utils;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * A class created by yi.dnl - 08.05.2022 / 17:27
 */

public class TeamSheep {

    private int sheepHearts = ((int) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.SHEEP_HEARTS) * 2);

    private Sheep sheep;

    @Setter
    @Getter
    private boolean alive = false;

    private Location spawn;

    @Getter
    private final Team team;

    public TeamSheep(Team team, Location spawn) {
        this.spawn = spawn;
        this.team = team;
    }

    public void spawnSheep(int hearts) {
        if (isAlive()) return;
        setAlive(true);
        sheepHearts = hearts;

        spawn.getBlock().setType(Material.AIR);
        spawn.getNearbyEntitiesByType(ArmorStand.class, 1).forEach(Entity::remove);

        sheep = (Sheep) spawn.getWorld().spawnEntity(spawn, EntityType.SHEEP);
        sheep.setCustomNameVisible(true);
        sheep.setCustomName(team.getTeamsEnum().getName());
        sheep.setSilent(true);
        sheep.setAI(false);
        sheep.setColor(team.getColor());
        sheep.setHealth(Math.min(sheepHearts, 8));
        sheep.setInvulnerable(false);
        sheep.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60 * 60, 255).withParticles(false));
    }

    public void spawnSheep() {
        spawnSheep(((int) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.SHEEP_HEARTS) * 2));
    }

    public void removeSheep() {
        if (!isAlive()) return;

        Bukkit.getOnlinePlayers().stream().forEach(p -> Utils.sendMessage(p, "§7Das Schaf von " + team.getTeamsEnum().getName() + " §7wurde getötet!"));

        List<MinigamePlayer> players = BoatBattle.getInstance().getTeamHandler().getPlayersInTeam(team.getTeamsEnum());

        players.stream().forEach(p -> Utils.sendMessage(Bukkit.getPlayer(UUID.fromString(p.getUuid())), "§cDein Schaf wurde getötet! Dadurch wirst bei dem nächsten Tod nicht mehr wiederbelebt."));

        sheep.setHealth(0);
        spawn.getBlock().setType(team.getTeamsEnum().getSheepMaterial());
        alive = false;

        ArmorStand armorStand = (ArmorStand) spawn.getWorld().spawnEntity(spawn.clone().subtract(0, 0.7, 0), EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setAI(true);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("§c> Wiederbeleben <");

        BoatBattle.getInstance().getTeamHandler().getPlayersInTeam(team.getTeamsEnum()).stream().forEach(p -> Bukkit.getPlayer(UUID.fromString(p.getUuid())).playSound(spawn, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.2f, 0.2f));
    }

    public void addHearts(int amount) { //in hearts (1 = 2 minecraft hearts)
        sheepHearts += amount * 2;
    }

    public void removeHearts(int amount) {
        sheepHearts -= amount * 2;
        if (sheepHearts <= 0) removeSheep();
    }

    public void updateName(Team team) {
        if (!isAlive()) return;
        sheep.setCustomNameVisible(true);
        sheep.customName(Component.text("❤ " + (sheepHearts / 2)).color(team.getTeamsEnum().getChatColor()));
    }
}
