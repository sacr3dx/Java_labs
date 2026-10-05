package com.coffeevan.factory;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coffeevan.domain.BagMaterial;
import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.domain.GrainCoffee;
import com.coffeevan.domain.RoastLevel;
import com.coffeevan.io.CoffeeRecord;

public class GrainCoffeeCreator extends CoffeeCreator {

    private static final Logger LOGGER = LogManager.getLogger(GrainCoffeeCreator.class);
    private static final RoastLevel DEFAULT_ROAST_LEVEL = RoastLevel.MEDIUM;
    private static final BagMaterial DEFAULT_BAG_MATERIAL = BagMaterial.PAPER;

    @Override
    protected CoffeeProduct createCoffee(
        CoffeeRecord record, String sort, BigDecimal price, double netWeightKg, int quality) {
        RoastLevel roastLevel = parseRoastLevel(record);
        BagMaterial bagMaterial = parseBagMaterial(record);
        return new GrainCoffee(sort, price, netWeightKg, quality, roastLevel, bagMaterial);
    }

    private RoastLevel parseRoastLevel(CoffeeRecord record) {
        String raw = record.getField(4);
        try {
            return RoastLevel.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            LOGGER.warn(
                "Строка {}: некорректная степень обжарки '{}', используется значение по умолчанию {}",
                record.getLineNumber(), raw, DEFAULT_ROAST_LEVEL);
            return DEFAULT_ROAST_LEVEL;
        }
    }

    private BagMaterial parseBagMaterial(CoffeeRecord record) {
        String raw = record.getField(5);
        try {
            return BagMaterial.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            LOGGER.warn(
                "Строка {}: некорректный материал пакета '{}', используется значение по умолчанию {}",
                record.getLineNumber(), raw, DEFAULT_BAG_MATERIAL);
            return DEFAULT_BAG_MATERIAL;
        }
    }
}
