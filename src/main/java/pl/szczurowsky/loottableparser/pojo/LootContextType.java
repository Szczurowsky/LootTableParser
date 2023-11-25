package pl.szczurowsky.loottableparser.pojo;

import org.jetbrains.annotations.Nullable;

public enum LootContextType {

    EMPTY("empty"),
    CHEST("chest"),
    COMMAND("command"),
    SELECTOR("selector"),
    FISHING("fishing"),
    ENTITY("entity"),
    ARCHEOLOGY("archeology"),
    GIFT("gift"),
    BARTER("barter"),
    ADVANCEMENT_REWARD("advancement_reward"),
    ADVANCEMENT_ENTITY("advancement_entity"),
    ADVANCEMENT_LOCATION("advancement_location"),
    GENERIC("generic"),
    BLOCK("block");

    private final String lootContextType;

    LootContextType(String lootContextType) {
        this.lootContextType = lootContextType;
    }

    public String getLootContextType() {
        return lootContextType;
    }

    @Nullable
    public static LootContextType getLootContextType(String lootContextType) {
        for (LootContextType type : LootContextType.values()) {
            if (lootContextType.contains(type.getLootContextType())) {
                return type;
            }
        }
        return null;
    }
}
