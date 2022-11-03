package de.lecuutex.boatbattle.utils.inventory.gui;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.utils.GameHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * A class created by yi.dnl - 19.04.2022 / 22:13
 */

@Getter
public abstract class GUI {

    private final String title;

    private final int size;

    private Inventory inventory;

    private final GameHandler gameHandler;

    public GUI(String title, int size) {
        this.title = title;
        this.size = size;
        inventory = Bukkit.createInventory(null, size, title);
        gameHandler = BoatBattle.getInstance().getGameHandler();
    }

    protected void setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    protected abstract void createInventory(Player player);

    protected abstract void clickEvent(InventoryClickEvent event);

    public void open(Player player) {
        inventory = Bukkit.createInventory(null, size, title);
        createInventory(player);

        Bukkit.getScheduler().scheduleSyncDelayedTask(BoatBattle.getInstance(), () -> {
            player.openInventory(inventory);
        }, 1);
    }
}
