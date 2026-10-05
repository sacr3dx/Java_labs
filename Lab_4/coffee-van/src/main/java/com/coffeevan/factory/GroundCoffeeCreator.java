package com.coffeevan.factory;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.domain.GrindSize;
import com.coffeevan.domain.GroundCoffee;
import com.coffeevan.io.CoffeeRecord;

public class GroundCoffeeCreator extends CoffeeCreator {

    private static final Logger LOGGER = LogManager.getLogger(GroundCoffeeCreator.class);
    private static final GrindSize DEFAULT_GRIND_SIZE = GrindSize.MEDIUM;

    @Override
    protected CoffeeProduct createCoffee(
        CoffeeRecord record, String sort, BigDecimal price, double netWeightKg, int quality) {
        GrindSize grindSize = parseGrindSize(record);
        return new GroundCoffee(sort, price, netWeightKg, quality, grindSize);
    }

    private GrindSize parseGrindSize(CoffeeRecord record) {
        String raw = record.getField(4);
        try {
            return GrindSize.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            LOGGER.warn(
                "Строка {}: некорректная степень помола '{}', используется значение по умолчанию {}",
                record.getLineNumber(), raw, DEFAULT_GRIND_SIZE);
            return DEFAULT_GRIND_SIZE;
        }
    }
}
