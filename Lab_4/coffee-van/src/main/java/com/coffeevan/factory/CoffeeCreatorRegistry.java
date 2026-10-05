package com.coffeevan.factory;

import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.io.CoffeeRecord;

public final class CoffeeCreatorRegistry {

    public static final String TYPE_GRAIN = "grain";
    public static final String TYPE_GROUND = "ground";
    public static final String TYPE_INSTANT_JAR = "instant_jar";
    public static final String TYPE_INSTANT_SACHET = "instant_sachet";

    private static final Logger LOGGER = LogManager.getLogger(CoffeeCreatorRegistry.class);

    private final Map<String, CoffeeCreator> creatorsByType = Map.of(
        TYPE_GRAIN, new GrainCoffeeCreator(),
        TYPE_GROUND, new GroundCoffeeCreator(),
        TYPE_INSTANT_JAR, new InstantJarCoffeeCreator(),
        TYPE_INSTANT_SACHET, new InstantSachetCoffeeCreator());

    /**
     * @return созданный товар или null, если тип неизвестен (запись игнорируется)
     */
    public CoffeeProduct create(CoffeeRecord record) {
        String type = record.getType().toLowerCase(Locale.ROOT);
        CoffeeCreator creator = creatorsByType.get(type);
        if (creator == null) {
            LOGGER.error("Строка {}: неизвестный тип товара '{}', строка проигнорирована",
                record.getLineNumber(), record.getType());
            return null;
        }
        return creator.create(record);
    }
}
