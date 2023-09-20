import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class DriverTw {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        boolean cont = true;

        do {
            System.out.println("1. View Purchases");
            System.out.println("2. View Schedules");
            System.out.println("3. View Reports");
            System.out.println("* Press '#' to exit\n");

            do {
                
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
                if (userInput.equals("1")) {
                    viewPurchase();
                } else if (userInput.equals("2")) {
                    viewSchedule();
                } else if (userInput.equals("3")) {
                    viewReport();
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/#).");
                }
                
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("#"));
            
        } while (cont);
        scanner.close();

    }

    //====================================================================================================================//
    //                                            View Purchases                                                          //
    //====================================================================================================================//
    public static void viewPurchase() throws Exception{
        Scanner scanner = new Scanner(System.in);
        boolean cont = true;
        String userInput;

        do{
            System.out.println("==================================================");
            System.out.println("               View Purchase History");
            System.out.println("==================================================\n");
            System.out.println("1. View F&B Purchase History");
            System.out.println("2. View Train Ticket Purchase History");
            System.out.println("* Press '#' to exit\n");
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
                if (userInput.equals("1")) {
                    viewFnbHistory();
                } else if (userInput.equals("2")) {
                    viewTicketHistory();
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/#).");
                }
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("#")); 
        }while(cont == true);
    }
    

    public static void viewFnbHistory() throws Exception{
        
        ArrayList<Snacks> purchaseSnack = Purchase.readFromSnackFile("purchaseSnack.txt");
        ArrayList<String> snackCust = Purchase.snackCust;
        
        
        ArrayList<Drinks> purchaseDrink = Purchase.readFromDrinkFile("purchaseDrink.txt");
        ArrayList<String> drinkCust = Purchase.drinkCust;
        

        System.out.println("==============================================================================================================");
        System.out.println("                                      Food & Beverage Purchase History");
        System.out.println("==============================================================================================================\n");
        System.out.println("No. \t Food Name \t Purchase Quantity \t Description \t Price(RM) \t Subtotal(RM) \t   Date Bought\n\n");

        int i = 0;
            for (Snacks snack : purchaseSnack) {
                System.out.printf("%-7d %s", (i + 1), snack.displayToReport());
                i++;
                System.out.println();
            } 
            
            for (Drinks drink : purchaseDrink) {
                System.out.printf("%-7d %s", (i + 1), drink.displayToReport());
                i++;
                System.out.println();
            }
    }

    public static void viewTicketHistory() throws Exception{
        ArrayList<Ticket> purchaseTicket = Purchase.readFromTicketFile("purchaseTicket.txt");

        System.out.println("==================================================================================");
        System.out.println("                         Train Ticket Purchase History");
        System.out.println("==================================================================================\n");
        System.out.println("No. \t   Date \t Ticket ID \t   Location (From - To) \t Price(RM)\n\n");
        int i = 0;
        for (Ticket ticket : purchaseTicket) {
            System.out.printf("%-7d %s", (i + 1), ticket.displayToReport());
            i++;
            System.out.println();
        }
    }



    //====================================================================================================================//
    //                                             View Schedules                                                         //
    //====================================================================================================================//
    public static void viewSchedule() throws Exception{
        ArrayList<Schedule> scheduleList = Schedule.readFile("scheduleFile.txt");
        System.out.println("==================================================");
        System.out.println("                Train Schedules");
        System.out.println("==================================================\n");

        int i = 0;
        for (Schedule schedule : scheduleList) {
            System.out.printf("%-7d %s", (i + 1), schedule.displayToReport());
            System.out.println();
        }
        
    }

    public static void viewScheduleStaff() throws Exception{
        ArrayList<Schedule> scheduleList = Schedule.readFile("scheduleFile.txt");
        System.out.println("==================================================");
        System.out.println("                Train Schedules");
        System.out.println("==================================================\n");

        int i = 0;
        for (Schedule schedule : scheduleList) {
            System.out.printf("%-7d %s", (i + 1), schedule.displayToReportStaff());
            System.out.println();
        }
    }

    public void custSchedules() throws Exception{
        Schedule sch = new Schedule();
        ArrayList<Schedule> scheduleList = sch.getScheduleList();
        if (scheduleList.size()==0){
            System.out.println("\nNO SCHEDULES IN THE RECORD.\n");
        }else{
            System.out.println("==================================================================================================");
            System.out.printf("%-10s\t%-10s\t%-10s\t%-10s\t%-10s\t%-10s\n",  "From", "To", "Departure Time", "Arrival Time", "Train No", "Price(RM)");
            System.out.println("==================================================================================================");
        }
        for (int i=0; i< scheduleList.size(); i++){
            System.out.println(scheduleList.get(i).custSchedule());
        }
        System.out.println("==================================================================================================");

    }
    


    //====================================================================================================================//
    //                                              View Reports                                                          //
    //====================================================================================================================//
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
        
        System.out.println("============================================================================================");
        System.out.println("                              Food & Beverage Sales Report");
        System.out.println("============================================================================================");
        System.out.println("\nNo. \t Food ID \t Description \t  Quantity Purchased \t Price(RM) \t SubTotal(RM)\n");
        
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
        System.out.println("===============================================================================");
        System.out.println("                          Train Ticket Sales Report");
        System.out.println("===============================================================================");
        System.out.println("\n   Departure - Arrival \t\t    Ticket Price(RM) \t\t Date Purchased\n");

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