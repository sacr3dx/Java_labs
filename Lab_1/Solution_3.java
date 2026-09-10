// Каждое натуральное число n из заданной последовательности чисел разложить на
// простые множители.

import java.util.ArrayList;

public class Solution_3 {
    
    public static void analyzePrime(int number) {
        ArrayList<Integer> prime_numbers = new ArrayList<>();

        if (number == 1) {
           System.out.print(1);
           return;
        } else if(number < 1) {
           System.out.print("Wrong number(number must be > 0)");
            return;
        }

        int temp = number;
        int delim = 2;
        while(temp > 1) {
            if (temp % delim == 0) {
                temp /= delim;
                prime_numbers.add(delim);
            } else {
                delim+=1;
            }
        }

        for (Integer num : prime_numbers) {
            System.out.print(num + " ");
        }
    }

    public static void main(String[] args) {
        int number = Utils.enterNumber(args);

        analyzePrime(number);
    }
}
