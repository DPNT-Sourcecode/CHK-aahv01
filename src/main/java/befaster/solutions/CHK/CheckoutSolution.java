package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {

    private static final Map<Character, Product> products = new HashMap<>();

    {
        products.put('A', new Product(50));
        products.get('A').addSpecialOffer(5, 200);
        products.get('A').addSpecialOffer(3, 130);

        products.put('B', new Product(30));
        products.get('B').addSpecialOffer(2, 45);

        products.put('C', new Product(20));
        products.put('D', new Product(15));
        products.put('E', new Product(40));
        products.put('F', new Product(10));
        products.put('G', new Product(20));

        products.put('H', new Product(10));
        products.get('H').addSpecialOffer(10, 80);
        products.get('H').addSpecialOffer(5, 45);

        products.put('I', new Product(35));
        products.put('J', new Product(60));

        products.put('K', new Product(80));
        products.get('K').addSpecialOffer(2, 150);

        products.put('L', new Product(90));
        products.put('M', new Product(15));
        products.put('N', new Product(40));
        products.put('O', new Product(10));

        products.put('P', new Product(50));
        products.get('P').addSpecialOffer(5, 200);

        products.put('Q', new Product(30));
        products.get('Q').addSpecialOffer(3, 80);

        products.put('R', new Product(50));
        products.put('S', new Product(30));
        products.put('T', new Product(20));
        products.put('U', new Product(40));

        products.put('V', new Product(50));
        products.get('V').addSpecialOffer(3, 130);
        products.get('V').addSpecialOffer(2, 90);

        products.put('W', new Product(20));
        products.put('X', new Product(90));
        products.put('Y', new Product(10));
        products.put('Z', new Product(50));
    }

    public Integer checkout(String skus) {
        Map<Character, Integer> itemCount = new HashMap<>();
        for(char item : skus.toCharArray()) {
            if(item != 'A' && item != 'B' && item != 'C' && item != 'D' && item != 'E' && item != 'F'){
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

        int countF = itemCount.getOrDefault('F', 0);
        if(countF >= 3){
            total += (countF / 3) * 2 * priceF;
            countF = countF % 3;
        }
        total += countF * priceF;

        return total;
    }

    class Product {
        int price;
        Map<Integer, Integer> specialOffers;

        public Product(int price) {
            this.price = price;
            this.specialOffers = new HashMap<>();
        }

        public void addSpecialOffer(int count, int price) {
            specialOffers.put(count, price);
        }
    }

    public static void main(String[] args) {
        CheckoutSolution checkoutSolution = new CheckoutSolution();
        System.out.println(checkoutSolution.checkout("FFF"));
    }
}

