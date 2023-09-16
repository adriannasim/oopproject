import java.util.*;
import java.time.LocalDate;

public class DriverJQ {
    public static void main(String[] args) throws Exception {
        ArrayList<Ticket> ticketList = new ArrayList<Ticket>();

        ticketList.add(buyTicket());

        for (Ticket tickets: ticketList){
            System.out.println(tickets);
        }
    }

    public static Ticket buyTicket() throws Exception {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Schedule> scheduleList;
        Ticket ticket;
        Schedule ticketSchedule;
        LocalDate ticketDate;
        LocalDate today = LocalDate.now();
        int day, month;
        boolean validDate = true;
        int scheduleNo;

        scheduleList = DriveJh.readScheduleFromFile("scheduleFile.txt");

        for (int i = 0; i < scheduleList.size(); i++) {
            System.out.println(scheduleList.get(i).toString());
            System.out.println();
        }

        System.out.print("Select your schedule : ");
        scheduleNo = scanner.nextInt();
        
        ticketSchedule = scheduleList.get(scheduleNo-1);
        
        do {
            System.out.println("Enter your date");
            System.out.print("Month : ");
            month = scanner.nextInt();
            System.out.print("Day : ");
            day = scanner.nextInt();
        
            ticketDate = LocalDate.of(today.getYear(), month, day);

            if(ticketDate.isBefore(today)){
                System.out.println("Invalid Date");
                validDate = false;
            }

        } while (!validDate);

        ticket = new Ticket(ticketSchedule, ticketDate);

        if(scanner!=null){
            scanner.close();
        }

        return ticket;
    }
}
