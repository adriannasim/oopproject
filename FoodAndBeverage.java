import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class FoodAndBeverage {
    protected String foodId;
    protected String foodName;
    protected double foodPrice;
    protected int purchaseQty;
    protected int stockQty;

    // -----------------------------------CONSTRUCTOR----------------------------------------
    // NO-ARG CONSTRUCTOR
    FoodAndBeverage() {
        foodId = "Undefined";
        foodName = "Undefined";
    }

    // PARAMETERIZED CONSTRUCTOR
    FoodAndBeverage(String foodName, double foodPrice, int stockQty) {
        foodId = String.valueOf((int) (100000 + (Math.random() * (200000 - 100000 + 1))));
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.stockQty = stockQty;
        purchaseQty = 0;
    }

    FoodAndBeverage(String foodId, String foodName, double foodPrice, int purchaseQty, int stockQty) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.purchaseQty = purchaseQty;
        this.stockQty = stockQty;

    }

    FoodAndBeverage(String foodId, String foodName, double foodPrice, int purchaseQty) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.purchaseQty = purchaseQty;

    }

    // ------------------------------------METHOD-----------------------------------------
    // GETTER
    public String getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public int getStockQty() {
        return stockQty;
    }

    public int getPurchaseQty() {
        return purchaseQty;
    }

    // SETTER
    public void editFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void editFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public boolean editStockQty(String sign, int stockQty) {
        if (sign.equals("+")) {
            this.stockQty += stockQty;
            return true;
        }else{
            if(stockQty>this.stockQty){
                System.out.println("\nERROR. THIS QUANTITY IS LARGER THAN THE STOCK QTY.\n");
                return false;
            } else {
                this.stockQty -= stockQty;
                return true;
            }
        }
    }

    public void setPurchaseQty(int purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    // DISPLAY METHOD
    public String toString() {
        return String.format("%-10s\t%-10s\t%-10.2f\t%10d", foodId, foodName, foodPrice, stockQty);
    }

    public String displayToCust() {
        return "Food id       : " + foodId +
                "\nFood Name     : " + foodName +
                "\nFood Price    : " + foodPrice;
    }

    public String displayToReport(){
        return String.format("%-24s %-15s %6.2f %17.2f %20s\n", foodName, purchaseQty, foodPrice, calculatePrice(), LocalDate.now());
    }

    public String displaySalesReport(){
        return String.format("%-15s %-24s %-14d %6.2f %17.2f\n", foodId, foodName, purchaseQty, foodPrice, calculatePrice());
    }

    // CALCULATION METHOD
    public abstract double calculatePrice();

    public boolean checkStockQty(int purchaseQty) {
        if (purchaseQty > stockQty) {
            return false;
        }

        return true;
    }

    public void calculateStockQty(int purchaseQty) {
        stockQty -= purchaseQty;
    }

    public void addPurchaseQty(int purchaseQty) {
        this.purchaseQty += purchaseQty;
    }

    public void addStockQty(int purchaseQty) {
        stockQty += purchaseQty;
    }

    public abstract ArrayList<FoodAndBeverage> addFnb(ArrayList<Drinks> drinksList, ArrayList<Snacks> snacksList, ArrayList<FoodAndBeverage> fnbList,
            Scanner scanner) throws Exception;

    public abstract ArrayList<FoodAndBeverage> editFnb(ArrayList<Drinks> drinksList, ArrayList<FoodAndBeverage> fnbList,
            Scanner scanner) throws Exception;

    public abstract ArrayList<FoodAndBeverage> dltFnb(ArrayList<FoodAndBeverage> fnbList, Scanner scanner)
            throws Exception;

    public abstract ArrayList<FoodAndBeverage> buyFnb(ArrayList<FoodAndBeverage> fnbList, Scanner scanner)
            throws Exception;
}