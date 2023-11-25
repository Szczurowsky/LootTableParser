package pl.szczurowsky.loottableparser.exception;

public class NotALootTableException extends Exception {

    public NotALootTableException() {
        super("Provided JSON Object is not a valid Loot Table");
    }
}
