package com.coffeevan.factory;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.io.CoffeeRecord;

public abstract class CoffeeCreator {

    protected static final String DEFAULT_SORT = "Unknown";
    protected static final BigDecimal DEFAULT_PRICE = BigDecimal.valueOf(100);
    protected static final double DEFAULT_WEIGHT_KG = 0.25;
    protected static final int DEFAULT_QUALITY = 50;

    private static final Logger LOGGER = LogManager.getLogger(CoffeeCreator.class);

    public final CoffeeProduct create(CoffeeRecord record) {
        String sort = parseSort(record);
        BigDecimal price = parsePrice(record);
        double weight = parseWeight(record);
        int quality = parseQuality(record);
        return createCoffee(record, sort, price, weight, quality);
    }

    protected abstract CoffeeProduct createCoffee(
        CoffeeRecord record, String sort, BigDecimal price, double netWeightKg, int quality);

    protected String parseSort(CoffeeRecord record) {
        String raw = record.getField(0);
        if (raw == null || raw.isEmpty()) {
            LOGGER.warn("Строка {}: не указан сорт, используется значение по умолчанию '{}'",
                record.getLineNumber(), DEFAULT_SORT);
            return DEFAULT_SORT;
        }
        return raw;
    }

    protected BigDecimal parsePrice(CoffeeRecord record) {
        String raw = record.getField(1);
        try {
            BigDecimal price = new BigDecimal(raw);
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new NumberFormatException("цена должна быть положительной: " + raw);
            }
            return price;
        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.warn("Строка {}: некорректная цена '{}', используется значение по умолчанию {}",
                record.getLineNumber(), raw, DEFAULT_PRICE);
            return DEFAULT_PRICE;
        }
    }

    protected double parseWeight(CoffeeRecord record) {
        String raw = record.getField(2);
        try {
            double weight = Double.parseDouble(raw);
            if (weight <= 0) {
                throw new NumberFormatException("вес должен быть положительным: " + raw);
            }
            return weight;
        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.warn("Строка {}: некорректный вес '{}', используется значение по умолчанию {}",
                record.getLineNumber(), raw, DEFAULT_WEIGHT_KG);
            return DEFAULT_WEIGHT_KG;
        }
    }

    protected int parseQuality(CoffeeRecord record) {
        String raw = record.getField(3);
        try {
            int quality = Integer.parseInt(raw);
            if (quality < 0 || quality > 100) {
                throw new NumberFormatException("качество вне диапазона 0..100: " + raw);
            }
            return quality;
        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.warn(
                "Строка {}: некорректное качество '{}', используется значение по умолчанию {}",
                record.getLineNumber(), raw, DEFAULT_QUALITY);
            return DEFAULT_QUALITY;
        }
    }

    protected static Logger logger() {
        return LOGGER;
    }
}
