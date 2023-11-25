package pl.szczurowsky.loottableparser.pojo.integer;

public class LootUniformInteger implements LootInteger {

    private final int min;
    private final int max;

    public LootUniformInteger(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public int getValue() {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
