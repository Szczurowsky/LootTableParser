package pl.szczurowsky.loottableparser.util.paper;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.szczurowsky.loottableparser.pojo.LootEntry;
import pl.szczurowsky.loottableparser.pojo.LootTableObject;
import pl.szczurowsky.loottableparser.util.GenerateLootUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Utility class for converting loot table to list of items
 */
public class ConvertLootTableUtil {

    /**
     * Private constructor to hide implicit public one
     */
    private ConvertLootTableUtil() {
    }

    /**
     * Converts loot table to list of items
     * @param lootTableObject loot table object
     * @param bonus bonus
     * @return list of items
     */
    public static List<ItemStack> convertLootTable(LootTableObject lootTableObject, boolean bonus) {
        List<ItemStack> convertedItems = new ArrayList<>();
        List<LootEntry> generatedLoot = GenerateLootUtil.generateLootForTable(lootTableObject, bonus);
        generatedLoot.forEach(lootEntry -> {
            Optional<Material> material = Optional.ofNullable(Material.getMaterial(lootEntry.getName().replace("minecraft:", "").toUpperCase()));
            if (material.isEmpty()) return;
            ItemStack itemStack = new ItemStack(material.get(), 1);
            HandleFunctionUtil.handleFunction(itemStack, lootEntry);
            convertedItems.add(itemStack);
        });
        return convertedItems;
    }

}
