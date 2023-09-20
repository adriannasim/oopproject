import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ticket {
    private static int ticketIdNo;
    private String ticketId;
    private Schedule ticketSchedule;
    private LocalDate ticketDate;

    public Ticket() {

    }

    public Ticket(Schedule ticketSchedule, LocalDate ticketDate) {
        ticketIdNo = (int) (10000 + (Math.random() * (20000 - 10000 + 1)));
        String tempTicketId = "T" + ticketIdNo;
        ticketId = tempTicketId;

        this.ticketSchedule = ticketSchedule;
        this.ticketDate = ticketDate;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketSchedule(Schedule ticketSchedule) {
        this.ticketSchedule = ticketSchedule;
    }

    public Schedule getTicketSchedule() {
        return ticketSchedule;
    }

    public void setTicketDate(LocalDate ticketDate) {
        this.ticketDate = ticketDate;
    }

    public LocalDate getTicketDate() {
        return ticketDate;
    }

    public String toString() {
        return String.format(
                "\nTicket ID : %s\nSchedule\n-------------------------------------\n%s \n-------------------------------------\nTicket Date : %s\n",
                ticketId, ticketSchedule.displayInTicket(), ticketDate);
    }

    public String displayToReport(){
        return String.format("Date : %s\t Ticket ID : %s\n\n--------------------------------------------- \n\t\tTrain Schedule\n---------------------------------------------\n\n%s \n\n---------------------------------------------\n", ticketDate, ticketId, ticketSchedule.displayInReport());
    }

    public void writePurchaseTicket(Ticket[] ticketList, String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchaseTicket.txt", true))) {
            for (Ticket ticket : ticketList) {
                writer.write(username + "||"
                        + ticket.getTicketSchedule().getDepartLocation() + "||"
                        + ticket.getTicketSchedule().getArriveLocation() + "||"
                        + ticket.getTicketSchedule().getTicketPrice() + "||"
                        + ticket.getTicketDate().getDayOfMonth() + "||" + ticket.getTicketDate().getMonth()
                        + "||" + ticket.getTicketDate().getYear());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ==============================================================================================================
    // BUY TICKETS
    // User may add tickets into ticketlist. After user choose to not continue
    // buying, user may ask for modification.
    // Then, user may ask for confirmation and proceed to purchase fnb.
    // ==============================================================================================================
    public ArrayList<Ticket> buyTicket(ArrayList<Ticket> ticketList, Scanner scanner) throws Exception {

        // yesno1 - add more Ticket, yesno2 - make changes to added ticket
        char yesno1 = 'n', yesno2 = 'n';
        // Option for delete or modify
        int userOpt1 = 0;

        boolean continueInput = true;
        

        // ------------------------------------------ScanScheduleFromFile----------------------------------------------
        
        Schedule schedule = new Schedule();
        ArrayList<Schedule> scheduleList = schedule.getScheduleList();
        // ArrayList<Schedule> scheduleList = new ArrayList<>();
        // ------------------------------------------------------------------------------------------------------------

        // ------------------------------------------ScanStationFromFile----------------------------------------------
        TrainStation station = new TrainStation();
        ArrayList<TrainStation> stationList = station.getStationList();
        // ArrayList<TrainStation> stationList = new ArrayList<TrainStation>();
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
            System.out.println("=========================================================");
            System.out.println("                           Tickets");
            System.out.println("=========================================================");
            int z = 1;
            for (Ticket tickets : ticketList) {
                System.out.println();
                System.out.println(z + ".");
                System.out.println(tickets);
                z++;
                System.out.println();
                System.out.println("---------------------------------------------------------");
                System.out.println();
            }
            // -----------------------------------------------------------------------------------------------------------------

            // ---------------------------------------MakeChangesToTheAddedTicket-----------------------------------------------

            System.out.print("Would you like to make changes on tickets? (Y - YES, others - NO) : ");
            yesno2 = scanner.next().charAt(0);
            yesno2 = Character.toUpperCase(yesno2);

            if (yesno2 == 'Y') {

                System.out.println("=========================================================");
                System.out.println("                           Modification");
                System.out.println("=========================================================");
                System.out.println("1.Delete added tickets");
                System.out.println("2.Edit added tickets");
                System.out.println();
                System.out.println("* Press other to exit");
                System.out.println("=========================================================");

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
                        ticketList = modifyTicket(ticketList, scheduleList, stationList, scanner);
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
    public ArrayList<Ticket> addTicket(ArrayList<Ticket> ticketList, ArrayList<TrainStation> stationList,
            ArrayList<Schedule> scheduleList, Scanner scanner) throws Exception {

        Ticket ticket = new Ticket();
        Schedule ticketSchedule = new Schedule();
        LocalDate ticketDate;
        LocalDate today = LocalDate.now();

        int day = 0, month = 0, year = 0;
        boolean validDate = true;

        int scheduleNo = 0;
        boolean validSchedule = false;

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
        System.out.println("=========================================================");
        System.out.println("                    Train Station");
        System.out.println("=========================================================");
        for (int i = 0; i < stationList.size(); i++) {
            System.out.println();
            System.out.printf("%d. ", i + 1);
            System.out.println(stationList.get(i).getLocationName());
            System.out.println();
            System.out.println("---------------------------------------------------------");
            System.out.println();
        }
        System.out.println("=========================================================");
        // --------------------------------------------------------------------------------------------------------------

        // -----------------------------------------SelectStation--------------------------------------------------------
        // Select depart station
        do {

            do {
                try {
                    System.out.print("Select your depart station > ");
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
                    System.out.print("Select your arrive station > ");
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

            // --------------------------------------------------------------------------------------------------------------

            System.out.println("Depart From : " + depart);
            System.out.println("Arrive At : " + arrive);
            System.out.print("Confirm? (Y- YES, N - NO) > ");
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
                System.out.println("Enter your date");
                System.out.println();
                System.out.println("---------------------------------------------------------");
                System.out.println();

                // Enter year
                do {
                    try {
                        System.out.print("Year > ");
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
                        System.out.print("Month > ");
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
                        System.out.print("Day > ");
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
    public ArrayList<Ticket> dltTicket(ArrayList<Ticket> ticketList, ArrayList<Schedule> scheduleList,
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
        System.out.println();
        System.out.println("* Press other to exit");
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println();

        do {
            try {
                System.out.print("Enter your option > ");
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
                        System.out.print("Select the ticket you would like to delete > ");
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
            System.out.println("Are you sure to delete this ticket? (Y - YES, Other -  NO) >> ");
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
    public ArrayList<Ticket> modifyTicket(ArrayList<Ticket> ticketList, ArrayList<Schedule> scheduleList,
            ArrayList<TrainStation> stationList, Scanner scanner) {
        int editOpt = 0;
        char confirmEdit;
        char confirmSchedule;
        char confirmDate;
        int editTicket = 0;
        int scheduleNo = 0;
        int day = 0, month = 0, year = 0;

        Schedule ticketSchedule;
        TrainStation departStation = new TrainStation();
        TrainStation arriveStation = new TrainStation();
        String depart;
        String arrive;
        int departNo = 0;
        boolean validDepart = true;
        int arriveNo = 0;
        boolean validArrive = true;

        LocalDate date;
        LocalDate today = LocalDate.now();

        boolean continueInputE = true;
        boolean continueInputT = true;
        boolean continueInputAS = true;
        boolean continueInputDS = true;
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
                    System.out.print("Select the ticket you would like to edit > ");
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

        System.out.println("Are you sure to edit this ticket? (Y - YES, Other -  NO) >> ");
        confirmEdit = scanner.next().charAt(0);
        confirmEdit = Character.toUpperCase(confirmEdit);

        if (confirmEdit == 'Y') {
            System.out.println("1. Schedule");
            System.out.println("2. Date");
            System.out.println();
            System.out.println("* Press other to exit ");
            System.out.println();
            System.out.println("---------------------------------------------------------");
            System.out.println();

            do {
                try {
                    System.out.print("Enter your option > ");
                    editOpt = scanner.nextInt();

                    continueInputE = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInputE);

            if (editOpt == 1) {

                // -----------------------------------------SelectSchedule--------------------------------------------------------
                // Select depart station
                do {

                    do {
                        try {
                            System.out.print("Select your depart station > ");
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
                            System.out.print("Select your arrive station > ");
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

                    // --------------------------------------------------------------------------------------------------------------

                    System.out.println("Depart From : " + depart);
                    System.out.println("Arrive At : " + arrive);
                    System.out.println("Confirm? (Y- YES, N - NO) >> ");
                    confirmSchedule = scanner.next().charAt(0);
                    confirmSchedule = Character.toUpperCase(confirmSchedule);

                    if (confirmSchedule == 'Y') {

                        departStation = stationList.get(departNo - 1);
                        arriveStation = stationList.get(arriveNo - 1);
                        double price = scheduleList.get(scheduleNo - 1).getTicketPrice();

                        ticketSchedule = new Schedule(departStation, arriveStation, price);

                        ticketList.get(editTicket - 1).setTicketSchedule(ticketSchedule);

                    }

                } else {
                    System.out.println("Schedule not found.");
                }

                // ---------------------------------------------------------------------------------------------------------------

            } else if (editOpt == 2) {

                // ------------------------------------------EnterDate------------------------------------------------------------
                do {
                    System.out.println("Enter your date ");
                    System.out.println();
                    System.out.println("---------------------------------------------------------");
                    System.out.println();

                    // Enter year
                    do {
                        try {
                            System.out.print("Year > ");
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
                            System.out.print("Month > ");
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
                            System.out.print("Day > ");
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