import java.time.LocalDate;

public class Ticket{
    private static int ticketIdNo = 1001;
    private String ticketId;
    private Schedule ticketSchedule;
    private LocalDate ticketDate;

    public Ticket(Schedule ticketSchedule, LocalDate ticketDate){
        String tempTicketId = "T" + ticketIdNo;
        ticketIdNo++;
        ticketId = tempTicketId;
        
        this.ticketSchedule = ticketSchedule;
        this.ticketDate = ticketDate;
    }
    
    public String getTicketId(){
    	return ticketId;
    }
    
    public void setTicketSchedule(Schedule ticketSchedule){
    	this.ticketSchedule = ticketSchedule;
    }
    public Schedule getTicketSchedule(){
    	return ticketSchedule;
    }
    
    public void setTicketDate(LocalDate ticketDate){
    	this.ticketDate = ticketDate;
    }
    public LocalDate getTicketDate(){
    	return ticketDate;
    }
    
    public String toString(){
    	return String.format("\nTicket ID : %s\nSchedule\n-------------------------------------\n%s \n-------------------------------------\nTicket Date : %s\n"
            , ticketId, ticketSchedule, ticketDate);
    }
}