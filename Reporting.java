import java.util.ArrayList;
import java.util.Scanner;

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
    public static void viewReport() throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean cont = true;
        String userInput;

        do {
            System.out.println("==================================================");
            System.out.println("                 View Report Menu");
            System.out.println("==================================================");
            System.out.println("1. Food and Beverage Sales Report");
            System.out.println("2. Train Ticket Sales Report");
            System.out.println("* Press '#' to exit\n");

            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
                if (userInput.equals("1")) {
                    fnbSalesReport();
                } else if (userInput.equals("2")) {
                    ticketSalesReport();
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/#).");
                }
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("#"));
        } while (cont);
    }

    //=========================================== F&B Sales Report =======================================================//
    public static void fnbSalesReport() throws Exception{
        Reporting report = new Reporting();
        ArrayList<Drinks> purchaseDrink = Purchase.readFromDrinkFile("purchaseDrink.txt");
        ArrayList<Snacks> purchaseSnack = Purchase.readFromSnackFile("purchaseSnack.txt");
        ArrayList<FoodAndBeverage> purchaseFnb = new ArrayList<FoodAndBeverage>();

        for (int i=0;i<purchaseDrink.size();i++){
            purchaseFnb.add(purchaseDrink.get(i));
        }
        for (int i=0;i<purchaseSnack.size();i++){
            purchaseFnb.add(purchaseSnack.get(i));
        }

        FoodAndBeverage[] fnbs = purchaseFnb.toArray(new FoodAndBeverage[purchaseFnb.size()]);
        if (purchaseFnb.size()==0){
            System.out.println("\nNO RECORDS AVAILABLE.\n");
        }else{
        System.out.println("============================================================================================");
        System.out.println("                              Food & Beverage Sales Report");
        System.out.println("============================================================================================");
        System.out.println("\nNo. \t Food ID \t Description \t  Quantity Purchased \t Price(RM) \t SubTotal(RM)\n");
        }
        int i = 0;
            for (Snacks snack : purchaseSnack) {
                System.out.printf("%-8d %s", (i + 1), snack.displaySalesReport());
                i++;
                System.out.println();
            } 
            
            for (Drinks drink : purchaseDrink) {
                System.out.printf("%-8d %s", (i + 1), drink.displaySalesReport());
                i++;
                System.out.println();
            }
            
        System.out.printf("Total Sales Amount (RM) : %.2f\n\n",report.calculateFnbSales(fnbs)); 
    }

    //========================================== Ticket Sales Report =====================================================//
    public static void ticketSalesReport() throws Exception{
        Reporting report = new Reporting();
        ArrayList<Ticket> ticketList = Purchase.readFromTicketFile("purchaseTicket.txt");
        Ticket[] ticketLists = ticketList.toArray(new Ticket[ticketList.size()]);
        if (ticketList.size()==0){
            System.out.println("\nNO RECORDS AVAILABLE.\n");
        }else{
        System.out.println("===============================================================================");
        System.out.println("                          Train Ticket Sales Report");
        System.out.println("===============================================================================");
        System.out.println("\n   Departure - Arrival \t\t    Ticket Price(RM) \t\t Date Purchased\n");
        }
        int i = 0;
        for (Ticket ticket : ticketList) {
            System.out.printf("%s", ticket.displaySalesReport());
            i++;
            System.out.println();
        }
        System.out.println("Number of Tickets Sold  : "+ i); 
        System.out.printf("\nTotal Sales Amount (RM) : %.2f\n\n",report.calculateTicketSales(ticketLists)); 
    }
}
