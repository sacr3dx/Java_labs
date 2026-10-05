package com.coffeevan.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class GroundCoffee extends CoffeeProduct {

    private static final double DENSITY_KG_PER_LITER = 0.40;
    private static final double VACUUM_PACK_OVERHEAD_LITERS = 0.05;

    private final GrindSize grindSize;

    public GroundCoffee(String sort, BigDecimal price, double netWeightKg, int quality,
                         GrindSize grindSize) {
        super(sort, price, netWeightKg, quality);
        this.grindSize = Objects.requireNonNull(grindSize, "grindSize");
    }

    public GrindSize getGrindSize() {
        return grindSize;
    }

    @Override
    protected double getDensityKgPerLiter() {
        return DENSITY_KG_PER_LITER;
    }

    @Override
    public double getPackagingVolumeLiters() {
        return VACUUM_PACK_OVERHEAD_LITERS;
    }
}
