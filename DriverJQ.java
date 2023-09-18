
import java.util.*;
import java.time.LocalDate;

public class DriverJQ {
    public static void makePurchase() throws Exception {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
        ArrayList<FoodAndBeverage> fnbList = new ArrayList<FoodAndBeverage>();
        Purchase userPurchase = new Purchase();
        PaymentType pType = new PaymentType();

        boolean continueInput = true;
        boolean continueInputPT = true;

        int userOpt1 = 0;
        int paymentTypeOpt = 0;
        char yesno1;

        do {

            // =================================================================================
            // BUY TICKETS OR FOOD AND BEVERAGE
            // =================================================================================
            System.out.println("1. Tickets");
            System.out.println("2. Food and Beverage");

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
                    ticketList = buyTicket(ticketList);
                    break;
                case 2:
                    fnbList = buyFnb(fnbList);
                    break;
                default:
                    System.out.println("Invalid Input. ");
                    break;
            }

            System.out.println("================================");
            System.out.println("            Tickets");
            System.out.println("================================");
            int z = 1;
            for (Ticket tickets : ticketList) {
                System.out.println(z + ".");
                System.out.println(tickets);
                z++;
            }

            System.out.println("================================");
            System.out.println("        Food And Beverage");
            System.out.println("================================");
            int y = 1;
            for (FoodAndBeverage fnbs : fnbList) {
                System.out.println(y + ".");
                System.out.println(fnbs.displayToCust());
                System.out.println("Purchase Quantity >> " + fnbs.getPurchaseQty());
                System.out.println("Total Amount      >> " + fnbs.calculatePrice());
                System.out.println();
                y++;
            }

            System.out.println("Confirm your purchase tickets and orders? (Y - YES, other - NO)");
            yesno1 = scanner.next().charAt(0);

        } while (yesno1 != 'y' || yesno1 != 'Y');

        // =================================================================================
        // ADD INTO PURCHASE ARRAYLIST
        // =================================================================================
        Ticket[] tickets = ticketList.toArray(new Ticket[ticketList.size()]);
        userPurchase.purchaseTicket(tickets);

        FoodAndBeverage[] fnbs = fnbList.toArray(new FoodAndBeverage[ticketList.size()]);
        userPurchase.purchaseFnb(fnbs);

        // =================================================================================
        // PROCEED TO PAYMENT
        // =================================================================================
        System.out.println("================================");
        System.out.println("           Payment Type");
        System.out.println("================================");
        System.out.println("1. Online Banking");
        System.out.println("2. E-wallet");
        System.out.println("3. Debit / Credit Card");
        do {

            do {
                try {
                    System.out.print("Choose your payment type : ");
                    paymentTypeOpt = scanner.nextInt();

                    continueInputPT = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputPT);

        } while (paymentTypeOpt != 1 && paymentTypeOpt != 1 && paymentTypeOpt != 1);

        switch (paymentTypeOpt) {
            case 1:
                pType = addOnlinebanking(scanner);
                break;
            case 2:
                pType = addEwallet(scanner);
                break;
            case 3:
                pType = addCard(scanner);
                break;
            default:
                System.out.println("Invalid Input");
                break;
        }

        userPurchase.setPaymentType(pType);

        System.out.println("Purchase successsfully. ");
    }

    public static OnlineBanking addOnlinebanking(Scanner scanner) {
        OnlineBanking onlineBanking;

        System.out.println("Enter bank : ");
        String bank = scanner.next();

        onlineBanking = new OnlineBanking(bank);

        return onlineBanking;
    }

    public static EWallet addEwallet(Scanner scanner) {
        EWallet eWallet;
        boolean continueInput = true;
        int telNo = 0;

        System.out.println("Enter e-wallet used : ");
        String eWalletType = scanner.next();

        do {
            try {
                System.out.print("Enter phone number : ");
                telNo = scanner.nextInt();

                continueInput = false;
            } catch (InputMismatchException ex) {
                System.out.println("Incorrect Input. Please try agin. ");
                scanner.nextLine();
            }
        } while (!continueInput);

        eWallet = new EWallet(eWalletType, telNo);

        return eWallet;
    }

