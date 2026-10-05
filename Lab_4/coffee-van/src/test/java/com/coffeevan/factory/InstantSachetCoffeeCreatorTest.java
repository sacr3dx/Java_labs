package com.coffeevan.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.coffeevan.domain.InstantSachetCoffee;
import com.coffeevan.io.CoffeeRecord;

class InstantSachetCoffeeCreatorTest {

    private final InstantSachetCoffeeCreator creator = new InstantSachetCoffeeCreator();

    @Test
    void createsProductFromValidRecord() {
        CoffeeRecord record = new CoffeeRecord(
            1, "instant_sachet", List.of("Breakfast", "15", "0.018", "60", "20", "0.005"));

        InstantSachetCoffee coffee = (InstantSachetCoffee) creator.create(record);

        assertEquals(20, coffee.getSachetCount());
        assertEquals(0.005, coffee.getSachetWrapperVolumeLiters(), 1e-9);
    }

    @Test
    void appliesDefaultSachetCountWhenZero() {
        CoffeeRecord record = new CoffeeRecord(
            1, "instant_sachet", List.of("Breakfast", "15", "0.018", "60", "0", "0.005"));

        InstantSachetCoffee coffee = (InstantSachetCoffee) creator.create(record);

        assertEquals(20, coffee.getSachetCount());
    }

    @Test
    void appliesDefaultWrapperVolumeWhenNotANumber() {
        CoffeeRecord record = new CoffeeRecord(
            1, "instant_sachet", List.of("Breakfast", "15", "0.018", "60", "20", "abc"));

        InstantSachetCoffee coffee = (InstantSachetCoffee) creator.create(record);

        assertEquals(0.005, coffee.getSachetWrapperVolumeLiters(), 1e-9);
    }
}
