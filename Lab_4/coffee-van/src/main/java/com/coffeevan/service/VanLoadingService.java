package com.coffeevan.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.domain.CoffeeVan;

public final class VanLoadingService {

    private static final Logger LOGGER = LogManager.getLogger(VanLoadingService.class);

    public void loadVan(CoffeeVan van, List<CoffeeProduct> candidates, Comparator<CoffeeProduct> order) {
        List<CoffeeProduct> sorted = new ArrayList<>(candidates);
        sorted.sort(order);

        int loaded = 0;
        for (CoffeeProduct candidate : sorted) {
            if (van.addProduct(candidate)) {
                loaded++;
                LOGGER.debug("Загружен товар: {}", candidate);
            } else {
                LOGGER.trace("Товар не поместился по объёму/бюджету: {}", candidate);
            }
        }

        LOGGER.info(
            "Загрузка завершена: {} из {} товаров, занято {} из {} л, потрачено {} из {}",
            loaded, candidates.size(), van.getUsedVolumeLiters(), van.getCapacityLiters(),
            van.getSpentAmount(), van.getBudget());
    }
}
