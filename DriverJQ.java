import java.util.*;
import java.time.LocalDate;

public class DriverJQ {
    public static void buyTicket(String[] args) throws Exception {
        char yesno1, yesno2;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
        Ticket[] ticketArray = ticketList.toArray(new Ticket[ticketList.size()]);

        do {

            ticketList.add(addTicket());
            System.out.print("Would you like to buy more ticket? ");
            yesno1 = scanner.next().charAt(0);

        } while (yesno1 == 'y' || yesno1 == 'Y');

        int z = 0;
        for (Ticket tickets : ticketList) {
            System.out.printf("%d.", z + 1);
            System.out.println(tickets);
            z++;
        }

        do {

            System.out.print("Would you like to make changes on tickets? (Y - CONTINUE, others - EXIT) : ");
            yesno2 = scanner.next().charAt(0);
            if (yesno2 == 'y' || yesno2 == 'Y') {
                System.out.println("1.Delete added tickets");
                System.out.println("2.Modify added tickets");
                int userOpt1 = scanner.nextInt();

                switch (userOpt1) {
                    case 1:
                        ticketList = dltTicket(ticketList);
                        break;
                    case 2:
                        System.out.println("Modify");
                }
            }

        } while (yesno2 == 'y' || yesno2 == 'Y');

    }

    // ---------------------------------------------------------------------------------
    // TICKETS
    // ---------------------------------------------------------------------------------
    // ADD TICKET
    // User can buy tickets for DIFFERENT details or SAME details. User can buy
    // tickets for current day and after current day.
    // ---------------------------------------------------------------------------------
    public static Ticket addTicket() throws Exception {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Schedule> scheduleList;
        Ticket ticket;
        Schedule ticketSchedule;
        LocalDate ticketDate;
        LocalDate today = LocalDate.now();
        int day, month, year;
        boolean validDate = true;
        int scheduleNo;
        int scheduleCount = 0;
        boolean validSchedule = true;

        scheduleList = DriveJh.readScheduleFromFile("scheduleFile.txt");

        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.printf("%d.\n", i + 1);
            System.out.println(scheduleList.get(i).toString());
            System.out.println();
            scheduleCount++;
        }

        do {

            System.out.print("Select your schedule : ");
            scheduleNo = scanner.nextInt();

            if (scheduleNo > scheduleCount) {
                System.out.println("Invalid Option. Please enter again.");
                validSchedule = false;
            } else {
                validSchedule = true;
            }

        } while (!validSchedule);

        ticketSchedule = scheduleList.get(scheduleNo - 1);

        do {
            System.out.println("Enter your date");
            System.out.print("Year : ");
            year = scanner.nextInt();
            System.out.print("Month : ");
            month = scanner.nextInt();
            System.out.print("Day : ");
            day = scanner.nextInt();

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
     * ArrayList<Schedule> scheduleList;
     * LocalDate date = LocalDate.now();
     * scheduleList = DriveJh.readScheduleFromFile("scheduleFile.txt");
     * 
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

    // ---------------------------------------------------------------------------------
    // DELETE TICKET
    // User can delete all the tickets or select a particular ticket to delete
    // ---------------------------------------------------------------------------------
    public static ArrayList<Ticket> dltTicket(ArrayList<Ticket> ticketList) throws Exception {

        Scanner scanner = new Scanner(System.in);

        int dltTicket;
        boolean validDlt = true;
        int dltOption;

        System.out.println("Would you like to delete all tickets or particular ticket? (1-all, 2-particular)");
        dltOption = scanner.nextInt();

        if (dltOption == 1) {

            ticketList.clear();
            if (ticketList.size() == 0) {
                System.out.println("You have delete all the tickets successfully.");
            }

        } else if (dltOption == 2) {

            do {

                System.out.print("Select the ticket you would like to delete : ");
                dltTicket = scanner.nextInt();

                if (dltTicket > ticketList.size()) {
                    System.out.println("Please enter a valid option.");
                    validDlt = false;
                } else {
                    validDlt = true;
                }

            } while (!validDlt);

            ticketList.remove(dltTicket - 1);

            int j = 0;
            for (Ticket tickets : ticketList) {
                System.out.printf("%d.", j + 1);
                System.out.println(tickets);
                j++;
            }
        }

        return ticketList;
    }

    // ---------------------------------------------------------------------------------
    // MODIFY TICKET
    // User can select the schedule or date of the tickets to modify.
    // ---------------------------------------------------------------------------------
    public static void main(String[] args) throws Exception {
        
    }
   
}
