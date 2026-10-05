package com.coffeevan.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coffeevan.comparator.PriceToWeightComparator;
import com.coffeevan.domain.CoffeeProduct;
import com.coffeevan.domain.CoffeeVan;
import com.coffeevan.factory.CoffeeCreatorRegistry;
import com.coffeevan.io.CoffeeFileReader;
import com.coffeevan.io.CoffeeRecord;
import com.coffeevan.service.QualityRangeFinder;
import com.coffeevan.service.VanLoadingService;

public final class CoffeeVanApplication {

    private static final Logger LOGGER = LogManager.getLogger(CoffeeVanApplication.class);
    private static final double VAN_CAPACITY_LITERS = 300.0;
    private static final BigDecimal VAN_BUDGET = BigDecimal.valueOf(50_000);
    private static final int QUALITY_RANGE_MIN = 70;
    private static final int QUALITY_RANGE_MAX = 95;

    public static void main(String[] args) throws IOException, URISyntaxException {
        Path dataFile = args.length > 0 ? Paths.get(args[0]) : resolveBundledDataFile();

        LOGGER.info("Старт загрузки фургона кофе, файл данных: {}", dataFile);

        List<CoffeeRecord> records = new CoffeeFileReader().readRecords(dataFile);

        CoffeeCreatorRegistry registry = new CoffeeCreatorRegistry();
        List<CoffeeProduct> products = new ArrayList<>();
        for (CoffeeRecord record : records) {
            CoffeeProduct product = registry.create(record);
            if (product != null) {
                products.add(product);
            }
        }
        LOGGER.info("Создано товаров из файла: {}", products.size());

        CoffeeVan van = new CoffeeVan(VAN_CAPACITY_LITERS, VAN_BUDGET);
        new VanLoadingService().loadVan(van, products, new PriceToWeightComparator());

        List<CoffeeProduct> sortedLoad = new ArrayList<>(van.getLoadedProducts());
        sortedLoad.sort(new PriceToWeightComparator());
        LOGGER.info("Загруженный товар, отсортированный по цене/весу:");
        sortedLoad.forEach(p -> LOGGER.info("  {}", p));

        List<CoffeeProduct> inQualityRange = new QualityRangeFinder()
            .findInRange(van, QUALITY_RANGE_MIN, QUALITY_RANGE_MAX);
        LOGGER.info("Товар с качеством в диапазоне [{}; {}]:", QUALITY_RANGE_MIN, QUALITY_RANGE_MAX);
        inQualityRange.forEach(p -> LOGGER.info("  {}", p));

        LOGGER.info("Итог: объём {} / {} л, бюджет {} / {}",
            van.getUsedVolumeLiters(), van.getCapacityLiters(), van.getSpentAmount(), van.getBudget());
    }

    private static Path resolveBundledDataFile() throws URISyntaxException {
        return Paths.get(CoffeeVanApplication.class.getClassLoader()
            .getResource("coffee-data.txt").toURI());
    }
}
