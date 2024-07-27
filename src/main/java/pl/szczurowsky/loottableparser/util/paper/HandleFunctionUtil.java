package pl.szczurowsky.loottableparser.util.paper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.tr7zw.changeme.nbtapi.NBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.szczurowsky.loottableparser.pojo.LootEntry;
import pl.szczurowsky.loottableparser.pojo.integer.LootUniformInteger;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling loot functions
 */
public class HandleFunctionUtil {

    /**
     * Private constructor to hide implicit public one
     */
    private HandleFunctionUtil() {
    }

    /**
     * Handles loot function for given item stack and loot entry
     * @param itemStack item stack to handle function for
     * @param lootEntry loot entry to handle function for
     */
    public static void handleFunction(ItemStack itemStack, LootEntry lootEntry) {
        if (lootEntry.getLootFunction().isEmpty()) return;
        lootEntry.getLootFunction().get().forEach(jsonElement -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String function = jsonObject.get("function").getAsString();
            if (!function.contains(":")) function = "minecraft:" + function;
            try {
                switch (function) {
                    case "minecraft:set_count" -> {
                        int count;
                        if (!jsonObject.has("count")) {
                            count = 1;
                        } else if(jsonObject.get("count").isJsonObject()) {
                            count = new LootUniformInteger(jsonObject.getAsJsonObject("count").get("min").getAsInt(), jsonObject.getAsJsonObject("count").get("max").getAsInt()).getValue();
                        } else {
                            count = jsonObject.get("count").getAsInt();
                        }
                        boolean add;
                        if (jsonObject.has("add")) add = jsonObject.get("add").getAsBoolean();
                        else add = false;
                        if (add) itemStack.setAmount(itemStack.getAmount() + count);
                        else itemStack.setAmount(count);
                    }
                    case "minecraft:set_damage" -> {
                        int damage = jsonObject.get("damage").getAsInt();
                        boolean add;
                        if (jsonObject.has("add")) add = jsonObject.get("add").getAsBoolean();
                        else add = false;
                        if (add) itemStack.setDurability((short) (itemStack.getDurability() + damage));
                        else itemStack.setDurability((short) damage);
                    }
                    case "minecraft:set_nbt" -> {
                        String tag = jsonObject.get("tag").getAsString();
                        NBT.modify(itemStack, nbt -> {
                            nbt.mergeCompound(NBT.parseNBT(tag));
                        });
                    }
                    case "minecraft:set_name" -> {
                        if (jsonObject.get("name").isJsonArray()) {
                            JsonArray name = jsonObject.getAsJsonArray("name");
                            List<Component> textComponents = new ArrayList<>();
                            name.forEach(jsonElement1 -> textComponents.add(JSONComponentSerializer.json().deserialize(jsonElement1.toString())));
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(textComponents.get(0));
                            itemStack.setItemMeta(itemMeta);
                        } else if (jsonObject.get("name").isJsonObject()) {
                            Component textComponent = JSONComponentSerializer.json().deserialize(jsonObject.getAsJsonObject("name").toString());
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(textComponent);
                            itemStack.setItemMeta(itemMeta);
                        } else {
                            String name = jsonObject.get("name").getAsString();
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(JSONComponentSerializer.json().deserialize("{\"text\":\"" + name + "\"}"));
                            itemStack.setItemMeta(itemMeta);
                        }
                    }
                    case "minecraft:set_lore" -> {
                        JsonArray lore = jsonObject.getAsJsonArray("lore");
                        List<Component> textComponents = new ArrayList<>();
                        lore.forEach(jsonElement1 -> textComponents.add(JSONComponentSerializer.json().deserialize(jsonElement1.toString())));
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.lore(textComponents);
                        itemStack.setItemMeta(itemMeta);
                    }
                    default -> {}
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
