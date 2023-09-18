import java.io.Serializable;

public class Drinks extends FoodAndBeverage implements Serializable{
    private String temperature;
    private String size;
    private boolean ice;

    //-----------------------------------CONSTRUCTOR---------------------------------------- 
    // NO-ARG CONSTRUCTOR
    Drinks(){
        this("NotDefined", 0, 0);
    }

    // PARAMETERIZED CONSTRUCTOR
    Drinks(String foodName, double foodPrice, int stockQty){
        super(foodName, foodPrice, stockQty);
    }

    Drinks(String foodName, double foodPrice, int stockQty, String temperature, String size){
        super(foodName, foodPrice, stockQty);
        this.temperature = temperature;
        this.size = size;
    }

    Drinks(String foodId, String foodName, double foodPrice, int purchaseQty, int stockQty, String temperature, String size, boolean ice){
        super(foodId, foodName, foodPrice, purchaseQty, stockQty);
        this.temperature = temperature;
        this.size = size;
        this.ice = ice;
    }
     //------------------------------------METHOD-----------------------------------------
     
    // UPDATE METHOD
    public void setSize(String size){
        this.size = size;
    }

    public void setIce(boolean ice){
        this.ice = ice;
    }

    public void setTemperature(String temperature){
        this.temperature =  temperature;
    }

    // READ METHOD
    public String getSize(){
        return size;
    }

    public String getTemperature(){
        return temperature;
    }

    public boolean getIce(){
        return ice;
    }

    // DISPLAY METHOD
    public String displayToCust(){
        String iceStatus = ice ? "Yes" : "No";
        return super.displayToCust() + 
                "\nIce           : " + iceStatus +
                "\nTemperature   : " + temperature  +
                "\nSize          : " + size;
    }

    // CALCULATION METHOD
    public double calculatePrice(){
        double sizePrice;
        if (size.equalsIgnoreCase("Medium")){
            sizePrice = 1.2;
        } else if (size.equalsIgnoreCase("Big")){
            sizePrice = 2;
        } else {
            sizePrice = 0;
        }
        return (foodPrice + sizePrice) * purchaseQty;
    }

}
