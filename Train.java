import java.io.Serializable;

public class Train implements Serializable{
    private int trainNo;
    private String trainName;
    private String trainModel;
    private static int nextTrainNo = 2900;

    //-----------------------------------CONSTRUCTOR---------------------------------------- 
    // NO-ARG CONSTRUCTOR
    public Train(){
        trainNo = 0;
        trainName = "Undefined";
        trainModel = "Undefined";
    }

    // PARAMETERIZED CONSTRUCTOR
    public Train(int trainNo,String trainName, String trainModel){
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.trainModel = trainModel;  
        nextTrainNo = this.trainNo+1;
    }

    public Train(String trainName, String trainModel){
        trainNo = nextTrainNo;
        this.trainName = trainName;
        this.trainModel = trainModel;  
        nextTrainNo++;
    }

    //------------------------------------METHOD-----------------------------------------

    // DELETE METHOD
    public void deleteTrain(){
        trainNo = 0;
        trainName = null;
        trainModel = null;
    }

    // READ METHOD
    public int getTrainNo(){
        return trainNo;
    }

    public String getTrainName(){
        return trainName;
    }

    public String getTrainModel(){
        return trainModel;
    }

    // UPDATE METHOD
    public void changeTrainName(String trainName){
        this.trainName = trainName;
    }

    // DISPLAY METHOD
    public String toString(){
           return "Train No: " + trainNo + "\nTrain Name: " + trainName + "\nTrain Model: " + trainModel + "\n";
    }

}