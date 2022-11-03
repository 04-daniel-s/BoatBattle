package de.lecuutex.boatbattle.utils.inventory;

import de.lecuutex.boatbattle.BoatBattle;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

/**
 * A class created by yi.dnl - 18.04.2022 / 16:29
 */

public class Itembuilder {

    private final ItemStack item;

    private final ItemMeta itemMeta;

    public Itembuilder(ItemStack item) {
        this.item = item;
        itemMeta = item.getItemMeta();
    }

    public Itembuilder(Material material) {
        this.item = new ItemStack(material);
        itemMeta = item.getItemMeta();
    }

    public Itembuilder(Material material, int amount) {
        this.item = new ItemStack(material);
        this.item.setAmount(amount);
        itemMeta = item.getItemMeta();
    }

    public Itembuilder setDisplayName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public Itembuilder setLore(String... text) {
        itemMeta.setLore(Arrays.asList(text));
        return this;
    }

    public Itembuilder setSkullOwner(Player player) {
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        skullMeta.setOwner(player.getName());

        return this;
    }

    public Itembuilder setUnbreakable(boolean b) {
        itemMeta.setUnbreakable(b);
        return this;
    }

    public Itembuilder setColor(Color color) {
        ((LeatherArmorMeta) itemMeta).setColor(color);
        return this;
    }

    public Itembuilder addEnchantment(Enchantment enchantment, int i) {
        itemMeta.addEnchant(enchantment, i, true);
        return this;
    }

    public Itembuilder addItemFlags(ItemFlag... flag) {
        itemMeta.addItemFlags(flag);
        return this;
    }

    public Itembuilder addAttribute(Attribute attribute, double amount) {
        itemMeta.addAttributeModifier(attribute, new AttributeModifier(attribute.getKey().getKey(), amount, AttributeModifier.Operation.ADD_NUMBER));
        return this;
    }

    public Itembuilder addGlow() {
        itemMeta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(itemMeta);
        return item;
    }
}
