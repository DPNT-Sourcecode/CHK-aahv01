package befaster.solutions.CHK;

import befaster.runner.SolutionNotImplementedException;

import java.util.*;

public class CheckoutSolution {

    private static final Map<Character, Product> products = new HashMap<>();
    private static final Set<Character> specialOfferProducts = new HashSet<>(Arrays.asList('S', 'T', 'X', 'Y', 'Z'));

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

        products.put('K', new Product(70));
        products.get('K').addSpecialOffer(2, 120);

        products.put('L', new Product(90));
        products.put('M', new Product(15));
        products.put('N', new Product(40));
        products.put('O', new Product(10));

        products.put('P', new Product(50));
        products.get('P').addSpecialOffer(5, 200);

        products.put('Q', new Product(30));
        products.get('Q').addSpecialOffer(3, 80);

        products.put('R', new Product(50));
        products.put('S', new Product(20));
        products.put('T', new Product(20));
        products.put('U', new Product(40));

        products.put('V', new Product(50));
        products.get('V').addSpecialOffer(3, 130);
        products.get('V').addSpecialOffer(2, 90);

        products.put('W', new Product(20));
        products.put('X', new Product(17));
        products.put('Y', new Product(20));
        products.put('Z', new Product(21));
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

        int total = 0;

        int specialOfferCount = 0;

        List<Character> itemsWithSpecialOffer = new ArrayList<>();

        //Process each product
        for(Map.Entry<Character, Integer> entry : itemCount.entrySet()) {
            char item = entry.getKey();
            int count = entry.getValue();
            Product product = products.get(item);
            int remaining = count;

            if(item == 'U' || item == 'F' || item == 'M'
                    || (item == 'B' && (itemCount.get('E') != null && itemCount.get('E')%2 == 0))
                    || (item == 'Q' && (itemCount.get('R') != null && itemCount.get('R') >= 3))){
                continue;
            }

            for(Offer offer : product.specialOffers){
                if(remaining >= offer.quantity){
                    int numberOfOffers = remaining / offer.quantity;
                    total += numberOfOffers * offer.price;
                    remaining %= offer.quantity;
                    itemCount.put(item, remaining);
                }
            }

            if(specialOfferProducts.contains(item)){
                specialOfferCount += count;
                for (int i = 0; i < count; i++) {
                    itemsWithSpecialOffer.add(item);
                }
            }else if(item != 'B'  && item != 'Q'){
                total += remaining * product.price;
            }

        }

        itemsWithSpecialOffer.sort((a,b) -> Integer.compare(products.get(b).price, products.get(a).price));

        if (specialOfferCount >= 3) {
            int applicableSets = specialOfferCount / 3;
            total += applicableSets * 45;
            specialOfferCount %= 3;
        }
        itemsWithSpecialOffer = itemsWithSpecialOffer.subList(itemsWithSpecialOffer.size() - specialOfferCount, itemsWithSpecialOffer.size());
        for(char item : itemsWithSpecialOffer){
            total += products.get(item).price;
        }


        //Special case for E giving free B
        int countB = itemCount.getOrDefault('B', 0);
        int countE = itemCount.getOrDefault('E', 0);
        if(countE >= 2){
            int freeB = countE / 2;
            countB -= freeB;
            if(countB < 0) countB = 0;
        }
        if(!(countB % 2 == 0)){
            total += countB * products.get('B').price;
        }else{
            for(Offer offer : products.get('B').specialOffers){
                if(countB >= offer.quantity){
                    int numberOfOffers = countB / offer.quantity;
                    total += numberOfOffers * offer.price;
                    countB %= offer.quantity;
                    itemCount.put('B', countB);
                }
            }
        }

       //Special case for F (buy 2 get 1 free)
        int countF = itemCount.getOrDefault('F', 0);
        if(countF >= 3){
            total += (countF / 3) * 2 * products.get('F').price;
            countF = countF % 3;
            total += countF * products.get('F').price;
        }else{
            total += countF * products.get('F').price;
        }

        //Special case for N giving free M
        int countM = itemCount.getOrDefault('M', 0);
        int countN = itemCount.getOrDefault('N', 0);
        if(countN >= 3){
            int freeM = countN / 3;
            countM -= freeM;
            if(countM < 0) countM = 0;
            total += countM * products.get('M').price;
        }else{
            total += countM * products.get('M').price;
        }

        //Special case for R giving free Q
        int countQ = itemCount.getOrDefault('Q', 0);
        int countR = itemCount.getOrDefault('R', 0);
        if(countR >= 3){
            int freeQ = countR / 3;
            countQ -= freeQ;
            if(countQ < 0) countQ = 0;
        }
        if(!(countQ % 3 == 0)){
            total += countQ * products.get('Q').price;
        }else{
            for(Offer offer : products.get('Q').specialOffers){
                if(countQ >= offer.quantity){
                    int numberOfOffers = countQ / offer.quantity;
                    total += numberOfOffers * offer.price;
                    countQ %= offer.quantity;
                    itemCount.put('Q', countQ);
                }
            }
        }

        //Special case for U (buy 3 get 1 free)
        int countU = itemCount.getOrDefault('U', 0);
        if(countU >= 4){
            total += (countU / 4) * 3 * products.get('U').price;
            countU = countU % 4;
        }
        total += countU * products.get('U').price;

        return total;

    }

    class Product {
        int price;
        List<Offer> specialOffers;

        public Product(int price) {
            this.price = price;
            this.specialOffers = new ArrayList<>();
        }

        public void addSpecialOffer(int count, int price) {
            specialOffers.add(new Offer(count, price));
            Collections.sort(specialOffers, (o1, o2) -> Integer.compare(o2.quantity, o1.quantity));
        }
    }

    class Offer {
        int quantity;
        int price;

        public Offer(int quantity, int price) {
            this.quantity = quantity;
            this.price = price;
        }
    }


    public static void main(String[] args) {
        CheckoutSolution checkoutSolution = new CheckoutSolution();
        System.out.println(checkoutSolution.checkout("STXZ"));
    }
}




