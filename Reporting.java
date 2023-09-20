import java.util.ArrayList;

public class Reporting {
    private FoodAndBeverage[] fnbSold;
    private Ticket[] ticketSold;

    public Reporting(){

    }
    //======================================Read from File===============================================//
    
    // ArrayList<Snacks> purchaseSnack = Purchase.readFromSnackFile("purchaseSnack.txt");
    // ArrayList<Drinks> purchaseDrink = Purchase.readFromDrinkFile("purchaseDrink.txt");
    ArrayList<Double> snackTotalPrice = Purchase.snackTotalPrice;
    ArrayList<Double> drinkTotalPrice = Purchase.drinkTotalPrice;

    //======================================Read from File===============================================//


    public double calculateFnbSales(FoodAndBeverage[] fnbSold){
        double totalFnb = 0;
        /* 
        for (Double snack : snackTotalPrice) {
            totalFnb += snack;
        }
        for (Double drink : drinkTotalPrice) {
            totalFnb += drink;
        }
        */
        for (FoodAndBeverage fnb : fnbSold){
            totalFnb += fnb.calculatePrice();
        }
        return totalFnb;
    }

    public double fnbDailySales(FoodAndBeverage[] fnb){
        return calculateFnbSales(fnb);
    }

    // public double calculateTicketSales(){
    //    double totalTicketSales = 0;
    //    for(Ticket ticket : ticketSold){
    //    totalTicketSales += ticket.getTicketSchedule().getTicketPrice();
    //    }
    //    return totalTicketSales;
    // }

     public double calculateTicketSales(Ticket[] ticketSold) throws Exception{
         ArrayList<Ticket> ticketList = Purchase.readFromTicketFile("purchaseTicket.txt");
         double totalTicketSales = 0;
         for(Ticket ticket : ticketList){
             totalTicketSales += ticket.getTicketSchedule().getTicketPrice();
         }
         return totalTicketSales;
     }

    //public static String calculateTicketSales(ArrayList<Ticket> ticketList) {
    //    return null;
    //}

    //public static String calculateFnbSales() {
    //    return null;
    //}

}
