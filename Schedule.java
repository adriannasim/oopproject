import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Schedule implements Serializable{
    private String scheduleId;
    private TrainStation departLocation;
    private TrainStation arriveLocation;
    private LocalTime departTime;
    private LocalTime arriveTime;
    private Train trainOperated;
    private double ticketPrice;
   

    //-----------------------------------CONSTRUCTOR---------------------------------------- 
    // NO-ARG CONSTRUCTOR
    public Schedule(){
        scheduleId = "Undefined";
        departLocation = null;
        arriveLocation = null;
        departTime = null;
        arriveTime = null;
        trainOperated = null;
        ticketPrice = 0;
    }

    // PARAMETERIZED CONSTRUCTOR
    public Schedule(TrainStation departLocation, TrainStation arriveLocation, LocalTime departTime, LocalTime arriveTime, Train trainOperated, double ticketPrice){
        scheduleId = departLocation.getLocationName().substring(0,3) + "-" + arriveLocation.getLocationName().substring(0,3) + String.valueOf((int) (100000 + (Math.random() * (200000 - 100000 + 1))));
        this.departLocation = departLocation;
        this.arriveLocation = arriveLocation;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.trainOperated = trainOperated;
        this.ticketPrice = ticketPrice;
    }

    public Schedule(String scheduleId, TrainStation departLocation, TrainStation arriveLocation, LocalTime departTime, LocalTime arriveTime, Train trainOperated, double ticketPrice){
        this.scheduleId = scheduleId;
        this.departLocation = departLocation;
        this.arriveLocation = arriveLocation;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.trainOperated = trainOperated;
        this.ticketPrice = ticketPrice;
    }

        public Schedule(TrainStation departLocation, TrainStation arriveLocation, LocalTime departTime, LocalTime arriveTime, double ticketPrice){
        scheduleId = departLocation.getLocationName().substring(0,3) + "-" + arriveLocation.getLocationName().substring(0,3) + String.valueOf((int) (100000 + (Math.random() * (200000 - 100000 + 1))));
        this.departLocation = departLocation;
        this.arriveLocation = arriveLocation;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.ticketPrice = ticketPrice;
    }

    public Schedule(TrainStation departLocation, TrainStation arriveLocation, double ticketPrice){
        scheduleId = departLocation.getLocationName().substring(0,3) + "-" + arriveLocation.getLocationName().substring(0,3) + String.valueOf((int) (100000 + (Math.random() * (200000 - 100000 + 1))));
        this.departLocation = departLocation;
        this.arriveLocation = arriveLocation;
        this.ticketPrice = ticketPrice;
    }

    //------------------------------------METHOD-----------------------------------------
    // DELETE METHOD
    public void deleteSchedule(){
        scheduleId = null;
        departLocation = null;
        arriveLocation = null;
        departTime = null;
        arriveTime = null;
        trainOperated = null;
        ticketPrice = 0;
    }

    // READ METHOD
    public String getScheduleId(){
        return scheduleId;
    }

    public TrainStation getDepartLocation(){
        return departLocation;
    }

    public TrainStation getArriveLocation(){
        return arriveLocation;
    }

    public LocalTime getDepartTime(){
        return departTime;
    }

    public LocalTime getArriveTime(){
        return arriveTime;
    }

    public Train getOperatedTrain(){
        return trainOperated;
    }

    public double getTicketPrice(){
        return ticketPrice;
    }

    // UPDATE METHOD
    public void editDepartLocation(TrainStation departLocation){
        this.departLocation = departLocation;
    }

    public void editArriveLocation(TrainStation arriveLocation){
        this.arriveLocation = arriveLocation;
    }

    public void editDepartTime(LocalTime departTime){
        this.departTime = departTime;
    }

    public void editArriveTime(LocalTime arriveTime){
        this.arriveTime = arriveTime;
    }

    public void editTrainOperated(Train trainOperated){
        this.trainOperated = trainOperated;
    }

    public void editTicketPrice(double ticketPrice){
        this.ticketPrice =  ticketPrice;
    }

    // DISPLAY METHOD
    public String toString(){
        return "Schedule id: " + scheduleId + "\nDeparture location: " + departLocation.getLocationName() + "\nArrival location: " + arriveLocation.getLocationName() + "\nDeparture time: " + departTime + "\nArrival time: " + arriveTime + "\nTrain operated: " + trainOperated.getTrainNo() + trainOperated.getTrainName() + "\nTicket Price: RM" + ticketPrice;
    }

    public String displayToCust(){
        return  "Schedule ID           : " + scheduleId + 
                "\nDeparture Location    : " + departLocation.getLocationName() + 
                "\nArrival Location      : " + arriveLocation.getLocationName() + 
                "\nDeparture Time        : " + departTime + 
                "\nArrival Time          : " + arriveTime + 
                "\nTicket Price          : RM" + ticketPrice;
    }

    public String displayInTicket(){
        return  "Departure location    : " + departLocation.getLocationName() + 
                "\nArrival location      : " + arriveLocation.getLocationName() + 
                "\nTicket Price          : RM" + ticketPrice;
    }

    public String displayInReport(){
        return  String.format("%19s - %-4s %24s - %-18s %6.2f\n", arriveTime, departTime, departLocation.getLocationName(), arriveLocation.getLocationName(), ticketPrice);
    }
    
    public String displayToReport(){
        return String.format("Departure Location \t Arrival Location \t Departure Time \t Arrival Time \t Ticket Price\n%20s %20s %10s %10s %.2lf",departLocation.getLocationName(), arriveLocation.getLocationName(), departTime, arriveTime, ticketPrice);
    }

    public String displayToReportStaff(){
        return String.format("Departure Location \t Arrival Location \t Departure Time \t Arrival Time \t Ticket Price\n%10s %20s %20s %10s %10s %.2lf",scheduleId, departLocation.getLocationName(), arriveLocation.getLocationName(), departTime, arriveTime, ticketPrice);
    }
    
    
    public static boolean writeFile(ArrayList<Schedule> scheduleList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scheduleFile.txt", true))) {
            for (Schedule schedule : scheduleList) {
                writer.write(schedule.getScheduleId() + "||" + schedule.getDepartLocation().getLocationId() + "||" 
                        + schedule.getDepartLocation().getLocationName() + "||" + schedule.getDepartLocation().getNumOfPlatform() + "||"
                        + schedule.getArriveLocation().getLocationId() + "||" + schedule.getArriveLocation().getLocationName() + "||"
                        + schedule.getArriveLocation().getNumOfPlatform() + "||" + schedule.getDepartTime() + "||"
                        + schedule.getArriveTime() + "||" + schedule.getOperatedTrain().getTrainNo() + "||"
                        + schedule.getOperatedTrain().getTrainName() + "||" + schedule.getOperatedTrain().getTrainModel() + "||"
                        + schedule.getTicketPrice() );
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Schedule> getScheduleList(){
        ArrayList<Schedule> scheduleList = readFile("scheduleFile.txt");
        return scheduleList;
    }

    // read file
    public static ArrayList<Schedule> readFile(String filename) {
        ArrayList<Schedule> scheduleList = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String scheduleDetails;
            
            while ((scheduleDetails = reader.readLine()) != null) {
                scheduleDetails = scheduleDetails.trim();
                String[] parts = scheduleDetails.split("\\|\\|");
    
                if (parts.length == 13) {
                    String scheduleId = parts[0];
                    String locationId1 = parts[1];
                    String locationName1 = parts[2];
                    int cNumOfPlatform1 = Integer.parseInt(parts[3]);
                    String locationId2 = parts[4];
                    String locationName2 = parts[5];
                    int cNumOfPlatform2 = Integer.parseInt(parts[6]);
                    LocalTime cDepartTime = LocalTime.parse(parts[7]);
                    LocalTime cArriveTime = LocalTime.parse(parts[8]);
                    int cTrainNo = Integer.parseInt(parts[9]);
                    String trainName = parts[10];
                    String trainModel = parts[11];
                    double cTicketPrice = Double.parseDouble(parts[12]);
    
                    TrainStation departLocation = new TrainStation(locationId1, locationName1, cNumOfPlatform1);
                    TrainStation arriveLocation = new TrainStation(locationId2, locationName2, cNumOfPlatform2);
                    Train trainOperated = new Train(cTrainNo, trainName, trainModel);
                    Schedule schedule = new Schedule(scheduleId, departLocation, arriveLocation, cDepartTime, cArriveTime, trainOperated, cTicketPrice);
                    scheduleList.add(schedule);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return scheduleList;
    }
    
    //===========================================================================================================
    //                                      SCHEDULE INFORMATION MODIFICATION
    //===========================================================================================================
    public void scheduleModification(Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;
    
        while (cont) {
            System.out.println("==================================================");
            System.out.println("      Schedule Information Modification");
            System.out.println("==================================================");
            System.out.println("1. View schedule");
            System.out.println("2. Update existing schedule");
            System.out.println("3. Add a new schedule");
            System.out.println("4. Delete an existing schedule");
            System.out.println("\n* Press # to go back");
            System.out.println("==================================================");
    
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
               
                if (userInput.equals("1")) {
                    viewSchedule();
                } else if (userInput.equals("2")) {
                    updateScheduleInfo(scanner);
                } else if (userInput.equals("3")) {
                    addSchedule(scanner);
                } else if (userInput.equals("4")) {
                    deleteSchedule(scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("\nINVALID OPTION. PLEASE ENTER (1/2/3/4/#).\n");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    public void viewSchedule() throws Exception{
        ArrayList<Schedule> scheduleList = getScheduleList();
        if (scheduleList.size()==0){
            System.out.println("\nNO SCHEDULES IN THE RECORD.\n");
        }
        for (int i=0; i< scheduleList.size(); i++){
            System.out.println(scheduleList.get(i).toString() + "\n");
        }

    }

    //-----------------------------------------------ADD SCHEDULE------------------------------------------------ 

    public void addSchedule(Scanner scanner) throws Exception {
        TrainStation departLocation = new TrainStation();
        TrainStation arriveLocation = new TrainStation();
        LocalTime departTime = LocalTime.of(0,0);
        LocalTime arriveTime = LocalTime.of(0,0);
        Train trainOperated = new Train();
        double ticketPrice;
        String userInput;
        int userInput2;
        int departureIndex = 0;
        int stationNumber;
        boolean added = false;
        boolean correctTime = false;
        boolean incompatible;
        boolean noPlatform;
        boolean cont = true;
        int usePlatformCount;
        Train obj1 = new Train();
        TrainStation obj2 = new TrainStation();
        ArrayList<Schedule> scheduleList = getScheduleList();
        ArrayList<Train> trainList = obj1.getTrainList();
        ArrayList<TrainStation> stationList = obj2.getStationList();

        if (stationList.isEmpty() && trainList.isEmpty()){
            System.out.println("\nNO TRAIN AND STATIONS AVAILABLE.\n");
            return;
        }else if(stationList.size()==1 && trainList.isEmpty()){
            System.out.println("\nNO TRAIN AVAILABLE AND NOT ENOUGH AVAILABLE STATIONS.\n");
            return;
        }else if (stationList.isEmpty()){
            System.out.println("\nNO TRAIN STATIONS AVAILABLE.\n");
            return;
        }else if(stationList.size()==1){
            System.out.println("\nNOT ENOUGH AVAILABLE STATIONS.\n");
            return;
        }else if(trainList.isEmpty()){
            System.out.println("\nNO AVAILABLE TRAIN.\n");
            return;
        }else{
            System.out.println("==================================================");
            System.out.println("              Add schedule Information");
            System.out.println("==================================================");

        }
        do{
            System.out.println("Select a departure station : ");
            for (int i = 0; i < stationList.size(); i++) {
                System.out.println((i+1) + ". " + stationList.get(i).getLocationName());
            }
            do{
                System.out.print("Enter the station number stated above > ");
                userInput2 = BackendStaff.validateIntegerInput(scanner, "Enter the station number stated above > ");
                if(userInput2>stationList.size()){
                    System.out.println("\nINVALID OPTION. PLEASE ENTER THE NUMBER ABOVE.\n");
                }else{
                    departLocation = stationList.get(userInput2-1);
                    departureIndex = stationList.indexOf(departLocation);
                }
            }while(userInput2>stationList.size());
            
            stationNumber = 1; // Initialize the station number
            System.out.println("Select an arrival station : ");
            for (int i = 0; i < stationList.size(); i++) {
                if (i != departureIndex){
                    System.out.println(stationNumber + ". " + stationList.get(i).getLocationName());
                    stationNumber++; // Increment the station number
                }
            }
            do{
                System.out.print("Enter the station number stated above > ");
                userInput2 = BackendStaff.validateIntegerInput(scanner, "Enter the station number stated above > ");
                if(userInput2>(stationList.size()-1)){
                    System.out.println("\nINVALID OPTION. PLEASE ENTER THE NUMBER STATED ABOVE.\n");
                }
            }while(userInput2>(stationList.size()-1));

            // Adjust user input based on the excluded departure station
            if (userInput2 > departureIndex) {
                userInput2++; // Increment the input station number to account for the excluded departure station
            }
            arriveLocation = stationList.get(userInput2 - 1);
            
            do{
                noPlatform = false;
                usePlatformCount = 1;
                System.out.print("Enter a departure time (HH:MM) (Press # to exit) > ");
                userInput = scanner.nextLine(); 
                if (userInput.equals("#")){
                    return;
                }
                if (BackendStaff.isValidTimeFormat(userInput)) {
                    departTime = BackendStaff.parseTime(userInput);
                    if (departTime != null) {
                        correctTime = true;
                        usePlatformCount = checkPlatformUsage(departTime, departLocation);
                        if (usePlatformCount > departLocation.getNumOfPlatform()) {
                            noPlatform = true;
                            System.out.println("\nTHE DEPARTURE STATION'S PLATFORMS ARE FULLY OCCUPIED.\n");
                        }
                   
                    } else {
                        correctTime = false;
                        System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
                    }
                } else {
                    correctTime = false;
                    System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
                }    
                
            }while(correctTime == false || noPlatform == true);
            
            do{
                noPlatform = false;
                usePlatformCount = 1;
                System.out.print("Enter an arrival time (HH:MM)  (Press # to exit) > ");
                userInput = scanner.nextLine(); 
                if (userInput.equals("#")){
                    return;
                }
                if (BackendStaff.isValidTimeFormat(userInput)) {
                    arriveTime = BackendStaff.parseTime(userInput);
                    if (arriveTime != null) {
                        correctTime = true;
                        usePlatformCount = checkPlatformUsage(arriveTime, arriveLocation);

                        if (usePlatformCount > arriveLocation.getNumOfPlatform()) {
                            noPlatform = true;
                            System.out.println("\nTHE DEPARTURE STATION'S PLATFORMS ARE FULLY OCCUPIED.\n");
                        }

                         int compareDepartTime = arriveTime.compareTo(departTime);
                         if(compareDepartTime>0){
            	            correctTime = true;
                         }else{
            	            correctTime = false;
            	            System.out.println("\nINVALID TIME. ARRIVAL TIME CANNOT BE EARLIER THAN DEPARTURE TIME.\n");
                         }
                        
                    } else {
                        correctTime = false;
                        System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
                    }
                } else {
                    correctTime = false;
                    System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
                }
                
            }while(correctTime == false || noPlatform == true);

            System.out.println("==================================================");
            System.out.println("Select a train for this schedule : ");
            ArrayList<Train> availableTrains = new ArrayList<>(trainList);
        
            for (int i = 0; i < availableTrains.size(); i++) {
                    System.out.println((i+1) + ". " + availableTrains.get(i).getTrainNo());
            }
            System.out.println("==================================================");
            do{
                incompatible = false;
                System.out.print("Enter the train number stated above (Press # to exit) > ");
                
                userInput2 = BackendStaff.validateIntegerInput(scanner, "Enter the train number stated above (Press # to exit) > ");
                if (userInput2==-1)
                    return;
                if(userInput2>availableTrains.size()){
                    System.out.println("\nINVALID OPTION. PLEASE ENTER  THE NUMBER AS STATED ABOVE.\n");
                }else{
                    trainOperated = availableTrains.get(userInput2-1);
                }
                
                incompatible = isTimeConflict(trainOperated, departTime, arriveTime); 	
                
            }while(userInput2>trainList.size()||incompatible == true);

            System.out.print("Enter the ticket price (RM) > ");
            ticketPrice = BackendStaff.validateDoubleInput(scanner, "Enter the ticket price (RM) > ");

            System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
            userInput = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

            if(userInput.equalsIgnoreCase("Y")){
                Schedule s = new Schedule(departLocation, arriveLocation, departTime, arriveTime, trainOperated, ticketPrice);
                scheduleList.add(s);
                added = writeFile(scheduleList);

                if (added){
                    System.out.println("\nSCHEDULE HAS ADDED.\n");
                }else{
                    System.out.println("\nFAILED TO ADD THE SCHEDULE.\n");
                }
            }else{
                System.out.println("\nMODIFICATION CANCELLED.\n");
            }
            System.out.print("Do you want to continue add schedules? (Y-Yes/N-No) > ");
            userInput = BackendStaff.validateYNInput(scanner, "Do you want to continue make changes? (Y-Yes/N-No) > ");
            if (userInput.equalsIgnoreCase("Y")){
                cont = true;
            }else{
                cont = false;
            }
        }while(cont==true);
    }

     //----------------------------------------------UPDATE SCHEDULE----------------------------------------------

     public void updateScheduleInfo(Scanner scanner) throws Exception {
        String scheduleId;
        String userInput;
        boolean cont = true;
        int index = 0;
        boolean found = false;
        ArrayList<Schedule> scheduleList = getScheduleList();


        System.out.println("==================================================");
        System.out.println("           Update Schedule Information");
        System.out.println("==================================================");
    
        do{
            System.out.print("Enter the schedule id to search the schedule (Press # to exit) > ");
            scheduleId = scanner.nextLine();

            if (scheduleId.equalsIgnoreCase("#"))
                break;

            for (int i = 0; i < scheduleList.size(); i++) {
                if (scheduleId.equals(scheduleList.get(i).getScheduleId())) {
                    found = true;
                    index = i;
                }
            }
            if(found==true){
                System.out.println("Do you want to update the schedule information as shown below? ");
                System.out.println();
                System.out.println(scheduleList.get(index).toString());
                System.out.println();
                System.out.print("Enter your option (Y-Yes/N-No)> ");
                userInput = BackendStaff.validateYNInput(scanner, "Enter your option (Y-Yes/N-No)> ");
                do{
                    if(userInput.equalsIgnoreCase("Y")){
                        System.out.println("==================================================");
                        System.out.println("The field that can be updated :");
                        System.out.println("1. Departure time");
                        System.out.println("2. Arrival time");
                        System.out.println("3. Train operated");
                        System.out.println("4. Ticket price");
                        System.out.println("* Press # to exit");
                        System.out.println("==================================================");
    
                        do{
                            System.out.print("Enter option in number stated above > ");
                            userInput = scanner.nextLine();
                        
                            if(userInput.equals("1")){
                                departureInfoUpdate(scanner, index);
                            }else if(userInput.equals("2")){
                                arrivalInfoUpdate(scanner, index);
                            }else if(userInput.equals("3")){
                                trainOperatedUpdate(scanner, index);
                            }else if(userInput.equals("4")){
                                ticketPriceUpdate(scanner, index);
                            }else if(userInput.equals("#")){
                                cont = false;
                                return;
                            }else{
                                System.out.println("\nINVALID INPUT. PLEASE ENTER (1/2/3/4/#).\n");
                            }

                        }while(!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
                    }else{
                        found = false;
                        cont = false;
                        break;
                    }
                    System.out.print("Do you want to continue make changes? (Y-Yes/N-No) > ");
                    userInput = BackendStaff.validateYNInput(scanner, "Do you want to continue make changes? (Y-Yes/N-No) > ");
                    if (userInput.equalsIgnoreCase("Y")){
                        cont = true;
                    }else{
                        cont = false;
                    }
                } while(cont == true);

            }else{
                System.out.println("\nSCHEDULE NOT FOUND. PLEASE SEARCH AGAIN.\n");
            }
               
        }while(!found);
    }
    
     //----------------------------------UPDATE SCHEDULE DEPARTURE INFO-------------------------------------------

    public void departureInfoUpdate(Scanner scanner, int index) throws Exception{
        String userInput;
        String confirm;
        LocalTime departTime = LocalTime.of(0, 0);;
        boolean updated = false;
        boolean correctTime = false;
        boolean noPlatform;
        int usePlatformCount = 1;
        boolean incompatible;
        ArrayList<Schedule> scheduleList = getScheduleList();
        
        System.out.println("Current departure time: " + scheduleList.get(index).getDepartTime());
               
        do {
            correctTime = false;
            noPlatform = false;
            incompatible = false;
            
            System.out.print("Enter a new departure time (HH:MM) (Press # to exit) > ");
            userInput = scanner.nextLine();
            
            if (userInput.equals("#")) {
                return; // Exit the loop and function if user enters '#'
            }
            
            // Validate the time format
            if (BackendStaff.isValidTimeFormat(userInput)) {
                departTime = BackendStaff.parseTime(userInput);
                
                if (departTime != null) {
                    correctTime = true;
                    usePlatformCount = checkPlatformUsage(departTime, scheduleList.get(index).getDepartLocation(), scheduleList.get(index).getOperatedTrain());
                    // Check if there are available platforms
                    if (usePlatformCount > scheduleList.get(index).getDepartLocation().getNumOfPlatform()) {
                        noPlatform = true;
                        System.out.println("\nTHE DEPARTURE STATION'S PLATFORMS ARE FULLY OCCUPIED.\n");
                    }else{
                        // Check for time conflicts with other schedules
                        incompatible = isTimeConflict(scheduleList.get(index).getOperatedTrain(), departTime, scheduleList.get(index).getArriveTime()); 	
                    }
                }
            } else {
                System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
            }
            
            
        } while (!correctTime || noPlatform || incompatible);

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(confirm.equalsIgnoreCase("Y")){
            scheduleList.get(index).editDepartTime(departTime);
            updated = writeFile(scheduleList);
            if(updated){
                System.out.println("SCHEDULE DEPARTURE TIME HAS UPDATED.");
            }else{
                System.out.println("\nFAILED TO UPDATE THE DEPARTURE TIME.\n");
            }
        }else{
            System.out.println("\nMODIFICATION CANCELLED.\n");
        }
    }

    //----------------------------------CHECK AVAILABILITY OF PLATFORM-------------------------------------------

    // Check for depart time's & arrive time's platform availability with unknown train (when add schedule)
    public int checkPlatformUsage(LocalTime time, TrainStation station) throws Exception{
        int usePlatformCount = 1;
        ArrayList<Schedule> scheduleList = getScheduleList();
        // Iterate through the scheduleList to check for conflicts with time
        for (int j = 0; j < scheduleList.size(); j++) {
            // Check if scheduleList's departure location matches specified departure location
            if (scheduleList.get(j).getDepartLocation().getLocationName().equals(station.getLocationName()) || 
            scheduleList.get(j).getArriveLocation().getLocationName().equals(station.getLocationName())) {
                // Check if scheduleList's departure time matches specified departure time or arrive time
                if (scheduleList.get(j).getDepartTime().compareTo(time) == 0 ||
                    scheduleList.get(j).getArriveTime().compareTo(time) == 0) {
                        usePlatformCount++;       
                }
            }
        }
        return usePlatformCount;      
    }

    // Check for depart time's & arrive time's platform availability with known train (when update schedule)
    public int checkPlatformUsage(LocalTime time, TrainStation station, Train train) throws Exception{
        int usePlatformCount = 1;
        ArrayList<Schedule> scheduleList = getScheduleList();
        // Iterate through the scheduleList to check for conflicts with time
        for (int j = 0; j < scheduleList.size(); j++) {
            // Check if scheduleList's departure location matches specified departure location
            if (scheduleList.get(j).getDepartLocation().getLocationName().equals(station.getLocationName()) || 
            scheduleList.get(j).getArriveLocation().getLocationName().equals(station.getLocationName())) {
                // Check if scheduleList's departure time matches specified departure time or arrive time
                if (scheduleList.get(j).getDepartTime().compareTo(time) == 0 ||
                    scheduleList.get(j).getArriveTime().compareTo(time) == 0) {
                        // Check if the operated trains are different
                        if (!scheduleList.get(j).getOperatedTrain().equals(train)) {
                            usePlatformCount++;
                        }
                }
            }
        }
        return usePlatformCount;      
    }

    //-------------------------------------UPDATE SCHEDULE ARRIVAL INFO-------------------------------------------

    public void arrivalInfoUpdate(Scanner scanner, int index) throws Exception{
        String userInput;
        String confirm;
        LocalTime arriveTime = LocalTime.of(0, 0);;
        boolean updated = false;
        boolean correctTime = false;
        boolean noPlatform;
        int usePlatformCount;
        boolean incompatible;
        ArrayList<Schedule> scheduleList = getScheduleList();

        System.out.println("Current arrival time: " + scheduleList.get(index).getArriveTime());
               
        do{
        	noPlatform = false;
        	usePlatformCount = 1;
            incompatible = false;
            System.out.print("Enter a new arrival time (HH:MM) (Press # to exit)> ");
            userInput = scanner.nextLine(); 
            if (userInput.equals("#")){
            	return;
            }
            if (BackendStaff.isValidTimeFormat(userInput)) {
                arriveTime = BackendStaff.parseTime(userInput);
                if (arriveTime != null) {
                    correctTime = true;
                    usePlatformCount = checkPlatformUsage(arriveTime, scheduleList.get(index).getArriveLocation(), scheduleList.get(index).getOperatedTrain());
                    // Check if there are available platforms
                    if (usePlatformCount > scheduleList.get(index).getArriveLocation().getNumOfPlatform()) {
                        noPlatform = true;
                        System.out.println("\nTHE DEPARTURE STATION'S PLATFORMS ARE FULLY OCCUPIED.\n");
                    }
                } else {
                    correctTime = false;
                    System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
                }
            } else {
                correctTime = false;
                System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
            }

            int compareDepartTime = arriveTime.compareTo(scheduleList.get(index).getDepartTime());
            if(compareDepartTime>0){
            	correctTime = true;
                // Check for time conflicts with other schedules
                incompatible = isTimeConflict(scheduleList.get(index).getOperatedTrain(), scheduleList.get(index).getDepartTime(), scheduleList.get(index).getArriveTime()); 
            }else{
            	correctTime = false;
            	System.out.println("\nINVALID TIME. ARRIVAL TIME CANNOT BE EARLIER THAN DEPARTURE TIME.\n");
            }

            
        }while(correctTime==false || noPlatform == true || incompatible == true);

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(confirm.equalsIgnoreCase("Y")){
            scheduleList.get(index).editArriveTime(arriveTime);
            updated = writeFile(scheduleList);
            if(updated){
                System.out.println("\nSCHEDULE ARRIVAL TIME HAS UPDATED.\n");
            }else{
                System.out.println("\nFAILED TO UPDATE THE ARRIVAL TIME.\n");
            }
        }else{
            System.out.println("\nMODIFICATION CANCELLED.\n");
        }

    }
    
    //--------------------------------------UPDATE SCHEDULE TRAIN INFO-------------------------------------------

    public void trainOperatedUpdate(Scanner scanner, int index) throws Exception{
        int userInput;
        Train trainOperated;
        String confirm;
        boolean updated = false;    
        boolean incompatible = false;
        Train obj1 = new Train();
        ArrayList<Schedule> scheduleList = getScheduleList();
        ArrayList<Train> trainList = obj1.getTrainList();
        
        ArrayList<Train> availableTrains = new ArrayList<>(trainList);
        int currentTrainNo = scheduleList.get(index).getOperatedTrain().getTrainNo();
        availableTrains.removeIf(train -> train.getTrainNo()==currentTrainNo);
        if (availableTrains.size()==0){
            System.out.println("\nSORRY, YOU CANNOT CHANGE THE TRAIN OPERATED AS IT IS NO TRAIN AVAILABLE TO REPLACE THE CURRENT TRAIN.\n");
            return;
        }
        System.out.println("Current operated train: " + scheduleList.get(index).getOperatedTrain().toString());
        System.out.println("==================================================");
        System.out.println("Select a new train to replace train above: ");
        
        int trainNumber = 1; // Initialize the train number
        for (int n = 0; n < availableTrains.size(); n++) {
            System.out.println(trainNumber + ". " + availableTrains.get(n).getTrainNo());
            trainNumber++; // Increment the station number
        }
        System.out.println("==================================================");
        do{
        	incompatible = false;
            System.out.print("Enter the station number stated above > ");
            userInput = BackendStaff.validateIntegerInput(scanner, "Enter the station number stated above > ");

            if (userInput >= 1 && userInput <= availableTrains.size()){
                trainOperated = availableTrains.get(userInput - 1);
                // Check for time conflicts with other schedules
                incompatible = isTimeConflict(trainOperated, scheduleList.get(index).getDepartTime(), scheduleList.get(index).getArriveTime()); 
                if (!incompatible){
                    System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
                    confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");
                    if(confirm.equalsIgnoreCase("Y")){
                        scheduleList.get(index).editTrainOperated(trainOperated);
                    	updated = writeFile(scheduleList);

                    	if(updated){
                        	System.out.println("\nTRAIN OPERATED FOR THE SCHEDULE HAS UPDATED\n");
                    	}else{
                        	System.out.println("\nFAILED TO UPDATE THE TRAIN OPERATED FOR THE SCHEDULE.\n");
                    	}

                    } else{
                        System.out.println("\nMODIFICATION CANCELLED.\n");
                    }
                }   
            } else{
                System.out.println("\nINVALID OPTION. PLEASE ENTER THE NUMBER AS STATED ABOVE.\n");
            }
            
        }while ((userInput < 1 && userInput > availableTrains.size()) || incompatible == true);    
    }

    //-----------------------------------CHECK TRAIN AVAILABILITY---------------------------------------

    // check train availability
    public boolean isTimeConflict(Train train, LocalTime departTime, LocalTime arriveTime) throws Exception {
        ArrayList<Schedule> scheduleList = getScheduleList();

        for (int i=0; i<scheduleList.size(); i++){
            if (scheduleList.get(i).getOperatedTrain().getTrainNo()==(train.getTrainNo())){
                // Check if schedule's departure time is between scheduleList's departure and arrival times
                if (departTime.compareTo(scheduleList.get(i).getDepartTime()) >= 0 && 
                    departTime.compareTo(scheduleList.get(i).getArriveTime()) <= 0){
                        System.out.println("\nTRAIN INVOLVES IN OTHER SCHEDULE DURING THE TIME FRAME SELECTED.\n ");
                        return true;
                }
                // Check if schedule's arrival time is between scheduleList's departure and arrival times
                if (arriveTime.compareTo(scheduleList.get(i).getDepartTime()) >= 0 && 
                    arriveTime.compareTo(scheduleList.get(i).getArriveTime()) <= 0){
                        System.out.println("\nTRAIN INVOLVES IN OTHER SCHEDULE DURING THE TIME FRAME SELECTED.\n ");
                        return true;
                }

            }
        }
        
        // No time conflict
        return false;
    }

    //-----------------------------------UPDATE SCHEDULE TICKET PRICE INFO---------------------------------------

    public void ticketPriceUpdate(Scanner scanner, int index) throws Exception{
        String confirm;
        boolean updated;
        double ticketPrice;
        ArrayList<Schedule> scheduleList = getScheduleList();


        System.out.println("Current ticket price (RM): " + scheduleList.get(index).getTicketPrice());
        System.out.print("Enter a new ticket price (RM): ");
        ticketPrice = BackendStaff.validateDoubleInput(scanner, "Enter a new ticket price (RM): ");

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(confirm.equalsIgnoreCase("Y")){
            scheduleList.get(index).editTicketPrice(ticketPrice);
            updated = writeFile(scheduleList);

            if(updated){
                System.out.println("\nTICKET PRICE FOR THE SCHEDULE HAS UPDATED.\n");
            }else{
                System.out.println("\nFAILED TO UPDATE THE TICKET PRICE FOR THE SCHEDULE.\n");
            }
        }else{
            System.out.println("\nMODIFICATION CANCELLED.\n");
        }
        
    }

     //----------------------------------------------DELETE SCHEDULE----------------------------------------------

     public void deleteSchedule(Scanner scanner) throws Exception {
        boolean found = false;
        String userInput;
        String userInput2;
        boolean deleted = false;
        int index = 0;
         ArrayList<Schedule> scheduleList = getScheduleList();
        
        System.out.println("==================================================");
        System.out.println("            Delete Schedule Information");
        System.out.println("==================================================");

        do {
            System.out.print("Enter the schedule id to search the schedule (Press # to exit) > ");
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
    
            for (int i = 0; i < scheduleList.size(); i++) {
                if (userInput.equalsIgnoreCase(scheduleList.get(i).getScheduleId())) {
                    index = i;
                    found = true;
                }
            }
            if (found==true){
                System.out.println("Do you want to delete the schedule information as shown below? ");
                System.out.println();
                System.out.println(scheduleList.get(index).toString());
                 System.out.println();
                System.out.print("Enter your option (Y-Yes/N-No)> ");
                userInput = BackendStaff.validateYNInput(scanner, "Enter your option (Y-Yes/N-No)> ");
    
                if (userInput.equalsIgnoreCase("Y")) {
                    System.out.print("Do you confirm (Y-Yes/N-No) ? ");
                    userInput2 = BackendStaff.validateYNInput(scanner, "Do you confirm (Y-Yes/N-No) ? ");

                    if(userInput2.equalsIgnoreCase("Y")){
                        scheduleList.remove(index); // Remove the train from the list
                        deleted = writeFile(scheduleList);

                        if (deleted) {
                             System.out.println("\nSCHEDULE HAS REMOVED.\n");
                        }else{
                            System.out.println("\nFAILED TO REMOVE THE SCHEDULE.\n");
                        }

                    } else {
                        System.out.println("\nMODIFICATION CANCELLED.\n");
                    }        
                }else{
                    found = false;
                }
            
            }else{
                System.out.println("\nSCHEDULE NO FOUND. PLEASE SEARCH AGAIN.\n");
            } 
        } while (!found);
    
    }

}