    public static DebitCreditCard addCard(Scanner scanner) {
        DebitCreditCard card;
        boolean continueInputNo = true;
        boolean continueInputCCV = true;
        boolean continueInputD = true;
        boolean continueInputM = true;
        boolean continueInputY = true;
        boolean validDate = true;

        int cardNo = 0;
        int ccv = 0;
        LocalDate expiryDate;
        LocalDate today = LocalDate.now();
        int day = 0, month = 0, year = 0;
        String cardHolder;

        // Enter cardNo
        do {
            try {
                System.out.print("Enter phone number : ");
                cardNo = scanner.nextInt();

                continueInputNo = false;
            } catch (InputMismatchException ex) {
                System.out.println("Incorrect Input. Please try agin. ");
                scanner.nextLine();
            }
        } while (!continueInputNo);

        // Enter expiry date
        do {
            System.out.println("Enter expiry date ");

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

            expiryDate = LocalDate.of(year, month, day);

            if (expiryDate.isBefore(today)) {
                System.out.println("Invalid Date. Please enter again.");
                validDate = false;
            } else {
                validDate = true;
            }

        } while (!validDate);

        System.out.println("Enter card holder name : ");
        cardHolder = scanner.nextLine();

        // Enter ccv
        do {
            try {
                System.out.print("Enter ccv : ");
                ccv = scanner.nextInt();

                continueInputCCV = false;
            } catch (InputMismatchException ex) {
                System.out.println("Incorrect Input. Please try agin. ");
                scanner.nextLine();
            }
        } while (!continueInputCCV);

        card = new DebitCreditCard(cardNo, expiryDate, cardHolder, ccv);

        return card;
    }

    // ==============================================================================================================
    // BUY TICKETS
    // User may add tickets into ticketlist. After user choose to not continue
    // buying, user may ask for modification.
    // Then, user may ask for confirmation and proceed to purchase fnb.
    // ==============================================================================================================
    public static ArrayList<Ticket> buyTicket(ArrayList<Ticket> ticketList) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // yesno1 - add more Ticket, yesno2 - make changes to added ticket
        char yesno1 = 'n', yesno2 = 'n';
        // Option for delete or modify
        int userOpt1 = 0;

        boolean continueInput = true;

        // ------------------------------------------ScanScheduleFromFile----------------------------------------------
        Schedule schedule = new Schedule();
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
        DriverJh.readFromFile("scheduleFile.txt", scheduleList, schedule);
        // ------------------------------------------------------------------------------------------------------------

        // --------------------------------------------AddTicketsIntoArrayList-------------------------------------------
        do {

            ticketList.add(addTicket(scheduleList));
            System.out.print("Would you like to add more ticket? (Y - YES, OTHER - NO)    ");
            yesno1 = scanner.next().charAt(0);

        } while (yesno1 == 'y' || yesno1 == 'Y');
        // ---------------------------------------------------------------------------------------------------------------

        do {
            // --------------------------------------------DisplayTicketArrayList---------------------------------------------
            int z = 1;
            for (Ticket tickets : ticketList) {
                System.out.println(z + ".");
                System.out.println(tickets);
                z++;
            }
            // -----------------------------------------------------------------------------------------------------------------

            // ---------------------------------------MakeChangesToTheAddedTicket-----------------------------------------------

            System.out.print("Would you like to make changes on tickets? (Y - YES, others - NO) : ");
            yesno2 = scanner.next().charAt(0);

            if (yesno2 == 'y' || yesno2 == 'Y') {

                System.out.println("1.Delete added tickets");
                System.out.println("2.Edit added tickets");
                System.out.println("Other - EXIT Modifying");

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

        return ticketList;
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

        // ---------------------------------------DisplaySchedule--------------------------------------------------------
        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.printf("%d.\n", i + 1);
            System.out.println(scheduleList.get(i).displayToCust());
            System.out.println();
        }
        // --------------------------------------------------------------------------------------------------------------

        // -----------------------------------------SelectSchedule--------------------------------------------------------
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

        TrainStation depart = scheduleList.get(scheduleNo - 1).getDepartLocation();
        TrainStation arrive = scheduleList.get(scheduleNo - 1).getArriveLocation();
        double price = scheduleList.get(scheduleNo - 1).getTicketPrice();
        ticketSchedule = new Schedule(depart, arrive, price);

        // ---------------------------------------------------------------------------------------------------------------

        // ------------------------------------------EnterDate------------------------------------------------------------
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
        // ---------------------------------------------------------------------------------------------------------------

        // -------------------------------------CreateTempTicketObj--------------------------------------------------------
        ticket = new Ticket(ticketSchedule, ticketDate);
        // ---------------------------------------------------------------------------------------------------------------

        return ticket;
    }

