//Base class for all products the store will sell
public abstract class Product {
    private double price;
    private int stockQuantity;
    private int soldQuantity;

    private int pendingTransaction; // holds onto an item until the final transaction

    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
        pendingTransaction = 0;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public double getPrice() {
        return price;
    }

    public int getPendingTransaction(){ return pendingTransaction; }

    //Returns the total revenue (price * amount) if there are at least amount items in stock
    //Return 0 otherwise (i.e., there is no sale completed)
    public double sellUnits(int amount) {
        if (amount > 0 && stockQuantity >= amount) {
            stockQuantity -= amount;
            soldQuantity += amount;
            return price * amount;
        }
        return 0.0;
    }

    public void holdItem(){
        pendingTransaction++;
    }

    public void voidItemHold(int amount){ pendingTransaction -= amount; }

}