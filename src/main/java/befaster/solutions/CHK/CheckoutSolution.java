package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    Map<Character, Integer> itemCount = new HashMap<>();
    final int priceA = 50;
    final int priceB = 30;
    final int priceC = 20;
    final int priceD = 15;
    final int specialPriceA = 130;
    final int specialCountA = 3;
    final int specialPriceB = 45;
    final int specialCountB = 2;

    public Integer checkout(String skus) {
        for(char item : skus.toCharArray()) {
           itemCount.put(item, itemCount.getOrDefault(item, 0) + 1);
        }
        int total = 0;

        //Calculate price for item A
        int countA = itemCount.getOrDefault('A', 0);
        total += (countA / specialCountA) * specialPriceA + (countA % specialCountA) * priceA;

        //Calculate price for item B
        int countB = itemCount.getOrDefault('B', 0);
        total += (countB / specialCountB) * specialPriceB + (countB % specialCountB) * priceB;

        //Calculate price for item C
        int countC = itemCount.getOrDefault('C', 0);
        total += countC * priceC;

        //Calculate price for item D
        int countD = itemCount.getOrDefault('D', 0);
        total += countD * priceD;

        return total;
    }
}

