import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DriverJunq {

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
        // Schedule schedule = new Schedule();
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
        // DriverJh.readFromFile("scheduleFile.txt", scheduleList, schedule);
        // ------------------------------------------------------------------------------------------------------------

        // ------------------------------------------ScanStationFromFile----------------------------------------------
        // TrainStation station = new TrainStation();
        ArrayList<TrainStation> stationList = new ArrayList<TrainStation>();
        // DriverJh.readFromFile("stationFile.txt", stationList, station);
        // ------------------------------------------------------------------------------------------------------------

        // --------------------------------------------AddTicketsIntoArrayList-------------------------------------------
        do {
            ticketList = addTicket(ticketList, stationList, scheduleList, scanner);
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
            yesno2 = Character.toUpperCase(yesno2);

            if (yesno2 == 'Y') {

                System.out.println("1.Delete added tickets");
                System.out.println("2.Edit added tickets");
                System.out.println("Other - EXIT Modifying");

                do {
                    try {
                        System.out.print("Enter your option > ");
                        userOpt1 = scanner.nextInt();

                        continueInput = false;
                    } catch (InputMismatchException ex) {
                        System.out.println("Incorrect Input. Please try agin. ");
                        scanner.nextLine();
                    }
                } while (continueInput);

                switch (userOpt1) {
                    case 1:
                        // Delete tickets
                        ticketList = dltTicket(ticketList, scheduleList, scanner);
                        break;
                    case 2:
                        // Edit tickets
                        ticketList = modifyTicket(ticketList, scheduleList, scanner);
                        break;
                    default:
                        System.out.println("Exit Modifying. ");
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
    public static ArrayList<Ticket> addTicket(ArrayList<Ticket> ticketList, ArrayList<TrainStation> stationList,
            ArrayList<Schedule> scheduleList, Scanner scanner) throws Exception {

        Ticket ticket = new Ticket();
        Schedule ticketSchedule = new Schedule();
        LocalDate ticketDate;
        LocalDate today = LocalDate.now();

        int day = 0, month = 0, year = 0;
        boolean validDate = true;

        int scheduleNo = 0;
        boolean validSchedule = true;

        String depart;
        String arrive;
        int departNo = 0;
        boolean validDepart = true;
        int arriveNo = 0;
        boolean validArrive = true;
        char confirmSchedule;

        TrainStation departStation;
        TrainStation arriveStation;

        boolean continueInputAS = true;
        boolean continueInputDS = true;
        boolean continueInputY = true;
        boolean continueInputD = true;
        boolean continueInputM = true;

        // ---------------------------------------DisplayStation---------------------------------------------------------
        for (int i = 0; i < stationList.size(); i++) {
            System.out.printf("%d. ", i + 1);
            System.out.println(stationList.get(i).getLocationName());
            System.out.println();
        }
        // --------------------------------------------------------------------------------------------------------------

        // -----------------------------------------SelectStation--------------------------------------------------------
        // Select depart station
        do {

            do {
                try {
                    System.out.print("Select your depart station : ");
                    departNo = scanner.nextInt();

                    continueInputDS = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect input. Please try again. ");
                    scanner.nextLine();
                }
            } while (continueInputDS);

            if (departNo > stationList.size()) {
                System.out.println("Station not found. Please enter again.");
                validDepart = false;
            } else {
                validDepart = true;
            }

        } while (!validDepart);

        depart = stationList.get(departNo - 1).getLocationName();

        // Select arrive station
        do {

            do {
                try {
                    System.out.print("Select your arrive station : ");
                    arriveNo = scanner.nextInt();

                    continueInputAS = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect input. Please try again. ");
                    scanner.nextLine();
                }
            } while (continueInputAS);

            if (arriveNo > stationList.size()) {
                System.out.println("Station not found. Please enter again.");
                validArrive = false;
            } else {
                validArrive = true;
            }

        } while (!validArrive);

        arrive = stationList.get(arriveNo - 1).getLocationName();

        for (Schedule schedules : scheduleList) {
            if (depart.equalsIgnoreCase(schedules.getDepartLocation().getLocationName())
                    && arrive.equalsIgnoreCase(schedules.getArriveLocation().getLocationName())) {
                validSchedule = true;
            }
        }

        if (validSchedule) {

            // Schedule found
            // ---------------------------------------DisplaySchedule--------------------------------------------------------
            for (int i = 0; i < scheduleList.size(); i++) {
                if (depart.equalsIgnoreCase(scheduleList.get(i).getDepartLocation().getLocationName())
                        && arrive.equalsIgnoreCase(scheduleList.get(i).getArriveLocation().getLocationName())) {
                    System.out.printf("%d.\n", i + 1);
                    System.out.println(scheduleList.get(i).displayToCust());
                    System.out.println();
                }

            }
            // --------------------------------------------------------------------------------------------------------------

            System.out.println("Depart From : " + depart);
            System.out.println("Arrive At : " + arrive);
            System.out.println("Confirm? (Y- YES, N - NO) > ");
            confirmSchedule = scanner.next().charAt(0);
            confirmSchedule = Character.toUpperCase(confirmSchedule);

            if (confirmSchedule == 'Y') {

                departStation = stationList.get(departNo - 1);
                arriveStation = stationList.get(arriveNo - 1);
                double price = scheduleList.get(scheduleNo - 1).getTicketPrice();

                ticketSchedule = new Schedule(departStation, arriveStation, price);

            }

        } else {
            System.out.println("Schedule not found.");
        }

        // ---------------------------------------------------------------------------------------------------------------

        // Continue when schedule is found.
        // ------------------------------------------EnterDate------------------------------------------------------------
        if (validSchedule) {

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
            ticketList.add(ticket);

        }
        // ---------------------------------------------------------------------------------------------------------------

        return ticketList;
    }

    // -------------------------------------------DELETETICKET-----------------------------------------------------------
    // User can delete all the added tickets or select a particular ticket to delete
    // ------------------------------------------------------------------------------------------------------------------
    public static ArrayList<Ticket> dltTicket(ArrayList<Ticket> ticketList, ArrayList<Schedule> scheduleList,
            Scanner scanner)
            throws Exception {

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
    public static ArrayList<Ticket> modifyTicket(ArrayList<Ticket> ticketList, ArrayList<Schedule> scheduleList,
            Scanner scanner) {
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
                            System.out.print("Select your arrive station : ");
                            arriveNo = scanner.nextInt();

                            continueInputAS = false;
                        } catch (InputMismatchException ex) {
                            System.out.println("Incorrect input. Please try again. ");
                            scanner.nextLine();
                        }
                    } while (continueInputAS);

                    if (arriveNo > stationList.size()) {
                        System.out.println("Station not found. Please enter again.");
                        validArrive = false;
                    } else {
                        validArrive = true;
                    }

                } while (!validArrive);

                arrive = stationList.get(arriveNo - 1).getLocationName();

                for (Schedule schedules : scheduleList) {
                    if (depart.equalsIgnoreCase(schedules.getDepartLocation().getLocationName())
                            && arrive.equalsIgnoreCase(schedules.getArriveLocation().getLocationName())) {
                        validSchedule = true;
                    }
                }

                if (validSchedule) {

                    // Schedule found
                    // ---------------------------------------DisplaySchedule--------------------------------------------------------
                    for (int i = 0; i < scheduleList.size(); i++) {
                        if (depart.equalsIgnoreCase(scheduleList.get(i).getDepartLocation().getLocationName())
                                && arrive.equalsIgnoreCase(scheduleList.get(i).getArriveLocation().getLocationName())) {
                            System.out.printf("%d.\n", i + 1);
                            System.out.println(scheduleList.get(i).displayToCust());
                            System.out.println();
                        }

                    }
                    // --------------------------------------------------------------------------------------------------------------

                    System.out.println("Depart From : " + depart);
                    System.out.println("Arrive At : " + arrive);
                    System.out.println("Confirm? (Y- YES, N - NO) > ");
                    confirmSchedule = scanner.next().charAt(0);
                    confirmSchedule = Character.toUpperCase(confirmSchedule);

                    if (confirmSchedule == 'Y') {

                        departStation = stationList.get(departNo - 1);
                        arriveStation = stationList.get(arriveNo - 1);
                        double price = scheduleList.get(scheduleNo - 1).getTicketPrice();

                        ticketSchedule = new Schedule(departStation, arriveStation, price);

                    }

                } else {
                    System.out.println("Schedule not found.");
                }

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

}
