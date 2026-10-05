package com.coffeevan.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.coffeevan.domain.BagMaterial;
import com.coffeevan.domain.GrainCoffee;
import com.coffeevan.domain.RoastLevel;
import com.coffeevan.io.CoffeeRecord;

class GrainCoffeeCreatorTest {

    private final GrainCoffeeCreator creator = new GrainCoffeeCreator();

    @Test
    void createsProductFromValidRecord() {
        CoffeeRecord record = new CoffeeRecord(
            1, "grain", List.of("Arabica", "800", "1.0", "90", "DARK", "FOIL"));

        GrainCoffee coffee = (GrainCoffee) creator.create(record);

        assertEquals("Arabica", coffee.getSort());
        assertEquals(BigDecimal.valueOf(800), coffee.getPrice());
        assertEquals(1.0, coffee.getNetWeightKg(), 1e-9);
        assertEquals(90, coffee.getQuality());
        assertEquals(RoastLevel.DARK, coffee.getRoastLevel());
        assertEquals(BagMaterial.FOIL, coffee.getBagMaterial());
    }

    @Test
    void appliesDefaultRoastLevelWhenInvalid() {
        CoffeeRecord record = new CoffeeRecord(
            1, "grain", List.of("Arabica", "800", "1.0", "90", "ULTRA_DARK", "FOIL"));

        GrainCoffee coffee = (GrainCoffee) creator.create(record);

        assertEquals(RoastLevel.MEDIUM, coffee.getRoastLevel());
    }

    @Test
    void appliesDefaultBagMaterialWhenMissing() {
        CoffeeRecord record = new CoffeeRecord(
            1, "grain", List.of("Arabica", "800", "1.0", "90", "LIGHT"));

        GrainCoffee coffee = (GrainCoffee) creator.create(record);

        assertEquals(BagMaterial.PAPER, coffee.getBagMaterial());
    }

    @Test
    void appliesDefaultPriceWhenNegative() {
        CoffeeRecord record = new CoffeeRecord(
            1, "grain", List.of("Arabica", "-5", "1.0", "90", "LIGHT", "PAPER"));

        GrainCoffee coffee = (GrainCoffee) creator.create(record);

        assertEquals(BigDecimal.valueOf(100), coffee.getPrice());
    }

    @Test
    void appliesDefaultQualityWhenOutOfRange() {
        CoffeeRecord record = new CoffeeRecord(
            1, "grain", List.of("Arabica", "800", "1.0", "150", "LIGHT", "PAPER"));

        GrainCoffee coffee = (GrainCoffee) creator.create(record);

        assertEquals(50, coffee.getQuality());
    }
}
