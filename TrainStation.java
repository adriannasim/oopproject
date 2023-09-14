public class TrainStation extends Location {
      private int numOfPlatform;
      private String status;

      TrainStation(){

      }

      TrainStation(String locationId, String locationName, int numOfPlatform, String status){
         super(locationId, locationName);
         this.numOfPlatform = numOfPlatform;
         this.status = status;
      }

      TrainStation(String locationName, int numOfPlatform){
         super(locationName);
         this.numOfPlatform = numOfPlatform;
         status = "Not in use";
      }

      public int getNumOfPlatform(){
        return numOfPlatform;
      }

      public String getStatus(){
        return status;
      }

      public void changeNumOfPlatform(int numOfPlatform){
         this.numOfPlatform = numOfPlatform;
      }

      public String toString(){
         return super.toString() + "Number of platform :" + numOfPlatform + "\nStatus : " + status + "\n";
      }

}
