// «Уплотнить» матрицу, удаляя из нее столбцы,
// заполненные четными числами.
import java.util.ArrayList;
import java.util.Scanner;

public class Solution_1 {
    public static boolean isColumnAllEven(ArrayList<ArrayList<Integer>> a, int col) {
        for (ArrayList<Integer> row : a) {
            if (row.get(col) % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    public static void compactMatrix(ArrayList<ArrayList<Integer>> a) {
        if (a.isEmpty()) {
            return;
        }

        int columnsCount = a.get(0).size();

        for (int col = columnsCount - 1; col >= 0; col--) {
            if (isColumnAllEven(a, col)) {
                for (ArrayList<Integer> row : a) {
                    row.remove(col);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<ArrayList<Integer>> matrix = UtilsForMatrix.createMatrix(scanner);
        System.out.println("Default matrix:");
        UtilsForMatrix.printMatrix(matrix);

        compactMatrix(matrix);

        System.out.println("Matrix after operation:");
        UtilsForMatrix.printMatrix(matrix);

        scanner.close();
    }
}