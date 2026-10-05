package com.coffeevan.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoffeeFileReaderTest {

    private final CoffeeFileReader reader = new CoffeeFileReader();
    private Path tempFile;

    @BeforeEach
    void createTempFile() throws IOException {
        tempFile = Files.createTempFile("coffee-data", ".txt");
    }

    @AfterEach
    void deleteTempFile() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    private void writeLines(String... lines) throws IOException {
        Files.write(tempFile, List.of(lines));
    }

    @Test
    void parsesValidLineIntoRecord() throws IOException {
        writeLines("grain;Arabica;800;1.0;90;DARK;FOIL");

        List<CoffeeRecord> records = reader.readRecords(tempFile);

        assertEquals(1, records.size());
        assertEquals("grain", records.get(0).getType());
        assertEquals("Arabica", records.get(0).getField(0));
    }

    @Test
    void skipsBlankLinesAndComments() throws IOException {
        writeLines(
            "# comment",
            "",
            "grain;Arabica;800;1.0;90;DARK;FOIL");

        List<CoffeeRecord> records = reader.readRecords(tempFile);

        assertEquals(1, records.size());
    }

    @Test
    void ignoresLineWithMissingType() throws IOException {
        writeLines(";Arabica;800;1.0;90");

        List<CoffeeRecord> records = reader.readRecords(tempFile);

        assertEquals(0, records.size());
    }

    @Test
    void ignoresLineWithTooFewFields() throws IOException {
        writeLines("grain;Arabica;800");

        List<CoffeeRecord> records = reader.readRecords(tempFile);

        assertEquals(0, records.size());
    }

    @Test
    void keepsLineWithMinimumRequiredFieldsEvenWithoutExtras() throws IOException {
        writeLines("ground;Espresso;600;0.25;80");

        List<CoffeeRecord> records = reader.readRecords(tempFile);

        assertEquals(1, records.size());
        assertEquals(4, records.get(0).getFieldCount());
    }
}
