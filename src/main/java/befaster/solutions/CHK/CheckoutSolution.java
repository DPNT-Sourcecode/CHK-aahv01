package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    final int priceA = 50;
    final int priceB = 30;
    final int priceC = 20;
    final int priceD = 15;
    final int priceE = 40;
    final int specialPriceA3 = 130;
    final int specialCountA3 = 3;
    final int specialPriceA5 = 200;
    final int specialCountA5 = 5;
    final int specialPriceB = 45;
    final int specialCountB = 2;

    public Integer checkout(String skus) {
        Map<Character, Integer> itemCount = new HashMap<>();
        for(char item : skus.toCharArray()) {
            if(item != 'A' && item != 'B' && item != 'C' && item != 'D' && item != 'E'){
                return -1;
            }
           itemCount.put(item, itemCount.getOrDefault(item, 0) + 1);
        }
        int total = 0;

        //Calculate price for item A
        int countA = itemCount.getOrDefault('A', 0);
        if(countA >= specialCountA5){
            total += (countA / specialCountA5) * specialPriceA5;
            countA = countA % specialCountA5;
        }
        if(countA >= specialCountA3){
            total += (countA / specialCountA3) * specialPriceA3;
            countA = countA % specialCountA3;
        }
        total += countA * priceA;

        //Calculate price for item B
        int countB = itemCount.getOrDefault('B', 0);

        //Calculate price for item E
        int countE = itemCount.getOrDefault('E', 0);
        if(countE >= 2){
            int freeB = countE / 2;
            countB -= freeB;
            if(countB < 0) countB = 0;
        }
        total += countE * priceE;

        //Apply special pricing for B after applying E's offer
        if(countB >= specialCountB){
            total += (countB / specialCountB) * specialPriceB;
            countB = countB % specialCountB;
        }
        total += countB * priceB;

        //Calculate price for item C
        int countC = itemCount.getOrDefault('C', 0);
        total += countC * priceC;

        //Calculate price for item D
        int countD = itemCount.getOrDefault('D', 0);
        total += countD * priceD;


        return total;
    }

    public static void main(String[] args) {
        CheckoutSolution checkoutSolution = new CheckoutSolution();
        System.out.println(checkoutSolution.checkout("AAABBBCCCD"));
    }
}