    // -------------------------------------------DELETETICKET-----------------------------------------------------------
    // User can delete all the added tickets or select a particular ticket to delete
    // ------------------------------------------------------------------------------------------------------------------
    public static ArrayList<Ticket> dltTicket(ArrayList<Ticket> ticketList, ArrayList<Schedule> scheduleList)
            throws Exception {

        Scanner scanner = new Scanner(System.in);

        int dltTicket = 0;
        char confirmDlt;
        boolean validDlt = true;
        int dltOption = 0;

        boolean continueInputA = true;
        boolean continueInputP = true;

        System.out.println("1. Delete all added tickets");
        System.out.println("2. Delete a particular ticket");
        System.out.println("Other - Exit Deleting");

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

            // ------------------------------------ClearAllElementInArrayList------------------------------------------------
            ticketList.clear();
            if (ticketList.size() == 0) {
                System.out.println("You have delete all the tickets successfully.");
            }
            // ---------------------------------------------------------------------------------------------------------------

        } else if (dltOption == 2) {

            // ----------------------------------RemoveParticularElementInArrayList---------------------------------------------
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

            System.out.println("=================================");
            System.out.println("             Ticket");
            System.out.println("=================================");
            System.out.println(ticketList.get(dltTicket - 1));
            System.out.println("Are you sure to delete this ticket? (Y - YES, Other -  NO)   ");
            confirmDlt = scanner.next().charAt(0);
            confirmDlt = Character.toUpperCase(confirmDlt);

            if (confirmDlt == 'Y') {
                ticketList.remove(dltTicket - 1);
                System.out.println("Delete successfully. ");
            }

            // ---------------------------------------------------------------------------------------------------------------

        } else {

            System.out.println("Exit Deleting. ");

        }

