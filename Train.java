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

    public Train(String trainName, String trainModel, String status){
        trainNo = nextTrainNo;
        this.trainName = trainName;
        this.trainModel = trainModel;  
        this.status = status;
        nextTrainNo++;
    }

    public void deleteTrain(String trainNo){

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

    public void editTrainNo(int trainNo){
        this.trainNo = trainNo;
    }

    public void editTrainName(String trainName){
        this.trainName = trainName;
    }

    public void editTrainModel(String trainModel){
        this.trainModel = trainModel;
    }

    public void changeTrainStatus(String status){
        this.status = status;
    }

    public String toString(){
           return "Train No: " + trainNo + "\nTrain Name: " + trainName + "\nTrain Model: " + trainModel + "\nTrain status: " + status;
    }

}