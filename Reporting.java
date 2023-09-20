import java.util.ArrayList;

public class Reporting {
    private FoodAndBeverage[] fnbSold;
    private Ticket[] ticketSold;

    public Reporting(){

    }
    //======================================Read from File===============================================//
    
    ArrayList<Snacks> purchaseSnack = Purchase.readFromSnackFile("purchaseSnack.txt");
    ArrayList<Drinks> purchaseDrink = Purchase.readFromDrinkFile("purchaseDrink.txt");

    //======================================Read from File===============================================//


    public double calculateFnbSales(FoodAndBeverage[] fnbSold){
        double totalFnb = 0;
        for(int i = 0;i <= fnbSold.length;i++){
            totalFnb += fnbSold[i].getFoodPrice();
        }
        return totalFnb;
    }

    public double fnbDailySales(FoodAndBeverage[] fnb){
        return calculateFnbSales(fnb);
    }

    public double calculateTicketSales(){
        double totalTicketSales = 0;
        for(Ticket ticket : ticketSold){
        totalTicketSales += ticket.getTicketSchedule().getTicketPrice();
        }
        return totalTicketSales;
    }

    // public double calculateTicketSales(Ticket[] ticketSold) throws Exception{
    //     ArrayList<Ticket> ticketList = Purchase.readFromTicketFile("purchaseTicket.txt");
    //     double totalTicketSales = 0;
    //     for(Ticket ticket : ticketList){
    //         totalTicketSales += ticket.getTicketSchedule().getTicketPrice();
    //     }
    //     return totalTicketSales;
    // }

    public static String calculateTicketSales(ArrayList<Ticket> ticketList) {
        return null;
    }

}
