package pl.szczurowsky.loottableparser.pojo;

import pl.szczurowsky.loottableparser.pojo.integer.LootInteger;

import java.util.List;

public class LootPool {

    LootInteger rolls;
    LootInteger bonusRolls;
    List<LootEntry> entries;

    public LootPool(LootInteger rolls, LootInteger bonusRolls, List<LootEntry> entries) {
        this.rolls = rolls;
        this.bonusRolls = bonusRolls;
        this.entries = entries;
    }

    public LootInteger getRolls() {
        return rolls;
    }

    public void setRolls(LootInteger rolls) {
        this.rolls = rolls;
    }

    public LootInteger getBonusRolls() {
        return bonusRolls;
    }

    public void setBonusRolls(LootInteger bonusRolls) {
        this.bonusRolls = bonusRolls;
    }

    public List<LootEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LootEntry> entries) {
        this.entries = entries;
    }
}
