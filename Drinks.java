public class Drinks extends FoodAndBeverage{
    private boolean ice;
    private String size;
    private String temperature;

    Drinks(){
        this("NotDefined", 0, 0);
    }

    Drinks(String foodName, double foodPrice, int stockQty){
        super(foodName, foodPrice, stockQty);
        ice = false;
        size = "Small";
        temperature = "Cold";
    }

    public void setIce(boolean ice){
        this.ice = ice;
    }

    public boolean getIce(){
        return ice;
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
