package com.coffeevan.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.coffeevan.domain.GrindSize;
import com.coffeevan.domain.GroundCoffee;
import com.coffeevan.io.CoffeeRecord;

class GroundCoffeeCreatorTest {

    private final GroundCoffeeCreator creator = new GroundCoffeeCreator();

    @Test
    void createsProductFromValidRecord() {
        CoffeeRecord record = new CoffeeRecord(
            1, "ground", List.of("Espresso", "600", "0.25", "80", "FINE"));

        GroundCoffee coffee = (GroundCoffee) creator.create(record);

        assertEquals(GrindSize.FINE, coffee.getGrindSize());
    }

    @Test
    void appliesDefaultGrindSizeWhenInvalid() {
        CoffeeRecord record = new CoffeeRecord(
            1, "ground", List.of("Espresso", "600", "0.25", "80", "VERY_FINE"));

        GroundCoffee coffee = (GroundCoffee) creator.create(record);

        assertEquals(GrindSize.MEDIUM, coffee.getGrindSize());
    }

    @Test
    void appliesDefaultSortWhenBlank() {
        CoffeeRecord record = new CoffeeRecord(
            1, "ground", List.of("", "600", "0.25", "80", "FINE"));

        GroundCoffee coffee = (GroundCoffee) creator.create(record);

        assertEquals("Unknown", coffee.getSort());
    }

    @Test
    void appliesDefaultWeightWhenNotANumber() {
        CoffeeRecord record = new CoffeeRecord(
            1, "ground", List.of("Espresso", "600", "abc", "80", "FINE"));

        GroundCoffee coffee = (GroundCoffee) creator.create(record);

        assertEquals(0.25, coffee.getNetWeightKg(), 1e-9);
    }
}
