public class Drinks extends FoodAndBeverage{
    private String temperature;
    private String size;


    Drinks(){
        this("NotDefined", 0, 0);
    }

    Drinks(String foodName, double foodPrice, int stockQty, String temperature, String size){
        super(foodName, foodPrice, stockQty);
        this.temperature = temperature;
        this.size = size;
    }

    public void setTemperature(String temperature){
        this.temperature = temperature;
    }
    public void setSize(String size){
        this.size = size;
    }

    public String getSize(){
        return size;
    }

    public void setTemperature(String temperature){
        this.temperature =  temperature;
    }

    public String getTemperature(){
        return temperature;
    }

    public String displayToCust(){
        String iceStatus = ice ? "Yes" : "No";
        return super.displayToCust() + 
                "\nIce           : " + iceStatus +
                "\nTemperature   : " + temperature  +
                "\nSize          : " + size;
    }

}
