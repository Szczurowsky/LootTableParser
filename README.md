<div align="center">
<h1>LootTableParser</h1>
<h4>Parse loot table .json files into usable itemstacks easily</h4>
<img src="https://forthebadge.com/images/badges/made-with-java.svg" alt="Build with java">
<img src="https://forthebadge.com/images/badges/you-didnt-ask-for-this.svg" alt="No one asked for this">
</div>

## Usefull links
Helpful links:
- [GitHub issues](https://github.com/Szczurowsky/LootTableParser/issues)
- [Javadocs](https://szczurowsky.github.io/LootTableParser/)

## Paper version
- 1.20.1+

## Szczurowsky Repository (Maven or Gradle) ️
```xml
<repository>
    <id>szczurowsky-repository-releases</id>
    <name>Szczurowsky Repository</name>
    <url>https://repo.szczurowsky.pl/releases</url>
</repository>
```
```kotlin
maven {
    url = uri("https://repo.szczurowsky.pl/releases")
}
```

### Dependencies (Maven or Gradle)
Framework Core
```xml
<dependency>
    <groupId>pl.szczurowsky</groupId>
    <artifactId>loot-table-parse</artifactId>
    <version>1.0.1</version>
</dependency>
```
```kotlin
implementation("pl.szczurowsky:loot-table-parse:1.0.1")
```

### Important note!
It's worth to mention that library uses NBT-API for parsing NBT data, because of that we highly advise to shade NBT-API
into your plugin. <br><br>
<a href="https://github.com/tr7zw/Item-NBT-API/wiki/Using-Maven" target="_blank">Click here</a> to see how to shade NBT-API into your plugin.

## Usage

### Example use cases

<details>
<summary>Fetch loot table from CDN and give player items</summary>

```java
public class ExamplePlugin extends JavaPlugin {

    private ParsedLootTable parsedLootTable;
    
    @Override
    public void onEnable() {
        this.parsedLootTable = new ParsedLootTable(new URL("https://example.com/loot-table"));
        try {
            parsedLootTable.fetch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

```

```java
public class ExampleListener implements Listener {

    private final ParsedLootTable parsedLootTable;
    
    public ExampleListener(ParsedLootTable parsedLootTable) {
        this.parsedLootTable = parsedLootTable;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.parsedLootTable.getLoot(false).forEach(item -> player.getInventory().addItem(item));
    }
    
}
```

</details>
