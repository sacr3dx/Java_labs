package com.coffeevan.factory;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.domain.InstantSachetCoffee;
import com.coffeevan.io.CoffeeRecord;

public class InstantSachetCoffeeCreator extends CoffeeCreator {

    private static final Logger LOGGER = LogManager.getLogger(InstantSachetCoffeeCreator.class);
    private static final int DEFAULT_SACHET_COUNT = 20;
    private static final double DEFAULT_SACHET_WRAPPER_VOLUME_LITERS = 0.005;

    @Override
    protected CoffeeProduct createCoffee(
        CoffeeRecord record, String sort, BigDecimal price, double netWeightKg, int quality) {
        int sachetCount = parseSachetCount(record);
        double sachetVolume = parseSachetWrapperVolume(record);
        return new InstantSachetCoffee(sort, price, netWeightKg, quality, sachetCount, sachetVolume);
    }

    private int parseSachetCount(CoffeeRecord record) {
        String raw = record.getField(4);
        try {
            int count = Integer.parseInt(raw);
            if (count <= 0) {
                throw new NumberFormatException("число пакетиков должно быть положительным: " + raw);
            }
            return count;
        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.warn(
                "Строка {}: некорректное число пакетиков '{}', используется значение по умолчанию {}",
                record.getLineNumber(), raw, DEFAULT_SACHET_COUNT);
            return DEFAULT_SACHET_COUNT;
        }
    }

    private double parseSachetWrapperVolume(CoffeeRecord record) {
        String raw = record.getField(5);
        try {
            double volume = Double.parseDouble(raw);
            if (volume <= 0) {
                throw new NumberFormatException("объём обёртки должен быть положительным: " + raw);
            }
            return volume;
        } catch (NumberFormatException | NullPointerException e) {
            LOGGER.warn(
                "Строка {}: некорректный объём обёртки пакетика '{}', используется значение по умолчанию {}",
                record.getLineNumber(), raw, DEFAULT_SACHET_WRAPPER_VOLUME_LITERS);
            return DEFAULT_SACHET_WRAPPER_VOLUME_LITERS;
        }
    }
}
