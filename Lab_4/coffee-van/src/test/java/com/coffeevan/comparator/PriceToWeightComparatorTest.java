package com.coffeevan.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.domain.GrindSize;
import com.coffeevan.domain.GroundCoffee;

class PriceToWeightComparatorTest {

    private CoffeeProduct product(String sort, double price, double weightKg) {
        return new GroundCoffee(sort, BigDecimal.valueOf(price), weightKg, 70, GrindSize.MEDIUM);
    }

    @Test
    void sortsAscendingByPricePerKilogram() {
        CoffeeProduct cheap = product("Cheap", 100, 1.0);
        CoffeeProduct medium = product("Medium", 300, 1.0);
        CoffeeProduct expensive = product("Expensive", 100, 0.1);

        List<CoffeeProduct> list = new ArrayList<>(List.of(expensive, cheap, medium));
        list.sort(new PriceToWeightComparator());

        assertEquals(List.of(cheap, medium, expensive), list);
    }

    @Test
    void treatsEqualRatioAsEqual() {
        CoffeeProduct a = product("A", 100, 1.0);
        CoffeeProduct b = product("B", 200, 2.0);

        assertEquals(0, new PriceToWeightComparator().compare(a, b));
    }
}
