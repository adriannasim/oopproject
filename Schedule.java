import java.time.LocalDate;
import java.time.LocalDateTime;

public class Schedule {
    private String scheduleId;
    private Location departLocation;
    private Location arriveLocation;
    private LocalDateTime departTime;
    private LocalDateTime arriveTime;
    private Train trainOperated;
    private double ticketPrice;

    public Schedule(){

    }

    public Schedule(String departLocation, String arriveLocation, LocalDateTime departTime, LocalDateTime arriveTime, Train trainOperated, double ticketPrice){

    }

    public void deleteSchedule(){

    }

    public void editDepartLocation(Location departLocation){

    }

    public void editArriveLocation(Location arriveLocation){

    }

    public void editDepartTime(LocalDateTime departTime){

    }

    public void editArriveTime(LocalDateTime arriveTime){

    }

    public void editTrainOperated(Train trainOperated){

    }

    public void editTicketPrice(double ticketPrice){

    }

    public String toString(){
        return "";
    }

}
