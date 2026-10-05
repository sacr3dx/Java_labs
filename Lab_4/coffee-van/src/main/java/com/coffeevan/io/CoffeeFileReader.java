package com.coffeevan.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CoffeeFileReader {

    private static final Logger LOGGER = LogManager.getLogger(CoffeeFileReader.class);
    private static final int MIN_REQUIRED_FIELDS = 4; // sort;price;weight;quality

    public List<CoffeeRecord> readRecords(Path file) throws IOException {
        List<CoffeeRecord> records = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                CoffeeRecord record = parseLine(lineNumber, line);
                if (record != null) {
                    records.add(record);
                }
            }
        }
        LOGGER.info("Прочитано строк файла: {}, из них распознано как записи: {}",
            countNonBlankLines(file), records.size());
        return records;
    }

    private CoffeeRecord parseLine(int lineNumber, String rawLine) {
        String line = rawLine.trim();
        if (line.isEmpty() || line.startsWith("#")) {
            LOGGER.trace("Строка {}: пустая или комментарий, пропущена", lineNumber);
            return null;
        }

        List<String> tokens = Arrays.stream(line.split(";", -1))
            .map(String::trim)
            .collect(Collectors.toList());

        String type = tokens.get(0);
        if (type.isEmpty()) {
            LOGGER.warn("Строка {}: не указан тип товара, строка проигнорирована: '{}'",
                lineNumber, rawLine);
            return null;
        }

        List<String> fields = tokens.subList(1, tokens.size());
        if (fields.size() < MIN_REQUIRED_FIELDS) {
            LOGGER.warn(
                "Строка {}: недостаточно полей ({} из {} обязательных), строка проигнорирована: '{}'",
                lineNumber, fields.size(), MIN_REQUIRED_FIELDS, rawLine);
            return null;
        }

        return new CoffeeRecord(lineNumber, type, fields);
    }

    private long countNonBlankLines(Path file) throws IOException {
        try (var lines = Files.lines(file)) {
            return lines.map(String::trim)
                .filter(l -> !l.isEmpty() && !l.startsWith("#"))
                .count();
        }
    }
}
