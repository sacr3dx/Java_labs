package com.coffeevan.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class InstantJarCoffeeTest {

    @Test
    void packagingVolumeIsJarMinusCoffeeVolume() {
        InstantJarCoffee coffee = new InstantJarCoffee(
            "Gold", BigDecimal.valueOf(1000), 0.06, 80, 0.3, JarMaterial.GLASS);

        assertEquals(0.3 - coffee.getNetVolumeLiters(), coffee.getPackagingVolumeLiters(), 1e-9);
    }

    @Test
    void packagingVolumeNeverNegativeWhenJarSmallerThanCoffee() {
        InstantJarCoffee coffee = new InstantJarCoffee(
            "Gold", BigDecimal.valueOf(1000), 1.0, 80, 0.1, JarMaterial.PLASTIC);

        assertEquals(0.0, coffee.getPackagingVolumeLiters(), 1e-9);
    }
}
