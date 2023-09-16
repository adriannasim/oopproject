import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class DriverJiahui {

    //===========================================================================================================
    //                                              MAIN PROGRAM 
    //===========================================================================================================
    public static void main(String[] args) throws Exception{
        // Declare and create the ArrayList for Train, TrainStation, Schedule, Snacks, Drinks
        ArrayList<Train> trainList = new ArrayList<Train>();
        ArrayList<TrainStation> stationList = new ArrayList<TrainStation>();
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
        ArrayList<Snacks> snacksList = new ArrayList<Snacks>();
        ArrayList<Drinks> drinksList = new ArrayList<Drinks>();
        // Declare and create Scanner object to read input from user
        Scanner scanner = new Scanner(System.in);
        // Declare variable to store userInput
        String userInput = "";
        // Declare a variable to set the loop status (Continue (true) or Stop (false))
        boolean cont = true;
        // declare dummy variable
        Train train = new Train();
        // Read data from each file
        readFromFile("trainFile.txt", trainList, train);

        do{
            System.out.println("1. Train Information Modification");
            System.out.println("2. Schedule Modification");
            System.out.println("3. Train Station Information Modification");
            System.out.println("4. Food and Beverage Information Modification");
            System.out.println("* Press '#' to exit");
 
            do{
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
                
                // Compare user input and invoke specific method
                if (userInput.equals("1")) {
                    trainModification(trainList, scheduleList, scanner);
                } else if (userInput.equals("2")) {
                    scheduleModification(scheduleList, stationList, trainList, scanner);
                } else if (userInput.equals("3")) {
                    stationModification(stationList, scheduleList, scanner);
                } else if (userInput.equals("4")) {
                    //foodAndBeverageModification(snacksList, scanner);
                } else if (userInput.equals("#")) {
                    // Handle exit
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
            }while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
 
        }while(cont==true);
    }

    //===========================================================================================================
    //                                      TRAIN INFORMATION MODIFICATION
    //===========================================================================================================
    
    // Method to modify train information
    public static void trainModification(ArrayList<Train> trainList, ArrayList<Schedule> scheduleList, Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;

        while(cont==true){
            // Display the user option menu
            System.out.println("1. View existing train information");
            System.out.println("2. Update an existing train information");
            System.out.println("3. Add a new train information");
            System.out.println("4. Delete an existing train information");
            System.out.println("* Press # to go back");
           
            do{
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();

                if (userInput.equals("1")) {
                    for (int i = 0; i < trainList.size(); i++) {
                        System.out.println(trainList.get(i).toString());
                        System.out.println();
                    }
                } else if (userInput.equals("2")) {
                    updateTrainInfo(trainList, scanner);
                } else if (userInput.equals("3")) {
                    addTrain(trainList, scanner);
                } else if (userInput.equals("4")) {
                    deleteTrain(trainList, scheduleList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                    break; // Exit the loop
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
                
            }while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    //-------------------------------------------UPDATE TRAIN INFORMATION----------------------------------------

    public static void updateTrainInfo(ArrayList<Train> trainList, Scanner scanner) throws Exception {
        String userInput = "";
        int trainNo;
        String trainName;
        boolean found = false;
        boolean updated = false;
        boolean invalidInput = false;
        Train obj = new Train();
        int index = 0;

        System.out.println("====================================");
        System.out.println(" Update Train Information");
        System.out.println("====================================");
        do{
            System.out.print("Enter train no to search the train (Press # to exit) > "); 
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
            trainNo = Integer.parseInt(userInput);
            for (int i = 0; i < trainList.size(); i++) {
                if (trainNo==trainList.get(i).getTrainNo()) {
                    found = true;
                    index = i;
                 }
            }
            if (found == true){
                System.out.println("Do you want to update the train information as shown below ?");
                System.out.println(trainList.get(index).toString());
                do{
                    System.out.print("Enter your option (Y-Yes/N-No) > ");
                    userInput = scanner.nextLine();
                    if(userInput.equalsIgnoreCase("Y")){
                        System.out.println("The field that can be updated :");
                        System.out.println("1. Train Name");
                        System.out.println("Press # to exit");
                        do {
                            System.out.print("Enter option in number stated above > ");
                            userInput = scanner.nextLine();

                            if (userInput.equals("1")) {
                                System.out.print("Enter new train name > ");
                                trainName = scanner.nextLine();
                                trainList.get(index).changeTrainName(trainName);
                                updated = writeIntoFile("trainfile.txt", trainList, obj);
                            } else if (userInput.equals("#")){
                                break; // Exit the loop
                            }else {
                                System.out.println("Invalid option. Please enter (1/#).");
                            }
                                
                        } while (!userInput.equals("1") && !userInput.equals("#"));
                    }else if(userInput.equalsIgnoreCase("N")){
                        found = false;
                        break;
                    }else{
                        System.out.println("Invalid input. Please enter (Y/N).");
                        invalidInput = true;
                    }

                }while(invalidInput==true);
                invalidInput = false;
            }else{
                System.out.println("Train is not found. Please search again.");
            }

            if (updated){
                System.out.println("\nTrain information has updated.\n");
            }
                
            
        } while (!found);      
    }

    //--------------------------------------------ADD TRAIN INFORMATION------------------------------------------

    public static void addTrain(ArrayList<Train> trainList, Scanner scanner) throws FileNotFoundException {
        String trainName;
        String trainModel; 
        boolean added = false;
        Train obj = new Train();

        System.out.print("Enter train name > ");
        trainName = scanner.nextLine(); // Read the train name
    
        System.out.print("Enter train model > ");
        trainModel = scanner.nextLine(); // Read the train model
        trainList.add(new Train(trainName, trainModel));
        added = writeIntoFile("trainFile.txt", trainList, obj);
        if (added == true){
            System.out.println("\nTrain has added.\n");
        }
    }

    //------------------------------------------DELETE TRAIN INFORMATION-----------------------------------------

    public static void deleteTrain(ArrayList<Train> trainList, ArrayList<Schedule> scheduleList, Scanner scanner) throws Exception {
        int trainNo;
        boolean found = false;
        boolean deleted = false;
        boolean invalidInput = false;
        String userInput;
        int index = 0;
        Train obj = new Train();
        Schedule obj2 = new Schedule();

        System.out.println("====================================");
        System.out.println(" Delete Train Information");
        System.out.println("====================================");

        do{
            System.out.print("Enter train no to search the train (Press # to exit) > "); 
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
            trainNo = Integer.parseInt(userInput);
            for (int i = 0; i < trainList.size(); i++) {
                if (trainNo==trainList.get(i).getTrainNo()) {
                    found = true;
                    index = i;
                 }
            }
            if (found == true){
                System.out.println("Do you want to delete the train information as shown below ?");
                System.out.println(trainList.get(index).toString());
                do{
                    System.out.print("Enter your option (Y-Yes/N-No) > ");
                    userInput = scanner.nextLine();
                    if(userInput.equalsIgnoreCase("Y")){
                        if (trainList.get(index).getTrainStatus().equals("In service")){
                            System.out.println("Please think carefully as it will affect the schedule below.");
                            for (int j=0; j<scheduleList.size(); j++){
                                if (scheduleList.get(j).getOperatedTrain().getTrainNo()==trainList.get(index).getTrainNo()){
                                    System.out.println(scheduleList.get(j).toString());
                                    System.out.println();
                                }   
                            }
                            System.out.print("Do you confirm to delete the train?");
                            userInput = scanner.nextLine();
                            if(userInput.equalsIgnoreCase("Y")){
                                System.out.println("Do you want to remove all the schedule associated with this train or find other train to replace this train?");
                                System.out.println("1. Remove all the schedule associated");
                                System.out.println("2. Find other train to replace it");
                                System.out.println("Press # to exit");
                                System.out.print("Enter your option > ");
                                userInput = scanner.nextLine();
                                if (userInput.equals("1")){
                                    System.out.print("Confirm? (Y-Yes/N-No) > ");
                                    userInput = scanner.nextLine();
                                    if (userInput.equals("Y")){
                                        for (int j=0; j<scheduleList.size(); j++){
                                            if (scheduleList.get(j).getOperatedTrain().getTrainNo()==trainList.get(index).getTrainNo()){
                                                scheduleList.remove(j);
                                            }
                                            writeIntoFile("trainFile.txt", trainList, obj);  
                                            writeIntoFile("scheduleFile.txt",scheduleList, obj2);
                                        }
                                    }else if(userInput.equals("N")){
                                        continue;
                                    }else{
                                        System.out.println("Invalid input. Please enter (Y/N).");
                                    }
                                } else if(userInput.equals("2")){
                                     for (int j=0; j<scheduleList.size(); j++){
                                            if (scheduleList.get(j).getOperatedTrain().getTrainNo()==trainList.get(index).getTrainNo()){
                                                System.out.println(scheduleList.get(j).toString());
                                                System.out.println("Select a train for schedule above:");
                                                for (int k = 0; k < trainList.size(); k++) {
                                                    if (trainList.get(k).getTrainNo() != trainList.get(index).getTrainNo()){
                                                        System.out.println((k+1) + ". " + trainList.get(k).getTrainNo());
                                                    }else{
                                                        k--;
                                                    }    
                                                } 
                                                System.out.print("Enter the train number stated above > ");
                                                userInput = scanner.nextLine(); 
                                                int uIn = Integer.parseInt(userInput);
                                                Train trainOperated = trainList.get(uIn-1);
                                                scheduleList.get(j).editTrainOperated(trainOperated);
                                                writeIntoFile("trainFile.txt", trainList, obj);  
                                                writeIntoFile("scheduleFile.txt", scheduleList, obj2);  
                                            }
                                    }  
                                }else if(userInput.equals("#")){
                                    break;
                                }else{
                                    System.out.println("Invalid input. Please enter (1/2/#).");
                                }

                            }else if(userInput.equalsIgnoreCase("N")){
                                found = false;
                                break;
                            }else{
                                System.out.println("Invalid input. Please enter (Y/N).");
                                invalidInput = true;
                            }
                        }

                    }
                }while(invalidInput==true);
                invalidInput = false;
            }else{
                System.out.println("Train is not found. Please search again.");
            }

            if (deleted){
                System.out.println("\nTrain information has updated.\n");
            }    
        } while (!found);   
    }

    //===========================================================================================================
    //                                   TRAIN STATION INFORMATION MODIFICATION
    //===========================================================================================================

    public static void stationModification(ArrayList<TrainStation> stationList, ArrayList<Schedule> scheduleList, Scanner scanner) throws FileNotFoundException {
        String userInput = "";
        boolean cont = true;
    
        while (cont) {
            // Display the user option menu
            System.out.println("1. View existing train station information");
            System.out.println("2. Update train station information");
            System.out.println("3. Add a new train station information");
            System.out.println("4. Delete an existing train station information");
            System.out.println("#. Exit");
    
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.next();
               
                if (userInput.equals("1")) {
                    for (int i = 0; i < stationList.size(); i++) {
                        System.out.println(stationList.get(i).toString());
                        System.out.println();
                    }
                } else if (userInput.equals("2")) {
                    updateStationInfo(stationList, scheduleList, scanner);
                } else if (userInput.equals("3")) {
                    
                } else if (userInput.equals("4")) {
                    
                } else if (userInput.equals("#")) {
                    cont = false;
                    break; // Exit the loop
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    //-------------------------------------UPDATE TRAIN STATION INFORMATION---------------------------------------

    public static void updateStationInfo(ArrayList<TrainStation> stationList, ArrayList<Schedule> scheduleList, Scanner scanner){
        String userInput = "";
        String locationName;
        boolean found = false;
        boolean updated = false;
        boolean invalidInput = false;
        TrainStation obj = new TrainStation();
        int index = 0;
        int numOfPlatform;

        System.out.println("====================================");
        System.out.println(" Update Train Station Information");
        System.out.println("====================================");
        do{
            System.out.print("Enter station name to search the station (Press # to exit) > "); 
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
            for (int i = 0; i < stationList.size(); i++) {
                if (userInput==stationList.get(i).getLocationName()) {
                    found = true;
                    index = i;
                 }
            }
            if (found == true){
                System.out.println("Do you want to update the station information as shown below ?");
                System.out.println(stationList.get(index).toString());
                do{
                    System.out.print("Enter your option (Y-Yes/N-No) > ");
                    userInput = scanner.nextLine();
                    if(userInput.equalsIgnoreCase("Y")){
                        System.out.println("The field that can be updated :");
                        System.out.println("1. Station name");
                        System.out.println("2. Number of platform");
                        System.out.println("Press # to exit");
                        do {
                            System.out.print("Enter option in number stated above > ");
                            userInput = scanner.nextLine();

                            if (userInput.equals("1")) {
                                System.out.print("Enter new station name > ");
                                locationName = scanner.nextLine();
                                stationList.get(index).changeLocationName(locationName);
                                updated = writeIntoFile("stationfile.txt", stationList, obj);
                            }
                            else if (userInput.equals("2")) {
                                System.out.print("Enter new number of platform > ");
                                userInput = scanner.nextLine();
                                numOfPlatform = Integer.parseInt(userInput);
                                stationList.get(index).changeNumOfPlatform(numOfPlatform);
                                updated = writeIntoFile("stationfile.txt", stationList, obj);
                            } else if (userInput.equals("#")){
                                break; // Exit the loop
                            }else {
                                System.out.println("Invalid option. Please enter (1/#).");
                            }
                                
                        } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("#"));
                    }else if(userInput.equalsIgnoreCase("N")){
                        found = false;
                        break;
                    }else{
                        System.out.println("Invalid input. Please enter (Y/N).");
                        invalidInput = true;
                    }

                }while(invalidInput==true);
                invalidInput = false;
            }else{
                System.out.println("Station is not found. Please search again.");
            }

            if (updated){
                System.out.println("\nStation information has updated.\n");
            }
                
            
        } while (!found);      

    }

    //-----------------------------------------ADD TRAIN STATION INFORMATION--------------------------------------

    public static void addStation(ArrayList<TrainStation> stationList, Scanner scanner) throws FileNotFoundException {
        String locationName;
        int numOfPlatform; 
        boolean added = false;
        TrainStation obj = new TrainStation();
        String userInput;


        System.out.print("Enter station name > ");
        locationName = scanner.nextLine(); // Read the train name
    
        System.out.print("Enter number of platform provided > ");
        userInput = scanner.nextLine(); // Read the train model
        numOfPlatform = Integer.parseInt(userInput);
        stationList.add(new TrainStation(locationName, numOfPlatform));
        added = writeIntoFile("stationFile.txt", stationList, obj);
        if (added == true){
            System.out.println("\nStation has added.\n");
        }
    }

    //-----------------------------------------DELETE TRAIN STATION INFORMATION-------------------------------------- 

    public static void deleteStation(ArrayList<TrainStation> stationList, ArrayList<Schedule> scheduleList, Scanner scanner) throws FileNotFoundException {
        boolean found = false;
        boolean deleted = false;
        String userInput;
        int index = 0;
        TrainStation obj = new TrainStation();
        Schedule obj2 = new Schedule();
    
        System.out.println("====================================");
        System.out.println(" Delete Train Station Information");
        System.out.println("====================================");

        do {
            System.out.print("Enter station name to search the station (Press # to exit) > "); 
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
    
            for (int i = 0; i < stationList.size(); i++) {
                if (userInput.equals(stationList.get(i).getLocationName())) {
                    found = true;
                    index = i;
                    System.out.println("Do you want to delete the station information as shown below?");
                    System.out.println(stationList.get(i).toString());
                    System.out.print("Enter your option > ");
                    userInput = scanner.next();
    
                    if (userInput.equalsIgnoreCase("Y")) {
                        System.out.println("Please think carefully as it will remove the schedule below as well.");
                            for (int j=0; j<scheduleList.size(); j++){
                                if ((scheduleList.get(j).getDepartLocation().getLocationName()==stationList.get(index).getLocationName())||(scheduleList.get(j).getArriveLocation().getLocationName()==stationList.get(index).getLocationName())){
                                    System.out.println(scheduleList.get(j).toString());
                                    System.out.println();
                                }   
                            }
                            System.out.print("Do you confirm to delete the station?");
                            userInput = scanner.nextLine();
                            if(userInput.equalsIgnoreCase("Y")){
                                stationList.remove(i); // Remove the train from the list
                                deleted = writeIntoFile("stationFile.txt", stationList, obj);
                                for (int j=0; j<scheduleList.size(); j++){
                                    if ((scheduleList.get(j).getDepartLocation().getLocationName()==stationList.get(index).getLocationName())||(scheduleList.get(j).getArriveLocation().getLocationName()==stationList.get(index).getLocationName())){
                                        scheduleList.remove(j);
                                    }   
                                }
                                deleted = writeIntoFile("scheduleFile.txt", scheduleList, obj2);
                            }else if (userInput.equalsIgnoreCase("N")){
                                break;
                            }else{
                                System.out.println("Invalid input. Please enter (Y/N).");
                            }
                        
                    } else if(userInput.equalsIgnoreCase("N")) {
                        break;
                    }else{
                        System.out.println("Invalid input. Please enter (Y/N).");
                    }
                
                    break; // Exit the loop since we found and processed the train
                }
            }
            if (deleted == true) {
                System.out.println("Station has been removed.");
            }
            if (!found) {
                System.out.println("Station not found. Please search again.");
            }
        } while (!found);
    }

    //===========================================================================================================
    //                                      SCHEDULE INFORMATION MODIFICATION
    //===========================================================================================================

    public static void scheduleModification(ArrayList<Schedule> scheduleList, ArrayList<TrainStation> stationList, ArrayList<Train> trainList, Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;
    
        while (cont) {
            // Display the user option menu
            System.out.println("1. View schedule");
            System.out.println("2. Update existing schedule");
            System.out.println("3. Add a new schedule");
            System.out.println("4. Delete an existing schedule");
            System.out.println("#. Exit");
    
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.next();
               
                if (userInput.equals("1")) {
                    for (int i = 0; i < scheduleList.size(); i++) {
                        System.out.println(scheduleList.get(i).toString());
                        System.out.println();
                    }
                } else if (userInput.equals("2")) {
                    
                } else if (userInput.equals("3")) {
                    
                } else if (userInput.equals("4")) {
                    
                } else if (userInput.equals("#")) {
                    cont = false;
                    break; // Exit the loop
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    //-------------------------------------------ADD SCHEDULE INFORMATION---------------------------------------- 

    public static void addSchedule(ArrayList<Schedule> scheduleList, Scanner scanner, ArrayList<TrainStation> stationList, ArrayList<Train> trainList) throws Exception {
        TrainStation departLocation;
        TrainStation arriveLocation;
        LocalTime departTime = LocalTime.of(0,0);
        LocalTime arriveTime = LocalTime.of(0,0);
        Train trainOperated;
        double ticketPrice;
        String userInput = "";
        int index=0;
        Schedule obj = new Schedule();
        Train obj2 = new Train();

        boolean added = false;
        scanner.nextLine();
        System.out.println("Select a departure station : ");
        for (int i = 0; i < stationList.size(); i++) {
            System.out.println((i+1) + ". " + stationList.get(i).getLocationName());
        }
        System.out.print("Enter the station number stated above > ");
        userInput = scanner.nextLine(); 
        int uIn = Integer.parseInt(userInput);
        departLocation = stationList.get(uIn-1);

        System.out.println("Select an arrival station : ");
        for (int i = 0; i < stationList.size(); i++) {
            if (!stationList.get(i).equals(departLocation))
                System.out.println((i+1) + ". " + stationList.get(i).getLocationName());
            else{
                index = i;
                 i--;
            }
        }
        System.out.print("Enter the station number stated above > ");
        userInput = scanner.nextLine(); 
        uIn = Integer.parseInt(userInput);
        if ((uIn-1)>=index)
            --uIn;
        arriveLocation = stationList.get(uIn-1);

        System.out.print("Enter a departure time (HH:MM) > ");
        userInput = scanner.nextLine(); 
        try {
            departTime = LocalTime.parse(userInput);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid departure time format. Please use HH:MM.");
        }

        System.out.print("Enter an arrival time (HH:MM) > ");
        userInput = scanner.nextLine(); 
        try {
            arriveTime = LocalTime.parse(userInput);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid arrival time format. Please use HH:MM.");
        }
 
        System.out.println("Select a train for this schedule : ");
        for (int i = 0; i < trainList.size(); i++) {
                System.out.println((i+1) + ". " + trainList.get(i).getTrainNo());
        }
        System.out.print("Enter the train number stated above > ");
        userInput = scanner.nextLine(); 
        uIn = Integer.parseInt(userInput);
        trainOperated = trainList.get(uIn-1);

        System.out.print("Enter the ticket price (RM) > ");
        userInput = scanner.nextLine(); 
        ticketPrice = Double.valueOf(userInput) ;

        
        Schedule s = new Schedule(departLocation, arriveLocation, departTime, arriveTime, trainOperated, ticketPrice);
        scheduleList.add(s);
        added = writeIntoFile("scheduleFile.txt", scheduleList, obj);
        
        if (added == true){
            System.out.println("Schedule has added.");
            for(int k=0; k < trainList.size(); k++){
                if(trainOperated.getTrainNo()==trainList.get(k).getTrainNo()){
                    trainList.get(k).changeTrainStatus("In service");
                }
            }
            added = writeIntoFile("trainFile.txt", trainList, obj2);
        }
        s = null;    
    }

    //-------------------------------------------DELETE SCHEDULE INFORMATION---------------------------------------- 

    public static void deleteSchedule(ArrayList<Schedule> scheduleList, Scanner scanner) throws Exception {
        boolean found = false;
        String userInput;
        boolean deleted = false;
        Schedule obj = new Schedule();
        
        do {
            System.out.print("Enter the schedule id to search the schedule (Press # to exit) > ");
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
    
            for (int i = 0; i < scheduleList.size(); i++) {
                if (userInput.equals(scheduleList.get(i).getScheduleId())) {
                    found = true;
                    System.out.println("Do you want to delete the schedule information as shown below? ");
                    System.out.println(scheduleList.get(i).toString());
                    System.out.print("Enter your option (Y-Yes/N-No)> ");
                    userInput = scanner.next();
    
                    if (userInput.equalsIgnoreCase("Y")) {
                        scheduleList.remove(i); // Remove the train from the list
                        deleted = writeIntoFile("scheduleFile.txt",scheduleList, obj);
                    } else {
                        System.out.println("Deletion canceled.");
                    }
                
                    break; // Exit the loop since we found and processed the train
                }
            }
            if (deleted == true) {
                System.out.println("Schedule has been removed.");
            }
            if (!found) {
                System.out.println("Schedule not found. Please search again.");
            }
            deleted = false;
        } while (!found);
    
    }

    //-------------------------------------------UPDATE SCHEDULE INFORMATION---------------------------------------- 

    public static void updateScheduleInfo(ArrayList<Schedule> scheduleList, ArrayList<TrainStation> stationList, ArrayList<Train> trainList, Scanner scanner) throws FileNotFoundException {
        String scheduleId;
        TrainStation departLocation;
        TrainStation arriveLocation;
        LocalTime departTime = LocalTime.of(0,0);
        LocalTime arriveTime = LocalTime.of(0,0);
        Train trainOperated;
        double ticketPrice;
        String userInput = "";
        boolean found = false;
        boolean updated = false;
        String userInput2;
        String userInput3;
        Schedule obj = new Schedule();
    
        do {
            System.out.print("Enter the schedule id to search the schedule (Press # to exit) > ");
            scheduleId = scanner.nextLine();
            if (scheduleId.equals("#"))
                break;
    
            for (int i = 0; i < scheduleList.size(); i++) {
                if (scheduleId.equals(scheduleList.get(i).getScheduleId())) {
                    found = true;
                    System.out.println(scheduleList.get(i).toString());
                    System.out.println("The field that can be updated :");
                    System.out.println("1. Departure information");
                    System.out.println("2. Arrival information");
                    System.out.println("3. Train operated information");
                    System.out.println("4. Ticket price");
                    System.out.println("#. Back");
    
                    do {
                        System.out.print("Enter option in number stated above > ");
                        userInput = scanner.nextLine();
    
                        if (userInput.equals("1")) {
                            System.out.println("Select a field to update :");
                            System.out.println("1. Departure location");
                            System.out.println("2. Departure time");
                            System.out.println("#. Back");
                            do{
                                System.out.print("Enter option > ");
                                userInput2 = scanner.nextLine(); 
                                if (userInput2.equals("1")){
                                    System.out.println("Current departure location: " + scheduleList.get(i).getDepartLocation().getLocationName());
                                    ArrayList<TrainStation> temp = new ArrayList<>(stationList);
                                    temp.remove(scheduleList.get(i).getDepartLocation());
        
                                    System.out.println("Select a new departure station : ");
                                    for (int j = 0; j < temp.size(); j++) {
                                        System.out.println((j + 1) + ". " + temp.get(j).getLocationName());
                                    }
                                    System.out.print("Enter the station number stated above > ");
                                    userInput3 = scanner.nextLine();
                                    int uIn = Integer.parseInt(userInput3);
                                    departLocation = temp.get(uIn - 1);
                                    scheduleList.get(i).editDepartLocation(departLocation);
                                } else if(userInput2.equals("2")){
                                    System.out.println("Departure time: " + scheduleList.get(i).getDepartTime());
                                    System.out.print("Enter a new departure time : ");
                                    userInput3 = scanner.nextLine(); 
                                    try {
                                        departTime = LocalTime.parse(userInput3);
                                    } catch (DateTimeParseException e) {
                                        System.err.println("Invalid departure time format. Please use HH:MM.");
                                    }
                                    scheduleList.get(i).editDepartTime(departTime);
                                } else if(userInput2.equals("#")){
                                    break;
                                } else {
                                    System.out.println("Invalid input. please enter the option as stated above.");
                                }
    
                            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("#"));
    
                        } else if (userInput.equals("2")) {
                            System.out.println("Select a field to update :");
                            System.out.println("1. Arrival location");
                            System.out.println("2. Arrival time");
                            System.out.println("#. Back");
                            do{
                                System.out.print("Enter option > ");
                                userInput2 = scanner.nextLine(); 
                                if (userInput2.equals("1")){
                                    System.out.println("Arrival location: " + scheduleList.get(i).getArriveLocation().getLocationName());
                                    System.out.println("Select a new arrival station : ");
                                    ArrayList<TrainStation> availableArrivalLocations = new ArrayList<>(stationList);
                                    availableArrivalLocations.remove(scheduleList.get(i).getArriveLocation());
            
                                    for (int j = 0; j < availableArrivalLocations.size(); j++) {
                                        System.out.println((j + 1) + ". " + availableArrivalLocations.get(j).getLocationName());
                                    }
                                    System.out.print("Enter the station number stated above > ");
                                    userInput3 = scanner.nextLine();
                                    int uIn = Integer.parseInt(userInput3);
                                    if (uIn > 0 && uIn <= availableArrivalLocations.size()) {
                                        arriveLocation = availableArrivalLocations.get(uIn - 1);
                                        scheduleList.get(i).editArriveLocation(arriveLocation);
                                    } else {
                                        System.out.println("Invalid station number. Please select a different arrival location.");
                                    }
                                } else if(userInput2.equals("2")){
                                    System.out.println("Arrival time: " + scheduleList.get(i).getArriveTime());
                                    System.out.print("Enter a new arrival time : ");
                                    userInput3 = scanner.nextLine(); 
                                    try {
                                        arriveTime = LocalTime.parse(userInput3);
                                    } catch (DateTimeParseException e) {
                                        System.err.println("Invalid arrival time format. Please use HH:MM.");
                                    }
                                    scheduleList.get(i).editArriveTime(arriveTime);
                                } else if(userInput2.equals("#")){
                                    break;
                                } else {
                                    System.out.println("Invalid input. please enter the option as stated above.");
                                }
    
                            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("#"));
    
                        } else if (userInput.equals("3")) {
                            System.out.println("Train operated: " + scheduleList.get(i).getOperatedTrain().toString());
                            System.out.println("Select a new train to replace train above: ");
                            
                            ArrayList<Train> availableTrains = new ArrayList<>(trainList);
                            availableTrains.remove(scheduleList.get(i).getOperatedTrain());
                        
                            for (int j = 0; j < availableTrains.size(); j++) {
                                System.out.println((j + 1) + ". " + availableTrains.get(j).getTrainNo());
                            }
                            
                            System.out.print("Enter the train number stated above > ");
                            userInput = scanner.nextLine();
                            int uIn2 = Integer.parseInt(userInput);
                            
                            if (uIn2 > 0 && uIn2 <= availableTrains.size()) {
                                trainOperated = availableTrains.get(uIn2 - 1);
                                scheduleList.get(i).editTrainOperated(trainOperated);
                            } else {
                                System.out.println("Invalid train number. Please select a different train.");
                            }
                        } else if (userInput.equals("4")) {
                            System.out.println("Ticket price (RM): " + scheduleList.get(i).getTicketPrice());
                            System.out.print("Enter a new ticket price : ");
                            userInput2 = scanner.nextLine(); 
                            ticketPrice = Double.valueOf(userInput2) ;
                            scheduleList.get(i).editTicketPrice(ticketPrice);
                        } else if (userInput.equals("#")) {
                            break;
                        } else {
                            System.out.println("Invalid input. please enter the option as stated above.");
                        }
                    } while ( !userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
                }
            }
    
            if (!found) {
                System.out.println("Schedule not found. Please search again.");
            }
            updated = writeIntoFile("scheduleFile.txt", scheduleList, obj);
            if (updated){
                System.out.println("Schedule information has updated.");
            } else {
                System.out.println("Update failed.");
            }
            updated = false;
        } while (!found);  
    }


    //===========================================================================================================
    //                                     METHOD TO READ DATA FROM FILE 
    //===========================================================================================================

    public static void readFromFile(String filename, ArrayList<Train> trainList, Train obj) throws Exception {
        File file = new File(filename);
        ObjectInputStream input = null; // Declare outside the try block
    
        try {
            input = new ObjectInputStream(new FileInputStream(file));
            while (true) {
                try {
                    obj = (Train) input.readObject();
                    trainList.add(obj);
                } catch (EOFException eofe) {
                    break;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }     
    }

    public static void readFromFile(String filename, ArrayList<TrainStation> stationList, TrainStation obj) throws Exception {
        File file = new File(filename);
        ObjectInputStream input = null; // Declare outside the try block
    
        try {
            input = new ObjectInputStream(new FileInputStream(file));
            while (true) {
                try {
                    obj = (TrainStation) input.readObject();
                    stationList.add(obj);
                } catch (EOFException eofe) {
                    break;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }     
    }

    public static void readFromFile(String filename, ArrayList<Schedule> scheduleList, Schedule obj) throws Exception {
        File file = new File(filename);
        ObjectInputStream input = null; // Declare outside the try block
    
        try {
            input = new ObjectInputStream(new FileInputStream(file));
            while (true) {
                try {
                    obj = (Schedule) input.readObject();
                    scheduleList.add(obj);
                } catch (EOFException eofe) {
                    break;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }     
    }

    public static void readFromFile(String filename, ArrayList<Snacks> snacksList, Snacks obj) throws Exception {
        File file = new File(filename);
        ObjectInputStream input = null; // Declare outside the try block
    
        try {
            input = new ObjectInputStream(new FileInputStream(file));
            while (true) {
                try {
                    obj = (Snacks) input.readObject();
                    snacksList.add(obj);
                } catch (EOFException eofe) {
                    break;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }     
    }

    public static void readFromFile(String filename, ArrayList<Drinks> drinksList, Drinks obj) throws Exception {
        File file = new File(filename);
        ObjectInputStream input = null; // Declare outside the try block
    
        try {
            input = new ObjectInputStream(new FileInputStream(file));
            while (true) {
                try {
                    obj = (Drinks) input.readObject();
                    drinksList.add(obj);
                } catch (EOFException eofe) {
                    break;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }     
    }
    
    //===========================================================================================================
    //                                     METHOD TO WRITE DATA INTO FILE 
    //===========================================================================================================

    public static boolean writeIntoFile(String filename, ArrayList<Train> trainList, Train obj) {
        boolean write = false;
        File file = new File(filename);
        ObjectOutputStream oos = null;
    
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file, false));
            for (int i = 0; i < trainList.size(); i++) {
                oos.writeObject(trainList.get(i));
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

    public static boolean writeIntoFile(String filename, ArrayList<TrainStation> stationList, TrainStation obj) {
        boolean write = false;
        File file = new File(filename);
        ObjectOutputStream oos = null;
    
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file, false));
            for (int i = 0; i < stationList.size(); i++) {
                oos.writeObject(stationList.get(i));
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

    public static boolean writeIntoFile(String filename, ArrayList<Schedule> scheduleList, Schedule obj) {
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
    
     public static boolean writeIntoFile(String filename, ArrayList<Snacks> snacksList, Snacks obj) {
        boolean write = false;
        File file = new File(filename);
        ObjectOutputStream oos = null;
    
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file, false));
            for (int i = 0; i < snacksList.size(); i++) {
                oos.writeObject(snacksList.get(i));
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

    public static boolean writeIntoFile(String filename, ArrayList<Drinks> drinksList, Drinks obj) {
        boolean write = false;
        File file = new File(filename);
        ObjectOutputStream oos = null;
    
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file, false));
            for (int i = 0; i < drinksList.size(); i++) {
                oos.writeObject(drinksList.get(i));
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


 

}