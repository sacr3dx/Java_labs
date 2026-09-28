package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class BoolMatrixTest {

    private static List<List<Boolean>> m(String... rows) {
        return Arrays.stream(rows)
            .map(r -> r.chars().mapToObj(c -> c == '1').collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    @Test
    public void create() {
        assertEquals(m("000", "000"), BoolMatrix.create(2, 3));
    }

    @Test
    public void of() {
        assertEquals(m("101", "010"), BoolMatrix.of(new int[]{1, 0, 2}, new int[]{0, -1, 0}));
    }

    @Test
    public void or() {
        assertEquals(m("101", "010"), BoolMatrix.or(m("100", "010"), m("001", "010")));
    }

    @Test
    public void orDifferentSizes() {
        assertThrows(IllegalArgumentException.class,
            () -> BoolMatrix.or(BoolMatrix.create(2, 2), BoolMatrix.create(2, 3)));
    }

    @Test
    public void multiply() {
        assertEquals(m("011", "101", "111"),
            BoolMatrix.multiply(m("101", "010", "110"), m("011", "101", "001")));
    }

    @Test
    public void multiplyNonSquare() {
        assertEquals(m("11", "01"), BoolMatrix.multiply(m("100", "011"), m("11", "00", "01")));
    }

    @Test
    public void multiplyIncompatibleSizes() {
        assertThrows(IllegalArgumentException.class,
            () -> BoolMatrix.multiply(BoolMatrix.create(2, 3), BoolMatrix.create(2, 3)));
    }

    @Test
    public void invert() {
        assertEquals(m("010", "101"), BoolMatrix.invert(m("101", "010")));
    }

    @Test
    public void countOnes() {
        assertEquals(5, BoolMatrix.countOnes(m("101", "010", "110")));
        assertEquals(0, BoolMatrix.countOnes(BoolMatrix.create(2, 2)));
    }

    @Test
    public void sortRows() {
        assertEquals(m("000", "010", "101", "111"),
            BoolMatrix.sortRows(m("101", "000", "111", "010")));
    }
}
