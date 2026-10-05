package com.coffeevan.domain;

import java.math.BigDecimal;
import java.util.Objects;

/** Зерновой кофе: добавляет степень обжарки и материал пакета. */
public class GrainCoffee extends CoffeeProduct {

    private static final double DENSITY_KG_PER_LITER = 0.35;
    private static final double HEADSPACE_RATIO = 0.05;
    private static final double FOIL_BAG_OVERHEAD_LITERS = 0.05;
    private static final double PAPER_BAG_OVERHEAD_LITERS = 0.03;

    private final RoastLevel roastLevel;
    private final BagMaterial bagMaterial;

    public GrainCoffee(String sort, BigDecimal price, double netWeightKg, int quality,
                        RoastLevel roastLevel, BagMaterial bagMaterial) {
        super(sort, price, netWeightKg, quality);
        this.roastLevel = Objects.requireNonNull(roastLevel, "roastLevel");
        this.bagMaterial = Objects.requireNonNull(bagMaterial, "bagMaterial");
    }

    public RoastLevel getRoastLevel() {
        return roastLevel;
    }

    public BagMaterial getBagMaterial() {
        return bagMaterial;
    }

    @Override
    protected double getDensityKgPerLiter() {
        return DENSITY_KG_PER_LITER;
    }

    @Override
    public double getPackagingVolumeLiters() {
        double headspace = getNetVolumeLiters() * HEADSPACE_RATIO;
        double bagOverhead = bagMaterial == BagMaterial.FOIL
            ? FOIL_BAG_OVERHEAD_LITERS
            : PAPER_BAG_OVERHEAD_LITERS;
        return headspace + bagOverhead;
    }
}
