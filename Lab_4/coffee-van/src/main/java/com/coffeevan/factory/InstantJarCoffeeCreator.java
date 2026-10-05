package com.coffeevan.factory;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.domain.InstantJarCoffee;
import com.coffeevan.domain.JarMaterial;
import com.coffeevan.io.CoffeeRecord;

public class InstantJarCoffeeCreator extends CoffeeCreator {

    private static final Logger LOGGER = LogManager.getLogger(InstantJarCoffeeCreator.class);
    private static final double DEFAULT_JAR_VOLUME_LITERS = 0.3;
    private static final JarMaterial DEFAULT_JAR_MATERIAL = JarMaterial.GLASS;

    @Override
    protected CoffeeProduct createCoffee(
        CoffeeRecord record, String sort, BigDecimal price, double netWeightKg, int quality) {
        double jarVolumeLiters = parseJarVolume(record);
        JarMaterial jarMaterial = parseJarMaterial(record);
        return new InstantJarCoffee(sort, price, netWeightKg, quality, jarVolumeLiters, jarMaterial);
    }

    private double parseJarVolume(CoffeeRecord record) {
        String raw = record.getField(4);
        try {
            double volume = Double.parseDouble(raw);
            if (volume <= 0) {
                throw new NumberFormatException("объём банки должен быть положительным: " + raw);
            }
            return volume;
        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.warn(
                "Строка {}: некорректный объём банки '{}', используется значение по умолчанию {}",
                record.getLineNumber(), raw, DEFAULT_JAR_VOLUME_LITERS);
            return DEFAULT_JAR_VOLUME_LITERS;
        }
    }

    private JarMaterial parseJarMaterial(CoffeeRecord record) {
        String raw = record.getField(5);
        try {
            return JarMaterial.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            LOGGER.warn(
                "Строка {}: некорректный материал банки '{}', используется значение по умолчанию {}",
                record.getLineNumber(), raw, DEFAULT_JAR_MATERIAL);
            return DEFAULT_JAR_MATERIAL;
        }
    }
}
