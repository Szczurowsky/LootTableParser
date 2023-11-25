package pl.szczurowsky.loottableparser.pojo;

public enum LootEntryType {

    ITEM("item", true),
    ALTERNATIVES("alternatives", false),
    DYNAMIC("dynamic", false),
    EMPTY("empty", false),
    GROUP("group", false),
    LOOT_TABLE("loot_table", false),
    SEQUENCE("sequence", false),
    TAG("tag", false);

    private final String entryType;
    private final boolean supported;

    LootEntryType(String entryType, boolean supported) {
        this.entryType = entryType;
        this.supported = supported;
    }

    public String getEntryType() {
        return entryType;
    }

    public boolean isSupported() {
        return supported;
    }

    public static LootEntryType getEntryType(String entryType) {
        for (LootEntryType type : values()) {
            if (entryType.contains(type.getEntryType())) {
                return type;
            }
        }
        return null;
    }
}
