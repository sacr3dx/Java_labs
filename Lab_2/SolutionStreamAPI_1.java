// «Уплотнить» матрицу, удаляя из нее столбцы,
// заполненные четными числами.

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolutionStreamAPI_1{
    public static ArrayList<ArrayList<Integer>> compactMatrix(ArrayList<ArrayList<Integer>> a) {
        if (a.isEmpty()) {
            return new ArrayList<>();
        }
        int columnsCount = a.get(0).size();

        List<Integer> columnsToKeep = IntStream.range(0, columnsCount)
                .filter(col -> a.stream().anyMatch(row -> row.get(col) % 2 != 0))
                .boxed()
                .collect(Collectors.toList());

        return a.stream()
                .map(row -> columnsToKeep.stream()
                        .map(row::get)
                        .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<ArrayList<Integer>> a1 = UtilsForMatrix.createMatrix(scanner);
        System.out.println("Default matrix:");
        UtilsForMatrix.printMatrix(a1);

        ArrayList<ArrayList<Integer>> compacted = compactMatrix(a1);
        System.out.println("Matrix after operation:");
        UtilsForMatrix.printMatrix(compacted);

        scanner.close();
    }
}
