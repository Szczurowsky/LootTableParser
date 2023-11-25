package pl.szczurowsky.loottableparser.pojo;

import com.google.gson.JsonArray;

import java.util.Optional;

public class LootEntry {
    LootEntryType type;
    String name;
    Integer weight;
    JsonArray lootFunction;

    public LootEntry(LootEntryType type, String name, Integer weight, JsonArray lootFunction) {
        this.type = type;
        this.name = name;
        this.weight = weight;
        this.lootFunction = lootFunction;
    }

    public LootEntry(LootEntryType type, String name, Integer weight) {
        this.type = type;
        this.name = name;
        this.weight = weight;
    }

    public LootEntryType getType() {
        return type;
    }

    public void setType(LootEntryType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Optional<JsonArray> getLootFunction() {
        return Optional.ofNullable(lootFunction);
    }

    public void setLootFunction(JsonArray lootFunction) {
        this.lootFunction = lootFunction;
    }

}
