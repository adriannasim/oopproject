import java.util.*;
import java.io.*;

public class DriveJh {
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
           System.out.println("3. Food and Beverage Information Modification");
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
                   stationModification();
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

    // Method to write the train information to a file 
   public static boolean writeTrainIntoFile(ArrayList<Train> trainList) throws FileNotFoundException {
        boolean write = false;
        try {
            FileWriter fwrite = new FileWriter("trainFile.txt", false);
            try (Writer output = new BufferedWriter(fwrite)) {
                for(int i=0; i<trainList.size(); i++){
                    output.write(trainList.get(i).getTrainNo() + "\n");
                    output.write(trainList.get(i).getTrainName() + "\n");
                    output.write(trainList.get(i).getTrainModel() + "\n");
                    output.write(trainList.get(i).getTrainStatus() + "\n");
                }
                write = true;
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        return write;
    }

    // Method to modify train information
    public static void trainModification(ArrayList<Train> trainList, Scanner scanner) throws FileNotFoundException {
        String userInput = "";
        boolean cont = true;

        while(cont==true){
            // Display the user option menu
            System.out.println("1. View existing train information");
            System.out.println("2. Add a new train");
            System.out.println("3. Delete an existing train information");
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
                    addTrain(trainList, scanner);
                } else if (userInput.equals("3")) {
                    deleteTrain(trainList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                    break; // Exit the loop
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
                
            }while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("#"));
        }
    }


    // Method to add a new train information
    public static void addTrain(ArrayList<Train> trainList, Scanner scanner) throws FileNotFoundException {
        boolean added = false;
        scanner.nextLine();
        System.out.print("Enter train name > ");
        String trainName = scanner.nextLine(); // Read the train name
    
        System.out.print("Enter train model > ");
        String trainModel = scanner.nextLine(); // Read the train model

        Train tempTrain = new Train(trainName, trainModel);
        trainList.add(tempTrain);
        added = writeTrainIntoFile(trainList);
        if (added == true){
            System.out.println("Train has added.");
        }
        tempTrain = null;    
    }


    public static void deleteTrain(ArrayList<Train> trainList, Scanner scanner) throws FileNotFoundException {
        int trainNoInput;
        boolean found = false;
        boolean deleted = false;
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
                        deleted = writeTrainIntoFile(trainList);        
                    } else {
                        System.out.println("Deletion canceled.");
                    }
                    break; // Exit the loop since we found and processed the train
                }
            }
            if (deleted == true){
                 System.out.println("Train has removed.");
            }
            if (!found) {
                System.out.println("Train not found. Please search again.");
            }
        } while (!found);
    }

}
