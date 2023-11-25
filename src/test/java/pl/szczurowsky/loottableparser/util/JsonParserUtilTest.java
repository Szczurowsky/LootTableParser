package pl.szczurowsky.loottableparser.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserUtilTest {

    @Test
    void readFromFile() {
        File file = new File("src/test/resources/JsonParserUtilTest.json");
        assertTrue(file.exists());
        assertEquals(JsonParserUtil.readFromFile(file).toString(), "{\"one\":\"two\",\"key\":\"value\"}");
    }

    @Test
    void readFromUrl() {
        String url ="http://echo.jsontest.com/key/value/one/two";
        try {
            assertEquals(JsonParserUtil.readFromUrl(new URL(url)).toString(), "{\"one\":\"two\",\"key\":\"value\"}");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}