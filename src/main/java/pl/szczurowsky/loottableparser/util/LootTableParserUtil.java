package pl.szczurowsky.loottableparser.util;

import com.google.gson.JsonObject;
import pl.szczurowsky.loottableparser.exception.NotALootTableException;
import pl.szczurowsky.loottableparser.pojo.LootContextType;
import pl.szczurowsky.loottableparser.pojo.LootEntry;
import pl.szczurowsky.loottableparser.pojo.LootEntryType;
import pl.szczurowsky.loottableparser.pojo.LootPool;
import pl.szczurowsky.loottableparser.pojo.LootTableObject;
import pl.szczurowsky.loottableparser.pojo.integer.LootConstantInteger;
import pl.szczurowsky.loottableparser.pojo.integer.LootInteger;
import pl.szczurowsky.loottableparser.pojo.integer.LootUniformInteger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Utility class for parsing loot tables.
 */
public class LootTableParserUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private LootTableParserUtil() {
    }

    /**
     * Parses a loot table from a JSON object.
     *
     * @param jsonObject The JSON object to parse.
     * @return The parsed loot table.
     * @throws NotALootTableException If the JSON object is not a loot table.
     */
    public static LootTableObject parseJsonObject(JsonObject jsonObject) throws NotALootTableException {
        if (!(jsonObject.has("type") && jsonObject.has("pools")))
            throw new NotALootTableException();

        Optional<LootContextType> lootContextType = Optional.ofNullable(LootContextType.getLootContextType(jsonObject.get("type").getAsString()));
        if (lootContextType.isEmpty())
            throw new IllegalArgumentException("Unknown loot context type: " + jsonObject.get("type").getAsString());

        List<JsonObject> pools = new ArrayList<>();
        jsonObject.getAsJsonArray("pools").forEach(jsonElement -> pools.add(jsonElement.getAsJsonObject()));
        List<LootPool> lootPools = new ArrayList<>();
        pools.forEach(pool -> {
            try {
                // Set rolls
                LootInteger rolls;
                if (pool.get("rolls").isJsonObject()) {
                    JsonObject rollsObject = pool.getAsJsonObject("rolls");
                    rolls = new LootUniformInteger(rollsObject.get("min").getAsInt(), rollsObject.get("max").getAsInt());
                } else {
                    rolls = new LootConstantInteger(pool.get("rolls").getAsInt());
                }

                // Set bonus rolls
                LootInteger bonusRolls;
                if (!pool.has("bonus_rolls")) {
                    bonusRolls = new LootConstantInteger(1);
                } else if (pool.get("bonus_rolls").isJsonObject()) {
                    JsonObject bonusRollsObject = pool.getAsJsonObject("bonus_rolls");
                    bonusRolls = new LootUniformInteger(bonusRollsObject.get("min").getAsInt(), bonusRollsObject.get("max").getAsInt());
                } else {
                    bonusRolls = new LootConstantInteger(pool.get("bonus_rolls").getAsInt());
                }

                // Set entries
                List<JsonObject> entries = new ArrayList<>();
                pool.getAsJsonArray("entries").forEach(jsonElement -> entries.add(jsonElement.getAsJsonObject()));
                List<LootEntry> lootEntries = new ArrayList<>();
                entries.forEach(entry -> {
                    try {
                        String type = entry.get("type").getAsString();
                        String name = entry.get("name").getAsString();
                        Integer weight;
                        if (entry.has("weight"))
                            weight = entry.get("weight").getAsInt();
                        else
                            weight = 1;
                        boolean hasFunctions = entry.has("functions");
                        Optional<LootEntryType> lootEntryType = Optional.ofNullable(LootEntryType.getEntryType(type));
                        if (lootEntryType.isEmpty())
                            throw new IllegalArgumentException("Unknown entry type: " + type);
                        if (!lootEntryType.get().isSupported())
                            throw new IllegalArgumentException("Unsupported entry type: " + type);
                        LootEntry lootEntry = new LootEntry(lootEntryType.get(), name, weight);
                        if (hasFunctions)
                            lootEntry.setLootFunction(entry.getAsJsonArray("functions"));
                        lootEntries.add(lootEntry);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                });

                // Make loot pool
                LootPool lootPool = new LootPool(rolls, bonusRolls, lootEntries);
                lootPools.add(lootPool);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        });
        return new LootTableObject(lootContextType.get(), lootPools);
    }

}
