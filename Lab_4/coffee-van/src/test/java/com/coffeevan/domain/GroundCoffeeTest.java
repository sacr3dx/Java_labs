package com.coffeevan.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class GroundCoffeeTest {

    @Test
    void netVolumeComputedFromWeightAndDensity() {
        GroundCoffee coffee = new GroundCoffee(
            "Espresso", BigDecimal.valueOf(600), 0.40, 85, GrindSize.FINE);

        assertEquals(1.0, coffee.getNetVolumeLiters(), 1e-9);
    }

    @Test
    void packagingVolumeIsFixedVacuumOverhead() {
        GroundCoffee small = new GroundCoffee(
            "Espresso", BigDecimal.valueOf(600), 0.25, 85, GrindSize.FINE);
        GroundCoffee large = new GroundCoffee(
            "Espresso", BigDecimal.valueOf(1200), 1.0, 85, GrindSize.COARSE);

        assertEquals(small.getPackagingVolumeLiters(), large.getPackagingVolumeLiters(), 1e-9);
    }
}
