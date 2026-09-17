// Для каждой строки матрицы найти наибольшее число возрастающих (убывающих) элементов матрицы, идущих подряд. 
// Упорядочить строки матрицы по возрастанию (убыванию) их числа. 
// Вывести саму матрицу. 
// На основе исходной матрицы построить «зубчатый» массив, где каждая строка будет представлять собой самую длинную последовательность возрастающих (убывающих) элементов матрицы, идущих подряд. 
// Полученный «зубчатый» массив вывести на экран.

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Solution_2 {
    public static ArrayList<Integer> longestRun(ArrayList<Integer> row, boolean typeRun) {
        int maxLen = 1, curLen = 1, maxStart = 0, curStart = 0;

        for (int i = 1; i < row.size(); i++) {
            boolean type = typeRun ? row.get(i) > row.get(i - 1) : row.get(i) < row.get(i - 1);
            curLen = type ? curLen + 1 : 1;
            curStart = type ? curStart : i;
            if (curLen > maxLen) { maxLen = curLen; maxStart = curStart; }
        }
        return new ArrayList<>(row.subList(maxStart, maxStart + maxLen));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> a = UtilsForMatrix.createMatrix(scanner);
        System.out.println("Default matrix:");
        UtilsForMatrix.printMatrix(a);

        ArrayList<ArrayList<Integer>> original = new ArrayList<>();
        for (ArrayList<Integer> row : a) {
            original.add(new ArrayList<>(row));
        }

        System.out.println("1 - upper run, 2 - lower run:");
        boolean typeRun = scanner.nextInt() == 1;

        System.out.println("Sort strings: 1 - upper run, 2 - lower run:");
        boolean typeRunStrigs = scanner.nextInt() == 1;

        Comparator<ArrayList<Integer>> cmp = Comparator.comparingInt(row -> longestRun(row, typeRun).size());
        a.sort(typeRunStrigs ? cmp : cmp.reversed());

        System.out.println("Matrix after sorting strings:");
        UtilsForMatrix.printMatrix(a);

        ArrayList<ArrayList<Integer>> jagged = new ArrayList<>();
        for (ArrayList<Integer> row : original) {
            jagged.add(longestRun(row, typeRun));
        }

        System.out.println("Jagged array:");
        UtilsForMatrix.printMatrix(jagged);

        scanner.close();
    }
}
