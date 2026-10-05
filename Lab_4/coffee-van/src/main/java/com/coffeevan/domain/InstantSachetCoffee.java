package com.coffeevan.domain;

import java.math.BigDecimal;

public class InstantSachetCoffee extends CoffeeProduct {

    private static final double DENSITY_KG_PER_LITER = 0.30;

    private final int sachetCount;
    private final double sachetWrapperVolumeLiters;

    public InstantSachetCoffee(String sort, BigDecimal price, double netWeightKg, int quality,
                                int sachetCount, double sachetWrapperVolumeLiters) {
        super(sort, price, netWeightKg, quality);
        this.sachetCount = sachetCount;
        this.sachetWrapperVolumeLiters = sachetWrapperVolumeLiters;
    }

    public int getSachetCount() {
        return sachetCount;
    }

    public double getSachetWrapperVolumeLiters() {
        return sachetWrapperVolumeLiters;
    }

    @Override
    protected double getDensityKgPerLiter() {
        return DENSITY_KG_PER_LITER;
    }

    @Override
    public double getPackagingVolumeLiters() {
        return sachetCount * sachetWrapperVolumeLiters;
    }
}
