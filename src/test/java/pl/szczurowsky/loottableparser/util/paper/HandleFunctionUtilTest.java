package pl.szczurowsky.loottableparser.util.paper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczurowsky.loottableparser.exception.NotALootTableException;
import pl.szczurowsky.loottableparser.pojo.LootEntry;
import pl.szczurowsky.loottableparser.pojo.LootTableObject;
import pl.szczurowsky.loottableparser.util.LootTableParserUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HandleFunctionUtilTest {

    LootTableObject lootTableObject;

    @BeforeEach
    void setUp() throws NotALootTableException {
        String simpleLootTable = """
                {
                  "type": "minecraft:chest",
                  "pools": [
                    {
                      "rolls": 1,
                      "entries": [
                        {
                          "type": "minecraft:item",
                          "name": "minecraft:stone",
                          "functions": [
                            {
                              "function": "minecraft:set_count",
                              "count": 3
                            }
                          ]
                        }
                      ]
                    }
                  ]
                }
                                
                """;
        JsonObject parsedLootTable = new Gson().fromJson(simpleLootTable, JsonObject.class);
        lootTableObject = LootTableParserUtil.parseJsonObject(parsedLootTable);
    }

    @Test
    void handleFunction() {
        LootEntry entry = lootTableObject.getPools().get(0).getEntries().get(0);
        Optional<Material> material = Optional.ofNullable(Material.getMaterial(entry.getName().replace("minecraft:", "").toUpperCase()));
        assertTrue(material.isPresent());
        assertEquals(Material.STONE, material.get());
        assertEquals(1, entry.getLootFunction().get().size());
        ItemStack itemStack = new ItemStack(material.get(), 1);
        HandleFunctionUtil.handleFunction(itemStack, entry);

    }
}