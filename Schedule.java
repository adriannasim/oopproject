import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Schedule {
    private String scheduleId;
    private Location departLocation;
    private Location arriveLocation;
    private LocalTime departTime;
    private LocalTime arriveTime;
    private Train trainOperated;
    private double ticketPrice;

    public Schedule(){

    }

    public Schedule(Location departLocation, Location arriveLocation, LocalTime departTime, LocalTime arriveTime, Train trainOperated, double ticketPrice){
        // random id
        this.departLocation = departLocation;
        this.arriveLocation = arriveLocation;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.trainOperated = trainOperated;
        this.ticketPrice = ticketPrice;
    }

    public void deleteSchedule(){

    }

    public void editDepartLocation(Location departLocation){
        this.departLocation = departLocation;

    }

    public void editArriveLocation(Location arriveLocation){
        this.arriveLocation = arriveLocation;

    }

    public void editDepartTime(LocalTime departTime){
        this.departTime = departTime;

    }

    public void editArriveTime(LocalTime arriveTime){
        this.arriveTime = arriveTime;

    }

    public void editTrainOperated(Train trainOperated){
        this.trainOperated = trainOperated;

    }

    public void editTicketPrice(double ticketPrice){
        this.ticketPrice =  ticketPrice;

    }

    public String toString(){
        return "";
    }

}
