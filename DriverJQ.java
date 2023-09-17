
import java.util.*;
import java.time.LocalDate;

public class DriverJQ {
    public static void purchasing() throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean continueInput = true;
        int userOpt1 = 0;
        char yesno1;

        do {

            // =================================================================================
            // BUY TICKETS OR FOOD AND BEVERAGE
            // =================================================================================
            System.out.println("1. Tickets");
            System.out.println("1. Food and Beverage");

            do {
                try {
                    System.out.print("Enter your option : ");
                    userOpt1 = scanner.nextInt();

                    continueInput = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInput);

            switch (userOpt1) {
                case 1:
                    System.out.println("buy ticket"); // return ticketList
                    break;
                case 2:
                    System.out.println("buy fnb"); // return fnbList
                    break;
                default:
                    System.out.println("Invalid Input. ");
                    break;
            }

            System.out.println("Confirm your purchase tickets and orders? (Y - YES, other - NO)");
            yesno1 = scanner.next().charAt(0);

        } while (yesno1 != 'y' || yesno1 != 'Y');

        //Add to Purchase
        //Proceed to Payment

    }

    // ==============================================================================================================
    // BUY TICKETS
    // User may add tickets into ticketlist. After user choose to not continue
    // buying, user may ask for modification.
    // Then, user may ask for confirmation and proceed to purchase fnb.
    // ==============================================================================================================
    public static void buyTicket(String[] args) throws Exception {
        char yesno1 = 'n', yesno2 = 'n'; // yesno1 - add more Ticket, yesno2 - make changes to added ticket
        int userOpt1 = 0; // option for delete or modify
        Scanner scanner = new Scanner(System.in);
        ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
        boolean continueInput = true;

        // Add tickets into ticketlist
        do {

            ticketList.add(addTicket(scheduleList));
            System.out.print("Would you like to buy more ticket? (Y - YES, OTHER - NO)");
            yesno1 = scanner.next().charAt(0);

        } while (yesno1 == 'y' || yesno1 == 'Y');

        // Display added tickets
        int z = 1;
        for (Ticket tickets : ticketList) {
            System.out.println(z + ".");
            System.out.println(tickets);
            z++;
        }
        do {
            // Make changes to the added ticket (DELETE or MODIFY)
            System.out.print("Would you like to make changes on tickets? (Y - YES, others - NO) : ");
            yesno2 = scanner.next().charAt(0);
            if (yesno2 == 'y' || yesno2 == 'Y') {

                System.out.println("1.Delete added tickets");
                System.out.println("2.Modify added tickets");

                do {
                    try {
                        System.out.print("Enter your option : ");
                        userOpt1 = scanner.nextInt();

                        continueInput = false;
                    } catch (InputMismatchException ex) {
                        System.out.println("Incorrect Input. Please try agin. ");
                        scanner.nextLine();
                    }
                } while (continueInput);

                switch (userOpt1) {
                    case 1:
                        ticketList = dltTicket(ticketList, scheduleList);
                        break;
                    case 2:
                        ticketList = modifyTicket(ticketList, scheduleList);
                        break;
                    default:
                        System.out.println("Invalid Input. ");
                        break;
                }
            }

        } while (yesno2 == 'y' || yesno2 == 'Y');

        // Confirmation
    }

    // -------------------------------------ADDTICKET----------------------------------------------------
    // User can buy tickets for DIFFERENT details or SAME details. User can only buy
    // tickets for current day and after current day.
    // --------------------------------------------------------------------------------------------------
    public static Ticket addTicket(ArrayList<Schedule> scheduleList) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Ticket ticket;
        Schedule ticketSchedule;
        LocalDate ticketDate;
        LocalDate today = LocalDate.now();
        int day = 0, month = 0, year = 0;
        boolean validDate = true;
        int scheduleNo = 0;
        boolean validSchedule = true;
        boolean continueInputS = true;
        boolean continueInputY = true;
        boolean continueInputD = true;
        boolean continueInputM = true;

