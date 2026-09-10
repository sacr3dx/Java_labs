import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
    
    public static ArrayList<Integer> createCollection() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.println("Enter numbers(enter 0 for finish):");

        while(true) {
            int input = scanner.nextInt();
            if (input <= 0){
                break; 
            };
            numbers.add(input);
        }

        scanner.close();
        return numbers;
    }

    public static int enterNumber(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number: ");
        int number = scanner.nextInt();
        scanner.close();
        return number;
    }
}
