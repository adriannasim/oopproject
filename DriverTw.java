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

        ArrayList<Train> trainList = new ArrayList<Train>();
        ArrayList<TrainStation> stationList = new ArrayList<TrainStation>();
        ArrayList<Schedule> scheduleList = schedule.getScheduleList(); 
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
            scanner.close();
        } while (cont);

        // Ticket[] tickets = ticketList.toArray(new Ticket[ticketList.size()]);
        // userPurchase.purchaseTicket(tickets);

        // FoodAndBeverage[] fnbs = fnbList.toArray(new FoodAndBeverage[ticketList.size()]);
        // userPurchase.purchaseFnb(fnbs);

    }

    //====================================================================================================================//
    //                                            View Purchases                                                          //
    //====================================================================================================================//
    public static void viewPurchase() {
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
                    
                } else if (userInput.equals("2")) {
                    
                } else if (userInput.equals("x")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/x).");
                }
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("x")); 
            scanner.close();
        }while(cont == true);
    }

    public static void viewFnbHistory(){

    }

    public static void viewTicketHistory(){

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