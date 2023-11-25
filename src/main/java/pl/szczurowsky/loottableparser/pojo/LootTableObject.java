package pl.szczurowsky.loottableparser.pojo;

import java.util.List;

public class LootTableObject {

    LootContextType type;
    List<LootPool> pools;

    public LootTableObject(LootContextType type, List<LootPool> pools) {
        this.type = type;
        this.pools = pools;
    }

    public LootContextType getType() {
        return type;
    }

    public List<LootPool> getPools() {
        return pools;
    }

    public void setType(LootContextType type) {
        this.type = type;
    }

    public void setPools(List<LootPool> pools) {
        this.pools = pools;
    }

}
