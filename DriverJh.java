import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DriverJh {
    private static final String TIME_REGEX = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
    private static final Pattern TIME_PATTERN = Pattern.compile(TIME_REGEX);
    //===========================================================================================================
    //                                              MAIN PROGRAM 
    //===========================================================================================================
    public static void main(String[] args) throws Exception{

        ArrayList<Train> trainList = new ArrayList<Train>();
        ArrayList<TrainStation> stationList = new ArrayList<TrainStation>();
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
        ArrayList<Snacks> snacksList = new ArrayList<Snacks>();
        ArrayList<Drinks> drinksList = new ArrayList<Drinks>();

        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        boolean cont = true;
        // declare dummy variable
        Train train = new Train();
        TrainStation station = new TrainStation();
        Schedule schedule = new Schedule();
        Snacks snacks = new Snacks();
        Drinks drinks = new Drinks();

        // Read data from each file
        readFromFile("trainFile.txt", trainList, train);
        readFromFile("stationFile.txt", stationList, station);
        readFromFile("scheduleFile.txt", scheduleList, schedule);
        readFromFile("snacksFile.txt", snacksList, snacks);
        readFromFile("drinksFile.txt", drinksList, drinks);
        
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
                    trainModification(trainList, stationList, scheduleList, scanner);
                } else if (userInput.equals("2")) {
                    scheduleModification(trainList, stationList, scheduleList, scanner);
                } else if (userInput.equals("3")) {
                    stationModification(trainList, stationList, scheduleList, scanner);
                } else if (userInput.equals("4")) {
                    foodAndBeverageModification(snacksList, drinksList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
            }while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
 
        }while(cont==true);
    }

    //===========================================================================================================
    //                                COMMON SHARED METHODS (FOR VALIDATION USE)
    //===========================================================================================================

    //------------------------------------------VALIDATE DOUBLE INPUT-------------------------------------------- 

    private static double validateDoubleInput(Scanner scanner, String str) {
        while (true) {
            try {
                String userInput = scanner.nextLine();
                return Double.parseDouble(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid price: ");
                System.out.print(str);
            }
        }
    }
    
    //-----------------------------------------VALIDATE INTEGER INPUT-------------------------------------------- 

    private static int validateIntegerInput(Scanner scanner, String str) {
        while (true) {
            try {
                String userInput = scanner.nextLine();
                if(userInput.equals("#")){
                    return -1;
                }
                return Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                System.out.print(str);
            }
        }
    }

    //------------------------------------------VALIDATE Y/N INPUT---------------------------------------------- 

    private static String validateYNInput(Scanner scanner, String str) {
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("Y")||userInput.equalsIgnoreCase("N")){
                return userInput;
            }else{
                System.out.println("Invalid input. Please enter Y/N only.");
                System.out.print(str);
            }
        }
    }

    //------------------------------------------VALIDATE TIME INPUT---------------------------------------------- 

    public static boolean isValidTimeFormat(String time) {
        Matcher matcher = TIME_PATTERN.matcher(time);
        return matcher.matches();
    }

    public static LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            // Handle the exception if the time format is invalid
            return null;
        }
    }

    //===========================================================================================================
    //                                      TRAIN INFORMATION MODIFICATION
    //===========================================================================================================
    
    public static void trainModification(ArrayList<Train> trainList, ArrayList<TrainStation> stationList, ArrayList<Schedule> scheduleList, Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;

        while(cont==true){
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
                    updateTrainInfo(trainList, scheduleList, scanner);
                } else if (userInput.equals("3")) {
                    addTrain(trainList, scanner);
                } else if (userInput.equals("4")) {
                    deleteTrain(trainList, scheduleList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }     
            }while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    //-----------------------------------------------ADD TRAIN--------------------------------------------------- 

    public static void addTrain(ArrayList<Train> trainList, Scanner scanner) throws FileNotFoundException {
        String trainName;
        String trainModel; 
        String userInput;
        boolean added = false;
        Train obj = new Train();

        System.out.print("Enter train name > ");
        trainName = scanner.nextLine(); 
    
        System.out.print("Enter train model > ");
        trainModel = scanner.nextLine(); 

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        userInput = validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(userInput.equalsIgnoreCase("Y")){
            trainList.add(new Train(trainName, trainModel));
            added = writeIntoFile("trainFile.txt", trainList, obj);
            if (added == true){
                System.out.println("\nTrain has added.\n");
            }else{
                System.out.println("\nUnable to add the train.\n");
            }
        }else{
            System.out.println("\nModification cancelled.\n");
        }
        
    }

    //----------------------------------------------UPDATE TRAIN-------------------------------------------------

    public static void updateTrainInfo(ArrayList<Train> trainList, ArrayList<Schedule> scheduleList, Scanner scanner) throws Exception {
        String userInput;
        String userInput2;
        String trainName;
        int trainNo;
        int index = 0;
        boolean cont = true;
        boolean found = false;
        boolean updated = false;
        boolean updated2 = false;
        Train obj = new Train();
        Schedule obj2 = new Schedule();

        System.out.println("====================================");
        System.out.println(" Update Train Information");
        System.out.println("====================================");
        do{
            System.out.print("Enter train no to search the train > "); 
            trainNo = validateIntegerInput(scanner, "Enter train no to search the train (Press # to exit) > ");

            for (int i = 0; i < trainList.size(); i++) {
                if (trainNo==trainList.get(i).getTrainNo()) {
                    found = true;
                    index = i;
                 }
            }
            if (found == true){
                System.out.println("Do you want to update the train information as shown below ?");
                System.out.println(trainList.get(index).toString());
                
                System.out.print("Enter your option (Y-Yes/N-No) > ");
                userInput = validateYNInput(scanner, "Enter your option (Y-Yes/N-No) > ");
                do{
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
                                System.out.print("Do you confirm with the changes? (Y-Yes/N-No) > ");
                                userInput2 = validateYNInput(scanner, "Do you confirm with the changes? (Y-Yes/N-No) > ");
                                if (userInput2.equalsIgnoreCase("Y")){
                                    for (Schedule schedule : scheduleList) {
                                        if (schedule.getOperatedTrain().getTrainNo()==(trainList.get(index).getTrainNo())) {
                                            schedule.getOperatedTrain().changeTrainName(trainName);
                                        }
                                    }
                                    trainList.get(index).changeTrainName(trainName);
                                    updated = writeIntoFile("trainfile.txt", trainList, obj);
                                    updated2 = writeIntoFile("scheduleFile.txt", scheduleList, obj2);
                                    if (updated && updated2){
                                        System.out.println("\nTrain information has updated.\n");
                                    }else{
                                        System.out.println("\nUnable to update the train information.\n");
                                    }
                                }else{
                                    System.out.println("\nModification cancelled.\n");
                                }     
                            } else if (userInput.equals("#")){
                                cont = false;
                                return; 
                            }else {
                                System.out.println("Invalid option. Please enter (1/#).");
                            }            
                        } while (!userInput.equals("1") && !userInput.equals("#"));
                
                    }else {
                        found = false;
                    }
                    System.out.print("Do you want to continue make changes? (Y-Yes/N-No) > ");
                    userInput = validateYNInput(scanner, "Do you want to continue make changes? (Y-Yes/N-No) > ");
                    if (userInput.equalsIgnoreCase("Y")){
                        cont = true;
                    }else{
                        cont = false;
                    }
                }while(cont==true);
            }else{
                System.out.println("Train is not found. Please search again.");
            }  
        } while (!found);      
    }
    
    //----------------------------------------------DELETE TRAIN-------------------------------------------------

    public static void deleteTrain(ArrayList<Train> trainList, ArrayList<Schedule> scheduleList, Scanner scanner) throws Exception {
        int trainNo;
        boolean found = false;
        boolean deleted = false;
        boolean deleted2 = false;
        boolean hasSchedule = false;
        String userInput;
        int index = 0;
        Train obj = new Train();
        Schedule obj2 = new Schedule();
    
        System.out.println("====================================");
        System.out.println(" Delete Train Information");
        System.out.println("====================================");
    
        do {
            System.out.print("Enter train no to search the train (Press # to exit) > ");
            trainNo = validateIntegerInput(scanner, "Enter train no to search the train (Press # to exit) > ");

            if (trainNo==-1)
                break;
    
            // Find the train by train number
            for (int i = 0; i < trainList.size(); i++) {
                if (trainNo == trainList.get(i).getTrainNo()) {
                    found = true;
                    index = i;
                    break; // No need to continue searching
                }
            }
    
            if (found) {
                System.out.println("Do you want to delete the train information as shown below ?");
                System.out.println(trainList.get(index).toString());
                System.out.print("Enter your option (Y-Yes/N-No) > ");
                userInput = validateYNInput(scanner, "Enter your option (Y-Yes/N-No) > ");
    
                if (userInput.equalsIgnoreCase("Y")) {
                    for (int j=0; j<scheduleList.size(); j++){
                        if (scheduleList.get(j).getOperatedTrain().getTrainNo()==trainList.get(index).getTrainNo()){
                            hasSchedule = true;
                            break;
                        }   
                    }
                    if(hasSchedule){
                        System.out.println("Please think carefully as it will remove the schedule below as well.");
                        for (int j=0; j<scheduleList.size(); j++){
                            if (scheduleList.get(j).getOperatedTrain().getTrainNo()==trainList.get(index).getTrainNo()){
                                System.out.println(scheduleList.get(j).toString());
                                System.out.println();
                            }   
                        }
                    }        
    
                    System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                    userInput = validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
    
                    if (userInput.equalsIgnoreCase("Y")) {
                            
                        // Remove all associated schedules
                        for (int j = scheduleList.size() - 1; j >= 0; j--) {
                            if (scheduleList.get(j).getOperatedTrain().getTrainNo() == trainList.get(index).getTrainNo()) {
                                scheduleList.remove(j);
                            }
                        }
                        trainList.remove(index);
                        deleted = writeIntoFile("trainFile.txt", trainList, obj);
                        deleted2 = writeIntoFile("scheduleFile.txt", scheduleList, obj2);

                        if (deleted && deleted2) {
                            System.out.println("\nTrain information has removed.\n");
                        }else{
                            System.out.println("\nUnable to remove train information.\n");
                        }
                                
                    } else {
                        System.out.println("Modification cancelled.");
                    }
                               
                                
                } else {
                    found = false;
                }
                
            } else {
                System.out.println("Train is not found. Please search again.");
            }
    
           
        } while (!found);
    }
    
    //===========================================================================================================
    //                                   TRAIN STATION INFORMATION MODIFICATION
    //===========================================================================================================

    public static void stationModification(ArrayList<Train> trainList, ArrayList<TrainStation> stationList, ArrayList<Schedule> scheduleList, Scanner scanner) throws FileNotFoundException {
        String userInput = "";
        boolean cont = true;
    
        while (cont) {
            // Display the user option menu
            System.out.println("1. View existing train station information");
            System.out.println("2. Update train station information");
            System.out.println("3. Add a new train station information");
            System.out.println("4. Delete an existing train station information");
            System.out.println("* Press # to go back");
    
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
               
                if (userInput.equals("1")) {
                    for (int i = 0; i < stationList.size(); i++) {
                        System.out.println(stationList.get(i).toString());
                        System.out.println();
                    }
                } else if (userInput.equals("2")) {
                    updateStationInfo(stationList, scheduleList, scanner);
                } else if (userInput.equals("3")) {
                    addStation(stationList, scanner);
                } else if (userInput.equals("4")) {
                    deleteStation(trainList, stationList, scheduleList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    //--------------------------------------------ADD TRAIN STATION---------------------------------------------- 

    public static void addStation(ArrayList<TrainStation> stationList, Scanner scanner) throws FileNotFoundException {
        String locationName;
        int numOfPlatform; 
        boolean added = false;
        boolean duplicated = false;
        TrainStation obj = new TrainStation();
        String userInput;

        do {
            System.out.print("Enter new station name > ");
            locationName = scanner.nextLine(); // Read the station name
            duplicated = false; // Reset duplicated to false before checking the list
        
            for (int i = 0; i < stationList.size(); i++) {
                if (locationName.equalsIgnoreCase(stationList.get(i).getLocationName())) {
                    duplicated = true;
                    System.out.println("The station name exists. Please use another name.");
                    break; // Exit the loop as soon as a duplicate is found
                }
            }
        } while (duplicated);
        
        System.out.print("Enter number of platform provided > ");
        numOfPlatform = validateIntegerInput(scanner, "Enter number of platform provided > ");

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        userInput = validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(userInput.equalsIgnoreCase("Y")){
            stationList.add(new TrainStation(locationName, numOfPlatform));
            added = writeIntoFile("stationFile.txt", stationList, obj);
            if (added == true){
                System.out.println("\nStation has added.\n");
            }else{
                System.out.println("\nUnable to add the station.\n");
            }
        }else{
            System.out.println("\nModification cancelled.\n");
        }    
        
    }

    //-------------------------------------------UPDATE TRAIN STATION--------------------------------------------

    public static void updateStationInfo(ArrayList<TrainStation> stationList, ArrayList<Schedule> scheduleList, Scanner scanner) {
        String userInput;
        String userInput2;
        String locationName;
        boolean found = false;
        boolean duplicated = false;
        boolean updated = false;
        boolean updated2 = false;
        boolean cont = true;
        int index = 0;
        int numOfPlatform;
        TrainStation obj = new TrainStation();
        Schedule obj2 = new Schedule();
    
        System.out.println("====================================");
        System.out.println(" Update Train Station Information");
        System.out.println("====================================");
    
        do {
            System.out.print("Enter station name to search the station (Press # to exit) > ");
            userInput = scanner.nextLine();
    
            if (userInput.equals("#")) {
                break;
            }
    
            for (int i = 0; i < stationList.size(); i++) {
                if (userInput.equalsIgnoreCase(stationList.get(i).getLocationName())) {
                    found = true;
                    index = i;
                }
            }
            if (found==true){
                System.out.println("Do you want to update the station information as shown below ?");
                System.out.println(stationList.get(index).toString());
    
                System.out.print("Enter your option (Y-Yes/N-No) > ");
                userInput = validateYNInput(scanner, "Enter your option (Y-Yes/N-No) > ");
                do{  
                    if (userInput.equalsIgnoreCase("Y")) {
                        System.out.println("The field that can be updated :");
                        System.out.println("1. Station name");
                        System.out.println("2. Number of platform");
                        System.out.println("Press # to exit");
    
                        do {
                            System.out.print("Enter option in number stated above > ");
                            userInput = scanner.nextLine();
    
                            if (userInput.equals("1")) {
                                do {
                                    System.out.print("Enter new station name > ");
                                    locationName = scanner.nextLine(); // Read the station name
                                    duplicated = false; // Reset duplicated to false before checking the list
                                
                                    for (int i = 0; i < stationList.size(); i++) {
                                        if (locationName.equalsIgnoreCase(stationList.get(i).getLocationName())) {
                                            duplicated = true;
                                            System.out.println("The station name exists. Please use another name.");
                                            break; // Exit the loop as soon as a duplicate is found
                                        }
                                    }
                                } while (duplicated);
                                System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
                                userInput2 = validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");
                                if (userInput2.equalsIgnoreCase("Y")){
                                    for (Schedule schedule : scheduleList) {
                                        if (schedule.getDepartLocation().getLocationName().equals(stationList.get(index).getLocationName())) {
                                            schedule.getDepartLocation().changeLocationName(locationName);
                                        }
                                        if(schedule.getArriveLocation().getLocationName().equals(stationList.get(index).getLocationName())){
                                            schedule.getArriveLocation().changeLocationName(locationName);
                                        }
                                    }
                                    stationList.get(index).changeLocationName(locationName);
                                    updated = writeIntoFile("stationFile.txt", stationList, obj);
                                    updated2 = writeIntoFile("scheduleFile.txt", scheduleList, obj2);
                                    if(updated && updated2){
                                        System.out.println("\nThe station has updated.\n");
                                    }else{
                                        System.out.println("\nUnable to update the station.\n");
                                    }
                                }else{
                                    System.out.println("\nModification cancelled.\n");
                                }
                            } else if (userInput.equals("2")) {
                                System.out.print("Enter new number of platform > ");
                                numOfPlatform = validateIntegerInput(scanner, "Enter new number of platform > ");
                                System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
                                userInput2 = validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");
                                if (userInput2.equalsIgnoreCase("Y")){
                                    for (Schedule schedule : scheduleList) {
                                        if (schedule.getDepartLocation().getLocationName().equals(stationList.get(index).getLocationName())) {
                                            schedule.getDepartLocation().changeNumOfPlatform(numOfPlatform);
                                        }
                                        if(schedule.getArriveLocation().getLocationName().equals(stationList.get(index).getLocationName())){
                                            schedule.getArriveLocation().changeNumOfPlatform(numOfPlatform);
                                        }
                                    }
                                    stationList.get(index).changeNumOfPlatform(numOfPlatform);
                                    updated = writeIntoFile("stationfile.txt", stationList, obj);
                                    updated2 = writeIntoFile("scheduleFile.txt", scheduleList, obj2);
                                    if(updated && updated2){
                                        System.out.println("\nThe station has updated.\n");
                                    }else{
                                        System.out.println("\nUnable to update the station.\n");
                                    }
                                }else{
                                    System.out.println("\nModification cancelled.\n");
                                }
                            } else if (userInput.equals("#")) {
                                cont = false;
                                return;
                            } else {
                                System.out.println("Invalid option. Please enter (1/2/#).");
                            }
                        } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("#"));
                    } else {
                        found = false;
                    }
                    System.out.print("Do you want to continue make changes? (Y-Yes/N-No) > ");
                    userInput = validateYNInput(scanner, "Do you want to continue make changes? (Y-Yes/N-No) > ");
                    if (userInput.equalsIgnoreCase("Y")){
                        cont = true;
                    }else{
                        cont = false;
                    }
                } while(cont == true);
            
            }else{
                System.out.println("Station is not found. Please search again.");        
            }
        } while (!found);
    }

    //-------------------------------------------DELETE TRAIN STATION--------------------------------------------

    public static void deleteStation(ArrayList<Train> trainList, ArrayList<TrainStation> stationList, ArrayList<Schedule> scheduleList, Scanner scanner) throws FileNotFoundException {
        boolean found = false;
        boolean deleted = false;
        boolean deleted2 = false;
        String userInput;
        int index = 0;
        TrainStation obj = new TrainStation();
        Schedule obj2 = new Schedule();
        boolean hasSchedule = false;
    
        System.out.println("====================================");
        System.out.println(" Delete Train Station Information");
        System.out.println("====================================");

        do {
            System.out.print("Enter station name to search the station (Press # to exit) > "); 
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
    
            for (int i = 0; i < stationList.size(); i++) {
                if (userInput.equalsIgnoreCase(stationList.get(i).getLocationName())) {
                    found = true;
                    index = i;
                }
            }
            if(found==true){
                System.out.println("Do you want to delete the station information as shown below?");
                System.out.println(stationList.get(index).toString());
                System.out.print("Enter your option (Y-Yes/N-No) > ");
                userInput = validateYNInput(scanner, "Enter your option (Y-Yes/N-No) > ");
                
                if (userInput.equalsIgnoreCase("Y")) {
                    hasSchedule = false; // Initialize the flag
                    for (int j = 0; j < scheduleList.size(); j++) {
                        if (scheduleList.get(j).getDepartLocation().getLocationName().equals(stationList.get(index).getLocationName()) ||
                            scheduleList.get(j).getArriveLocation().getLocationName().equals(stationList.get(index).getLocationName())) {
                            hasSchedule = true;
                            break; // Exit the loop early if a schedule is found
                        }
                    }
                    
                    if (hasSchedule) {
                        System.out.println("Please think carefully as it will remove the schedule below as well.");
                        for (int j = 0; j < scheduleList.size(); j++) {
                            if (scheduleList.get(j).getDepartLocation().getLocationName().equals(stationList.get(index).getLocationName()) ||
                                scheduleList.get(j).getArriveLocation().getLocationName().equals(stationList.get(index).getLocationName())) {
                                System.out.println(scheduleList.get(j).toString());
                                System.out.println();
                            }
                        }
                    }
                
                    
                    System.out.print("Do you confirm to delete the station (Y-Yes/N-No)? ");
                    userInput = validateYNInput(scanner,"Do you confirm to delete the station (Y-Yes/N-No) ?");
                    if(userInput.equalsIgnoreCase("Y")){
                        for (int j = 0; j < scheduleList.size(); j++) {
                            if (scheduleList.get(j).getDepartLocation().getLocationName().equals(stationList.get(index).getLocationName()) ||
                                scheduleList.get(j).getArriveLocation().getLocationName().equals(stationList.get(index).getLocationName())) {
                                scheduleList.remove(j);
                            }
                        }
                        stationList.remove(index); 
                        deleted = writeIntoFile("stationFile.txt", stationList, obj);
                        deleted2 = writeIntoFile("scheduleFile.txt", scheduleList, obj2);
                            
                        if (deleted && deleted2){
                            System.out.println("The train station has deleted.");
                        }else{
                            System.out.println("Unable to delete the train station.");
                        }
  
                    }else {
                        System.out.println("Modification cancelled.");
                    }
                } else  {
                    found = false;
                }
            }else{
                System.out.println("Train station is not found. Please search again.");
            }        
        } while (!found);
    }

    //===========================================================================================================
    //                                      SCHEDULE INFORMATION MODIFICATION
    //===========================================================================================================

    public static void scheduleModification(ArrayList<Train> trainList, ArrayList<TrainStation> stationList, ArrayList<Schedule> scheduleList, Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;
    
        while (cont) {
            System.out.println("1. View schedule");
            System.out.println("2. Update existing schedule");
            System.out.println("3. Add a new schedule");
            System.out.println("4. Delete an existing schedule");
            System.out.println("* Press # to go back");
    
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
               
                if (userInput.equals("1")) {
                    for (int i = 0; i < scheduleList.size(); i++) {
                        System.out.println(scheduleList.get(i).toString());
                        System.out.println();
                    }
                } else if (userInput.equals("2")) {
                    updateScheduleInfo(scheduleList, stationList, trainList, scanner);
                } else if (userInput.equals("3")) {
                    addSchedule(scheduleList, scanner, stationList, trainList);
                } else if (userInput.equals("4")) {
                    deleteSchedule(scheduleList, stationList, trainList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    //-----------------------------------------------ADD SCHEDULE------------------------------------------------ 

    public static void addSchedule(ArrayList<Schedule> scheduleList, Scanner scanner, ArrayList<TrainStation> stationList, ArrayList<Train> trainList) throws Exception {
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
        Schedule obj = new Schedule();
        boolean added = false;
        boolean correctTime = false;

        if (stationList.isEmpty() && trainList.isEmpty()){
            System.out.println("No train stations and train available. Please add some train stations and train.");
            return;
        }else if(stationList.size()==1 && trainList.isEmpty()){
            System.out.println("There is no train available and not enough train stations to create a schedule. Please add a train station and a train.");
            return;
        }else if (stationList.isEmpty()){
            System.out.println("No train stations available. Please add a train station.");
            return;
        }else if(stationList.size()==1){
            System.out.println("Not enough stations to create a schedule. Please add a station.");
            return;
        }else if(trainList.isEmpty()){
            System.out.println("No train available. Please add a train.");
            return;
        }else{
            System.out.println("====================================");
            System.out.println("      Add Schedule Information");
            System.out.println("====================================");
        }
        System.out.println("Select a departure station : ");
        for (int i = 0; i < stationList.size(); i++) {
            System.out.println((i+1) + ". " + stationList.get(i).getLocationName());
        }
        do{
            System.out.print("Enter the station number stated above > ");
            userInput2 = validateIntegerInput(scanner, "Enter the station number stated above > ");
            if(userInput2>stationList.size()){
                System.out.println("Invalid option. Please enter the number stated above.");
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
            userInput2 = validateIntegerInput(scanner, "Enter the station number stated above > ");
            if(userInput2>(stationList.size()-1)){
                System.out.println("Invalid option. Please enter the number stated above.");
            }
        }while(userInput2>(stationList.size()-1));

        // Adjust user input based on the excluded departure station
        if (userInput2 > departureIndex) {
            userInput2++; // Increment the input station number to account for the excluded departure station
        }
        arriveLocation = stationList.get(userInput2 - 1);
        
        do{
            System.out.print("Enter a departure time (HH:MM) > ");
            userInput = scanner.nextLine(); 
            if (isValidTimeFormat(userInput)) {
                departTime = parseTime(userInput);
                if (departTime != null) {
                    correctTime = true;
                } else {
                    correctTime = false;
                    System.out.println("Invalid time format. Please enter in format HH:MM.");
                }
            } else {
                correctTime = false;
                System.out.println("Invalid time format. Please enter in format HH:MM.");
            }
        }while(correctTime==false);
        
        do{
            System.out.print("Enter an arrival time (HH:MM) > ");
            userInput = scanner.nextLine(); 
            if (isValidTimeFormat(userInput)) {
                arriveTime = parseTime(userInput);
                if (arriveTime != null) {
                    correctTime = true;
                } else {
                    correctTime = false;
                    System.out.println("Invalid time format. Please enter in format HH:MM.");
                }
            } else {
                correctTime = false;
                System.out.println("Invalid time format. Please enter in format HH:MM.");
            }
        }while(correctTime==false);
 
        System.out.println("Select a train for this schedule : ");
        for (int i = 0; i < trainList.size(); i++) {
                System.out.println((i+1) + ". " + trainList.get(i).getTrainNo());
        }
        do{
            System.out.print("Enter the train number stated above > ");
            userInput2 = validateIntegerInput(scanner, "Enter the train number stated above > ");
            if(userInput2>trainList.size()){
                System.out.println("Invalid option. Please enter the number stated above.");
            }else{
                trainOperated = trainList.get(userInput2-1);
            }
            
        }while(userInput2>trainList.size());

        System.out.print("Enter the ticket price (RM) > ");
        ticketPrice = validateDoubleInput(scanner, "Enter the ticket price (RM) > ");

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        userInput = validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(userInput.equalsIgnoreCase("Y")){
            Schedule s = new Schedule(departLocation, arriveLocation, departTime, arriveTime, trainOperated, ticketPrice);
            scheduleList.add(s);
            added = writeIntoFile("scheduleFile.txt", scheduleList, obj);

            if (added){
                System.out.println("\nSchedule has added.\n");
            }else{
                System.out.println("\nUnable to add the schedule.\n");
            }
        }else{
            System.out.println("\nModification cancelled.");
        }
    }
   
    //----------------------------------------------UPDATE SCHEDULE----------------------------------------------

    public static void updateScheduleInfo(ArrayList<Schedule> scheduleList, ArrayList<TrainStation> stationList, ArrayList<Train> trainList, Scanner scanner) throws Exception {
        String scheduleId;
        String userInput;
        boolean cont = true;
        int index = 0;
        boolean found = false;

        do{
            System.out.print("Enter the schedule id to search the schedule (Press # to exit) > ");
            scheduleId = scanner.nextLine();

            if (scheduleId.equals("#"))
                break;

            for (int i = 0; i < scheduleList.size(); i++) {
                if (scheduleId.equals(scheduleList.get(i).getScheduleId())) {
                    found = true;
                    index = i;
                }
            }
            if(found==true){
                System.out.println("Do you want to update the schedule information as shown below? ");
                System.out.println(scheduleList.get(index).toString());
                System.out.print("Enter your option (Y-Yes/N-No)> ");
                userInput = validateYNInput(scanner, "Enter your option (Y-Yes/N-No)> ");
                do{
                    if(userInput.equalsIgnoreCase("Y")){
                        System.out.println("The field that can be updated :");
                        System.out.println("1. Departure time");
                        System.out.println("2. Arrival time");
                        System.out.println("3. Train operated");
                        System.out.println("4. Ticket price");
                        System.out.println("* Press # to exit");
                        do{
                            System.out.print("Enter option in number stated above > ");
                            userInput = scanner.nextLine();
                        
                            if(userInput.equals("1")){
                                departureInfoUpdate(scheduleList, stationList, trainList, scanner, index);
                            }else if(userInput.equals("2")){
                                arrivalInfoUpdate(scheduleList, stationList, trainList, scanner, index);
                            }else if(userInput.equals("3")){
                                trainOperatedUpdate(scheduleList, stationList, trainList, scanner, index);
                            }else if(userInput.equals("4")){
                                ticketPriceUpdate(scheduleList, stationList, trainList, scanner, index);
                            }else if(userInput.equals("#")){
                                cont = false;
                                return;
                            }else{
                                System.out.println("Invalid input. Please enter (1/2/3/4/#).");
                            }

                        }while(!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
                    }else{
                        found = false;
                        cont = false;
                        break;
                    }
                    System.out.print("Do you want to continue make changes? (Y-Yes/N-No) > ");
                    userInput = validateYNInput(scanner, "Do you want to continue make changes? (Y-Yes/N-No) > ");
                    if (userInput.equalsIgnoreCase("Y")){
                        cont = true;
                    }else{
                        cont = false;
                    }
                } while(cont == true);

            }else{
                System.out.println("The schedule is not found. Please serch again.");
            }
               
        }while(!found);
    }
    
     //----------------------------------UPDATE SCHEDULE DEPARTURE INFO-------------------------------------------

    public static void departureInfoUpdate(ArrayList<Schedule> scheduleList, ArrayList<TrainStation> stationList, ArrayList<Train> trainList, Scanner scanner, int index){
        String userInput;
        String confirm;
        LocalTime departTime = LocalTime.of(0, 0);;
        boolean updated = false;
        boolean correctTime = false;
        Schedule obj = new Schedule();

        
        System.out.println("Current departure time: " + scheduleList.get(index).getDepartTime());
               
        do{
            System.out.print("Enter a new departure time (HH:MM) > ");
            userInput = scanner.nextLine(); 
            if (isValidTimeFormat(userInput)) {
                departTime = parseTime(userInput);
                if (departTime != null) {
                    correctTime = true;
                } else {
                    correctTime = false;
                    System.out.println("Invalid time format. Please enter in format HH:MM.");
                }
            } else {
                correctTime = false;
                System.out.println("Invalid time format. Please enter in format HH:MM.");
            }
        }while(correctTime==false);

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        confirm = validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(confirm.equalsIgnoreCase("Y")){
            scheduleList.get(index).editDepartTime(departTime);
            updated = writeIntoFile("scheduleFile.txt", scheduleList, obj);
            if(updated){
                System.out.println("Schedule departure time has updated.");
            }else{
                System.out.println("Unable to update the schedule departure time.");
            }
        }else{
            System.out.println("Modification cancelled.");
        }
    }

    //-------------------------------------UPDATE SCHEDULE ARRIVAL INFO-------------------------------------------

    public static void arrivalInfoUpdate(ArrayList<Schedule> scheduleList, ArrayList<TrainStation> stationList, ArrayList<Train> trainList, Scanner scanner, int index){
        String userInput;
        String confirm;
        LocalTime arriveTime = LocalTime.of(0, 0);;
        boolean updated = false;
        boolean correctTime = false;
        Schedule obj = new Schedule();

        System.out.println("Current arrival time: " + scheduleList.get(index).getArriveTime());
               
        do{
            System.out.print("Enter a new arrival time (HH:MM) > ");
            userInput = scanner.nextLine(); 
            if (isValidTimeFormat(userInput)) {
                arriveTime = parseTime(userInput);
                if (arriveTime != null) {
                    correctTime = true;
                } else {
                    correctTime = false;
                    System.out.println("Invalid time format. Please enter in format HH:MM.");
                }
            } else {
                correctTime = false;
                System.out.println("Invalid time format. Please enter in format HH:MM.");
            }
        }while(correctTime==false);

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        confirm = validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(confirm.equalsIgnoreCase("Y")){
            scheduleList.get(index).editArriveTime(arriveTime);
            updated = writeIntoFile("scheduleFile.txt", scheduleList, obj);
            if(updated){
                System.out.println("Schedule arrival time has updated.");
            }else{
                System.out.println("Unable to update the schedule arrival time.");
            }
        }else{
            System.out.println("Modification cancelled.");
        }

    }
    
    //--------------------------------------UPDATE SCHEDULE TRAIN INFO-------------------------------------------

    public static void trainOperatedUpdate(ArrayList<Schedule> scheduleList, ArrayList<TrainStation> stationList, ArrayList<Train> trainList, Scanner scanner, int index) throws Exception{
        int userInput;
        Train trainOperated;
        String confirm;
        boolean updated = false;
        Schedule obj = new Schedule();

        
        ArrayList<Train> availableTrains = new ArrayList<>(trainList);
        int currentTrainNo = scheduleList.get(index).getOperatedTrain().getTrainNo();
        availableTrains.removeIf(train -> train.getTrainNo()==currentTrainNo);
        if (availableTrains.size()==0){
            System.out.println("Sorry. You cannot change the train operated as it is no train available to replace the current train.");
            return;
        }
        System.out.println("Current operated train: " + scheduleList.get(index).getOperatedTrain().toString());
        System.out.println("Select a new train to replace train above: ");
        
        int trainNumber = 1; // Initialize the train number
        for (int n = 0; n < availableTrains.size(); n++) {
            System.out.println(trainNumber + ". " + availableTrains.get(n).getTrainNo());
            trainNumber++; // Increment the station number
        }
        do{
            System.out.print("Enter the station number stated above > ");
            userInput = validateIntegerInput(scanner, "Enter the station number stated above > ");

            if (userInput >= 1 && userInput <= availableTrains.size()) {
                System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
                confirm = validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");
                if(confirm.equalsIgnoreCase("Y")){
                    trainOperated = availableTrains.get(userInput - 1);
                    scheduleList.get(index).editTrainOperated(trainOperated);
                    updated = writeIntoFile("scheduleFile.txt", scheduleList, obj);

                    if(updated){
                        System.out.println("\nThe train operated for the schedule has updated.");
                    }else{
                        System.out.println("\nUnable to update the train operated for the schedule.");
                    }

                }else{
                    System.out.println("Modification cancelled.");
                }   
            }else{
                System.out.println("Invalid train number. Please select a valid train.");
            }
        }while ((userInput < 1 && userInput > availableTrains.size()));    
    }

    //-----------------------------------UPDATE SCHEDULE TICKET PRICE INFO---------------------------------------

    public static void ticketPriceUpdate(ArrayList<Schedule> scheduleList, ArrayList<TrainStation> stationList, ArrayList<Train> trainList, Scanner scanner, int index) throws Exception{
        String confirm;
        boolean updated;
        double ticketPrice;
        Schedule obj = new Schedule();

        System.out.println("Current ticket price (RM): " + scheduleList.get(index).getTicketPrice());
        System.out.print("Enter a new ticket price (RM): ");
        ticketPrice = validateDoubleInput(scanner, "Enter a new ticket price (RM): ");

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        confirm = validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(confirm.equalsIgnoreCase("Y")){
            scheduleList.get(index).editTicketPrice(ticketPrice);
            updated = writeIntoFile("scheduleFile.txt", scheduleList, obj);

            if(updated){
                System.out.println("Ticket price for the schedule has updated.");
            }else{
                System.out.println("Unable to update the ticket price for the schedule.");
            }
        }else{
            System.out.println("Modification cancelled.");
        }
        
    }

    //----------------------------------------------DELETE SCHEDULE----------------------------------------------

    public static void deleteSchedule(ArrayList<Schedule> scheduleList, ArrayList<TrainStation> stationList, ArrayList<Train> trainList, Scanner scanner) throws Exception {
        boolean found = false;
        String userInput;
        String userInput2;
        boolean deleted = false;
        Schedule obj = new Schedule();
        int index = 0;
        
        do {
            System.out.print("Enter the schedule id to search the schedule (Press # to exit) > ");
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
    
            for (int i = 0; i < scheduleList.size(); i++) {
                if (userInput.equals(scheduleList.get(i).getScheduleId())) {
                    index = i;
                    found = true;
                }
            }
            if (found==true){
                System.out.println("Do you want to delete the schedule information as shown below? ");
                System.out.println(scheduleList.get(index).toString());
                System.out.print("Enter your option (Y-Yes/N-No)> ");
                userInput = validateYNInput(scanner, "Enter your option (Y-Yes/N-No)> ");
    
                if (userInput.equalsIgnoreCase("Y")) {
                    System.out.print("Do you confirm (Y-Yes/N-No) ? ");
                    userInput2 = validateYNInput(scanner, "Do you confirm (Y-Yes/N-No) ? ");

                    if(userInput2.equalsIgnoreCase("Y")){
                        scheduleList.remove(index); // Remove the train from the list
                        deleted = writeIntoFile("scheduleFile.txt",scheduleList, obj);

                        if (deleted) {
                             System.out.println("Schedule has been removed.");
                        }else{
                            System.out.println("Unable to remove the schedule.");

                        }

                    } else {
                        System.out.println("Modification cancelled.");
                    }        
                }else{
                    found = false;
                }
            
            }else{
                System.out.println("The schedule is not found. Please search again.");
            } 
        } while (!found);
    
    }

    //===========================================================================================================
    //                                   FOOD AND BEVERAGE INFORMATION MODIFICATION
    //===========================================================================================================

    public static void foodAndBeverageModification(ArrayList<Snacks> snacksList, ArrayList<Drinks> drinksList, Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;
    
        while (cont) {
            System.out.println("1. Snacks modification");
            System.out.println("2. Drinks modification");
            System.out.println("* Press # to go back");
    
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
               
                if (userInput.equals("1")) {
                    snacksModification(snacksList, scanner);
                } else if (userInput.equals("2")) {
                    drinksModification(drinksList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/#).");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("#"));
        }
    }

    //===========================================================================================================
    //                                   SNACKS INFORMATION MODIFICATION
    //===========================================================================================================

    public static void snacksModification(ArrayList<Snacks> snacksList, Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;
    
        while (cont) {
            System.out.println("1. View snacks");
            System.out.println("2. Update existing snacks");
            System.out.println("3. Add a new snacks");
            System.out.println("4. Delete an existing snacks");
            System.out.println("* Press # to go back");
    
    
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
               
                if (userInput.equals("1")) {
                    for(int i=0; i<snacksList.size(); i++){
                        System.out.println(snacksList.get(i).toString());
                    }
                } else if (userInput.equals("2")) {
                    updateSnacks(snacksList, scanner);
                } else if (userInput.equals("3")) {
                    addSnacks(snacksList, scanner);
                } else if (userInput.equals("4")) {
                    deleteSnacks(snacksList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    //-----------------------------------------ADD SNACKS INFORMATION-------------------------------------------- 

    public static void addSnacks(ArrayList<Snacks> snacksList, Scanner scanner) throws FileNotFoundException{
        String foodName;
        double foodPrice;
        int stockQty = 0;
        boolean partyPack = false;
        boolean added = false;
        String userInput;
        Snacks snacks = new Snacks();
    
        System.out.print("Enter snacks name > ");
        foodName = scanner.nextLine();
    
        System.out.print("Enter snacks price > ");
        foodPrice = validateDoubleInput(scanner, "Enter snacks price > ");
    
        System.out.print("Enter stock qty > ");
        stockQty = validateIntegerInput(scanner, "Enter stock qty > ");
    
        System.out.print("Is it a party pack? (Y-Yes/N-No) > ");
        userInput = validateYNInput(scanner, "Is it a party pack? (Y-Yes/N-No) > ");
            
        if (userInput.equalsIgnoreCase("Y")) {
            partyPack = true;
        } else {
            partyPack = false;
        } 
    
        System.out.print("Do you confirm? (Y-Yes/N-No) > ");
        userInput = validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");

        if(userInput.equalsIgnoreCase("Y")){
            Snacks tempSnacks = new Snacks(foodName, foodPrice, stockQty, partyPack);
            snacksList.add(tempSnacks);
            added = writeIntoFile("snacksFile.txt", snacksList, snacks);
    
            if (added) {
                System.out.println("Snacks has been added.");
            } else {
                System.out.println("Unable to add snacks.");
            }

        }else{
            System.out.println("Modification cancelled.");
        }
        
    }
    
    //----------------------------------------------UPDATE SNACKS------------------------------------------------

    public static void updateSnacks(ArrayList<Snacks> snacksList, Scanner scanner) throws Exception{
        String foodId;
        String foodName;
        double foodPrice;
        int stockQty = 0; 
        int index = 0;
        boolean found = false;
        boolean cont = true;
        String userInput;
        String userInput2;
        String confirm;
        boolean updated = false;
        boolean invalidFormat = false;
        Snacks obj = new Snacks();
        String sign;
        String numericPart;

        do {
            System.out.print("Enter the snacks id to search the snacks (Press # to exit) > ");
            foodId = scanner.nextLine();

            for (int i = 0; i < snacksList.size(); i++) {
                if (foodId.equals(snacksList.get(i).getFoodId())) {
                    found = true;
                    index = i;
                }
            }
            if(found==true){
                do{
                    System.out.println(snacksList.get(index).toString());
                    System.out.println("Select a field: ");
                    System.out.println("1. Food name ");
                    System.out.println("2. Food price ");
                    System.out.println("3. Stock qty ");
                    System.out.println("4. Make it a party pack or vice versa");
                    System.out.println("#. Go back");

                    do {
                        System.out.print("Enter option in number stated above > ");
                        userInput = scanner.nextLine();

                        if (userInput.equals("1")) {
                            System.out.print("New snacks name > ");
                            foodName = scanner.nextLine();
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if(confirm.equalsIgnoreCase("Y")){
                                snacksList.get(index).editFoodName(foodName);
                                updated = writeIntoFile("snacksFile.txt", snacksList, obj);
                                if(updated){
                                    System.out.println("Food name has updated.");
                                }else{
                                    System.out.println("Unable to update food name.");
                                }
                            }else{
                                System.out.println("Modification cancelled.");
                            }
                            
                        } else if (userInput.equals("2")) {
                            System.out.print("New price > ");
                            foodPrice = validateDoubleInput(scanner, "New price > ");
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if(confirm.equalsIgnoreCase("Y")){
                                snacksList.get(index).editFoodPrice(foodPrice);
                                updated = writeIntoFile("snacksFile.txt", snacksList, obj);
                                if(updated){
                                    System.out.println("Food price has updated.");
                                }else{
                                    System.out.println("Unable to update food price.");
                                }
                            }else{
                                System.out.println("Modification cancelled.");
                            }
                            
                        } else if (userInput.equals("3")) {
                            do{
                                System.out.print("Add or Subtract stocky qty (eg. enter +100 to add 100; enter -100 to subtract 100) > " );
                                userInput2 = scanner.nextLine();
                                if (userInput2.startsWith("+") || userInput2.startsWith("-")) {
                                    sign = userInput2.substring(0, 1);
                                    numericPart = userInput2.substring(1);
                                    try {
                                        stockQty = Integer.parseInt(numericPart);
                                        invalidFormat = false;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid format. (eg. +100 / -100).");
                                        invalidFormat = true;
                                    }
                                } else {
                                    sign = "+";
                                    numericPart = userInput2;
                                    try {
                                        stockQty = Integer.parseInt(numericPart);
                                        invalidFormat = false;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid format. (eg. +100 / -100).");
                                        invalidFormat = true;
                                    }
                                }
                            }while (invalidFormat == true);
                       
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if(confirm.equalsIgnoreCase("Y")){
                                boolean success = snacksList.get(index).editStockQty(sign, stockQty);
                                updated = writeIntoFile("snacksFile.txt", snacksList, obj);
                                if(updated && success){
                                    System.out.println("Food stock qty has updated.");
                                }else{
                                    System.out.println("Unable to update food stock qty.");
                                }
                            }else{
                                System.out.println("Modification cancelled.");
                            }
                            
                        } else if (userInput.equals("4")) {
                            System.out.print("A party pack? (Y-Yes/N-No) > ");
                            userInput2 = validateYNInput(scanner, "A party pack? (Y-Yes/N-No) > ");
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");

                            if (confirm.equalsIgnoreCase("Y")){
                                if(userInput2.equalsIgnoreCase("Y")){
                                    snacksList.get(index).setPartyPack(true);
                                }else{
                                    snacksList.get(index).setPartyPack(false);
                                }
                                updated = writeIntoFile("snacksFile.txt", snacksList, obj);
                                if(updated){
                                    System.out.println("Party pack setting has updated.");
                                }else{
                                    System.out.println("Unable to update party pack setting.");
                                }
                            }else{
                                System.out.println("Modification cancelled.");
                            }
                        } else if (userInput.equals("#")) {
                            break;
                            
                        } else {
                            System.out.println("Invalid option. Please enter (1/2/3/4).");
                        }
                    } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
                    System.out.print("Do you want to continue make changes? (Y-Yes/N-No) > ");
                    userInput = validateYNInput(scanner, "Do you want to continue make changes? (Y-Yes/N-No) > ");
                    if (userInput.equalsIgnoreCase("Y")){
                        cont = true;
                    }else{
                        cont = false;
                    }
                }while(cont==true);
            }else{
                System.out.println("Snacks is not found. Please search again.");
            }
            
        }while(!found);
 
    }
    
    //----------------------------------------------DELETE SNACKS------------------------------------------------
    
    public static void deleteSnacks(ArrayList<Snacks> snacksList, Scanner scanner) throws Exception {
        boolean found = false;
        String userInput;
        String userInput2;
        boolean deleted = false;
        Snacks obj = new Snacks();
        int index = 0;
        
        do {
            System.out.print("Enter the snacks id to search the snacks (Press # to exit) > ");
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
    
            for (int i = 0; i < snacksList.size(); i++) {
                if (userInput.equals(snacksList.get(i).getFoodId())) {
                    index = i;
                    found = true;
                }
            }
            if (found==true){
                System.out.println("Do you want to delete the snacks information as shown below? ");
                System.out.println(snacksList.get(index).toString());
                System.out.print("Enter your option (Y-Yes/N-No)> ");
                userInput = validateYNInput(scanner, "Enter your option (Y-Yes/N-No)> ");
    
                if (userInput.equalsIgnoreCase("Y")) {
                    System.out.print("Do you confirm (Y-Yes/N-No) ? ");
                    userInput2 = validateYNInput(scanner, "Do you confirm (Y-Yes/N-No) ? ");

                    if(userInput2.equalsIgnoreCase("Y")){
                        snacksList.remove(index); // Remove the train from the list
                        deleted = writeIntoFile("snacksFile.txt", snacksList, obj);

                        if (deleted) {
                             System.out.println("Snacks has been removed.");
                        }else{
                            System.out.println("Unable to remove the snacks.");

                        }

                    } else {
                        System.out.println("Modification cancelled.");
                    }        
                }else{
                    found = false;
                }
            
            }else{
                System.out.println("The snacks is not found. Please search again.");
            } 
        } while (!found);
    
    }
    
    //===========================================================================================================
    //                                   DRINKS INFORMATION MODIFICATION
    //===========================================================================================================

    public static void drinksModification(ArrayList<Drinks> drinksList, Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;
    
        while (cont) {
            System.out.println("1. View drinks");
            System.out.println("2. Update existing drinks");
            System.out.println("3. Add a new drinks");
            System.out.println("4. Delete an existing drinks");
            System.out.println("* Press # to go back");
    
    
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
               
                if (userInput.equals("1")) {
                    for(int i=0; i<drinksList.size(); i++){
                        System.out.println(drinksList.get(i).toString());
                    }
                } else if (userInput.equals("2")) {
                    updateDrinks(drinksList, scanner);
                } else if (userInput.equals("3")) {
                    addDrinks(drinksList, scanner);
                } else if (userInput.equals("4")) {
                    deleteDrinks(drinksList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }
    
    //-----------------------------------------ADD DRINKS INFORMATION-------------------------------------------- 

    public static void addDrinks(ArrayList<Drinks> drinksList, Scanner scanner) {
        String foodName;
        double foodPrice;
        int stockQty;
        boolean added = false;
        String userInput;
        Drinks drinks = new Drinks();
    
        System.out.print("Enter drinks name > ");
        foodName = scanner.nextLine();
    
        System.out.print("Enter drinks price > ");
        foodPrice = validateDoubleInput(scanner, "Enter drinks price > ");
    
        System.out.print("Enter stock qty > ");
        stockQty = validateIntegerInput(scanner, "Enter stock qty > ");
    
        System.out.print("Do you confirm? (Y-Yes/N-No) > ");
        userInput = validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
    
        if (userInput.equalsIgnoreCase("Y")) {
            Drinks tempDrinks = new Drinks(foodName, foodPrice, stockQty);
            drinksList.add(tempDrinks);
            added = writeIntoFile("drinksFile.txt", drinksList, drinks);
    
            if (added) {
                System.out.println("Drinks have been added.");
            } else {
                System.out.println("Unable to add drinks.");
            }
        } else {
            System.out.println("Modification cancelled.");
        }
    }
    
    //----------------------------------------------UPDATE DRINKS----------------------------------------------
    
    public static void updateDrinks(ArrayList<Drinks> drinksList, Scanner scanner) throws Exception{
        String foodId;
        String foodName;
        double foodPrice;
        int stockQty = 0; 
        int index = 0;
        boolean found = false;
        boolean cont = true;
        String userInput;
        String userInput3;
        boolean updated = false;
        Drinks obj = new Drinks();
        String sign;
        String numericPart;
        boolean invalidFormat = false;
        String confirm;

        do {
            System.out.print("Enter the snacks id to search the snacks (Press # to exit) > ");
            foodId = scanner.nextLine();

            for (int i = 0; i < drinksList.size(); i++) {
                if (foodId.equals(drinksList.get(i).getFoodId())) {
                    found = true;
                    index = i;
                }
            }
            if(found==true){
                do{
                    System.out.println(drinksList.get(index).toString());
                    System.out.println("Select a field: ");
                    System.out.println("1. Food name ");
                    System.out.println("2. Food price ");
                    System.out.println("3. Stock qty ");
                    System.out.println("#. Go back");

                    do {
                        System.out.print("Enter option in number stated above > ");
                        userInput = scanner.nextLine();

                        if (userInput.equals("1")) {
                            System.out.print("New snacks name > ");
                            foodName = scanner.nextLine();
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if(confirm.equalsIgnoreCase("Y")){
                                drinksList.get(index).editFoodName(foodName);
                                updated = writeIntoFile("drinksFile.txt", drinksList, obj);
                                if(updated){
                                    System.out.println("Food name has updated.");
                                }else{
                                    System.out.println("Unable to update food name.");
                                }
                            }else{
                                System.out.println("Modification cancelled.");
                            }
                                
                        } else if (userInput.equals("2")) {
                            System.out.print("New price > ");
                            foodPrice = validateDoubleInput(scanner, "New price > ");
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if(confirm.equalsIgnoreCase("Y")){
                                drinksList.get(index).editFoodPrice(foodPrice);
                                updated = writeIntoFile("drinksFile.txt", drinksList, obj);
                                if(updated){
                                    System.out.println("Food price has updated.");
                                }else{
                                    System.out.println("Unable to update food price.");
                                }
                            }else{
                                System.out.println("Modification cancelled.");
                            }
                                
                        } else if (userInput.equals("3")) {
                            do{
                                System.out.print("Add or Subtract stocky qty (eg. enter +100 to add 100; enter -100 to subtract 100) > " );
                                userInput3 = scanner.nextLine();
                                if (userInput3.startsWith("+") || userInput3.startsWith("-")) {
                                    sign = userInput3.substring(0, 1);
                                    numericPart = userInput3.substring(1);
                                    try {
                                        stockQty = Integer.parseInt(numericPart);
                                        invalidFormat = false;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid format. (eg. +100 / -100).");
                                        invalidFormat = true;
                                    }
                                } else {
                                    sign = "+";
                                    numericPart = userInput3;
                                    try {
                                        stockQty = Integer.parseInt(numericPart);
                                        invalidFormat = false;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid format. (eg. +100 / -100).");
                                        invalidFormat = true;
                                    }
                                }
                            }while (invalidFormat == true);
                            System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                            confirm = validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
                            if(confirm.equalsIgnoreCase("Y")){
                                boolean success = drinksList.get(index).editStockQty(sign, stockQty);
                                updated = writeIntoFile("drinksFile.txt", drinksList, obj);
                                if(updated && success){
                                    System.out.println("Food stock qty has updated.");
                                }else{
                                    System.out.println("Unable to update food stock qty.");
                                }
                            }else{
                                System.out.println("Modification cancelled.");
                            }
                        } else if (userInput.equals("#")) {
                            break;
                                
                        } else {
                            System.out.println("Invalid option. Please enter (1/2/3/#).");
                        }
                    } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("#"));
                    System.out.print("Do you want to continue make changes? (Y-Yes/N-No) > ");
                    userInput = validateYNInput(scanner, "Do you want to continue make changes? (Y-Yes/N-No) > ");
                    if (userInput.equalsIgnoreCase("Y")){
                        cont = true;
                    }else{
                        cont = false;
                    }
                }while(cont==true);
            }else{
                System.out.println("Drinks is not found. Please search again.");
            }
            
        }while(!found);
 
    }
    
    //----------------------------------------------DELETE DRINKS----------------------------------------------
    
     public static void deleteDrinks(ArrayList<Drinks> drinksList, Scanner scanner) throws Exception {
        boolean found = false;
        String userInput;
        String userInput2;
        boolean deleted = false;
        Drinks obj = new Drinks();
        int index = 0;
        
        do {
            System.out.print("Enter the drinks id to search the drinks (Press # to exit) > ");
            userInput = scanner.nextLine();
            if (userInput.equals("#"))
                break;
    
            for (int i = 0; i < drinksList.size(); i++) {
                if (userInput.equals(drinksList.get(i).getFoodId())) {
                    index = i;
                    found = true;
                }
            }
            if (found==true){
                System.out.println("Do you want to delete the drinks information as shown below? ");
                System.out.println(drinksList.get(index).toString());
                System.out.print("Enter your option (Y-Yes/N-No)> ");
                userInput = validateYNInput(scanner, "Enter your option (Y-Yes/N-No)> ");
    
                if (userInput.equalsIgnoreCase("Y")) {
                    System.out.print("Do you confirm (Y-Yes/N-No) ? ");
                    userInput2 = validateYNInput(scanner, "Do you confirm (Y-Yes/N-No) ? ");

                    if(userInput2.equalsIgnoreCase("Y")){
                        drinksList.remove(index); // Remove the train from the list
                        deleted = writeIntoFile("drinksFile.txt", drinksList, obj);

                        if (deleted) {
                             System.out.println("Drinks has been removed.");
                        }else{
                            System.out.println("Unable to remove the drinks.");

                        }

                    } else {
                        System.out.println("Modification cancelled.");
                    }        
                }else{
                    found = false;
                }
            
            }else{
                System.out.println("The drinks is not found. Please search again.");
            } 
        } while (!found);
    
    }
    
    //===========================================================================================================
    //                                     METHOD TO READ DATA FROM FILE 
    //===========================================================================================================

    public static void readFromFile(String filename, ArrayList<Train> trainList, Train obj) throws FileNotFoundException {
        File file = new File(filename);

        if (file.exists()) {
            try (Scanner inputFile = new Scanner(file)) {
                 while (inputFile.hasNext()) {
                     int trainNo = inputFile.nextInt();
                     inputFile.nextLine(); // Consume newline
                     String trainName = inputFile.nextLine();
                     String trainModel = inputFile.nextLine();
                     trainList.add(new Train(trainNo, trainName, trainModel));
                 }
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
    }

    public static void readFromFile(String filename, ArrayList<Snacks> snacksList, Snacks obj) throws Exception {
        File file = new File(filename);

        if (file.exists()) {
            try (Scanner inputFile = new Scanner(file)) {
                 while (inputFile.hasNext()) {
                     String foodId = inputFile.nextLine();
                     String foodName= inputFile.nextLine();
                     double foodPrice= inputFile.nextDouble();
                     inputFile.nextLine();
                     int purchaseQty = inputFile.nextInt();
                     inputFile.nextLine();
                     int stockQty = inputFile.nextInt();
                     inputFile.nextLine();
                     boolean partyPack = inputFile.nextBoolean();
                     inputFile.nextLine();
                     snacksList.add(new Snacks(foodId, foodName, foodPrice, purchaseQty, stockQty, partyPack));
                 }
           }
       }
    }

    public static void readFromFile(String filename, ArrayList<Drinks> drinksList, Drinks obj) throws Exception {
        File file = new File(filename);

        if (file.exists()) {
            try (Scanner inputFile = new Scanner(file)) {
                 while (inputFile.hasNext()) {
                     String foodId = inputFile.nextLine();
                     String foodName= inputFile.nextLine();
                     double foodPrice= inputFile.nextDouble();
                     inputFile.nextLine();
                     int purchaseQty = inputFile.nextInt();
                     inputFile.nextLine();
                     int stockQty = inputFile.nextInt();
                     inputFile.nextLine();
                     String temperature = inputFile.nextLine();
                     String size = inputFile.nextLine();
                     boolean ice = inputFile.nextBoolean();
                     inputFile.nextLine();
                     drinksList.add(new  Drinks(foodId, foodName, foodPrice, purchaseQty, stockQty, temperature, size, ice));
                 }
           }
       }
    }
    
    //===========================================================================================================
    //                                     METHOD TO WRITE DATA INTO FILE 
    //===========================================================================================================

    public static boolean writeIntoFile(String filename, ArrayList<Train> trainList, Train obj)  throws FileNotFoundException {
        boolean write = false;
        try {
            FileWriter fwrite = new FileWriter(filename, false);
            try (Writer output = new BufferedWriter(fwrite)) {
                for(int i=0; i<trainList.size(); i++){
                    output.write(trainList.get(i).getTrainNo() + "\n");
                    output.write(trainList.get(i).getTrainName() + "\n");
                    output.write(trainList.get(i).getTrainModel() + "\n");
                }
                write = true;
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
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
    
    public static boolean writeIntoFile(String filename, ArrayList<Snacks> snacksList, Snacks obj)  throws FileNotFoundException {
        boolean write = false;
        try {
            FileWriter fwrite = new FileWriter(filename, false);
            try (Writer output = new BufferedWriter(fwrite)) {
                for(int i=0; i<snacksList.size(); i++){
                    output.write(snacksList.get(i).getFoodId() + "\n");
                    output.write(snacksList.get(i).getFoodName() + "\n");
                    output.write(snacksList.get(i).getFoodPrice() + "\n");
                    output.write(snacksList.get(i).getPurchaseQty() + "\n");
                    output.write(snacksList.get(i).getStockQty() + "\n");
                    output.write(snacksList.get(i).getPartyPack() + "\n");
                }
                write = true;
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        return write;
    }

    public static boolean writeIntoFile(String filename, ArrayList<Drinks> drinksList, Drinks obj) {
       boolean write = false;
        try {
            FileWriter fwrite = new FileWriter(filename, false);
            try (Writer output = new BufferedWriter(fwrite)) {
                for(int i=0; i<drinksList.size(); i++){
                    output.write(drinksList.get(i).getFoodId() + "\n");
                    output.write(drinksList.get(i).getFoodName() + "\n");
                    output.write(drinksList.get(i).getFoodPrice() + "\n");
                    output.write(drinksList.get(i).getPurchaseQty() + "\n");
                    output.write(drinksList.get(i).getStockQty() + "\n");
                    output.write(drinksList.get(i).getTemperature() + "\n");
                    output.write(drinksList.get(i).getSize() + "\n");
                    output.write(drinksList.get(i).getIce() + "\n");
                }
                write = true;
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        return write;
    }

 
}
