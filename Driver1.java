import java.util.*;
import java.io.*;
public class Driver1 {

    public static ArrayList<Train> readTrainFromFile(String filename) throws FileNotFoundException{
               ArrayList<Train> trainList = new ArrayList<Train>();
               File file = new File(filename);
               Scanner inputFile;
               int trainNo;
               String trainType;
               String trainModel;
               String status;

               if(file.exists()){
                inputFile = new Scanner(file);
                while(inputFile.hasNext()){
                    trainNo = inputFile.nextInt();
                    inputFile.nextLine();
                    trainType = inputFile.nextLine();
                    trainModel = inputFile.nextLine();
                    status = inputFile.nextLine();
                    trainList.add(new Train(trainNo, trainType, trainModel, status));
                }


               }
           
        return trainList;
    }
    public static void main(String[] args) throws FileNotFoundException{

        String userOpt;
        ArrayList<Train> trainList;
        trainList = readTrainFromFile("trainFile.txt");
        
        

        // Use try-with-resources to automatically close the Scanner
        try (Scanner input = new Scanner(System.in)) {

            System.out.println("1. Train Information Modification");
            System.out.println("2. Schedule Modification");
            System.out.println("3. Train Station Information Modification");
            System.out.println("( Enter '#' to go back)");

            do {
                System.out.print("Enter your option > ");
                userOpt = input.next();

                if (userOpt.equals("1")) {
                    trainModification(trainList);
                } else if (userOpt.equals("2")) {
                    scheduleModification();
                } else if (userOpt.equals("3")) {
                    stationModification();
                } else if (userOpt.equals("#")) {

                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/#).");
                }
            } while (!userOpt.equals("1") && !userOpt.equals("2") && !userOpt.equals("3") && !userOpt.equals("#"));
        } // Scanner will be automatically closed here
    }

    public static void trainModification(ArrayList<Train> trainList) {
        String userOpt;
        
        // Use try-with-resources to automatically close the Scanner
        try (Scanner input = new Scanner(System.in)) {

            System.out.println("1. View existing train information");
            System.out.println("2. Modify existing train information");
            System.out.println("3. Add a new train");

            do {
                System.out.print("Enter your option > ");
                userOpt = input.next();

                if (userOpt.equals("1")) {
                    for (int i = 0; i<trainList.size(); i++){
                        System.out.println(trainList.get(i).toString());
                        System.out.println();
                    }
                } else if (userOpt.equals("2")) {
                    
                } else if (userOpt.equals("3")) {
                    
                } else if (userOpt.equals("#")) {

                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/#).");
                }
            } while (!userOpt.equals("1") && !userOpt.equals("2") && !userOpt.equals("3") && !userOpt.equals("#"));
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
