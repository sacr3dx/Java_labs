import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class UtilsForMatrix {
    
    public static ArrayList<ArrayList<Integer>> createMatrix(Scanner scanner) {
        System.out.println("Enter size of matrix - n:");
        int n = scanner.nextInt();

        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();

        System.out.println("Select method that create matrix?");
        System.out.println("1 - Enter values by yourself");
        System.out.println("2 - Enter random values");
        int choice = scanner.nextInt();

        Random random = new Random();
        int bound = 100;

        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (choice == 1) {
                    row.add(scanner.nextInt());
                } else {
                    row.add(random.nextInt(bound));
                }
            }
            matrix.add(row);
        }

        return matrix;
    }

    public static void printMatrix(ArrayList<ArrayList<Integer>> matrix) {
        if (matrix.isEmpty() || matrix.get(0).isEmpty()) {
            System.out.println("(matrix is null)");
            return;
        }
        for (ArrayList<Integer> row : matrix) {
            for (int value : row) {
                System.out.printf("%5d", value);
            }
            System.out.println();
        }
    }
}