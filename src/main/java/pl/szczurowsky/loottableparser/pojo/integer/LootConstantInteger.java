package pl.szczurowsky.loottableparser.pojo.integer;

public class LootConstantInteger implements LootInteger {

    private final int value;

    public LootConstantInteger(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

}
