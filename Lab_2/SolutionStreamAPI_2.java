// Для каждой строки матрицы найти наибольшее число возрастающих (убывающих) элементов матрицы, идущих подряд. 
// Упорядочить строки матрицы по возрастанию (убыванию) их числа. 
// Вывести саму матрицу. 
// На основе исходной матрицы построить «зубчатый» массив, где каждая строка будет представлять собой самую длинную последовательность возрастающих (убывающих) элементов матрицы, идущих подряд. 
// Полученный «зубчатый» массив вывести на экран.


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolutionStreamAPI_2 {

    public static ArrayList<Integer> longestRun(ArrayList<Integer> row, boolean typeRun) {
        if (row == null || row.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> breakPoints = IntStream.range(1, row.size())
                .filter(i -> typeRun ? row.get(i) <= row.get(i - 1) : row.get(i) >= row.get(i - 1))
                .boxed()
                .collect(Collectors.toList());

        breakPoints.add(row.size());

        List<Integer> lengths = new ArrayList<>();
        int prev = 0;
        for (int bp : breakPoints) {
            lengths.add(bp - prev);
            prev = bp;
        }

        int maxLenIndex = IntStream.range(0, lengths.size())
                .boxed()
                .max(Comparator.comparingInt(lengths::get))
                .orElse(0);

        int start = (maxLenIndex == 0) ? 0 : breakPoints.get(maxLenIndex - 1);
        int end = breakPoints.get(maxLenIndex);

        return new ArrayList<>(row.subList(start, end));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<ArrayList<Integer>> a = UtilsForMatrix.createMatrix(scanner);
        System.out.println("Default matrix:");
        UtilsForMatrix.printMatrix(a);

        ArrayList<ArrayList<Integer>> original = a.stream()
                .map(ArrayList::new)
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("1 - upper run, 2 - lower run:");
        boolean typeRun = scanner.nextInt() == 1;

        System.out.println("Sort strings: 1 - upper run, 2 - lower run:");
        boolean typeRunStrings = scanner.nextInt() == 1;

        Comparator<ArrayList<Integer>> cmp = Comparator.comparingInt(row -> longestRun(row, typeRun).size());

        ArrayList<ArrayList<Integer>> sortedMatrix = a.stream()
                .sorted(typeRunStrings ? cmp : cmp.reversed())
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("Matrix after sorting strings:");
        UtilsForMatrix.printMatrix(sortedMatrix);

        ArrayList<ArrayList<Integer>> jagged = original.stream()
                .map(row -> longestRun(row, typeRun))
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("Jagged array:");
        UtilsForMatrix.printMatrix(jagged);

        scanner.close();
    }
}