package com.coffeevan.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.domain.CoffeeVan;

public final class QualityRangeFinder {

    private static final Logger LOGGER = LogManager.getLogger(QualityRangeFinder.class);

    public List<CoffeeProduct> findInRange(CoffeeVan van, int minQuality, int maxQuality) {
        if (minQuality > maxQuality) {
            throw new IllegalArgumentException(
                "minQuality (" + minQuality + ") не может быть больше maxQuality (" + maxQuality + ")");
        }
        List<CoffeeProduct> found = van.getLoadedProducts().stream()
            .filter(p -> p.getQuality() >= minQuality && p.getQuality() <= maxQuality)
            .collect(Collectors.toList());
        LOGGER.info("Поиск по качеству [{}; {}]: найдено {} товаров", minQuality, maxQuality, found.size());
        return found;
    }
}
