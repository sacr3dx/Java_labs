package com.coffeevan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.coffeevan.comparator.PriceToWeightComparator;
import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.domain.CoffeeVan;
import com.coffeevan.domain.GrindSize;
import com.coffeevan.domain.GroundCoffee;

class VanLoadingServiceTest {

    private final VanLoadingService service = new VanLoadingService();

    private CoffeeProduct product(String sort, double price, double weightKg) {
        return new GroundCoffee(sort, BigDecimal.valueOf(price), weightKg, 70, GrindSize.MEDIUM);
    }

    @Test
    void loadsAllProductsWhenCapacityAndBudgetAreEnough() {
        CoffeeVan van = new CoffeeVan(100.0, BigDecimal.valueOf(10_000));
        List<CoffeeProduct> candidates = List.of(
            product("A", 100, 1.0), product("B", 200, 1.0), product("C", 300, 1.0));

        service.loadVan(van, candidates, new PriceToWeightComparator());

        assertEquals(3, van.getLoadedProducts().size());
    }

    @Test
    void skipsProductsThatDoNotFitVolume() {
        CoffeeVan van = new CoffeeVan(1.5, BigDecimal.valueOf(10_000));
        List<CoffeeProduct> candidates = List.of(
            product("A", 100, 1.0), product("B", 200, 1.0), product("C", 300, 1.0));

        service.loadVan(van, candidates, new PriceToWeightComparator());

        assertTrue(van.getUsedVolumeLiters() <= 1.5);
        assertTrue(van.getLoadedProducts().size() < 3);
    }

    @Test
    void skipsProductsThatDoNotFitBudget() {
        CoffeeVan van = new CoffeeVan(100.0, BigDecimal.valueOf(250));
        List<CoffeeProduct> candidates = List.of(
            product("A", 100, 1.0), product("B", 200, 1.0), product("C", 300, 1.0));

        service.loadVan(van, candidates, new PriceToWeightComparator());

        assertTrue(van.getSpentAmount().compareTo(BigDecimal.valueOf(250)) <= 0);
    }

    @Test
    void doesNotExceedCapacityEvenWhenCandidateListIsLarge() {
        CoffeeVan van = new CoffeeVan(5.0, BigDecimal.valueOf(1_000_000));
        List<CoffeeProduct> candidates = List.of(
            product("A", 100, 2.0), product("B", 100, 2.0),
            product("C", 100, 2.0), product("D", 100, 2.0));

        service.loadVan(van, candidates, new PriceToWeightComparator());

        assertTrue(van.getUsedVolumeLiters() <= 5.0);
    }
}
