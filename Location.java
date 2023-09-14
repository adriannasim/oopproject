public class Location {
    private String locationId;
    private String locationName;

    Location(){
        locationId = "Undefined";
        locationName = "Undefined";
    }

    Location(String locationName){
        locationId = locationName.charAt(0) + String.valueOf((int) (1000 + (Math.random() * (2000 - 1000 + 1))));
        this.locationName = locationName;
    }

    public void changeLocationName(String locationName){
        this.locationName = locationName;
    }

    public String toString(){
        return "Location Id : " + locationId + "\nLocation Name : " + locationName +"\n";
    }
}
