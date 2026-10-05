package com.coffeevan.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class GrainCoffeeTest {

    @Test
    void netVolumeComputedFromWeightAndDensity() {
        GrainCoffee coffee = new GrainCoffee(
            "Arabica", BigDecimal.valueOf(800), 0.35, 90, RoastLevel.MEDIUM, BagMaterial.PAPER);

        assertEquals(1.0, coffee.getNetVolumeLiters(), 1e-9);
    }

    @Test
    void foilBagHasLargerPackagingVolumeThanPaperBag() {
        GrainCoffee foil = new GrainCoffee(
            "Arabica", BigDecimal.valueOf(800), 1.0, 90, RoastLevel.MEDIUM, BagMaterial.FOIL);
        GrainCoffee paper = new GrainCoffee(
            "Arabica", BigDecimal.valueOf(800), 1.0, 90, RoastLevel.MEDIUM, BagMaterial.PAPER);

        assertEquals(true, foil.getPackagingVolumeLiters() > paper.getPackagingVolumeLiters());
    }

    @Test
    void packagingVolumeIsHeadspacePlusBagOverhead() {
        GrainCoffee paper = new GrainCoffee(
            "Arabica", BigDecimal.valueOf(800), 0.35, 90, RoastLevel.MEDIUM, BagMaterial.PAPER);
        GrainCoffee foil = new GrainCoffee(
            "Arabica", BigDecimal.valueOf(800), 0.35, 90, RoastLevel.MEDIUM, BagMaterial.FOIL);

        // netVolume = 0.35 / 0.35 = 1.0 L -> headspace = 1.0 * 0.05 = 0.05 L
        assertEquals(0.05 + 0.03, paper.getPackagingVolumeLiters(), 1e-9);
        assertEquals(0.05 + 0.05, foil.getPackagingVolumeLiters(), 1e-9);
    }

    @Test
    void totalVolumeIsNetPlusPackaging() {
        GrainCoffee coffee = new GrainCoffee(
            "Arabica", BigDecimal.valueOf(800), 1.0, 90, RoastLevel.DARK, BagMaterial.PAPER);

        assertEquals(coffee.getNetVolumeLiters() + coffee.getPackagingVolumeLiters(),
            coffee.getTotalVolumeLiters(), 1e-9);
    }
}
