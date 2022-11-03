package de.lecuutex.boatbattle.minigame.lobby;

import de.lecuutex.boatbattle.utils.enums.config.SpecialItemEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A class created by yi.dnl - 19.04.2022 / 15:12
 */

@Getter
@AllArgsConstructor
public class KitItem implements ConfigurationSerializable {

    private final String name;

    private final Material material;

    private final int slot;

    private Enchantment enchantment;

    private int enchantmentPower;

    private int amount;

    private boolean allowEnchantment;

    private boolean unbreakable;

    private boolean dispensable;

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("material", material.toString());
        map.put("slot", slot);
        map.put("enchantment", enchantment.getKey().toString());
        map.put("enchantmentPower", enchantmentPower);
        map.put("amount", amount);
        map.put("allowEnchantment", allowEnchantment);
        map.put("unbreakable", unbreakable);
        map.put("dispensable", dispensable);

        return map;
    }

    public static KitItem deserialize(Map<String, Object> serialized) {
        return new KitItem(String.valueOf(serialized.get("name")),
                Material.valueOf(String.valueOf(serialized.get("material"))),
                (int) serialized.get("slot"),
                Enchantment.getByKey(NamespacedKey.minecraft(String.valueOf(serialized.get("enchantment")).substring(10))),
                (int) serialized.get("enchantmentPower"),
                (int) serialized.get("amount"),
                (boolean) serialized.get("allowEnchantment"),
                (boolean) serialized.get("unbreakable"),
                (boolean) serialized.get("dispensable"));
    }

    public static Map<String, Object> deserializeParameters(Map<String, Object> objectSerialized, String toDeserialize) {
        Map<String, Object> result = new HashMap<>();
        objectSerialized.keySet().stream().filter(s -> s.startsWith(toDeserialize + "."))
                .forEach(s ->
                        result.put(s.replaceFirst(toDeserialize + ".", ""), objectSerialized.get(s)));
        return result;
    }
}
