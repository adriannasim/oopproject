public class Train{
    private int trainNo;
    private String trainName;
    private String trainModel;
    private static int nextTrainNo = 2900;
    private String status;

    public Train(){
        trainNo = 0;
        trainName = "Undefined";
        trainModel = "Undefined";
    }

    public Train(int trainNo,String trainName, String trainModel, String status){
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.trainModel = trainModel;  
        this.status = status;
        nextTrainNo = trainNo+1;
    }

    public Train(String trainName, String trainModel){
        trainNo = nextTrainNo;
        this.trainName = trainName;
        this.trainModel = trainModel;  
        status = "Not in use";
        nextTrainNo++;
    }

    public int getTrainNo(){
        return trainNo;
    }

    public String getTrainName(){
        return trainName;
    }

    public String getTrainModel(){
        return trainModel;
    }

    public String getTrainStatus(){
        return status;
    }

    public void changeTrainStatus(String status){
        this.status = status;
    }

    public String toString(){
           return "Train No: " + trainNo + "\nTrain Name: " + trainName + "\nTrain Model: " + trainModel + "\nTrain status: " + status;
    }

}