import java.io.Serializable;


public class TrainStation implements Serializable{
      private String locationId;
      private String locationName;
      private int numOfPlatform;

      //-----------------------------------CONSTRUCTOR---------------------------------------- 
      // NO-ARG CONSTRUCTOR
      TrainStation(){
         locationId = "Undefined";
         locationName = "Undefined";
         numOfPlatform = 0;
      }
      // PARAMETERIZED CONSTRUCTOR
      TrainStation(String locationId, String locationName, int numOfPlatform){
         this.locationId = locationId;
         this.locationName = locationName;
         this.numOfPlatform = numOfPlatform;
      }

      TrainStation(String locationName, int numOfPlatform){
         locationId = locationName.charAt(0) + String.valueOf((int) (10000 + (Math.random() * (20000 - 10000 + 1))));
         this.locationName = locationName;
         this.numOfPlatform = numOfPlatform;
      }
 
      //------------------------------------METHOD----------------------------------------- 

      // DELETE METHOD
      public void deleteStation(){
         locationId = null;
         locationName = null;
         numOfPlatform = 0;
      }

      // READ METHOD
      public String getLocationId(){
         return locationId;
      }
 
      public String getLocationName(){
         return locationName;
      }

      public int getNumOfPlatform(){
        return numOfPlatform;
      }


      // UPDATE METHOD
      public void changeLocationName(String locationName){
         this.locationName = locationName;
     }
      
      public void changeNumOfPlatform(int numOfPlatform){
         this.numOfPlatform = numOfPlatform;
      }

      // DISPLAY METHOD
      public String toString(){
         return "Location Id : " + locationId + "\nLocation Name : " + locationName + "\nNumber of platform :" + numOfPlatform + "\n";
      }

}
