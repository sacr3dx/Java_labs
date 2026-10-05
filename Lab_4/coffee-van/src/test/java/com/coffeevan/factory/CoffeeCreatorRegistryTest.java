package com.coffeevan.factory;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.domain.GrainCoffee;
import com.coffeevan.domain.GroundCoffee;
import com.coffeevan.domain.InstantJarCoffee;
import com.coffeevan.domain.InstantSachetCoffee;
import com.coffeevan.io.CoffeeRecord;

class CoffeeCreatorRegistryTest {

    private final CoffeeCreatorRegistry registry = new CoffeeCreatorRegistry();

    @Test
    void dispatchesGrainToGrainCoffee() {
        CoffeeRecord record = new CoffeeRecord(
            1, "grain", List.of("Arabica", "800", "1.0", "90", "DARK", "FOIL"));

        assertInstanceOf(GrainCoffee.class, registry.create(record));
    }

    @Test
    void dispatchesGroundToGroundCoffee() {
        CoffeeRecord record = new CoffeeRecord(
            1, "ground", List.of("Espresso", "600", "0.25", "80", "FINE"));

        assertInstanceOf(GroundCoffee.class, registry.create(record));
    }

    @Test
    void dispatchesInstantJarToInstantJarCoffee() {
        CoffeeRecord record = new CoffeeRecord(
            1, "instant_jar", List.of("Gold", "1000", "0.19", "80", "0.3", "GLASS"));

        assertInstanceOf(InstantJarCoffee.class, registry.create(record));
    }

    @Test
    void dispatchesInstantSachetToInstantSachetCoffee() {
        CoffeeRecord record = new CoffeeRecord(
            1, "instant_sachet", List.of("Breakfast", "15", "0.018", "60", "20", "0.005"));

        assertInstanceOf(InstantSachetCoffee.class, registry.create(record));
    }

    @Test
    void isCaseInsensitiveOnType() {
        CoffeeRecord record = new CoffeeRecord(
            1, "GRAIN", List.of("Arabica", "800", "1.0", "90", "DARK", "FOIL"));

        assertInstanceOf(GrainCoffee.class, registry.create(record));
    }

    @Test
    void returnsNullForUnknownType() {
        CoffeeRecord record = new CoffeeRecord(
            1, "teabags", List.of("Earl Grey", "100", "0.1", "70"));

        CoffeeProduct product = registry.create(record);

        assertNull(product);
    }
}
