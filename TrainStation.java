public class TrainStation extends Location {
      private int numOfPlatform;
      private String status;

      TrainStation(){

      }

      TrainStation(String locationName, int numOfPlatform){
         super(locationName);
         this.numOfPlatform = numOfPlatform;
         status = "Not in use";
      }
}
