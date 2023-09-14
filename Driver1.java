import java.util.*;
import java.io.*;
public class Driver1 {

    // Methos below is to read the train information from file & store into trainList ArrayList<Train>
    public static ArrayList<Train> readTrainFromFile(String filename) throws FileNotFoundException{
            // ArrayList<Train> object creation (object named as trainList)
            ArrayList<Train> trainList = new ArrayList<Train>();
            // File object creation (object named as file)
            File file = new File(filename);
            // Scanner object declaration (object reference named as inputFile)
            Scanner inputFile;
            // Variables declared to store the Train properties read from file
            int trainNo;
            String trainType;
            String trainModel;
            String status;

            if(file.exists()){
                // Create an Scanner object and assign to the inputFile object reference
                inputFile = new Scanner(file);
                while(inputFile.hasNext()){
                    // Read the file content line by line into the variables
                    trainNo = inputFile.nextInt();
                    inputFile.nextLine();
                    trainType = inputFile.nextLine();
                    trainModel = inputFile.nextLine();
                    status = inputFile.nextLine();
                    // Create Train object and add to ArrayList<Train> - trainList
                    trainList.add(new Train(trainNo, trainType, trainModel, status));
                }
            }   
        return trainList;
    }

    // Method below is the main function
public static void main(String[] args) throws FileNotFoundException{
        // ArrayList<Train> object declaration (object reference named as trainList)
        ArrayList<Train> trainList;
        // Variable declared to store user input
        String userInput="";
        // Invoke readTrainFromFile() method
        trainList = readTrainFromFile("trainFile.txt");
        
        // Use try-with-resources to automatically close the Scanner
        try (Scanner input = new Scanner(System.in)) {
            do{
                  // Display the user option menu
                  System.out.println("1. Train Information Modification");
                  System.out.println("2. Schedule Modification");
                  System.out.println("3. Train Station Information Modification");
                  System.out.println("( Enter '#' to go back)");
                  do {
                      // Accept user input
                      System.out.print("Enter your option > ");
                
                      userInput = input.nextLine().trim(); // Read the whole line and trim

                    //Compare user input and invoke specific method
                if (userInput.equals("1")) {
                    trainModification(trainList, input);
                } else if (userInput.equals("2")) {
                    scheduleModification();
                } else if (userInput.equals("3")) {
                    stationModification();
                } else if (userInput.equals("#")) {

                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/#).");
                }
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("#"));
        }while(!userInput.equals("#"));
        } // Scanner will be automatically closed here
    
    }

    // Method below is to modify the train information
    public static void trainModification(ArrayList<Train> trainList, Scanner input) {
        String userInput = "";
    
        while (!userInput.equals("#")) { // Exit the loop when userInput is "#"
            // Display the user option menu
            System.out.println("1. View existing train information");
            System.out.println("2. Modify existing train information");
            System.out.println("3. Add a new train");
            System.out.println("4. Delete existing train information");
            System.out.println("#. Exit");
    
            System.out.print("Enter your option > ");
            userInput = input.next().trim();
    
            // Compare user input and invoke specific method
            if (userInput.equals("1")) {
                for (int i = 0; i < trainList.size(); i++) {
                    System.out.println(trainList.get(i).toString());
                    System.out.println();
                }
            } else if (userInput.equals("2")) {
                updateTrainInfo(trainList);
            } else if (userInput.equals("3")) {
                // Add new train functionality
            } else if (userInput.equals("4")) {
                // Delete train functionality
            } else if (!userInput.equals("#")) { // Check if userInput is not "#"
                System.out.println("Invalid option. Please enter (1/2/3/4/#).");
            }
        }

    }

    public static void updateTrainInfo(ArrayList<Train> trainList) {
        int trainNoInput;
        int statusSelection;
        boolean found = false;
    
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Search the train that needs to be updated");
    
            do {
                System.out.print("Train No > ");
                trainNoInput = input.nextInt();
    
                // Search for the train with the given train number
                for (int i = 0; i < trainList.size(); i++) {
                    if (trainNoInput == trainList.get(i).getTrainNo()) {
                        found = true;
                        System.out.println(trainList.get(i).toString());
                        System.out.println("Update the train status");
                        System.out.println("1. In service");
                        System.out.println("2. Out of service");
                        System.out.println("3. In maintenance");
    
                        do {
                            System.out.print("Enter option in number stated above > ");
                            statusSelection = input.nextInt();
    
                            // Update the train status based on the user's selection
                            if (statusSelection == 1) {
                                trainList.get(i).changeTrainStatus("In service");
                            } else if (statusSelection == 2) {
                                trainList.get(i).changeTrainStatus("Out of service");
                            } else if (statusSelection == 3) {
                                trainList.get(i).changeTrainStatus("In maintenance");
                            } else {
                                System.out.println("Invalid option. Please enter (1/2/3).");
                            }
                        } while (statusSelection != 1 && statusSelection != 2 && statusSelection != 3);
    
                        // Write the updated train information to the file
                        try {
                            FileWriter fwrite = new FileWriter("trainFile.txt", false);
                            try (Writer output = new BufferedWriter(fwrite)) {
                                for (int j = 0; j < trainList.size(); j++) {
                                    output.write(trainList.get(j).getTrainNo() + "\n");
                                    output.write(trainList.get(j).getTrainType() + "\n");
                                    output.write(trainList.get(j).getTrainModel() + "\n");
                                    output.write(trainList.get(j).getTrainStatus() + "\n");
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Error writing to the file: " + e.getMessage());
                            // You may want to handle this error more gracefully, e.g., by logging it.
                        }
                    }
                }
    
                if (!found) {
                    System.out.println("Train not found. Please search again.");
                }
            } while (!found);
        } // Scanner will be automatically closed here
    }
    

    public static void scheduleModification() {
        // Implement your schedule modification logic here
    }

    public static void stationModification() {
        // Implement your station modification logic here
    }
}

/*Staff CRUD
=============
CRUD train schedules (Train, Schedule, Location)
CRUD train ticket  
CRUD FnB (FoodAndBeverage, Snacks, Drinks)*/

// Train modification (update, delete)
// Schedule modification  (update, add, delete)
// Train location modification  (update, add, delete)

// Snacks modification (update, add, delete)
// Drinks modification (update, add, delete)
