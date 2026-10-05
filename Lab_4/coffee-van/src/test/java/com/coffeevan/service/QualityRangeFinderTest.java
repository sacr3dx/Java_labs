package com.coffeevan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.domain.CoffeeVan;
import com.coffeevan.domain.GrindSize;
import com.coffeevan.domain.GroundCoffee;

class QualityRangeFinderTest {

    private final QualityRangeFinder finder = new QualityRangeFinder();

    private CoffeeProduct product(String sort, int quality) {
        return new GroundCoffee(sort, BigDecimal.valueOf(100), 1.0, quality, GrindSize.MEDIUM);
    }

    private CoffeeVan vanWith(CoffeeProduct... products) {
        CoffeeVan van = new CoffeeVan(1000.0, BigDecimal.valueOf(1_000_000));
        for (CoffeeProduct product : products) {
            van.addProduct(product);
        }
        return van;
    }

    @Test
    void findsProductsWithinInclusiveRange() {
        CoffeeVan van = vanWith(product("Low", 50), product("Mid", 75), product("High", 95));

        List<CoffeeProduct> found = finder.findInRange(van, 70, 90);

        assertEquals(1, found.size());
        assertEquals("Mid", found.get(0).getSort());
    }

    @Test
    void boundariesAreInclusive() {
        CoffeeVan van = vanWith(product("AtMin", 70), product("AtMax", 90));

        List<CoffeeProduct> found = finder.findInRange(van, 70, 90);

        assertEquals(2, found.size());
    }

    @Test
    void returnsEmptyListWhenNothingMatches() {
        CoffeeVan van = vanWith(product("Low", 10), product("High", 99));

        List<CoffeeProduct> found = finder.findInRange(van, 40, 60);

        assertEquals(0, found.size());
    }

    @Test
    void throwsWhenMinGreaterThanMax() {
        CoffeeVan van = vanWith(product("Any", 50));

        assertThrows(IllegalArgumentException.class, () -> finder.findInRange(van, 90, 10));
    }
}
