// Для каждого числа из заданной последовательности натуральных чисел, найти
// произведение цифр больших 7.
import java.util.ArrayList;

public class Solution_1 {

    public static void analyzeCollection(ArrayList<Integer> numbers) {
        for (Integer num : numbers) {
            int res = 1;
            int temp = num;

            while(temp > 0){
                int digit = temp % 10;
                if (digit > 7){
                    res *= digit;
                }
                temp /= 10;
            }
            
            if(res > 7){
                System.out.println("sum is " + res);  
            }else{
                System.out.println("sum is " + 0);
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> numbers = Utils.createCollection();

        analyzeCollection(numbers);
    }
}