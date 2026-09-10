// Найти и вывести из заданной последовательности натуральных чисел, все
// p-значные числа, в записи которых встречаются цифры 0, 2, 3, 7 по одному разу.

import java.util.ArrayList;
import java.util.List;

public class Solution_2 {

    public static void analyzeNumbers(ArrayList<Integer> numbers) {
        List<Integer> eq_numbers = List.of(0, 2, 3, 7);

        for (Integer num : numbers) {
            int length = 0;
            int temp = num;
            while (temp > 0) {
                temp /= 10;
                length++;
            }
            if (length < 4) {
                System.out.println("Number isn't correct");
            } else {
                int counter = 0;
                for (Integer eq_num: eq_numbers) {
                    if (String.valueOf(num).contains(String.valueOf(eq_num))) {
                        counter += 1;
                    }
                }
                if (counter == 4) {
                    System.out.println(num);
                } else {
                    System.out.println("Number isn't correct");
                }
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> numbers = Utils.createCollection();

        analyzeNumbers(numbers);
    }
}