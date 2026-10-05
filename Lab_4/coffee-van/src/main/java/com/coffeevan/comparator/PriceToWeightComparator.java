package com.coffeevan.comparator;

import java.util.Comparator;

import com.coffeevan.domain.CoffeeProduct;

public final class PriceToWeightComparator implements Comparator<CoffeeProduct> {

    @Override
    public int compare(CoffeeProduct first, CoffeeProduct second) {
        double ratioFirst = first.getPrice().doubleValue() / first.getNetWeightKg();
        double ratioSecond = second.getPrice().doubleValue() / second.getNetWeightKg();
        return Double.compare(ratioFirst, ratioSecond);
    }
}
