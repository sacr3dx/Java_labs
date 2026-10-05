package com.coffeevan.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class InstantSachetCoffeeTest {

    @Test
    void packagingVolumeIsSachetCountTimesWrapperVolume() {
        InstantSachetCoffee coffee = new InstantSachetCoffee(
            "Breakfast", BigDecimal.valueOf(15), 0.018, 60, 20, 0.005);

        assertEquals(20 * 0.005, coffee.getPackagingVolumeLiters(), 1e-9);
    }

    @Test
    void totalVolumeIsNetPlusPackaging() {
        InstantSachetCoffee coffee = new InstantSachetCoffee(
            "Breakfast", BigDecimal.valueOf(15), 0.018, 60, 20, 0.005);

        assertEquals(coffee.getNetVolumeLiters() + coffee.getPackagingVolumeLiters(),
            coffee.getTotalVolumeLiters(), 1e-9);
    }
}
