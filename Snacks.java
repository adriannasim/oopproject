import java.io.Serializable;

public class Snacks extends FoodAndBeverage implements Serializable{
    private boolean partyPack;

    //-----------------------------------CONSTRUCTOR---------------------------------------- 
    // NO-ARG CONSTRUCTOR
    Snacks(){
 
    }

    // PARAMETERIZED CONSTRUCTOR
    Snacks(boolean partyPack){

    }

    Snacks(String foodName, double foodPrice, int stockQty, boolean partyPack){
        super(foodName,foodPrice,stockQty);
        this.partyPack = partyPack;
    }

    Snacks(String foodId, String foodName, double foodPrice, int purchaseQty, int stockQty, boolean partyPack){
        super(foodId, foodName, foodPrice, purchaseQty, stockQty);
        this.partyPack =  partyPack;
    }
    //------------------------------------METHOD-----------------------------------------
    // UPDATE METHOD
    public void setPartyPack(boolean partyPack){
        this.partyPack = partyPack;
    }

    // READ METHOD
    public boolean getPartyPack(){
        return partyPack;
    }

    // DISPLAY METHOD
    public String toString() {
        String partyPackStatus = partyPack ? "true" : "false";
        return super.toString() + "\nParty pack: " + partyPackStatus;
    }

    public String displayToCust(){
        String partyPackStatus = partyPack ? "Yes" : "No";
        return super.displayToCust() +
                "\nParty Pack    : " + partyPackStatus;
    }
}
