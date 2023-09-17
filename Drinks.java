public class Drinks extends FoodAndBeverage{
    private String temperature;
    private String size;


    Drinks(){

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

    }

    public String getSize(){
        return size;
    }

}
