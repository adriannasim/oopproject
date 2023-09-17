public class Drinks extends FoodAndBeverage{
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

    // DISPLAY METHOD
    public String displayToCust(){
        String iceStatus = ice ? "Yes" : "No";
        return super.displayToCust() + 
                "\nIce           : " + iceStatus +
                "\nTemperature   : " + temperature  +
                "\nSize          : " + size;
    }

}
