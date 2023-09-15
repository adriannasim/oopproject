public class TrainStation{
      private String locationId;
      private String locationName;
      private int numOfPlatform;
      private String status;

      TrainStation(){
         locationId = "Undefined";
         locationName = "Undefined";
         numOfPlatform = 0;
         status = "Undefined";
      }

      TrainStation(String locationId, String locationName, int numOfPlatform, String status){
         this.locationId = locationId;
         this.locationName = locationName;
         this.numOfPlatform = numOfPlatform;
         this.status = status;
      }

      TrainStation(String locationName, int numOfPlatform){
         locationId = locationName.charAt(0) + String.valueOf((int) (10000 + (Math.random() * (20000 - 10000 + 1))));
         this.locationName = locationName;
         this.numOfPlatform = numOfPlatform;
         status = "Not in use";
      }

      public void changeLocationName(String locationName){
         this.locationName = locationName;
     }
 
     public String getLocationId(){
         return locationId;
     }
 
     public String getLocationName(){
         return locationName;
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
         return "Location Id : " + locationId + "\nLocation Name : " + locationName + "\nNumber of platform :" + numOfPlatform + "\nStatus : " + status + "\n";
      }

}
