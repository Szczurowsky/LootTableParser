package pl.szczurowsky.loottableparser.util.paper;


import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczurowsky.loottableparser.exception.NotALootTableException;
import pl.szczurowsky.loottableparser.pojo.LootEntry;
import pl.szczurowsky.loottableparser.pojo.LootTableObject;
import pl.szczurowsky.loottableparser.util.LootTableParserUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HandleFunctionUtilTest {

    private ServerMock serverMock;
    private LootTableObject lootTableObject;

    @BeforeEach
    void setUp() throws NotALootTableException {
        this.serverMock = MockBukkit.mock();
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
                            },
                            {
                              "function": "minecraft:set_name",
                              "name": {
                                "text": "Test",
                                "bold": true
                              }
                            }
                          ]
                        }
                      ]
                    },
                    {
                      "rolls": 1,
                      "entries": [
                        {
                          "type": "minecraft:item",
                          "name": "minecraft:cobblestone",
                          "functions": [
                            {
                              "function": "minecraft:set_name",
                              "name": [
                                {
                                  "text": "test",
                                  "bold": true,
                                  "italic": false
                                },
                                {
                                  "text": "xd",
                                  "bold": true,
                                  "italic": false,
                                  "underlined": true
                                }
                              ]
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

    @AfterEach
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    void handleFunction() {
        LootEntry entry = lootTableObject.getPools().get(0).getEntries().get(0);
        Optional<Material> material = Optional.ofNullable(Material.getMaterial(entry.getName().replace("minecraft:", "").toUpperCase()));
        assertTrue(material.isPresent());
        assertEquals(Material.STONE, material.get());
        assertEquals(2, entry.getLootFunction().get().size());
        ItemStack itemStack = new ItemStack(material.get(), 1);
        HandleFunctionUtil.handleFunction(itemStack, entry);
        assertEquals(3, itemStack.getAmount());
        assertTrue(itemStack.getItemMeta().displayName().toString().contains("bold=true"));
        LootEntry secondEntry = lootTableObject.getPools().get(1).getEntries().get(0);
        Optional<Material> secondMaterial = Optional.ofNullable(Material.getMaterial(secondEntry.getName().replace("minecraft:", "").toUpperCase()));
        assertTrue(secondMaterial.isPresent());
        assertEquals(Material.COBBLESTONE, secondMaterial.get());
        assertEquals(1, secondEntry.getLootFunction().get().size());
        ItemStack secondItemStack = new ItemStack(secondMaterial.get(), 1);
        HandleFunctionUtil.handleFunction(secondItemStack, secondEntry);
        assertTrue(secondItemStack.getItemMeta().displayName().toString().contains("bold=true"));
    }
}