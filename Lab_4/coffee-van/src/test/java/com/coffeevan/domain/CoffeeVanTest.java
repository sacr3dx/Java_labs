package com.coffeevan.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class CoffeeVanTest {

    private CoffeeProduct product(double price, double weightKg) {
        return new GroundCoffee("Test", BigDecimal.valueOf(price), weightKg, 70, GrindSize.MEDIUM);
    }

    @Test
    void addsProductWhenItFitsVolumeAndBudget() {
        CoffeeVan van = new CoffeeVan(10.0, BigDecimal.valueOf(1000));

        assertTrue(van.addProduct(product(100, 1.0)));
        assertEquals(1, van.getLoadedProducts().size());
    }

    @Test
    void rejectsProductWhenVolumeExceeded() {
        CoffeeVan van = new CoffeeVan(0.5, BigDecimal.valueOf(1000));

        assertFalse(van.addProduct(product(100, 1.0)));
        assertEquals(0, van.getLoadedProducts().size());
    }

    @Test
    void rejectsProductWhenBudgetExceeded() {
        CoffeeVan van = new CoffeeVan(10.0, BigDecimal.valueOf(50));

        assertFalse(van.addProduct(product(100, 0.1)));
        assertEquals(0, van.getLoadedProducts().size());
    }

    @Test
    void tracksUsedVolumeAndSpentAmount() {
        CoffeeVan van = new CoffeeVan(10.0, BigDecimal.valueOf(1000));
        CoffeeProduct a = product(100, 1.0);
        CoffeeProduct b = product(200, 0.5);

        van.addProduct(a);
        van.addProduct(b);

        assertEquals(a.getTotalVolumeLiters() + b.getTotalVolumeLiters(), van.getUsedVolumeLiters(), 1e-9);
        assertEquals(0, van.getSpentAmount().compareTo(BigDecimal.valueOf(300)));
    }
}
