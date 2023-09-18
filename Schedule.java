import java.io.Serializable;
import java.time.LocalTime;

public class Schedule implements Serializable{
    private String scheduleId;
    private TrainStation departLocation;
    private TrainStation arriveLocation;
    private LocalTime departTime;
    private LocalTime arriveTime;
    private Train trainOperated;
    private double ticketPrice;

    //-----------------------------------CONSTRUCTOR---------------------------------------- 
    // NO-ARG CONSTRUCTOR
    public Schedule(){
        scheduleId = "Undefined";
        departLocation = null;
        arriveLocation = null;
        departTime = null;
        arriveTime = null;
        trainOperated = null;
        ticketPrice = 0;
    }

    // PARAMETERIZED CONSTRUCTOR
    public Schedule(TrainStation departLocation, TrainStation arriveLocation, LocalTime departTime, LocalTime arriveTime, Train trainOperated, double ticketPrice){
        scheduleId = departLocation.getLocationName().substring(0,3) + "-" + arriveLocation.getLocationName().substring(0,3) + String.valueOf((int) (100000 + (Math.random() * (200000 - 100000 + 1))));
        this.departLocation = departLocation;
        this.arriveLocation = arriveLocation;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.trainOperated = trainOperated;
        this.ticketPrice = ticketPrice;
    }

    public Schedule(TrainStation departLocation, TrainStation arriveLocation, double ticketPrice){
        scheduleId = departLocation.getLocationName().substring(0,3) + "-" + arriveLocation.getLocationName().substring(0,3) + String.valueOf((int) (100000 + (Math.random() * (200000 - 100000 + 1))));
        this.departLocation = departLocation;
        this.arriveLocation = arriveLocation;
        this.ticketPrice = ticketPrice;
    }

    //------------------------------------METHOD-----------------------------------------
    // DELETE METHOD
    public void deleteSchedule(){
        scheduleId = null;
        departLocation = null;
        arriveLocation = null;
        departTime = null;
        arriveTime = null;
        trainOperated = null;
        ticketPrice = 0;
    }

    // READ METHOD
    public String getScheduleId(){
        return scheduleId;
    }

    public TrainStation getDepartLocation(){
        return departLocation;
    }

    public TrainStation getArriveLocation(){
        return arriveLocation;
    }

    public LocalTime getDepartTime(){
        return departTime;
    }

    public LocalTime getArriveTime(){
        return arriveTime;
    }

    public Train getOperatedTrain(){
        return trainOperated;
    }

    public double getTicketPrice(){
        return ticketPrice;
    }

    // UPDATE METHOD
    public void editDepartLocation(TrainStation departLocation){
        this.departLocation = departLocation;
    }

    public void editArriveLocation(TrainStation arriveLocation){
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

    // DISPLAY METHOD
    public String toString(){
        return "Schedule id: " + scheduleId + "\nDeparture location: " + departLocation.getLocationName() + "\nArrival location: " + arriveLocation.getLocationName() + "\nDeparture time: " + departTime + "\nArrival time: " + arriveTime + "\nTrain operated: " + trainOperated.getTrainNo() + trainOperated.getTrainName() + "\nTicket Price: RM" + ticketPrice;
    }

    public String displayToCust(){
        return  "Schedule id           : " + scheduleId + 
                "\nDeparture location    : " + departLocation.getLocationName() + 
                "\nArrival location      : " + arriveLocation.getLocationName() + 
                "\nDeparture time        : " + departTime + 
                "\nArrival time          : " + arriveTime + 
                "\nTicket Price          : RM" + ticketPrice;
    }

    public String displayInTicket(){
        return  "Departure location    : " + departLocation.getLocationName() + 
                "\nArrival location      : " + arriveLocation.getLocationName() + 
                "\nTicket Price          : RM" + ticketPrice;
    }

}
