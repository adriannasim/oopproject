public class FoodAndBeverage{
    private String foodId;
    private String foodName;
    private double foodPrice;
    private int purchaseQty;
    private int stockQty;

    FoodAndBeverage(){
        this("NotDefined", 0, 0);
    }

    FoodAndBeverage(String foodName, double foodPrice, int stockQty){
        foodId = String.valueOf((int) (100000 + (Math.random() * (200000 - 100000 + 1))));
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.stockQty = stockQty; 
        purchaseQty = 0;
    }

    public String getFoodId(){
        return foodId;
    }

    public String getFoodName(){
        return foodName;
    }
    public void editFoodName(String foodName){
        this.foodName =  foodName;
    }

    public double getFoodPrice(){
        return foodPrice;
    }

    public int getStockQty(){
        return stockQty;
    }

    public int getPurchaseQty(){
        return purchaseQty;
    }

    public void editFoodPrice(double foodPrice){
        this.foodPrice = foodPrice;
    }

    public void editStockQty(int stockQty){
        this.stockQty = stockQty;
    }

    public void setPurchaseQty(int purchaseQty){
        this.purchaseQty = purchaseQty;
    }

    public String toString(){
        return "Food id: " + foodId + "\nFood name: " + foodName + "\nFood price: " + foodPrice + "\nStock qty: " + stockQty;
    }

    public double calculatePrice(){
        return foodPrice * purchaseQty;
    }

    public boolean checkStockQty(int purchaseQty){
        if (purchaseQty > stockQty){
            return false;
        }

        return true;
    }

    public void calculateStockQty(int purchaseQty){
        stockQty -= purchaseQty;
    }

    public void addPurchaseQty(int purchaseQty){
        this.purchaseQty += purchaseQty;
    }

}