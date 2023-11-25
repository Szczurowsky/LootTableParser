package pl.szczurowsky.loottableparser.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczurowsky.loottableparser.exception.NotALootTableException;
import pl.szczurowsky.loottableparser.pojo.LootContextType;
import pl.szczurowsky.loottableparser.pojo.LootTableObject;

import static org.junit.jupiter.api.Assertions.*;

class LootTableParserUtilTest {

    private JsonObject jsonObject;

    @BeforeEach
    void setUp() {
        String lootTableJson = """
                {
                  "type": "minecraft:entity",
                  "pools": [
                    {
                      "rolls": {
                        "min": 1,
                        "max": 3
                      },
                      "bonus_rolls": {
                        "min": 1,
                        "max": 3
                      },
                      "entries": [
                        {
                          "type": "minecraft:item",
                          "weight": 1,
                          "name": "minecraft:stone"
                        },
                        {
                          "type": "minecraft:item",
                          "weight": 2,
                          "name": "minecraft:stone"
                        },
                        {
                          "type": "minecraft:item",
                          "weight": 3,
                          "name": "minecraft:stone"
                        },
                        {
                          "type": "minecraft:item",
                          "weight": 4,
                          "name": "minecraft:stone",
                          "functions": [
                            {
                              "function": "minecraft:set_count",
                              "count": 12
                            }
                          ]
                        }
                      ]
                    }
                  ]
                }""";
        jsonObject = new Gson().fromJson(lootTableJson, JsonObject.class);
    }

    @Test
    void parseJsonObject() throws NotALootTableException {
        LootTableObject lootTableObject = LootTableParserUtil.parseJsonObject(jsonObject);
        assertNotNull(lootTableObject);
        assertEquals(LootContextType.ENTITY, lootTableObject.getType());
        assertEquals(1, lootTableObject.getPools().size());
        assertTrue(lootTableObject.getPools().get(0).getEntries().get(3).getLootFunction().isPresent());
        assertFalse(lootTableObject.getPools().get(0).getEntries().get(0).getLootFunction().isPresent());
        assertEquals(1, lootTableObject.getPools().get(0).getEntries().get(0).getWeight());

        assertThrows(NotALootTableException.class, () -> LootTableParserUtil.parseJsonObject(new JsonObject()));
    }
}