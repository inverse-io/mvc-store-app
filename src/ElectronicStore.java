//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue;

    private HashMap<Product, String> cartItems;

    private int totalSales;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        cartItems = new HashMap<>();
        totalSales = 0;
    }

    public String getName() {
        return name;
    }

    public double getRevenue(){ return revenue; }

    public int getTotalSales(){ return totalSales; }

    public HashMap<Product, String> getCartItems(){

        HashMap<Product, String> retVal = new HashMap<>();

        for(Product p : cartItems.keySet() ){
            if(p.getPendingTransaction() > 0){
                retVal.put(p, cartItems.get(p));
            }
        }

        return retVal;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }

    // return stock
    public Product[] getStock(){

        Product[] retVal = new Product[curProducts];
        int counter = 0;

        for(Product i : stock){
            // the item must exist and have enough stock
            if(i != null && i.getStockQuantity() != 0 && i.getPendingTransaction() != 10){
                retVal[counter] = i;
                counter++;
            }
        }

        return retVal;
    }

    // method finds the top 3 items bought in the store
    public List<Product> findMostPopular(){
        List<Product> copy = new ArrayList<>();
        List<Product> retVal = new ArrayList<>();

        // make a copy of all current items in stock
        for(int i = 0; i < curProducts; i++){
            copy.add(stock[i]);
        }

        // this isn't ideal, but it'll do because it's only 10 items
        for(int j = 0; j < 3; j++) {

            Product current = copy.get(0); // get first value of the list

            for (Product i : copy) {
                if (i.getSoldQuantity() > current.getSoldQuantity() && !retVal.contains(i)) {
                    current = i;
                }
            }

            retVal.add(current);
            copy.remove(current);
        }

        return retVal;
    }

    public void addToCart(Product p){
        String num = "";
        p.holdItem();
        num = p.getPendingTransaction() + " x " + p.toString();
        cartItems.put(p, num);
    }

    public void removeFromCart( Product p ){
        String num = "";
        p.voidItemHold(1);
        num = p.getPendingTransaction() + " x " + p.toString();
        cartItems.put(p, num); // updates the pending transaction in the cart
    }

    public void calcRevenue(){

        for( Product i : stock ){
            if(i != null) {
                revenue += (i.getSoldQuantity() * i.getPrice());
            }
        }

    }

    // finds a product based on the string name
    public Product findCartProduct(String n){

        for( Product p : cartItems.keySet() ){
            if(n.contains(p.toString())){
                return p;
            }
        }

        return null;
    }

    public void calcTotalSales(){
        totalSales++;
    }

    public double cartWorth(){
        double total = 0.0;

        for( Product p : getCartItems().keySet() ){
            total += p.getPendingTransaction() * p.getPrice();
        }

        return total;
    }

    public double avgSaleRev(){
        return revenue/totalSales;
    }


    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }


} 