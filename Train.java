public class Train{
    private int trainNo;
    private String trainType;
    private String trainModel;
    private static int nextTrainNo = 2900;
    private String status;

    public Train(){
        trainNo = 0;
        trainType = "Undefined";
        trainModel = "Undefined";
    }

    public Train(int trainNo,String trainType, String trainModel, String status){
        this.trainNo = trainNo;
        this.trainType = trainType;
        this.trainModel = trainModel;  
        this.status = status;
        nextTrainNo++;
    }

    public Train(String trainType, String trainModel, String status){
        trainNo = nextTrainNo;
        this.trainType = trainType;
        this.trainModel = trainModel;  
        this.status = status;
        nextTrainNo++;
    }

    public void deleteTrain(String trainNo){

    }

    public void editTrainNo(int trainNo){
        this.trainNo = trainNo;
    }

    public void editTrainType(String trainType){
        this.trainType = trainType;
    }

    public void editTrainModel(String trainModel){
        this.trainModel = trainModel;
    }

    public void changeTrainStatus(String status){
        this.status = status;
    }

    public String toString(){
           return "Train No: " + trainNo + "\nTrain Type: " + trainType + "\nTrain Model: " + trainModel + "\nTrain status: " + status;
    }

}