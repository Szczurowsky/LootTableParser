package pl.szczurowsky.loottableparser;

import com.google.gson.JsonObject;
import org.bukkit.inventory.ItemStack;
import pl.szczurowsky.loottableparser.exception.NotALootTableException;
import pl.szczurowsky.loottableparser.pojo.LootTableObject;
import pl.szczurowsky.loottableparser.util.JsonParserUtil;
import pl.szczurowsky.loottableparser.util.LootTableParserUtil;
import pl.szczurowsky.loottableparser.util.paper.ConvertLootTableUtil;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Parsed, convenient loot table object with methods to get loot.
 */
public class ParsedLootTable {

    private final JsonObject jsonObject;
    private LootTableObject lootTableObject;

    /**
     * Get loot table from json string.
     * @param jsonString json string
     */
    public ParsedLootTable(String jsonString) {
        this.jsonObject = JsonParserUtil.readFromString(jsonString);
    }

    /**
     * Get loot table from json file.
     * @param file json file
     */
    public ParsedLootTable(File file) {
        this.jsonObject = JsonParserUtil.readFromFile(file);
    }

    /**
     * Get loot table from json url.
     * @param siteURL json url
     */
    public ParsedLootTable(URL siteURL) {
        this.jsonObject = JsonParserUtil.readFromUrl(siteURL);
    }

    /**
     * Parse loot table.
     * @throws NotALootTableException if json is not a loot table
     */
    public void parse() throws NotALootTableException {
        lootTableObject = LootTableParserUtil.parseJsonObject(jsonObject);
    }

    /**
     * Get loot table object.
     * @return loot table object
     */
    public LootTableObject getLootTableObject() {
        return lootTableObject;
    }

    /**
     * Get loot in list of ItemStacks.
     * @param bonus if true, bonus loot will be returned
     * @return list of ItemStacks
     */
    public List<ItemStack> getLoot(boolean bonus) {
        return ConvertLootTableUtil.convertLootTable(lootTableObject, bonus);
    }

}
