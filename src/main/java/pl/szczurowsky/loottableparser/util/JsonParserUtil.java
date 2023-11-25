package pl.szczurowsky.loottableparser.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Scanner;

/**
 * Utility class for parsing JSON files.
 */
public class JsonParserUtil {

    /**
     * Private constructor to prevent creating instances of this class.
     */
    private JsonParserUtil() {
    }

    /**
     * Reads JSON file and returns it as JsonObject.
     *
     * @param file JSON file to read.
     * @return JsonObject representing the file.
     */
    public static JsonObject readFromFile(File file) {
        if (!file.canRead())
            throw new RuntimeException("Cannot read file: " + file.getAbsolutePath());
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
            return new Gson().fromJson(builder.toString(), JsonObject.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads JSON file from URL and returns it as JsonObject.
     * @param siteUrl URL to read.
     * @return JsonObject representing the file.
     */
    public static JsonObject readFromUrl(URL siteUrl) {
        try {
            try(Scanner scanner = new Scanner(siteUrl.openStream())) {
                StringBuilder builder = new StringBuilder();
                while (scanner.hasNextLine()) {
                    builder.append(scanner.nextLine());
                }
                return new Gson().fromJson(builder.toString(), JsonObject.class);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads JSON from string and returns it as JsonObject.
     * @param json JSON to read.
     * @return JsonObject representing the file.
     */
    public static JsonObject readFromString(String json) {
        return new Gson().fromJson(json, JsonObject.class);
    }

}
