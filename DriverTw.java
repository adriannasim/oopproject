import java.util.*;
import java.time.LocalDate;

public class DriverTw {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        boolean cont = true;
        ArrayList<Schedule> scheduleList = new ArrayList<>();
        Schedule schedule = new Schedule();

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
                    viewSchedule(scheduleList);
                } else if (userInput.equals("3")) {
                    viewReport();
                } else if (userInput.equals("x")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/x).");
                }
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("x"));

        } while (cont);

        System.out.println(scheduleList.toString());
    }

    //====================================================================================================================//
    //                                            View Purchases                                                          //
    //====================================================================================================================//
    public static void viewPurchase() {
        System.out.println("hihi");
    }

    //====================================================================================================================//
    //                                             View Schedules                                                         //
    //====================================================================================================================//
    public static void viewSchedule(ArrayList<Schedule> scheduleList) {
        System.out.println(scheduleList.size());
    }

    //====================================================================================================================//
    //                                              View Reports                                                          //
    //====================================================================================================================//
    public static void viewReport() {
        Scanner scanner = new Scanner(System.in);
        boolean cont = true;
        String userInput;

        do {
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

        } while (cont);
    }

    //=========================================== F&B Sales Report =======================================================//
    public static void fnbSalesReport() {
        System.out.println("============================");
        System.out.println("      F&B Sales Report");
        System.out.println("============================\n");
        System.out.println("Date : "); // loop based on date
        System.out.println("Food \t Quantity Purchased");
        // print array
        System.out.println("Total Sales Amount (RM) : "); //call calc function
    }

    //========================================== Ticket Sales Report =====================================================//
    public static void ticketSalesReport() {
        System.out.println("=============================");
        System.out.println("  Train Ticket Sales Report");
        System.out.println("=============================\n");
        System.out.println("Date : "); // loop based on date
        System.out.println("Departure - Arrival \t Quantity Purchased");
        System.out.println("Number of Tickets Sold  : "); 
        System.out.println("Total Sales Amount (RM) : "); //call calc function
    }
}