package com.example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Определить класс Булева матрица (BoolMatrix).
 * Реализовать методы для логического сложения (дизъюнкции), умножения и инверсии матриц.
 * Реализовать методы для подсчета числа единиц в матрице и упорядочения строк в лексикографическом порядке.
 */
public class BoolMatrix {

    public static List<List<Boolean>> create(int rows, int cols) {
        return IntStream.range(0, rows)
            .<List<Boolean>>mapToObj(i -> new ArrayList<>(Collections.nCopies(cols, false)))
            .collect(Collectors.toList());
    }

    public static List<List<Boolean>> of(int[]... rows) {
        return Arrays.stream(rows)
            .map(r -> Arrays.stream(r).mapToObj(x -> x != 0).collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    public static List<List<Boolean>> or(List<List<Boolean>> a, List<List<Boolean>> b) {
        if (a.size() != b.size() || a.get(0).size() != b.get(0).size()) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }
        return IntStream.range(0, a.size())
            .<List<Boolean>>mapToObj(i -> IntStream.range(0, a.get(i).size())
                .mapToObj(j -> a.get(i).get(j) || b.get(i).get(j))
                .collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    public static List<List<Boolean>> multiply(List<List<Boolean>> a, List<List<Boolean>> b) {
        if (a.get(0).size() != b.size()) {
            throw new IllegalArgumentException("Столбцы A должны равняться строкам B");
        }
        int m = b.get(0).size();
        int k = b.size();
        return IntStream.range(0, a.size())
            .<List<Boolean>>mapToObj(i -> IntStream.range(0, m)
                .mapToObj(j -> IntStream.range(0, k).anyMatch(t -> a.get(i).get(t) && b.get(t).get(j)))
                .collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    public static List<List<Boolean>> invert(List<List<Boolean>> a) {
        return a.stream()
            .<List<Boolean>>map(row -> row.stream().map(x -> !x).collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    public static long countOnes(List<List<Boolean>> a) {
        return a.stream().flatMap(List::stream).filter(x -> x).count();
    }

    public static List<List<Boolean>> sortRows(List<List<Boolean>> a) {
        return a.stream()
            .sorted(Comparator.comparing(BoolMatrix::rowKey))
            .collect(Collectors.toList());
    }

    private static String rowKey(List<Boolean> row) {
        return row.stream().map(x -> x ? "1" : "0").collect(Collectors.joining());
    }

    public static void print(String title, List<List<Boolean>> a) {
        System.out.println(title + ":");
        a.forEach(row -> System.out.println(
            row.stream().map(x -> x ? "1" : "0").collect(Collectors.joining(" "))));
        System.out.println();
    }

    public static void main(String[] args) {
        List<List<Boolean>> a = of(new int[]{1, 0, 1}, new int[]{0, 1, 0}, new int[]{1, 1, 0});
        List<List<Boolean>> b = of(new int[]{0, 1, 1}, new int[]{1, 0, 1}, new int[]{0, 0, 1});
        List<List<Boolean>> c = of(new int[]{1, 0, 1}, new int[]{0, 0, 0},
                                   new int[]{1, 1, 1}, new int[]{0, 1, 0});

        print("A", a);
        print("B", b);
        print("A OR B", or(a, b));
        print("A * B", multiply(a, b));
        print("NOT A", invert(a));
        System.out.println("Единиц в A: " + countOnes(a));
        System.out.println("Единиц в B: " + countOnes(b));
        System.out.println();
        print("C до сортировки", c);
        print("C после сортировки строк", sortRows(c));
        print("Пустая матрица 2x3", create(2, 3));
    }
}