        // Display schedule for user to choose
        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.printf("%d.\n", i + 1);
            System.out.println(scheduleList.get(i).toString());
            System.out.println();
        }

        // Select schedule for the ticket
        do {

            do {
                try {
                    System.out.print("Select your schedule : ");
                    scheduleNo = scanner.nextInt();

                    continueInputS = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect input. Please try again. ");
                    scanner.nextLine();
                }
            } while (continueInputS);

            if (scheduleNo > scheduleList.size()) {
                System.out.println("Schedule not found. Please enter again.");
                validSchedule = false;
            } else {
                validSchedule = true;
            }

        } while (!validSchedule);

        ticketSchedule = scheduleList.get(scheduleNo - 1);

        // Enter date for the ticket
        do {
            System.out.println("Enter your date ");

            // Enter year
            do {
                try {
                    System.out.print("Year : ");
                    year = scanner.nextInt();

                    continueInputY = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputY);

            // Enter month
            do {
                try {
                    System.out.print("Month : ");
                    month = scanner.nextInt();

                    continueInputM = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputM);

            // Enter day
            do {
                try {
                    System.out.print("Day : ");
                    day = scanner.nextInt();

                    continueInputD = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputD);

            ticketDate = LocalDate.of(year, month, day);

            if (ticketDate.isBefore(today)) {
                System.out.println("Invalid Date. Please enter again.");
                validDate = false;
            } else {
                validDate = true;
            }

        } while (!validDate);

        ticket = new Ticket(ticketSchedule, ticketDate);

        return ticket;
    }

    /*
     * //TEMP TICKET ARRAY FOR TESTING
     * ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
     * ticketList.add(new Ticket(scheduleList.get(1), date));
     * ticketList.add(new Ticket(scheduleList.get(0), date));
     * ticketList.add(new Ticket(scheduleList.get(1), date));
     * 
     * int z = 0;
     * for (Ticket tickets : ticketList) {
     * System.out.printf("%d.", z + 1);
     * System.out.println(tickets);
     * z++;
     * }
     */

    // -------------------------------------------DELETETICKET---------------------------------------------------
    // User can delete all the added tickets or select a particular ticket to delete
    // -----------------------------------------------------------------------------------------------------------
    public static ArrayList<Ticket> dltTicket(ArrayList<Ticket> ticketList, ArrayList<Schedule> scheduleList)
            throws Exception {

        Scanner scanner = new Scanner(System.in);

        int dltTicket = 0;
        boolean validDlt = true;
        int dltOption = 0;
        boolean continueInputA = true;
        boolean continueInputP = true;

        System.out.println("1. Delete all added tickets");
        System.out.println("2. Delete a particular ticket");
        System.out.println("3. Exit");

        // Select delete mode
        do {
            try {
                System.out.print("Enter your option : ");
                dltOption = scanner.nextInt();

                continueInputA = false;
            } catch (InputMismatchException ex) {
                System.out.println("Incorrect Input. Please try agin. ");
                scanner.nextLine();
            }
        } while (continueInputA);

        if (dltOption == 1) {

            // Clear all the element in ticketList
            ticketList.clear();
            if (ticketList.size() == 0) {
                System.out.println("You have delete all the tickets successfully.");
            }

        } else if (dltOption == 2) {

            // Remove a paticular element in ticketList
            do {

                do {
                    try {
                        System.out.print("Select the ticket you would like to delete : ");
                        dltTicket = scanner.nextInt();

                        continueInputP = false;
                    } catch (InputMismatchException ex) {
                        System.out.println("Incorrect Input. Please try agin. ");
                        scanner.nextLine();
                    }
                } while (continueInputP);

                if (dltTicket > ticketList.size()) {
                    System.out.println("Ticket not found. Please try again. ");
                    validDlt = false;
                } else {
                    validDlt = true;
                }

            } while (!validDlt);

            ticketList.remove(dltTicket - 1);

            System.out.println("Delete successfully. ");

        } else {

            System.out.println("Delete cancelled. ");

        }

        return ticketList;
    }

    // ----------------------------------------------MODIFYTICKET------------------------------------------------
    // User can select either schedule or date of the selected tickets to modify.
    // ----------------------------------------------------------------------------------------------------------
    public static ArrayList<Ticket> modifyTicket(ArrayList<Ticket> ticketList, ArrayList<Schedule> scheduleList) {

        return ticketList;
    }

    // ==============================================================================================================
    // BUY FNB
    // ==============================================================================================================
    public static void main(String[] args) throws Exception {
        // TEMP SNACK AND DRINK LIST
        ArrayList<Snacks> snacksList = new ArrayList<Snacks>();
        snacksList.add(new Snacks("Snack1", 20, 90, true));
        snacksList.add(new Snacks("Snack1", 10, 90, false));
        snacksList.add(new Snacks("Snack2", 12, 90, true));
        snacksList.add(new Snacks("Snack2", 8, 90, false));

        ArrayList<Drinks> drinksList = new ArrayList<Drinks>();
        drinksList.add(new Drinks("Drink1", 12, 90));
        drinksList.add(new Drinks("Drink2", 10, 90));
        drinksList.add(new Drinks("Drink3", 8, 90));

        // START
        Scanner scanner = new Scanner(System.in);
        ArrayList<FoodAndBeverage> fnbList = new ArrayList<FoodAndBeverage>();
        boolean continueInput = true;
        boolean continueInputF = true;
        int userOpt1 = 0;
        char yesno1, yesno2, yesno3; // yesno1 - Continue to add more fnb, yesno2 - Continue to add more snacks,
                                     // yesno3 - Continue to add more drinks
        char cont1; // cont1 - Make changes to fnb
        int addFnb = 0;
        double subtotal = 0;

        do {
            System.out.println("1. Snacks");
            System.out.println("2. Drinks");

            // Select snacks or drinks
            do {
                try {
                    System.out.print("Enter your option : ");
                    addFnb = scanner.nextInt();

                    continueInput = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInput);

            if (addFnb == 1) {

                // Add Snacks
                do {
                    fnbList.add(addSnacks(snacksList));
                    System.out.print("Would you like to buy more snacks? (Y - YES, OTHER - NO)  ");
                    yesno2 = scanner.next().charAt(0);
                } while (yesno2 == 'y' || yesno2 == 'Y');

                // Display Snacks
                int z = 1;
                for (FoodAndBeverage fnbs : fnbList) {
                    if (fnbs instanceof Snacks) {
                        System.out.println(z + ".");
                        System.out.println(fnbs);
                        System.out.println("Purchase Quantity : " + fnbs.getPurchaseQty());
                        System.out.println("Total Amount : " + fnbs.calculatePrice());
                        subtotal += fnbs.calculatePrice();
                        System.out.println();
                        z++;
                    }
                }

            } else if (addFnb == 2) {

                // Add Drinks
                do {
                    fnbList.add(addDrinks(drinksList));
                    System.out.print("Would you like to buy more drinks? (Y - YES, OTHER - NO)  ");
                    yesno3 = scanner.next().charAt(0);
                } while (yesno3 == 'y' || yesno3 == 'Y');

                // Display Drinks
                int y = 1;
                for (FoodAndBeverage fnbs : fnbList) {
                    if (fnbs instanceof Drinks) {
                        System.out.println(y + ".");
                        System.out.println(fnbs);
                        System.out.println("Purchase Quantity : " + fnbs.getPurchaseQty());
                        System.out.println("Total Amount : " + fnbs.calculatePrice());
                        subtotal += fnbs.calculatePrice();
                        System.out.println();
                        y++;
                    }
                }

            } else {

                System.out.println("Invalid Input.");

            }

            System.out.print("Would you like to add more orders? (Y - YES, other - NO)  ");
            yesno1 = scanner.next().charAt(0);

        } while (yesno1 == 'y' || yesno1 == 'Y');

        // Display added fnb
        int y = 1;
        for (FoodAndBeverage fnbs : fnbList) {
            System.out.println(y + ".");
            System.out.println(fnbs);
            System.out.println("Purchase Quantity : " + fnbs.getPurchaseQty());
            System.out.println("Total Amount : " + fnbs.calculatePrice());
            subtotal += fnbs.calculatePrice();
            System.out.println();
            y++;

        }

        do {
            // Make changes to the orders
            System.out.print("Would you like to make changes to orders? (Y - YES, other - NO)  ");
            cont1 = scanner.next().charAt(0);

            if (cont1 == 'y' || cont1 == 'Y') {

                System.out.println("1. Delete orders ");
                System.out.println("2. Modify orders ");

                do {
                    try {
                        System.out.print("Enter your option : ");
                        userOpt1 = scanner.nextInt();

                        continueInputF = false;
                    } catch (InputMismatchException ex) {
                        System.out.println("Incorrect Input. Please try agin. ");
                        scanner.nextLine();
                    }
                } while (continueInputF);

                // Confirmation

            }

        } while (cont1 == 'y' || cont1 == 'Y');

    }

    // ---------------------------------------ADDSNACKS------------------------------------
    // User can buy one or many different and same snacks
    // ------------------------------------------------------------------------------------
    public static FoodAndBeverage addSnacks(ArrayList<Snacks> snacksList) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Snacks snack;
        int addSnacks = 0;
        int snackQty = 0;
        boolean continueInputS = true;
        boolean continueInputSQ = true;
        boolean validSnack = true;

        // Display snacks
        for (int i = 0; i < snacksList.size(); i++) {
            System.out.printf("%d.\n", i + 1);
            System.out.println(snacksList.get(i).toString());
            System.out.println();
        }
        System.out.println("Which snack would you like to add?");

        // Enter option
        do {

            do {
                try {
                    System.out.print("Enter your option : ");
                    addSnacks = scanner.nextInt();

                    continueInputS = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputS);

            if (addSnacks > snacksList.size()) {
                System.out.println("Snack not found. Please try again. ");
                validSnack = false;
            } else {
                validSnack = true;
            }

        } while (!validSnack);

        snack = snacksList.get(addSnacks - 1);

        do {

            // Enter quantity
            do {
                try {
                    System.out.print("Enter quantity : ");
                    snackQty = scanner.nextInt();

                    continueInputSQ = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputSQ);

            if (snack.checkStockQty(snackQty)) {

                // Purchase quantity <= stock quantity
                snack.calculateStockQty(snackQty);
                snack.setPurchaseQty(snackQty);

            } else {

                // Purchase quantity > stock quantity
                System.out.println("Sorry, we have only " + snack.getStockQty() + " for " + snack.getFoodName());
                System.out.println("Please enter again : ");

            }

        } while (!snack.checkStockQty(snackQty));

        return snack;
    }

    // ---------------------------------------ADDDRINKS------------------------------------
    // User can buy one or many different and same drinks
    // ------------------------------------------------------------------------------------
    public static FoodAndBeverage addDrinks(ArrayList<Drinks> drinksList) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Drinks drink;
        int addDrinks = 0;
        int drinkQty = 0;
        boolean continueInputD = true;
        boolean continueInputDQ = true;
        boolean validDrink = true;

        // Display snacks
        for (int i = 0; i < drinksList.size(); i++) {
            System.out.printf("%d.\n", i + 1);
            System.out.println(drinksList.get(i).toString());
            System.out.println();
        }
        System.out.println("Which drink would you like to add?");

        // Enter option
        do {

            do {

                try {
                    System.out.print("Enter your option : ");
                    addDrinks = scanner.nextInt();

                    continueInputD = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }

            } while (continueInputD);

            if (addDrinks > drinksList.size()) {
                System.out.println("Snack not found. Please try again. ");
                validDrink = false;
            } else {
                validDrink = true;
            }

        } while (!validDrink);

        drink = drinksList.get(addDrinks - 1);

        do {

            // Enter quantity
            do {
                try {
                    System.out.print("Enter quantity : ");
                    drinkQty = scanner.nextInt();

                    continueInputDQ = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputDQ);

            if (drink.checkStockQty(drinkQty)) {

                // Purchase quantity <= Stock Quantity
                drink.calculateStockQty(drinkQty);
                drink.setPurchaseQty(drinkQty);

            } else {

                // Purchase quantity > Stock Quatity
                System.out.println("Sorry, we have only " + drink.getStockQty() + " for " + drink.getFoodName());
                System.out.println("Please enter again : ");

            }

        } while (!drink.checkStockQty(drinkQty));

        return drink;
    }

    // Delete fnb

}
