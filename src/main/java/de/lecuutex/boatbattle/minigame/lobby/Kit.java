package de.lecuutex.boatbattle.minigame.lobby;

import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A class created by yi.dnl - 19.04.2022 / 15:12
 */

@Getter
public class Kit implements ConfigurationSerializable {

    private final List<KitItem> items;

    private final String name;

    private final Material material;

    private final int slot;

    public Kit(List<KitItem> items, String name, Material material, int slot) {
        this.items = items;
        this.name = name;
        this.material = material;
        this.slot = slot;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("items", items);
        map.put("name", name);
        map.put("material", material.toString());
        map.put("slot", slot);

        return map;
    }

    public static Kit deserialize(Map<String, Object> serialized) {
        return new Kit((List<KitItem>) serialized.get("items"), String.valueOf(serialized.get("name")), Material.valueOf(String.valueOf(serialized.get("material"))), (int) serialized.get("slot"));
    }

    public static Map<String, Object> deserializeParameters(Map<String, Object> objectSerialized, String toDeserialize) {
        Map<String, Object> result = new HashMap<>();
        objectSerialized.keySet().stream().filter(s -> s.startsWith(toDeserialize + "."))
                .forEach(s ->
                        result.put(s.replaceFirst(toDeserialize + ".", ""), objectSerialized.get(s)));
        return result;
    }
}

