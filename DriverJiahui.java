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
                    //sheduleModification(scheduleList, stationList, trainList, scanner);
                } else if (userInput.equals("3")) {
                    //stationModification(stationList, scanner);
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
                            System.out.println("Please think carefully as it will affect the schedule below?");
                            for (int j=0; j<scheduleList.size(); j++){
                                if (scheduleList.get(j).getOperatedTrain().getTrainNo()==trainList.get(index).getTrainNo()){
                                    System.out.println(scheduleList.get(j).toString());
                                    System.out.println("\n");
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