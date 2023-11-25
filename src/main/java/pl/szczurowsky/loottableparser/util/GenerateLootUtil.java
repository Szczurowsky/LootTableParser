package pl.szczurowsky.loottableparser.util;

import pl.szczurowsky.loottableparser.pojo.LootEntry;
import pl.szczurowsky.loottableparser.pojo.LootPool;
import pl.szczurowsky.loottableparser.pojo.LootTableObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Utility class for generating loot from loot table
 */
public class GenerateLootUtil {

    /**
     * Private constructor to hide implicit public one
     */
    private GenerateLootUtil() {
    }

    /**
     * Generates loot for given loot pool
     *
     * @param lootPool loot pool to generate loot for
     * @param bonus if bonus rolls should be included
     * @return list of loot entries
     */
    public static List<LootEntry> generateLootForPool(LootPool lootPool, boolean bonus) {
        List<LootEntry> lootEntries = new ArrayList<>();
        HashMap<LootEntry, Integer> weightedLootEntries = new HashMap<>();
        lootPool.getEntries().forEach(lootEntry -> weightedLootEntries.put(lootEntry, lootEntry.getWeight()));
        int totalRolls = bonus ? lootPool.getBonusRolls().getValue() + lootPool.getRolls().getValue() : lootPool.getRolls().getValue();
        for (int i = 0; i < totalRolls; i++) {
            lootEntries.add(getRandomLootEntry(weightedLootEntries));
        }
        return lootEntries;
    }

    /**
     * Generates loot for given loot table
     * @param lootTableObject loot table to generate loot for
     * @param bonus if bonus rolls should be included
     * @return list of loot entries
     */
    public static List<LootEntry> generateLootForTable(LootTableObject lootTableObject, boolean bonus) {
        List<LootEntry> lootEntries = new ArrayList<>();
        List<LootPool> lootPools = lootTableObject.getPools();
        lootPools.forEach(lootPool -> lootEntries.addAll(generateLootForPool(lootPool, bonus)));
        return lootEntries;
    }

    /**
     * Gets random loot entry from given weighted loot entries
     * @param weightedLootEntries weighted loot entries
     * @return random loot entry
     */
    private static LootEntry getRandomLootEntry(HashMap<LootEntry, Integer> weightedLootEntries) {
        List<LootEntry> lootEntries = new ArrayList<>();
        weightedLootEntries.forEach((lootEntry, weight) -> {
            for (int i = 0; i < weight; i++) {
                lootEntries.add(lootEntry);
            }
        });
        return lootEntries.get((int) (Math.random() * lootEntries.size()));
    }

}