        return ticketList;
    }

    // ----------------------------------------------MODIFYTICKET------------------------------------------------
    // User can select either schedule or date of the selected tickets to modify.
    // ----------------------------------------------------------------------------------------------------------
    public static ArrayList<Ticket> modifyTicket(ArrayList<Ticket> ticketList, ArrayList<Schedule> scheduleList) {
        Scanner scanner = new Scanner(System.in);
        int editOpt = 0;
        char confirmEdit;
        char confirmSchedule;
        char confirmDate;
        int editTicket = 0;
        int scheduleNo = 0;
        int day = 0, month = 0, year = 0;

        Schedule schedule;
        LocalDate date;
        LocalDate today = LocalDate.now();

        boolean continueInputE = true;
        boolean continueInputT = true;
        boolean continueInputS = true;
        boolean continueInputY = true;
        boolean continueInputM = true;
        boolean continueInputD = true;

        boolean validEdit = true;
        boolean validSchedule = true;
        boolean validDate = true;

        // -----------------------------------------SelectTicket--------------------------------------------------------
        do {
            do {
                try {
                    System.out.print("Select the ticket you would like to delete : ");
                    editTicket = scanner.nextInt();

                    continueInputT = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputT);

            if (editTicket > ticketList.size()) {
                System.out.println("Ticket not found. Please try again. ");
                validEdit = false;
            } else {
                validEdit = true;
            }

        } while (!validEdit);
        // --------------------------------------------------------------------------------------------------------------

        // -----------------------------------------DisplayTicket--------------------------------------------------------
        System.out.println("=================================");
        System.out.println("             Ticket");
        System.out.println("=================================");
        System.out.println(ticketList.get(editTicket - 1));
        // --------------------------------------------------------------------------------------------------------------

        System.out.println("Are you sure to delete this ticket? (Y - YES, Other -  NO)   ");
        confirmEdit = scanner.next().charAt(0);
        confirmEdit = Character.toUpperCase(confirmEdit);

        if (confirmEdit == 'Y') {
            System.out.println("1. Schedule");
            System.out.println("2. Date");
            System.out.println("Other - Exit Editing");

            do {
                try {
                    System.out.print("Enter your option : ");
                    editOpt = scanner.nextInt();

                    continueInputE = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputE);

            if (editOpt == 1) {

                // -----------------------------------------SelectSchedule--------------------------------------------------------
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

                TrainStation depart = scheduleList.get(scheduleNo - 1).getDepartLocation();
                TrainStation arrive = scheduleList.get(scheduleNo - 1).getArriveLocation();
                double price = scheduleList.get(scheduleNo - 1).getTicketPrice();
                schedule = new Schedule(depart, arrive, price);

                // ---------------------------------------------------------------------------------------------------------------

                System.out.print("Confirm your date (Y - YES, Other - NO) : ");
                confirmSchedule = scanner.next().charAt(0);
                confirmSchedule = Character.toUpperCase(confirmSchedule);

                if (confirmSchedule == 'Y') {
                    ticketList.get(editTicket - 1).setTicketSchedule(schedule);
                    ;
                }

            } else if (editOpt == 2) {

                // ------------------------------------------EnterDate------------------------------------------------------------
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

                    date = LocalDate.of(year, month, day);

                    if (date.isBefore(today)) {
                        System.out.println("Invalid Date. Please enter again.");
                        validDate = false;
                    } else {
                        validDate = true;
                    }

                } while (!validDate);
                // ---------------------------------------------------------------------------------------------------------------

                System.out.print("Confirm your date (Y - YES, Other - NO) : ");
                confirmDate = scanner.next().charAt(0);
                confirmDate = Character.toUpperCase(confirmDate);

                if (confirmDate == 'Y') {
                    ticketList.get(editTicket - 1).setTicketDate(date);
                }

            } else {
                System.out.println("Exit Editing. ");
            }
        }

        return ticketList;
    }

    // ==============================================================================================================
    // BUY FNB
    // ==============================================================================================================
    public static ArrayList<FoodAndBeverage> buyFnb(ArrayList<FoodAndBeverage> fnbList) throws Exception {

        // --------------------------------------ScanFoodAndBeverageList----------------------------------------------
        ArrayList<Snacks> snacksList = new ArrayList<Snacks>();
        Snacks snacks = new Snacks();
        DriverJh.readFromFile("snacksFile.txt", snacksList, snacks);

        ArrayList<Drinks> drinksList = new ArrayList<Drinks>();
        Drinks drinks = new Drinks();
        DriverJh.readFromFile("drinksFile.txt", drinksList, drinks);
        // ------------------------------------------------------------------------------------------------------------

        Scanner scanner = new Scanner(System.in);

        boolean continueInput = true;
        boolean continueInputF = true;

        int userOpt1 = 0;
        // yesno1 - Continue to add more fnb, yesno2 - Continue to add more snacks,
        // yesno3 - Continue to add more drinks
        char yesno1, yesno2, yesno3;
        // cont1 - Make changes to fnb
        char cont1;

        int addFnb = 0;
        double subtotal = 0;

        do {
            // --------------------------------------SelectSnacksOrDrinks--------------------------------------------------
            System.out.println("1. Snacks");
            System.out.println("2. Drinks");

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
            // ------------------------------------------------------------------------------------------------------------

            if (addFnb == 1) {

                // -----------------------------------------AddSnacks--------------------------------------------------------
                do {
                    fnbList = addSnacks(snacksList, fnbList);
                    System.out.print("Would you like to buy more snacks? (Y - YES, OTHER - NO)  ");
                    yesno2 = scanner.next().charAt(0);
                } while (yesno2 == 'y' || yesno2 == 'Y');

                // Display Added Snacks
                System.out.println("\n============================");
                System.out.println("            Orders");
                System.out.println("============================");
                int z = 1;
                for (FoodAndBeverage fnbs : fnbList) {
                    System.out.println(z + ".");
                    System.out.println(fnbs.displayToCust());
                    System.out.println("Purchase Quantity >> " + fnbs.getPurchaseQty());
                    System.out.println("Total Amount      >> " + fnbs.calculatePrice());
                    subtotal += fnbs.calculatePrice();
                    System.out.println();
                    z++;
                }
                // ----------------------------------------------------------------------------------------------------------

            } else if (addFnb == 2) {

                // -----------------------------------------AddDrinks--------------------------------------------------------
                do {
                    fnbList = addDrinks(drinksList, fnbList);
                    System.out.print("Would you like to buy more drinks? (Y - YES, OTHER - NO)  ");
                    yesno3 = scanner.next().charAt(0);
                } while (yesno3 == 'y' || yesno3 == 'Y');

                // Display Added Drinks
                System.out.println("\n============================");
                System.out.println("            Orders");
                System.out.println("============================");
                int y = 1;
                for (FoodAndBeverage fnbs : fnbList) {
                    System.out.println(y + ".");
                    System.out.println(fnbs.displayToCust());
                    System.out.println("Purchase Quantity >> " + fnbs.getPurchaseQty());
                    System.out.println("Total Amount      >> " + fnbs.calculatePrice());
                    subtotal += fnbs.calculatePrice();
                    System.out.println();
                    y++;
                }
                // ----------------------------------------------------------------------------------------------------------

            } else {

                System.out.println("Invalid Input.");

            }

            System.out.print("Would you like to add more orders? (Y - YES, other - NO)  ");
            yesno1 = scanner.next().charAt(0);

        } while (yesno1 == 'y' || yesno1 == 'Y');

        do {
            // --------------------------------OrderSummary------------------------------------------------------------------
            // Noted : Every Modification, display once order summary
            System.out.println("\n============================");
            System.out.println("        Order Summary");
            System.out.println("============================");
            int y = 1;
            for (FoodAndBeverage fnbs : fnbList) {
                System.out.println(y + ".");
                System.out.println(fnbs.displayToCust());
                System.out.println("Purchase Quantity >>  " + fnbs.getPurchaseQty());
                System.out.println("Total Amount      >>  " + fnbs.calculatePrice());
                subtotal += fnbs.calculatePrice();
                System.out.println();
                y++;

            }
            // -----------------------------------------------------------------------------------------------------------------

            // ------------------------------------------MakeChangesToOrder-----------------------------------------------------
            System.out.print("Would you like to make changes to orders? (Y - YES, other - NO)  ");
            cont1 = scanner.next().charAt(0);

            if (cont1 == 'y' || cont1 == 'Y') {

                do {

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

                } while (userOpt1 != 1 && userOpt1 != 2);

                switch (userOpt1) {
                    case 1:
                        fnbList = dltFnb(fnbList);
                        break;
                    case 2:
                        fnbList = editFnb(drinksList, fnbList);
                        break;
                    default:
                        System.out.println("Invalid. ");
                        break;
                }

            }

        } while (cont1 == 'y' || cont1 == 'Y');
        // -----------------------------------------------------------------------------------------------------------------

        return fnbList;

    }

    // ------------------------------------------------ADDSNACKS-----------------------------------------------------------
    // User can buy one or many different and same snacks
    // ---------------------------------------------------------------------------------------------------------------------
    public static ArrayList<FoodAndBeverage> addSnacks(ArrayList<Snacks> snacksList, ArrayList<FoodAndBeverage> fnbList)
            throws Exception {

        Scanner scanner = new Scanner(System.in);

        Snacks snack;
        int addSnacks = 0;
        int snackQty = 0;

        boolean continueInputS = true;
        boolean continueInputSQ = true;

        boolean validSnack = true;
        boolean foundSnack = false;

        // ----------------------------------------------DisplaySnacks--------------------------------------------------------------
        for (int i = 0; i < snacksList.size(); i++) {
            System.out.printf("%d.\n", i + 1);
            System.out.println(snacksList.get(i).toString());
            System.out.println();
        }
        System.out.println("Which snack would you like to add?");
        // ------------------------------------------------------------------------------------------------------------------------

        // -----------------------------------------------SelectSnacks--------------------------------------------------------------
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
        // ------------------------------------------------------------------------------------------------------------------------

        if (snack.getStockQty() != 0) {

            do {

                // -------------------------------------------EnterQty-------------------------------------------------------------
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

                if (!snack.checkStockQty(snackQty)) {

                    // Purchase quantity > stock quantity
                    System.out.println("Sorry, we have only " + snack.getStockQty() + " for " + snack.getFoodName());
                    System.out.println("Please enter again. ");

                }

            } while (!snack.checkStockQty(snackQty));

            // Added snack list has found the same item
            for (FoodAndBeverage fnbs : fnbList) {
                if (fnbs.getFoodId().equals(snack.getFoodId())) {
                    fnbs.calculateStockQty(snackQty);
                    fnbs.addPurchaseQty(snackQty);
                    foundSnack = true;
                }
            }

            // Added snack list does not found the same item
            if (!foundSnack) {
                snack.calculateStockQty(snackQty);
                snack.setPurchaseQty(snackQty);
                fnbList.add(snack);
            }
            // ---------------------------------------------------------------------------------------------------------------------

        } else {
            System.out.println("Sorry, we have out of stock for " + snack.getFoodName() + ".");
        }

        return fnbList;
    }

    // ---------------------------------------ADDDRINKS-----------------------------------------------------------------------------
    // User can buy one or many different and same drinks
    // -----------------------------------------------------------------------------------------------------------------------------
    public static ArrayList<FoodAndBeverage> addDrinks(ArrayList<Drinks> drinksList, ArrayList<FoodAndBeverage> fnbList)
            throws Exception {
        Scanner scanner = new Scanner(System.in);
        Drinks drink;

        int addDrinks = 0;
        int drinkQty = 0;
        char size;
        char ice;
        int temperature = 0;

        boolean continueInputD = true;
        boolean continueInputDQ = true;
        boolean continueInputT = true;

        boolean validDrink = true;

        // -----------------------------------------------DisplayDrinks---------------------------------------------------------
        for (int i = 0; i < drinksList.size(); i++) {
            System.out.printf("%d.\n", i + 1);
            System.out.println(drinksList.get(i).toString());
            System.out.println();
        }
        System.out.println("Which drink would you like to add?");
        // ----------------------------------------------------------------------------------------------------------------------

        // -------------------------------------------------SelectDrink----------------------------------------------------------
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
        // -----------------------------------------------------------------------------------------------------------------------

        drink = drinksList.get(addDrinks - 1);

        if (drink.getStockQty() != 0) {

            do {

                // -------------------------------------------------EnterQty--------------------------------------------------------
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

                if (!drink.checkStockQty(drinkQty)) {

                    // Purchase quantity > Stock Quatity
                    System.out.println("Sorry, we have only " + drink.getStockQty() + " for " + drink.getFoodName());
                    System.out.println("Please enter again. ");

                }

            } while (!drink.checkStockQty(drinkQty));
            // ----------------------------------------------------------------------------------------------------------------

            drink.calculateStockQty(drinkQty);

            drink.setPurchaseQty(drinkQty);

            System.out.println("\n============================");
            System.out.println("           Size");
            System.out.println("============================");
            System.out.println("1. Small - S");
            System.out.println("2. Medium - M");
            System.out.println("3. Big - B");

            // Select size
            do {

                System.out.print("Enter (S, M, B) : ");
                size = scanner.next().charAt(0);
                size = Character.toUpperCase(size);

            } while (size != 'S' && size != 'M' && size != 'B');

            switch (size) {
                case 'S':
                    drink.setSize("Small");
                    break;
                case 'M':
                    drink.setSize("Medium");
                    break;
                case 'B':
                    drink.setSize("Big");
                    break;
                default:
                    System.out.println("Invalid.");
                    break;
            }

            // Select temperature
            do {

                System.out.println("\n============================");
                System.out.println("        Temperature");
                System.out.println("============================");
                System.out.println("1. Cold");
                System.out.println("2. Hot");
                do {
                    try {
                        System.out.print("Enter (1 / 2) : ");
                        temperature = scanner.nextInt();

                        continueInputT = false;
                    } catch (InputMismatchException ex) {
                        System.out.println("Incorrect Input. Please try agin. ");
                        scanner.nextLine();
                    }
                } while (continueInputT);

            } while (temperature != 1 && temperature != 2);

            if (temperature == 1) {
                drink.setTemperature("Cold");

                // Select Iced
                System.out.print("Y - ICED, other - NO ICE : ");
                ice = scanner.next().charAt(0);

                if (ice == 'Y' || ice == 'y') {
                    drink.setIce(true);
                } else {
                    drink.setIce(false);
                }
            }

            fnbList.add(drink);

        } else {

            System.out.println("Sorry, we have out of stock for " + drink.getFoodName() + ".");

        }

        return fnbList;
    }

    // ---------------------------------------DLTFNB----------------------------------------------------------------------
    // User can buy delete one or many orders
    // -------------------------------------------------------------------------------------------------------------------
    public static ArrayList<FoodAndBeverage> dltFnb(ArrayList<FoodAndBeverage> fnbList) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int dltFnbOpt = 0;
        char confirmDlt;
        boolean continueInputD = true;
        boolean validDlt = true;

        do {

            // --------------------------------------SelectItem----------------------------------------------------------------
            do {
                try {
                    System.out.print("Select the item you would like to delete : ");
                    dltFnbOpt = scanner.nextInt();

                    continueInputD = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputD);

            if (dltFnbOpt > fnbList.size()) {
                System.out.println("Order not found. Please try again. ");
                validDlt = false;
            } else {
                validDlt = true;
            }

        } while (!validDlt);
        // --------------------------------------------------------------------------------------------------------------------

        System.out.println("\n============================");
        System.out.println("            Order");
        System.out.println("============================");
        System.out.println(fnbList.get(dltFnbOpt - 1).displayToCust());
        System.out.print("\nAre you sure to delete this order? (Y - YES, other - NO)   ");
        confirmDlt = scanner.next().charAt(0);
        confirmDlt = Character.toUpperCase(confirmDlt);

        if (confirmDlt == 'Y') {
            fnbList.remove(dltFnbOpt - 1);
            System.out.println("Delete successfully.");
        } else {
            System.out.println("Delete cancelled.");
        }

        return fnbList;

    }

    // --------------------------------------EDITFNB----------------------------------------------------------------------
    public static ArrayList<FoodAndBeverage> editFnb(ArrayList<Drinks> drinksList, ArrayList<FoodAndBeverage> fnbList)
            throws Exception {
        Scanner scanner = new Scanner(System.in);

        int editFnbOpt = 0;
        int fnbIndex = 0;
        int editOpt = 0;

        boolean continueInput = true;
        boolean continueInputE = true;
        boolean continueInputEQ = true;
        boolean continueInputET = true;
        boolean validEdit = true;
        boolean isDrink = false;

        char confirmEdit;
        int editQty = 0;
        char editSize;
        char editIce;
        int editTemp = 0;
        Drinks tempDrink;

        do {

            do {
                try {
                    System.out.print("Select the item you would like to modify : ");
                    editFnbOpt = scanner.nextInt();

                    continueInputE = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputE);

            if (editFnbOpt > fnbList.size()) {
                System.out.println("Order not found. Please try again. ");
                validEdit = false;
            } else {
                validEdit = true;
            }

        } while (!validEdit);

        fnbIndex = editFnbOpt - 1;
        System.out.println("\n============================");
        System.out.println("            Order");
        System.out.println("============================");
        System.out.println(fnbList.get(fnbIndex).displayToCust());
        System.out.print("\nAre you sure to edit this order? (Y - YES, other - NO)   ");
        confirmEdit = scanner.next().charAt(0);
        confirmEdit = Character.toUpperCase(confirmEdit);

        if (confirmEdit == 'Y') {
            System.out.println("1. Quantity");
            for (Drinks drink : drinksList) {
                if (fnbList.get(fnbIndex).getFoodId().equals(drink.getFoodId())) {
                    System.out.println("2. Size (For Drinks Only)");
                    System.out.println("3. Temperature (For Drinks Only)");
                    System.out.println("4. Ice (For Cold Drinks Only)");
                    isDrink = true;
                }
            }

            do {
                try {
                    System.out.print("Enter your option : ");
                    editOpt = scanner.nextInt();

                    continueInput = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInput);

            if (editOpt == 1) {

                // Edit Qty
                if (fnbList.get(fnbIndex).getStockQty() != 0) {

                    do {

                        do {
                            try {
                                System.out.print("Enter quantity : ");
                                editQty = scanner.nextInt();

                                continueInputEQ = false;
                            } catch (InputMismatchException ex) {
                                System.out.println("Incorrect Input. Please try agin. ");
                                scanner.nextLine();
                            }
                        } while (continueInputEQ);

                        if (fnbList.get(fnbIndex).checkStockQty(editQty)) {

                            if (editQty > fnbList.get(fnbIndex).getPurchaseQty()) {

                                // Example: Get four items, and add one more item, become five items
                                int tempQty1 = fnbList.get(fnbIndex).getPurchaseQty();
                                fnbList.get(fnbIndex).setPurchaseQty(editQty);
                                fnbList.get(fnbIndex).calculateStockQty(editQty - tempQty1);
                                System.out.println("Edit successfully. ");

                            } else if (editQty < fnbList.get(fnbIndex).getPurchaseQty()) {

                                // Example: Get four items, and put back one item, left three items
                                int tempQty2 = fnbList.get(fnbIndex).getPurchaseQty();
                                fnbList.get(fnbIndex).setPurchaseQty(editQty);
                                fnbList.get(fnbIndex).addStockQty(tempQty2 - editQty);
                                System.out.println("Edit successfully. ");

                            } else {

                                fnbList.get(fnbIndex).setPurchaseQty(editQty);
                                System.out.println("Edit successfully. ");

                            }

                        }

                    } while (!fnbList.get(fnbIndex).checkStockQty(editQty));

                }

            } else if (editOpt == 2) {

                // Edit size
                if (isDrink) {
                    tempDrink = (Drinks) fnbList.get(fnbIndex);

                    System.out.println("\n============================");
                    System.out.println("           Size");
                    System.out.println("============================");
                    System.out.println("1. Small - S");
                    System.out.println("2. Medium - M");
                    System.out.println("3. Big - B");

                    do {

                        System.out.print("Enter (S, M, B) : ");
                        editSize = scanner.next().charAt(0);
                        editSize = Character.toUpperCase(editSize);

                    } while (editSize != 'S' && editSize != 'M' && editSize != 'B');

                    switch (editSize) {
                        case 'S':
                            tempDrink.setSize("Small");
                            break;
                        case 'M':
                            tempDrink.setSize("Medium");
                            break;
                        case 'B':
                            tempDrink.setSize("Big");
                            break;
                        default:
                            System.out.println("Invalid.");
                            break;
                    }
                }

            } else if (editOpt == 3) {

                // Edit temperature
                if (isDrink) {
                    tempDrink = (Drinks) fnbList.get(fnbIndex);

                    do {

                        System.out.println("\n============================");
                        System.out.println("        Temperature");
                        System.out.println("============================");
                        System.out.println("1. Cold");
                        System.out.println("2. Hot");
                        do {
                            try {
                                System.out.print("Enter (1 / 2) : ");
                                editTemp = scanner.nextInt();

                                continueInputET = false;
                            } catch (InputMismatchException ex) {
                                System.out.println("Incorrect Input. Please try agin. ");
                                scanner.nextLine();
                            }
                        } while (continueInputET);

                    } while (editTemp != 1 && editTemp != 2);

                    if (editTemp == 1) {

                        // Set cold
                        tempDrink.setTemperature("Cold");

                    } else if (editTemp == 2) {

                        // Set hot
                        tempDrink.setIce(false);
                        tempDrink.setTemperature("Hot");

                    }

                }

            } else if (editOpt == 4) {

                // Edit Iced
                if (isDrink) {
                    tempDrink = (Drinks) fnbList.get(fnbIndex);

                    System.out.print("Y - ICED, other - NO ICED");
                    editIce = scanner.next().charAt(0);
                    editIce = Character.toUpperCase(editIce);

                    if (editIce == 'Y') {

                        if (tempDrink.getTemperature().equalsIgnoreCase("Cold")) {

                            tempDrink.setIce(true);

                        }

                    }

                }

            } else {
                System.out.println("Invalid Option.");
            }

        }

        return fnbList;

    }

    public static void editAccount() {
        Customer cust = new Customer();
        Scanner scanner = new Scanner(System.in);
        int editOpt = 0;
        boolean continueInput = true;

        String regex = "^[a-zA-Z0-9 ]+$"; // regex with space
        String regex2 = "^[a-zA-Z0-9]+$"; // regex without space
        String regex3 = "^01[2-9]-\\\\d{7,8})$"; // regex for phone no.

        String fullname, email, contactNo;
        char gender;

        System.out.println("1. Full Name");
        System.out.println("2. Email");
        System.out.println("3. Contact Number");
        System.out.println("4. Gender");
        System.out.println("Other - Exit");
        do {
            try {
                System.out.print("Enter your option : ");
                editOpt = scanner.nextInt();

                continueInput = false;
            } catch (InputMismatchException ex) {
                System.out.println("Incorrect Input. Please try agin. ");
                scanner.nextLine();
            }
        } while (continueInput);

        if (editOpt == 1) {

            do {
                System.out.println("Enter  your full name (no special characters) >");
                fullname = scanner.nextLine();
            } while (!fullname.matches(regex));

            cust.setFullname(fullname);

        } else if (editOpt == 2) {

            do {
                System.out.printf("Enter your email (no spaces) > ");
                email = scanner.next();
            } while (!email.contains(" "));

            cust.setEmail(email);

        } else if (editOpt == 3) {
            do {
                System.out.println("Enter contact Number (eg. 012-3456789) > ");
                contactNo = scanner.nextLine();
            } while (!contactNo.matches(regex3));

            cust.setContactNo(contactNo);

        } else if (editOpt == 4) {

            do {
                System.out.println("Enter gender (F/M only) : ");
                gender = scanner.next().charAt(0);
                gender = Character.toUpperCase(gender);
            } while (gender != 'f' && gender != 'M');

            cust.setGender(gender);

        } else {
            System.out.println("Exit Editing");
        }

    }

}
