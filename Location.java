public abstract class Location {
    protected String locationId;
    protected String locationName;

    Location(){
        locationId = "Undefined";
        locationName = "Undefined";
    }

    Location(String locationId, String locationName){
        this.locationId = locationId;
        this.locationName = locationName;
    }

    Location(String locationName){
        locationId = locationName.charAt(0) + String.valueOf((int) (10000 + (Math.random() * (20000 - 10000 + 1))));
        this.locationName = locationName;
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

    public String toString(){
        return "Location Id : " + locationId + "\nLocation Name : " + locationName +"\n";
    }
}
