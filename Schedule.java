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

    public Schedule(){

    }

    public Schedule(TrainStation departLocation, TrainStation arriveLocation, LocalTime departTime, LocalTime arriveTime, Train trainOperated, double ticketPrice){
        scheduleId = departLocation.getLocationName().substring(0,3) + "-" + arriveLocation.getLocationName().substring(0,3) + String.valueOf((int) (100000 + (Math.random() * (200000 - 100000 + 1))));
        this.departLocation = departLocation;
        this.arriveLocation = arriveLocation;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.trainOperated = trainOperated;
        this.ticketPrice = ticketPrice;
    }

    public void deleteSchedule(){

    }

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

    public String toString(){
        return "Schedule id: " + scheduleId + "\nDeparture location: " + departLocation.getLocationName() + "\nArrival location: " + arriveLocation.getLocationName() + "\nDeparture time: " + departTime + "\nArrival time: " + arriveTime + "\nTrain operated: " + trainOperated.getTrainNo() + "\nTicket Price: RM" + ticketPrice;
    }

}
