package de.lecuutex.boatbattle;

import de.lecuutex.boatbattle.commands.ConfigCommand;
import de.lecuutex.boatbattle.commands.KitCommand;
import de.lecuutex.boatbattle.commands.StartCommand;
import de.lecuutex.boatbattle.commands.TestCommand;
import de.lecuutex.boatbattle.listener.*;
import de.lecuutex.boatbattle.minigame.ingame.scheduler.LobbyScheduler;
import de.lecuutex.boatbattle.minigame.ingame.teams.Team;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamHandler;
import de.lecuutex.boatbattle.minigame.lobby.Kit;
import de.lecuutex.boatbattle.minigame.lobby.KitItem;
import de.lecuutex.boatbattle.utils.GameHandler;
import de.lecuutex.boatbattle.utils.Utils;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import de.lecuutex.boatbattle.utils.inventory.gui.GUIManager;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class BoatBattle extends JavaPlugin {

    @Getter
    private static BoatBattle instance;

    private GUIManager guiManager;

    private GameHandler gameHandler;

    private TeamHandler teamHandler;

    private LobbyScheduler lobbyScheduler;

    @Override
    public void onEnable() {
        instance = this;

        ConfigurationSerialization.registerClass(Kit.class, "kit");
        ConfigurationSerialization.registerClass(KitItem.class, "kititem");

        new ConfigManager();
        new Utils();

        guiManager = new GUIManager();
        gameHandler = new GameHandler();
        teamHandler = new TeamHandler();
        lobbyScheduler = new LobbyScheduler((Integer) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.LOBBY_COUNTDOWN));

        getCommand("config").setExecutor(new ConfigCommand());
        getCommand("start").setExecutor(new StartCommand());
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("test").setExecutor(new TestCommand());

        Bukkit.getPluginManager().registerEvents(new VehicleEnterListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemSpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListPingListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockDropItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityPickupItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileHitListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemDespawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIManager(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new TabCompleteListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractAtEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncChatListener(), this);

        gameHandler.createTeams();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Team team : teamHandler.getTeams().values()) {
                team.getTeamSheep().updateName(team);
            }
        }, 0, 1);

    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(p -> p.kick(Component.text("").color(TextColor.color(0x59ebb5))));
    }

    public boolean canPlaceAndBreakBlocks(Location block) {
        for (PlaceholderEnum placeholder : PlaceholderEnum.values()) {
            if (!placeholder.toString().endsWith("_SPAWNPOINT")) continue;

            Location ph = ((Location) ConfigManager.get(ConfigEnum.LOCATIONS, placeholder)).clone();
            Location finalBlock = block.clone().set(block.getBlockX(), ph.getY(), block.getBlockZ());

            try {
                PlaceholderEnum p = PlaceholderEnum.valueOf(placeholder.toString().replace("SPAWNPOINT", "SPAWNPROTECTION_RADIUS"));
                if (ph.distance(finalBlock) <= (int) ConfigManager.get(ConfigEnum.CONFIG, p)) {
                    return false;
                }

            } catch (IllegalArgumentException ignored) {
                if (placeholder == PlaceholderEnum.TEAM_BLUE_SPAWNPOINT || placeholder == PlaceholderEnum.TEAM_RED_SPAWNPOINT) {
                    if (ph.distance(finalBlock) <= (int) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.TEAM_SPAWNPROTECTION_RADIUS)) {
                        return false;
                    }
                } else if (placeholder == PlaceholderEnum.TEAM_BLUE_SHEEP_SPAWNPOINT || placeholder == PlaceholderEnum.TEAM_RED_SHEEP_SPAWNPOINT) {
                    if (ph.distance(block) <= (int) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.SHEEP_SPAWNPROTECTION_RADIUS)) {
                        return false;
                    }
                } else if (placeholder == PlaceholderEnum.MAP_CENTER_SPAWNPOINT) {
                    if (ph.distance(finalBlock) > (int) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.MAP_BUILDING_ALLOWED_RADIUS)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
