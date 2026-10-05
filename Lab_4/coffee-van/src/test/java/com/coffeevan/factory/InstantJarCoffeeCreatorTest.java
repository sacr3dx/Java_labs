package com.coffeevan.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.coffeevan.domain.InstantJarCoffee;
import com.coffeevan.domain.JarMaterial;
import com.coffeevan.io.CoffeeRecord;

class InstantJarCoffeeCreatorTest {

    private final InstantJarCoffeeCreator creator = new InstantJarCoffeeCreator();

    @Test
    void createsProductFromValidRecord() {
        CoffeeRecord record = new CoffeeRecord(
            1, "instant_jar", List.of("Gold", "1000", "0.19", "80", "0.3", "GLASS"));

        InstantJarCoffee coffee = (InstantJarCoffee) creator.create(record);

        assertEquals(0.3, coffee.getJarVolumeLiters(), 1e-9);
        assertEquals(JarMaterial.GLASS, coffee.getJarMaterial());
    }

    @Test
    void appliesDefaultJarVolumeWhenNegative() {
        CoffeeRecord record = new CoffeeRecord(
            1, "instant_jar", List.of("Gold", "1000", "0.19", "80", "-0.2", "GLASS"));

        InstantJarCoffee coffee = (InstantJarCoffee) creator.create(record);

        assertEquals(0.3, coffee.getJarVolumeLiters(), 1e-9);
    }

    @Test
    void appliesDefaultJarMaterialWhenUnknown() {
        CoffeeRecord record = new CoffeeRecord(
            1, "instant_jar", List.of("Gold", "1000", "0.19", "80", "0.3", "TITANIUM"));

        InstantJarCoffee coffee = (InstantJarCoffee) creator.create(record);

        assertEquals(JarMaterial.GLASS, coffee.getJarMaterial());
    }
}
