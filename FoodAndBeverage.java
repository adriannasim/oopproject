public class FoodAndBeverage{
    private String foodId;
    private String foodName;
    private double foodPrice;
    private int purchaseQty;
    private int stockQty;

    FoodAndBeverage(){

    }

    FoodAndBeverage(String foodName, double foodPrice, int stockQty){
        foodId = String.valueOf((int) (100000 + (Math.random() * (200000 - 100000 + 1))));
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.stockQty = stockQty; 
    }

    public void editFoodName(String foodName){

    }

    public void editFoodPrice(double foodPrice){

    }

    public void editStockQty(int stockQty){

    }

    public void setPurchaseQty(int purchaseQty){

    }

    public String toString(){
        return "Food id: " + foodId + "\nFood name: " + foodName + "\nFood price: " + foodPrice + "\nStock qty: " + stockQty;
    }

}