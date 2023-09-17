import java.io.Serializable;

public class Snacks extends FoodAndBeverage implements Serializable{
    private boolean partyPack;

    Snacks(){
 
    }

    Snacks(boolean partyPack){

    }

    Snacks(String foodName, double foodPrice, int stockQty, boolean partyPack){
        super(foodName,foodPrice,stockQty);
        this.partyPack = partyPack;
    }

    public void setPartyPack(boolean partyPack){
        this.partyPack = partyPack;
    }

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
