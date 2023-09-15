import java.util.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class DriveJh {
    public static void main(String[] args) throws Exception {
        ArrayList<Train> trainList;
        ArrayList<TrainStation> stationList;
        ArrayList<Schedule> scheduleList;
        String userInput = "";
        Scanner scanner = new Scanner(System.in);
        boolean cont = true;
        // Invoke readTrainFromFile() method to read train information from the file
       trainList = readTrainFromFile("trainFile.txt");
       // Invoke readStationFromFile() method to read station information from the file
       stationList = readStationFromFile("stationFile.txt");
       scheduleList = readScheduleFromFile("scheduleFile.txt");

       do{
           System.out.println("1. Train Information Modification");
           System.out.println("2. Schedule Modification");
           System.out.println("3. Train Station Information Modification");
           System.out.println("4. Food and Beverage Information Modification");
           System.out.println("( Enter '#' to go back)");

           do{
               System.out.print("Enter your option > ");
               userInput = "";
               userInput = scanner.next();
               
               // Compare user input and invoke specific method
               if (userInput.equals("1")) {
                   trainModification(trainList, scanner);
               } else if (userInput.equals("2")) {
                   sheduleModification(scheduleList, scanner, stationList, trainList);
               } else if (userInput.equals("3")) {
                   stationModification(stationList, scanner);
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

    // Method to read the Train Station information from a file & store it into stationList ArrayList<TrainStation>
   public static ArrayList<TrainStation> readStationFromFile(String filename) throws FileNotFoundException {
         ArrayList<TrainStation> stationList = new ArrayList<TrainStation>();
         File file = new File(filename);

         if (file.exists()) {
             try (Scanner inputFile = new Scanner(file)) {
                  while (inputFile.hasNext()) {
                      String locationId = inputFile.nextLine();
                      String locationName = inputFile.nextLine();
                      int numOfPlatform = inputFile.nextInt();
                      inputFile.nextLine(); // Consume newline
                      String status = inputFile.nextLine();
                      stationList.add(new TrainStation(locationId, locationName, numOfPlatform, status));
                  }
            }
        }
        return stationList;
    }

    // Method to write the train station information to a file 
   public static boolean writeStationIntoFile(ArrayList<TrainStation> stationList) throws FileNotFoundException {
        boolean write = false;
        try {
            FileWriter fwrite = new FileWriter("stationFile.txt", false);
            try (Writer output = new BufferedWriter(fwrite)) {
                for(int i=0; i<stationList.size(); i++){
                    output.write(stationList.get(i).getLocationId() + "\n");
                    output.write(stationList.get(i).getLocationName() + "\n");
                    output.write(stationList.get(i).getNumOfPlatform() + "\n");
                    output.write(stationList.get(i).getStatus() + "\n");
                }
                write = true;
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        return write;
    }

    public static ArrayList<Schedule> readScheduleFromFile(String filename) throws Exception {
        ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
        File file = new File(filename);
        ObjectInputStream input = null; // Declare outside the try block
    
        try {
            input = new ObjectInputStream(new FileInputStream(file));
    
            while (true) {
                try {
                    Schedule s = (Schedule) input.readObject();
                    scheduleList.add(s);
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
        
        return scheduleList;
    }
    

    public static boolean writeScheduleIntoFile(String filename, ArrayList<Schedule> scheduleList) {
        boolean write = false;
        File file = new File(filename);
    
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < scheduleList.size(); i++) {
                oos.writeObject(scheduleList.get(i));
            }
            write = true; // Set write to true if writing is successful
        } catch (IOException e) {
            // Handle any IOException that might occur during writing
            e.printStackTrace();
            // write remains false if an error occurs
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
            System.out.println("2. Add a new train information");
            System.out.println("3. Delete an existing train information");
            System.out.println("4. Update an existing train information");
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
                } else if (userInput.equals("4")) {
                    updateTrainInfo(trainList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                    break; // Exit the loop
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
                
            }while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
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

    public static void updateTrainInfo(ArrayList<Train> trainList, Scanner scanner) throws FileNotFoundException {
        int trainNo;
        String newName;
        int userInput;
        boolean found = false;
        boolean updated = false;

        scanner.nextLine();
        System.out.println("Search the train that needs to be updated");
        do {
            System.out.print("Train No > ");
            trainNo = scanner.nextInt();

            for (int i = 0; i < trainList.size(); i++) {
                if (trainNo==trainList.get(i).getTrainNo()) {
                    found = true;
                    System.out.println(trainList.get(i).toString());
                    System.out.println("The field that can be updated :");
                    System.out.println("1. Train Name");
                    System.out.println("2. Go back");

                    do {
                        System.out.print("Enter option in number stated above > ");
                        
                        userInput = scanner.nextInt();
                        scanner.nextLine();

                        if (userInput == 1) {
                            System.out.print("New train name > ");
                            newName = scanner.nextLine();
                            trainList.get(i).chnageTrainName(newName);
                        } else if (userInput == 2){
                            break; // Exit the loop
                        }else {
                            System.out.println("Invalid option. Please enter (1).");
                        }
                    } while (userInput != 1 && userInput != 2);

                    updated = writeTrainIntoFile(trainList);

                 }
            }

            if (!found) {
                System.out.println("Train not found. Please search again.");
            }

            
            if (updated){
                System.out.println("Train information has updated.");
            }
        } while (!found);  
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


    // Method to modify train information
    public static void stationModification(ArrayList<TrainStation> stationList, Scanner scanner) throws FileNotFoundException {
        String userInput = "";
        boolean cont = true;
    
        while (cont) {
            // Display the user option menu
            System.out.println("1. View existing train station information");
            System.out.println("2. Add a new train station information");
            System.out.println("3. Update train station information");
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
                    addStation(stationList, scanner);
                } else if (userInput.equals("3")) {
                    updateStationInfo(stationList, scanner);
                } else if (userInput.equals("4")) {
                    deleteStation(stationList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                    break; // Exit the loop
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    // Method to add a new train information
    public static void addStation(ArrayList<TrainStation> stationList, Scanner scanner) throws FileNotFoundException {
        boolean added = false;
        scanner.nextLine();
        System.out.print("Enter station name > ");
        String locationName = scanner.nextLine(); // Read the train name
    
        System.out.print("Enter number of platform available > ");
        int numOfPlatform = scanner.nextInt(); // Read the train model

        TrainStation tempStation = new TrainStation(locationName, numOfPlatform);
        stationList.add(tempStation);
        added = writeStationIntoFile(stationList);
        if (added == true){
            System.out.println("Station has added.");
        }
        tempStation = null;    
    }
    
    public static void updateStationInfo(ArrayList<TrainStation> stationList, Scanner scanner) throws FileNotFoundException {
        String locationName;
        String newName;
        int num;
        int userInput;
        boolean found = false;
        boolean updated = false;

        scanner.nextLine();
        System.out.println("Search the station that needs to be updated");
        do {
            System.out.print("Station Name > ");
            locationName = scanner.nextLine();

            for (int i = 0; i < stationList.size(); i++) {
                if (locationName.equals(stationList.get(i).getLocationName())) {
                    found = true;
                    System.out.println(stationList.get(i).toString());
                    System.out.println("Select a field to update :");
                    System.out.println("1. Station name");
                    System.out.println("2. Number of platform");

                    do {
                        System.out.print("Enter option in number stated above > ");
                        
                        userInput = scanner.nextInt();
                        scanner.nextLine();

                        if (userInput == 1) {
                            System.out.print("New station name > ");
                            newName = scanner.nextLine();
                            stationList.get(i).changeLocationName(newName);
                        } else if (userInput == 2) {
                            System.out.print("New number of platform > ");
                            num = scanner.nextInt();
                            stationList.get(i).changeNumOfPlatform(num);
                        } else if (userInput == 3) {
                            
                        } else {
                            System.out.println("Invalid option. Please enter (1/2/3).");
                        }
                    } while (userInput != 1 && userInput != 2 && userInput != 3);

                 }
            }

            if (!found) {
                System.out.println("Train station not found. Please search again.");
            }

            updated = writeStationIntoFile(stationList);
            if (updated){
                System.out.println("Train station information has updated.");
            }else{
                System.out.println("Update failed.");
            }
        } while (!found);  
    }

    public static void deleteStation(ArrayList<TrainStation> stationList, Scanner scanner) throws FileNotFoundException {
        String locationName;
        boolean found = false;
        boolean deleted = false;
        String userInput;
    
        scanner.nextLine();
        System.out.println("Search the station that needs to be deleted");
        do {
            System.out.print("Station name > ");
            locationName = scanner.nextLine();
    
            for (int i = 0; i < stationList.size(); i++) {
                if (locationName.equals(stationList.get(i).getLocationName())) {
                    found = true;
                    System.out.println("Station found. Are you sure to delete the station information as shown below? (Y-Yes/N-No)  ");
                    System.out.println(stationList.get(i).toString());
                    System.out.print("Enter your option > ");
                    userInput = scanner.next();
    
                    if (userInput.equalsIgnoreCase("Y")) {
                        stationList.remove(i); // Remove the train from the list
                        deleted = writeStationIntoFile(stationList);
                    } else {
                        System.out.println("Deletion canceled.");
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

    // Method to modify schedule information
    public static void sheduleModification(ArrayList<Schedule> scheduleList, Scanner scanner, ArrayList<TrainStation> stationList, ArrayList<Train> trainList) throws Exception {
        String userInput = "";
        boolean cont = true;
    
        while (cont) {
            // Display the user option menu
            System.out.println("1. View schedule");
            System.out.println("2. Add a new schedule");
            System.out.println("3. Update existing schedule");
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
                    addSchedule(scheduleList, scanner, stationList, trainList);
                } else if (userInput.equals("3")) {
                    updateScheduleInfo(scheduleList, scanner, stationList, trainList);
                } else if (userInput.equals("4")) {
                    deleteSchedule(scheduleList, scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                    break; // Exit the loop
                } else {
                    System.out.println("Invalid option. Please enter (1/2/3/4/#).");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    // Method to add a new train information
    public static void addSchedule(ArrayList<Schedule> scheduleList, Scanner scanner, ArrayList<TrainStation> stationList, ArrayList<Train> trainList) throws Exception {
        TrainStation departLocation;
        TrainStation arriveLocation;
        LocalTime departTime = LocalTime.of(0,0);
        LocalTime arriveTime = LocalTime.of(0,0);
        Train trainOperated;
        double ticketPrice;
        String userInput = "";

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
                System.out.println((i+1) + ". " + stationList.get(i).getLocationName());
        }
        System.out.print("Enter the station number stated above > ");
        userInput = scanner.nextLine(); 
        uIn = Integer.parseInt(userInput);
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
        added = writeScheduleIntoFile("scheduleFile.txt", scheduleList);
        
        if (added == true){
            System.out.println("Schedule has added.");
        }
        s = null;    
    }

    public static void updateScheduleInfo(ArrayList<Schedule> scheduleList, Scanner scanner, ArrayList<TrainStation> stationList, ArrayList<Train> trainList) throws FileNotFoundException {
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

        scanner.nextLine();
        System.out.println("Search the schedule that needs to be updated");
        do {
            System.out.print("Schedule id > ");
            scheduleId = scanner.nextLine();

            for (int i = 0; i < scheduleList.size(); i++) {
                if (scheduleId.equals(scheduleList.get(i).getScheduleId())) {
                    found = true;
                    System.out.println(scheduleList.get(i).toString());
                    System.out.println("Select a field to update :");
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
                                    System.out.println("Departure location: " + scheduleList.get(i).getDepartLocation().getLocationName());
                                    System.out.println("Select a new departure station : ");
                                    for (int j = 0; j < stationList.size(); j++) {
                                        System.out.println((j+1) + ". " + stationList.get(j).getLocationName());
                                    }
                                    System.out.print("Enter the station number stated above > ");
                                    userInput3 = scanner.nextLine(); 
                                    int uIn = Integer.parseInt(userInput3);
                                    departLocation = stationList.get(uIn-1);
                                    scheduleList.get(i).editDepartLocation(departLocation);
                                }else if(userInput2.equals("2")){
                                    System.out.println("Departure time: " + scheduleList.get(i).getDepartTime());
                                    System.out.print("Enter a new departure time : ");
                                    userInput3 = scanner.nextLine(); 
                                    try {
                                        departTime = LocalTime.parse(userInput3);
                                    } catch (DateTimeParseException e) {
                                        System.err.println("Invalid departure time format. Please use HH:MM.");
                                    }
                                    scheduleList.get(i).editDepartTime(departTime);
                                }else if(userInput2.equals("#")){
                                    break;
                                }else{
                                    System.out.println("Invalid input. please enter the option as stated above.");
                                }

                            }while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("#"));
       
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
                                    for (int j = 0; j < stationList.size(); j++) {
                                        System.out.println((j+1) + ". " + stationList.get(j).getLocationName());
                                    }
                                    System.out.print("Enter the station number stated above > ");
                                    userInput3 = scanner.nextLine(); 
                                    int uIn = Integer.parseInt(userInput3);
                                    arriveLocation = stationList.get(uIn-1);
                                    scheduleList.get(i).editArriveLocation(arriveLocation);
                                }else if(userInput2.equals("2")){
                                    System.out.println("Arrival time: " + scheduleList.get(i).getArriveTime());
                                    System.out.print("Enter a new arrival time : ");
                                    userInput3 = scanner.nextLine(); 
                                    try {
                                        arriveTime = LocalTime.parse(userInput3);
                                    } catch (DateTimeParseException e) {
                                        System.err.println("Invalid arrival time format. Please use HH:MM.");
                                    }
                                    scheduleList.get(i).editArriveTime(arriveTime);
                                }else if(userInput2.equals("#")){
                                    break;
                                }else{
                                    System.out.println("Invalid input. please enter the option as stated above.");
                                }

                            }while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("#"));
                            
                        } else if (userInput.equals("3")) {
                            System.out.println("Train operated: " + scheduleList.get(i).getOperatedTrain().toString());
                            System.out.println("Select a new train to replace train above: ");
                            for (int j = 0; j < trainList.size(); j++) {
                                System.out.println((j+1) + ". " + trainList.get(j).getTrainNo());
                            }
                            System.out.print("Enter the train number stated above > ");
                            userInput = scanner.nextLine(); 
                            int uIn = Integer.parseInt(userInput);
                            trainOperated = trainList.get(uIn-1);
                            scheduleList.get(i).editTrainOperated(trainOperated);
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
            updated = writeScheduleIntoFile("scheduleFile.txt",scheduleList);
            if (updated){
                System.out.println("Schedule information has updated.");
            }else{
                System.out.println("Update failed.");
            }
            updated = false;
        } while (!found);  
    }


    public static void deleteSchedule(ArrayList<Schedule> scheduleList, Scanner scanner) throws Exception {
        String scheduleId;
        boolean found = false;
        String userInput;
        boolean deleted = false;
        scanner.nextLine();
        do {
            System.out.print("Enter the schedule id that need to be deleted > ");
            scheduleId = scanner.nextLine();
            if (scheduleId.equals("#"))
                break;
    
            for (int i = 0; i < scheduleList.size(); i++) {
                if (scheduleId.equals(scheduleList.get(i).getScheduleId())) {
                    found = true;
                    System.out.println("schedule found. Are you sure to delete the schedule information as shown below?  ");
                    System.out.println(scheduleList.get(i).toString());
                    System.out.print("Enter your option (Y-Yes/N-No)> ");
                    userInput = scanner.next();
    
                    if (userInput.equalsIgnoreCase("Y")) {
                        scheduleList.remove(i); // Remove the train from the list
                        deleted = writeScheduleIntoFile("scheduleFile.txt",scheduleList);
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

}
