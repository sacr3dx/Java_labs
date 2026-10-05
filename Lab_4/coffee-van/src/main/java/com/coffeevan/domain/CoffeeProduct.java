package com.coffeevan.domain;

import java.math.BigDecimal;
import java.util.Objects;

abstract class CoffeeProduct {

    private final String sort;
    private final BigDecimal price;
    private final double netWeightKg;
    private final int quality;

    protected CoffeeProduct(String sort, BigDecimal price, double netWeightKg, int quality) {
        this.sort = Objects.requireNonNull(sort, "sort");
        this.price = Objects.requireNonNull(price, "price");
        this.netWeightKg = netWeightKg;
        this.quality = quality;
    }

    public String getSort() {
        return sort;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public double getNetWeightKg() {
        return netWeightKg;
    }

    public int getQuality() {
        return quality;
    }

    protected abstract double getDensityKgPerLiter();

    public double getNetVolumeLiters() {
        return netWeightKg / getDensityKgPerLiter();
    }

    public abstract double getPackagingVolumeLiters();

    public final double getTotalVolumeLiters() {
        return getNetVolumeLiters() + getPackagingVolumeLiters();
    }

    @Override
    public String toString() {
        return String.format(
            "%s{sort='%s', price=%s, netWeightKg=%.3f, quality=%d, totalVolumeL=%.3f}",
            getClass().getSimpleName(), sort, price, netWeightKg, quality, getTotalVolumeLiters());
    }
}
