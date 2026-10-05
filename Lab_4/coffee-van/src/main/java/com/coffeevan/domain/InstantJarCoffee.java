package com.coffeevan.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class InstantJarCoffee extends CoffeeProduct {

    private static final double DENSITY_KG_PER_LITER = 0.30;

    private final double jarVolumeLiters;
    private final JarMaterial jarMaterial;

    public InstantJarCoffee(String sort, BigDecimal price, double netWeightKg, int quality,
                             double jarVolumeLiters, JarMaterial jarMaterial) {
        super(sort, price, netWeightKg, quality);
        this.jarVolumeLiters = jarVolumeLiters;
        this.jarMaterial = Objects.requireNonNull(jarMaterial, "jarMaterial");
    }

    public double getJarVolumeLiters() {
        return jarVolumeLiters;
    }

    public JarMaterial getJarMaterial() {
        return jarMaterial;
    }

    @Override
    protected double getDensityKgPerLiter() {
        return DENSITY_KG_PER_LITER;
    }

    @Override
    public double getPackagingVolumeLiters() {
        return Math.max(0.0, jarVolumeLiters - getNetVolumeLiters());
    }
}
