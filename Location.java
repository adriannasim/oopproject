public class Location {
    private String locationId;
    private String locationName;

    Location(){

    }

    Location(String locationName){
        //random id
        this.locationName = locationName;

    }

    public void deleteLocation(){

    }

    public void editLocation(String locationName){
        this.locationName = locationName;

    }

    public String toString(){
        return "";
    }
}
