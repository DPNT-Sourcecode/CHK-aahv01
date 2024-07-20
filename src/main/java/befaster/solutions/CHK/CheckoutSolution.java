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

        //Count occurrences of each product
        for(char item : skus.toCharArray()) {
            if(!products.containsKey(item)){
                return -1;
            }
           itemCount.put(item, itemCount.getOrDefault(item, 0) + 1);
        }

        // Calculate the total price
        int total = 0;

        // Process products with general special offers first
        for (Map.Entry<Character, Integer> entry : itemCount.entrySet()) {
            char productCode = entry.getKey();
            int count = entry.getValue();
            Product product = products.get(productCode);

            // Skip special cases that will be handled separately
            if (productCode == 'E' || productCode == 'F' || productCode == 'N' || productCode == 'R' || productCode == 'U') {
                continue;
            }

            // Apply special offers
            if (!product.specialOffers.isEmpty()) {
                int remainingCount = count;
                for (Map.Entry<Integer, Integer> offer : product.specialOffers.entrySet()) {
                    int offerQuantity = offer.getKey();
                    int offerPrice = offer.getValue();

                    total += (remainingCount / offerQuantity) * offerPrice;
                    remainingCount %= offerQuantity;
                }
                // Add remaining items at regular price
                total += remainingCount * product.price;
            } else {
                // Add items at regular price
                total += count * product.price;
            }
        }

        // Special case for E giving free B
        int countE = itemCount.getOrDefault('E', 0);
        int countB = itemCount.getOrDefault('B', 0);
        if (countE >= 2) {
            int freeB = countE / 2;
            countB -= freeB;
            if (countB < 0) countB = 0;
        }
        // Add remaining B items at regular price
        total += countB * products.get('B').price;

        // Special case for F (buy 2 get 1 free)
        int countF = itemCount.getOrDefault('F', 0);
        if (countF >= 3) {
            total += (countF / 3) * 2 * products.get('F').price;
            countF %= 3;
        }
        total += countF * products.get('F').price;

        // Special case for N giving free M
        int countN = itemCount.getOrDefault('N', 0);
        int countM = itemCount.getOrDefault('M', 0);
        if (countN >= 3) {
            int freeM = countN / 3;
            countM -= freeM;
            if (countM < 0) countM = 0;
        }
        // Add remaining M items at regular price
        total += countM * products.get('M').price;

        // Special case for R giving free Q
        int countR = itemCount.getOrDefault('R', 0);
        int countQ = itemCount.getOrDefault('Q', 0);
        if (countR >= 3) {
            int freeQ = countR / 3;
            countQ -= freeQ;
            if (countQ < 0) countQ = 0;
        }
        // Add remaining Q items at regular price
        total += countQ * products.get('Q').price;

        // Special case for U (buy 3 get 1 free)
        int countU = itemCount.getOrDefault('U', 0);
        if (countU >= 4) {
            total += (countU / 4) * 3 * products.get('U').price;
            countU %= 4;
        }
        total += countU * products.get('U').price;

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
        System.out.println(checkoutSolution.checkout("B"));
    }
}

