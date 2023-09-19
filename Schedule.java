import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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


    //===========================================================================================================
    //                                      SCHEDULE INFORMATION MODIFICATION
    //===========================================================================================================

    public ArrayList<Schedule> getScheduleList() throws Exception{
        ArrayList<Schedule> scheduleList = readFromFile("scheduleFile.txt");
        return scheduleList;
    }
    
    public static boolean writeIntoFile(String filename, ArrayList<Schedule> scheduleList) {
        boolean write = false;
        File file = new File(filename);
        ObjectOutputStream oos = null;
    
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file, false));
            for (int i = 0; i < scheduleList.size(); i++) {
                oos.writeObject(scheduleList.get(i));
            }
            write = true; // Set write to true if writing is successful
        } catch (IOException e) { // Handle any IOException that might occur during writing 
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return write;
    }

    public static ArrayList<Schedule> readFromFile(String filename) throws Exception {
        File file = new File(filename);
        ObjectInputStream input = null; // Declare outside the try block
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
        try {
            input = new ObjectInputStream(new FileInputStream(file));
            while (true) {
                try {
                    Schedule obj = (Schedule) input.readObject();
                    scheduleList.add(obj);
                } catch (EOFException eofe) {
                    break;
                } catch (IOException ioe) {
                    //ioe.printStackTrace();
                }
            }
        } catch (IOException ioe) {
            //ioe.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioe) {
                //ioe.printStackTrace();
            }
        }    
        return scheduleList; 
    }

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
                        for (int j = 0; j < scheduleList.size(); j++) {
                            if (scheduleList.get(j).getDepartLocation().getLocationName().equals(departLocation.getLocationName())) {
                                if (departTime.compareTo(scheduleList.get(j).getDepartTime()) == 0)  {
                                    usePlatformCount++;
                                }
                                if (departTime.compareTo(scheduleList.get(j).getArriveTime()) == 0) {
                                    usePlatformCount++;
                                }
                        }
                        if (scheduleList.get(j).getArriveLocation().getLocationName().equals(departLocation.getLocationName())) {
                                if (departTime.compareTo(scheduleList.get(j).getDepartTime()) == 0)  {
                                    usePlatformCount++;
                                }
                                if (departTime.compareTo(scheduleList.get(j).getArriveTime()) == 0) {
                                    usePlatformCount++;
                                }
                        }
                        
                    }
                    if (usePlatformCount > departLocation.getNumOfPlatform()){
                        noPlatform = true;
                        System.out.println("\nTHE DEPARTURE STATION'S PLATFORMS IS FULLY OCCUPIED.\n");
                    }
                
                    
                    } else {
                        correctTime = false;
                        System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
                    }
                } else {
                    correctTime = false;
                    System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
                }    
                
            }while(correctTime==false || noPlatform == true);
            
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
                        for (int j = 0; j < scheduleList.size(); j++) {
                            if (scheduleList.get(j).getDepartLocation().getLocationName().equals(arriveLocation.getLocationName())) {
                                if (arriveTime.compareTo(scheduleList.get(j).getDepartTime()) == 0)  {
                                    usePlatformCount++;
                                }
                                if (arriveTime.compareTo(scheduleList.get(j).getArriveTime()) == 0) {
                                    usePlatformCount++;
                                }
                        }
                        if (scheduleList.get(j).getArriveLocation().getLocationName().equals(arriveLocation.getLocationName())) {
                                if (departTime.compareTo(scheduleList.get(j).getDepartTime()) == 0)  {
                                    usePlatformCount++;
                                }
                                if (departTime.compareTo(scheduleList.get(j).getArriveTime()) == 0) {
                                    usePlatformCount++;
                                }
                        }
                        
                    }
                    if (usePlatformCount > arriveLocation.getNumOfPlatform()){
                        noPlatform = true;
                        System.out.println("\nTHE ARRIVAL STATION'S PLATFORMS IS FULLY OCCUPIED.\n");
                    }
                    } else {
                        correctTime = false;
                        System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
                    }
                } else {
                    correctTime = false;
                    System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
                }
                int compareDepartTime = arriveTime.compareTo(departTime);
                if(compareDepartTime>=0){
                    correctTime = true;
                }else{
                    correctTime = false;
                    System.out.println("\nINVALID TIME. ARRIVAL TIME CANNOT BE EARLIER THAN DEPARTURE TIME.\n");
                }
            }while(correctTime==false || noPlatform == true);

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
                
                for (int j=0; j<scheduleList.size(); j++){
                    if(scheduleList.get(j).getOperatedTrain().getTrainNo()==trainOperated.getTrainNo()){
                            if ((departTime.compareTo(scheduleList.get(j).getDepartTime()) >= 0) &&
                                departTime.compareTo(scheduleList.get(j).getArriveTime()) <= 0) {
                                incompatible = true;
                                break;
                            }
                            if ((arriveTime.plusMinutes(15).compareTo(scheduleList.get(j).getDepartTime()) >= 0) &&
                            (arriveTime.compareTo(scheduleList.get(j).getArriveTime()) <= 0)) {
                                incompatible = true;
                                break;
                            }
                    
                    }
                }
                if(incompatible == true){
                    System.out.println("\nTRAIN INVOLVES IN OTHER SCHEDULE DURING THE TIME FRAME SELECTED.\n ");
                } 	
                
            }while(userInput2>trainList.size()||incompatible == true);

            System.out.print("Enter the ticket price (RM) > ");
            ticketPrice = BackendStaff.validateDoubleInput(scanner, "Enter the ticket price (RM) > ");

            System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
            userInput = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

            if(userInput.equalsIgnoreCase("Y")){
                Schedule s = new Schedule(departLocation, arriveLocation, departTime, arriveTime, trainOperated, ticketPrice);
                scheduleList.add(s);
                added = writeIntoFile("scheduleFile.txt", scheduleList);

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
            
            System.out.print("Enter a new departure time (HH:MM) (Press # to exit)> ");
            userInput = scanner.nextLine();
            
            if (userInput.equals("#")) {
                return; // Exit the loop and function if user enters '#'
            }
            
            // Validate the time format
            if (BackendStaff.isValidTimeFormat(userInput)) {
                departTime = BackendStaff.parseTime(userInput);
                
                if (departTime != null) {
                    correctTime = true;
                    checkPlatformUsage(, LocalTime time, int index)
                    // Check if there are available platforms
                    if (usePlatformCount > scheduleList.get(index).getDepartLocation().getNumOfPlatform()) {
                        noPlatform = true;
                        System.out.println("\nTHE DEPARTURE STATION'S PLATFORMS ARE FULLY OCCUPIED.\n");
                    }
                }
            } else {
                System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
            }
            
            // Check for time conflicts with other schedules
            for (int j = 0; j < scheduleList.size(); j++) {
                if (j != index && scheduleList.get(j).getOperatedTrain().equals(scheduleList.get(index).getOperatedTrain())) {
                    if (isTimeConflict(scheduleList.get(j), scheduleList.get(index))) {
                        incompatible = true;
                        break;
                    }
                }
            }
        } while (!correctTime || noPlatform || incompatible);

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(confirm.equalsIgnoreCase("Y")){
            scheduleList.get(index).editDepartTime(departTime);
            updated = writeIntoFile("scheduleFile.txt", scheduleList);
            if(updated){
                System.out.println("SCHEDULE DEPARTURE TIME HAS UPDATED.");
            }else{
                System.out.println("\nFAILED TO UPDATE THE DEPARTURE TIME.\n");
            }
        }else{
            System.out.println("\nMODIFICATION CANCELLED.\n");
        }
    }

    public int checkPlatformUsage(Schedule schedule1, LocalTime time, int index) throws Exception {
        int usePlatformCount = 1;
        ArrayList<Schedule> scheduleList = getScheduleList();
    
        // Iterate through the scheduleList to check for conflicts with schedule1
        for (int j = 0; j < scheduleList.size(); j++) {
            if (j != index) {
                Schedule schedule2 = scheduleList.get(j); // Get the other schedule
    
                // Check if schedule1's departure location matches schedule2's departure location
                if (schedule1.getDepartLocation().equals(schedule2.getDepartLocation())) {
                    // Check if schedule1's departure time matches schedule2's departure time or arrive time
                    if (schedule1.getDepartTime().compareTo(schedule2.getDepartTime()) == 0 ||
                        schedule1.getDepartTime().compareTo(schedule2.getArriveTime()) == 0) {
                        // Check if the operated trains are different
                        if (!schedule1.getOperatedTrain().equals(schedule2.getOperatedTrain())) {
                            usePlatformCount++;
                        }
                    }
                }
            }
        }
        return usePlatformCount; // Return true if there are conflicts, false otherwise
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
                    for (int j = 0; j < scheduleList.size(); j++) {
                		if (scheduleList.get(j).getDepartLocation().getLocationName().equals(scheduleList.get(index).getDepartLocation().getLocationName())) {
                    		if (arriveTime.compareTo(scheduleList.get(j).getDepartTime()) == 0)  {
                        		if(!scheduleList.get(j).getOperatedTrain().equals(scheduleList.get(index).getOperatedTrain())){
                        			usePlatformCount++;
                        		}
                    		}
                    		if (arriveTime.compareTo(scheduleList.get(j).getArriveTime()) == 0) {
                       			 if(!scheduleList.get(j).getOperatedTrain().equals(scheduleList.get(index).getOperatedTrain())){
                        			usePlatformCount++;
                        		 }
                    		}
               	 	}
               	 	if (scheduleList.get(j).getArriveLocation().getLocationName().equals(scheduleList.get(index).getArriveLocation().getLocationName())) {
                    		if (arriveTime.compareTo(scheduleList.get(j).getDepartTime()) == 0)  {
                        		usePlatformCount++;
                    		}
                    		if (arriveTime.compareTo(scheduleList.get(j).getArriveTime()) == 0) {
                       			 usePlatformCount++;
                    		}
               	 	}
               	 	
            	}
            	if (usePlatformCount > scheduleList.get(index).getArriveLocation().getNumOfPlatform()){
        			noPlatform = true;
        			System.out.println("\nTHE ARRIVAL STATION'S PLATFORMS IS FULLY OCCUPIED.\n");
        		}
                } else {
                    correctTime = false;
                    System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
                }
            } else {
                correctTime = false;
                System.out.println("\nINVALID TIME FORMAT. PLEASE ENTER IN FORMAT (HH:MM).\n");
            }
            int compareDepartTime = arriveTime.compareTo(scheduleList.get(index).getArriveTime());
            if(compareDepartTime>=0){
            	correctTime = true;
            }else{
            	correctTime = false;
            	System.out.println("\nINVALID TIME. ARRIVAL TIME CANNOT BE EARLIER THAN DEPARTURE TIME.\n");
            }
            for (int j=0; j<scheduleList.size(); j++){
            	if(scheduleList.get(j).getOperatedTrain().equals(scheduleList.get(index).getOperatedTrain())){
                    incompatible = isTimeConflict(scheduleList.get(j), scheduleList.get(index));
                    break;
                }	
        	}
        }while(correctTime==false || noPlatform == true || incompatible == true);

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(confirm.equalsIgnoreCase("Y")){
            scheduleList.get(index).editArriveTime(arriveTime);
            updated = writeIntoFile("scheduleFile.txt", scheduleList);
            if(updated){
                System.out.println("SCHEDULE ARRIVAL TIME HAS UPDATED.");
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

            if (userInput >= 1 && userInput <= availableTrains.size()) {
                System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
                confirm = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");
                if(confirm.equalsIgnoreCase("Y")){
                    trainOperated = availableTrains.get(userInput - 1);
                    for (int j=0; j<scheduleList.size(); j++){
            		    if(scheduleList.get(j).getOperatedTrain().equals(trainOperated)){
                 			incompatible = isTimeConflict(scheduleList.get(j), scheduleList.get(index));
                         	break;
            		    }
        		    }
    
        		    if(incompatible == false){
        			    scheduleList.get(index).editTrainOperated(trainOperated);
                    	updated = writeIntoFile("scheduleFile.txt", scheduleList);

                    	if(updated){
                        	System.out.println("\nTRAIN OPERATED FOR THE SCHEDULE HAS UPDATED");
                    	}else{
                        	System.out.println("\nFAILED TO UPDATE THE TRAIN OPERATED FOR THE SCHEDULE.\n");
                    	}
        		    }	

                }else{
                    System.out.println("\nMODIFICATION CANCELLED.\n");
                }   
            }else{
                System.out.println("\nINVALID TRAIN NUMBER.\n");
            }
        }while ((userInput < 1 && userInput > availableTrains.size())||incompatible == true);    
    }

    public boolean isTimeConflict(Schedule schedule1, Schedule schedule2) {
        // Check if schedule1's departure time is between schedule2's departure and arrival times
        if (schedule1.getDepartTime().compareTo(schedule2.getDepartTime()) >= 0 &&
            schedule1.getDepartTime().compareTo(schedule2.getArriveTime()) <= 0) {
                System.out.println("\nTRAIN INVOLVES IN OTHER SCHEDULE DURING THE TIME FRAME SELECTED.\n ");
                return true;
        }
        
        // Check if schedule1's arrival time is between schedule2's departure and arrival times
        if (schedule1.getArriveTime().compareTo(schedule2.getDepartTime()) >= 0 &&
            schedule1.getArriveTime().compareTo(schedule2.getArriveTime()) <= 0) {
            return true;
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
            updated = writeIntoFile("scheduleFile.txt", scheduleList);

            if(updated){
                System.out.println("TICKET PRICE FOR THE SCHEDULE HAS UPDATED.");
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
                System.out.print("Enter your option (Y-Yes/N-No)> ");
                userInput = BackendStaff.validateYNInput(scanner, "Enter your option (Y-Yes/N-No)> ");
    
                if (userInput.equalsIgnoreCase("Y")) {
                    System.out.print("Do you confirm (Y-Yes/N-No) ? ");
                    userInput2 = BackendStaff.validateYNInput(scanner, "Do you confirm (Y-Yes/N-No) ? ");

                    if(userInput2.equalsIgnoreCase("Y")){
                        scheduleList.remove(index); // Remove the train from the list
                        deleted = writeIntoFile("scheduleFile.txt",scheduleList);

                        if (deleted) {
                             System.out.println("SCHEDULE HAS REMOVED.");
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
   
    


    public String displayViewTicket(){
        return "Departure Location %10s Arrival Location %10s Departure Time %10s Arrival Time %10s Ticket Price";
    }

}
