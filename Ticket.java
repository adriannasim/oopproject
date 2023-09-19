import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Ticket{
    private static int ticketIdNo;
    private String ticketId;
    private Schedule ticketSchedule;
    private LocalDate ticketDate;

    public Ticket(){

    }

    public Ticket(Schedule ticketSchedule, LocalDate ticketDate){
        ticketIdNo = (int) (10000 + (Math.random() * (20000 - 10000 + 1)));
        String tempTicketId = "T" + ticketIdNo;
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
            , ticketId, ticketSchedule.displayInTicket(), ticketDate);
    }

    public void writePurchaseTicket(Ticket[] ticketList , Login login) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchaseTicket.txt", true))) {
            for (Ticket ticket : ticketList) {
                writer.write(login.getUsername() + "||"
                + ticket.getTicketSchedule().getDepartLocation() + "||" 
                + ticket.getTicketSchedule().getArriveLocation() + "||" 
                + ticket.getTicketSchedule().getTicketPrice() + "||"
                + ticket.getTicketDate());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}