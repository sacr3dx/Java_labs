package com.coffeevan.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CoffeeVan {

    private final double capacityLiters;
    private final BigDecimal budget;
    private final List<CoffeeProduct> loadedProducts = new ArrayList<>();

    public CoffeeVan(double capacityLiters, BigDecimal budget) {
        this.capacityLiters = capacityLiters;
        this.budget = budget;
    }

    public double getCapacityLiters() {
        return capacityLiters;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public List<CoffeeProduct> getLoadedProducts() {
        return Collections.unmodifiableList(loadedProducts);
    }

    public double getUsedVolumeLiters() {
        return loadedProducts.stream().mapToDouble(CoffeeProduct::getTotalVolumeLiters).sum();
    }

    public BigDecimal getSpentAmount() {
        return loadedProducts.stream()
            .map(CoffeeProduct::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public double getRemainingVolumeLiters() {
        return capacityLiters - getUsedVolumeLiters();
    }

    public BigDecimal getRemainingBudget() {
        return budget.subtract(getSpentAmount());
    }

    /**
     * Пытается добавить товар, если он помещается по объёму и бюджету.
     *
     * @return true, если товар добавлен
     */
    public boolean addProduct(CoffeeProduct product) {
        boolean fitsVolume = product.getTotalVolumeLiters() <= getRemainingVolumeLiters();
        boolean fitsBudget = product.getPrice().compareTo(getRemainingBudget()) <= 0;
        if (fitsVolume && fitsBudget) {
            loadedProducts.add(product);
            return true;
        }
        return false;
    }
}
