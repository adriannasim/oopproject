import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class DriverTw {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        boolean cont = true;
        // ArrayList<Schedule> scheduleList = new ArrayList<>();
        // ArrayList<FoodAndBeverage> fnbList = new ArrayList<FoodAndBeverage>();
    //========================================Read from File=========================================================//
        // Train train = new Train();
        // TrainStation station = new TrainStation();
        Schedule schedule = new Schedule();
        // Snacks snacks = new Snacks();
        // Drinks drinks = new Drinks();

        
       ArrayList<Schedule> scheduleList = Schedule.readFromFile("ScheduleFile.dat");
        
        ArrayList<Train> trainList = new ArrayList<Train>();
        ArrayList<TrainStation> stationList = new ArrayList<TrainStation>();

        // ArrayList<Snacks> snacksList = new ArrayList<Snacks>();
        // ArrayList<Drinks> drinksList = new ArrayList<Drinks>();

        // DriverJh.readFromFile("trainFile.txt", trainList, train);
        // DriverJh.readFromFile("stationFile.txt", stationList, station);
        // DriverJh.readFromFile("scheduleFile.txt", scheduleList, schedule);
        // DriverJh.readFromFile("snacksFile.txt", snacksList, snacks);
        // DriverJh.readFromFile("drinksFile.txt", drinksList, drinks);

    //========================================Read from File=========================================================//

        do {
            System.out.println("1. View Purchases");
            System.out.println("2. View Schedules");
            System.out.println("3. View Reports");
            System.out.println("* Press 'x' to exit\n");

            do {
                
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
                if (userInput.equals("1")) {
                    viewPurchase();
                } else if (userInput.equals("2")) {
                    viewSchedule(trainList, stationList, scheduleList);
                } else if (userInput.equals("3")) {
                    viewReport();
                } else if (userInput.equals("x")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/x).");
                }
                
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("x"));
            
        } while (cont);
        scanner.close();

        // Ticket[] tickets = ticketList.toArray(new Ticket[ticketList.size()]);
        // userPurchase.purchaseTicket(tickets);

        // FoodAndBeverage[] fnbs = fnbList.toArray(new FoodAndBeverage[ticketList.size()]);
        // userPurchase.purchaseFnb(fnbs);

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
            System.out.println("* Press 'x' to exit\n");
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
                if (userInput.equals("1")) {
                    viewFnbHistory();
                } else if (userInput.equals("2")) {
                    viewTicketHistory();
                } else if (userInput.equals("x")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/x).");
                }
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("x")); 
        }while(cont == true);
        scanner.close();
    }

    public static void viewFnbHistory() throws Exception{
        
        ArrayList<Snacks> purchaseSnack = Purchase.readFromSnackFile("purchaseSnack.txt");
        // ArrayList<String> snackCust = Purchase.snackCust;
        // ArrayList<Double> snackTotalPrice = Purchase.snackTotalPrice;
        
        ArrayList<Drinks> purchaseDrink = Purchase.readFromDrinkFile("purchaseDrink.txt");
        // ArrayList<String> drinkCust = Purchase.drinkCust;
        // ArrayList<Double> drinkTotalPrice = Purchase.drinkTotalPrice;

        System.out.println("======================================================================");
        System.out.println("                        F&B Purchase History");
        System.out.println("======================================================================\n");

        int i = 0;
            for (Snacks snack : purchaseSnack) {
                System.out.printf("No. %d\n\n", (i + 1));
                System.out.println(snack.displayToReport());
                i++;
                System.out.println("\n----------------------------------------------------------------------\n");
            }
        

        int j = 0;
            for (Drinks drink : purchaseDrink) {
                System.out.printf("No. %d\n\n", (j + 1));
                System.out.println(drink.displayToReport());
                j++;
                System.out.println("\n----------------------------------------------------------------------\n");
            }
        
    }

    public static void viewTicketHistory() throws Exception{
        // ArrayList<Ticket> purchaseTicket = Purchase.readFromTicketFile("purchaseTicket.txt");
        // ArrayList<String> ticketCust = Purchase.ticketList;

        // int z = 1;
        // for (Ticket tickets : purchaseTicket) {
        //     System.out.println();
        //     System.out.println(z + ".");
        //     System.out.println(ticketCust);
        //     z++;
        //     System.out.println();
        //     System.out.println("---------------------------------------------------------");
        //     System.out.println();
        // }
    }



    //====================================================================================================================//
    //                                             View Schedules                                                         //
    //====================================================================================================================//
    public static void viewSchedule(ArrayList<Train> trainList, ArrayList<TrainStation> stationList, ArrayList<Schedule> scheduleList) {
        System.out.println("==================================================");
        System.out.println("                Train Schedules");
        System.out.println("==================================================\n");
        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.println("Schedule No. " + (i+1) + "\n\n" + scheduleList.get(i).displayViewSchedule());
            System.out.println();
        }
    }

    public static void viewScheduleStaff(ArrayList<Train> trainList, ArrayList<TrainStation> stationList, ArrayList<Schedule> scheduleList){
        System.out.println("==================================================");
        System.out.println("                Train Schedules");
        System.out.println("==================================================\n");
        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.println("Schedule No. " + (i+1) + "\n\n" + scheduleList.get(i).displayViewScheduleStaff());
            System.out.println();
        }
    }
    


    //====================================================================================================================//
    //                                              View Reports                                                          //
    //====================================================================================================================//
    public static void viewReport() {
        Scanner scanner = new Scanner(System.in);
        boolean cont = true;
        String userInput;

        do {
            System.out.println("==================================================");
            System.out.println("                 View Report Menu");
            System.out.println("==================================================");
            System.out.println("1. Food and Beverage Sales Report");
            System.out.println("2. Train Ticket Sales Report");
            System.out.println("* Press 'x' to exit\n");

            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
                if (userInput.equals("1")) {
                    fnbSalesReport();
                } else if (userInput.equals("2")) {
                    ticketSalesReport();
                } else if (userInput.equals("x")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/x).");
                }
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("x"));
            scanner.close();
        } while (cont);
    }

    //=========================================== F&B Sales Report =======================================================//
    public static void fnbSalesReport() {
        System.out.println("==================================================");
        System.out.println("                F&B Sales Report");
        System.out.println("==================================================");
        System.out.println("Date : "); // loop based on date
        System.out.println("Food ID \t Description \t Quantity Purchased");
        // print array
        System.out.println("Total Sales Amount (RM) : "); //call calc function
    }

    //========================================== Ticket Sales Report =====================================================//
    public static void ticketSalesReport() {
        System.out.println("==================================================");
        System.out.println("            Train Ticket Sales Report");
        System.out.println("==================================================");
        System.out.println("Date : "); // loop based on date
        System.out.println("Departure - Arrival \t Quantity Purchased");
        System.out.println("Number of Tickets Sold  : "); 
        System.out.println("Total Sales Amount (RM) : "); //call calc function
    }

}