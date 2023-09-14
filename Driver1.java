/*Staff CRUD
=============
CRUD train schedules (Train, Schedule, Location)
CRUD train ticket  
CRUD FnB (FoodAndBeverage, Snacks, Drinks)*/

// Train modification           (update, add, delete)
// Schedule modification        (update, add, delete)
// Train location modification  (update, add, delete)

// Snacks modification          (update, add, delete)
// Drinks modification          (update, add, delete)

import java.util.*;
import java.io.*;

public class Driver1{
    public static void main(String[] args) throws FileNotFoundException {
         ArrayList<Train> trainList;
         String userInput = "";
         Scanner scanner = new Scanner(System.in);
         boolean cont = true;
         // Invoke readTrainFromFile() method to read train information from the file
        trainList = readTrainFromFile("trainFile.txt");

        do{
            System.out.println("1. Train Information Modification");
            System.out.println("2. Schedule Modification");
            System.out.println("3. Train Station Information Modification");
            System.out.println("( Enter '#' to go back)");

            do{
                System.out.print("Enter your option > ");
                userInput = "";
                userInput = scanner.next();
                
                // Compare user input and invoke specific method
                if (userInput.equals("1")) {
                    trainModification(trainList, scanner);
                } else if (userInput.equals("2")) {
                    //scheduleModification();
                } else if (userInput.equals("3")) {
                    //stationModification();
                } else if (userInput.equals("#")) {
                    // Handle going back
                    cont = false;
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/#).");
                }
            }while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("#"));

        }while(cont==true);
    }

    // Method to read the train information from a file & store it into trainList ArrayList<Train>
    public static ArrayList<Train> readTrainFromFile(String filename) throws FileNotFoundException {
        ArrayList<Train> trainList = new ArrayList<Train>();
        File file = new File(filename);

        if (file.exists()) {
            try (Scanner inputFile = new Scanner(file)) {
                while (inputFile.hasNext()) {
                    int trainNo = inputFile.nextInt();
                    inputFile.nextLine(); // Consume newline
                    String trainName = inputFile.nextLine();
                    String trainModel = inputFile.nextLine();
                    String status = inputFile.nextLine();
                    trainList.add(new Train(trainNo, trainName, trainModel, status));
                }
            }
        }
        return trainList;
    }


    // Method to modify train information
    public static void trainModification(ArrayList<Train> trainList, Scanner scanner) {
        String userInput = "";
        boolean cont = true;

        while(cont==true){
            // Display the user option menu
            System.out.println("1. View existing train information");
            System.out.println("2. Modify existing train information");
            System.out.println("3. Add a new train");
            System.out.println("4. Delete existing train information");
            System.out.println("#. Exit");
           
            do{
                System.out.print("Enter your option > ");
                userInput = "";
                userInput = scanner.next();

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
                    deleteTrain(trainList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                    break; // Exit the loop
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
                
            }while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }


    // Method to update train information
    public static void updateTrainInfo(ArrayList<Train> trainList, Scanner scanner) {
        int trainNoInput;
        int statusSelection;
        boolean found = false;

        System.out.println("Search the train that needs to be updated");

        do {
            System.out.print("Train No > ");
            trainNoInput = scanner.nextInt();

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
                        statusSelection = scanner.nextInt();

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
                                output.write(trainList.get(j).getTrainName() + "\n");
                                output.write(trainList.get(j).getTrainModel() + "\n");
                                output.write(trainList.get(j).getTrainStatus() + "\n");
                            }
                            System.out.println("The train information has updated.");
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
    }


    // Method to add a new train information
    public static void addTrain(ArrayList<Train> trainList, Scanner scanner) {
        scanner.nextLine();
        System.out.print("Enter train name > ");
        String trainName = scanner.nextLine(); // Read the train name
    
        System.out.print("Enter train model > ");
        String trainModel = scanner.nextLine(); // Read the train model
    
        int statusSelection;
        String status = "";
        System.out.println("Select the train status");
        System.out.println("1. In service");
        System.out.println("2. Out of service");
        System.out.println("3. In maintenance");
    
        do {
            System.out.print("Enter option in number stated above > ");
            statusSelection = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
    
            if (statusSelection == 1) {
                status = "In service";
            } else if (statusSelection == 2) {
                status = "Out of service";
            } else if (statusSelection == 3) {
                status = "In maintenance";
            } else {
                System.out.println("Invalid option. Please enter (1/2/3).");
            }
        } while (statusSelection != 1 && statusSelection != 2 && statusSelection != 3);
    
        // Write the added train information to the file
        try {
            FileWriter fwrite = new FileWriter("trainFile.txt", true);
            try (Writer output = new BufferedWriter(fwrite)) {
                Train tempTrain = new Train(trainName, trainModel, status);
                trainList.add(tempTrain);
                output.write(tempTrain.getTrainNo() + "\n");
                output.write(tempTrain.getTrainName() + "\n");
                output.write(tempTrain.getTrainModel() + "\n");
                output.write(tempTrain.getTrainStatus() + "\n");
                tempTrain = null;
    
                System.out.println("The train has been added.");
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            // You may want to handle this error more gracefully, e.g., by logging it.
        }
    }


    public static void deleteTrain(ArrayList<Train> trainList, Scanner scanner) {
        int trainNoInput;
        boolean found = false;
        String userInput;
    
        System.out.println("Search the train that needs to be deleted");
    
        do {
            System.out.print("Train No > ");
            trainNoInput = scanner.nextInt();
    
            for (int i = 0; i < trainList.size(); i++) {
                if (trainNoInput == trainList.get(i).getTrainNo()) {
                    found = true;
                    System.out.println("Train found. Are you sure to delete the train information as shown below? (Y-Yes/N-No)  ");
                    System.out.println(trainList.get(i).toString());
                    System.out.print("Enter your option > ");
                    userInput = scanner.next();
    
                    if (userInput.equalsIgnoreCase("Y")) {
                        trainList.remove(i); // Remove the train from the list
    
                        try (FileWriter fwrite = new FileWriter("trainFile.txt", false);
                             Writer output = new BufferedWriter(fwrite)) {
                            for (Train train : trainList) {
                                output.write(train.getTrainNo() + "\n");
                                output.write(train.getTrainName() + "\n");
                                output.write(train.getTrainModel() + "\n");
                                output.write(train.getTrainStatus() + "\n");
                            }
                        } catch (IOException e) {
                            System.err.println("Error writing to the file: " + e.getMessage());
                            // Handle this error more gracefully
                        }
    
                        System.out.println("The train has been removed.");
                    } else {
                        System.out.println("Deletion canceled.");
                    }
    
                    break; // Exit the loop since we found and processed the train
                }
            }
    
            if (!found) {
                System.out.println("Train not found. Please search again.");
            }
        } while (!found);
    }

}
