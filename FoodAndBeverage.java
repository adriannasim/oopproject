
public abstract class FoodAndBeverage{
    protected String foodId;
    protected String foodName;
    protected double foodPrice;
    protected int purchaseQty;
    protected int stockQty;

    //-----------------------------------CONSTRUCTOR---------------------------------------- 
    // NO-ARG CONSTRUCTOR
    FoodAndBeverage(){
        foodId = "Undefined";
        foodName = "Undefined";
    }

    // PARAMETERIZED CONSTRUCTOR
    FoodAndBeverage(String foodName, double foodPrice, int stockQty){
        foodId = String.valueOf((int) (100000 + (Math.random() * (200000 - 100000 + 1))));
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.stockQty = stockQty; 
        purchaseQty = 0;
    }

    FoodAndBeverage(String foodId, String foodName, double foodPrice, int purchaseQty, int stockQty){
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.purchaseQty = purchaseQty;
        this.stockQty = stockQty;

    }
    //------------------------------------METHOD-----------------------------------------
    // READ METHOD
    public String getFoodId(){
        return foodId;
    }

    public String getFoodName(){
        return foodName;
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

    // UPDATE METHOD
    public void editFoodName(String foodName){
        this.foodName =  foodName;
    }

    public void editFoodPrice(double foodPrice){
        this.foodPrice = foodPrice;
    }

    public boolean editStockQty(String sign, int stockQty){
        if (sign.equals("+")){
            this.stockQty+=stockQty;
            return true;
        }else{
            if(stockQty>this.stockQty){
                System.out.println("Error: This quantity is larger than the stock qty.");
                return false;
            }else{
                this.stockQty-=stockQty;
                return true;
            }
        }
    }

    public void setPurchaseQty(int purchaseQty){
        this.purchaseQty = purchaseQty;
    }

    // DISPLAY METHOD
    public String toString(){
        return "Food id: " + foodId + "\nFood name: " + foodName + "\nFood price: " + foodPrice + "\nStock qty: " + stockQty;
    }

    public String displayToCust(){
        return "Food id       : " + foodId +
               "\nFood Name     : " + foodPrice +
               "\nFood Price    : " + foodPrice;
    }

    // CALCULATION METHOD
    public abstract double calculatePrice();

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

    public void addStockQty(int purchaseQty){
        stockQty += purchaseQty;
    }

    public abstract void writePurchaseFnB(FoodAndBeverage fnbList , Login login);
}