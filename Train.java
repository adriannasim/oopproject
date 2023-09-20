import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;

public class Train{
    private int trainNo;
    private String trainName;
    private String trainModel;
    private static int nextTrainNo = 2900;

    //-----------------------------------CONSTRUCTOR---------------------------------------- 
    // NO-ARG CONSTRUCTOR
    public Train(){
        trainNo = 0;
        trainName = "Undefined";
        trainModel = "Undefined";
    }

    // PARAMETERIZED CONSTRUCTOR
    public Train(int trainNo,String trainName, String trainModel){
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.trainModel = trainModel;  
        nextTrainNo = this.trainNo+1;
    }

    public Train(String trainName, String trainModel){
        trainNo = nextTrainNo;
        this.trainName = trainName;
        this.trainModel = trainModel;  
        nextTrainNo++;
    }
    

    //------------------------------------METHOD-----------------------------------------

    // GETTER
    public int getTrainNo(){
        return trainNo;
    }

    public String getTrainName(){
        return trainName;
    }

    public String getTrainModel(){
        return trainModel;
    }

    // SETTER
    public void changeTrainName(String trainName){
        this.trainName = trainName;
    }

    // TOSTRING
    public String toString(){
        return String.format("%-30s\t%-30s\t%10d", trainNo, trainName, trainModel);
    }

    // READ FROM FILE
    public static ArrayList<Train> readFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        ArrayList<Train> trainList = new ArrayList<Train>();

        if (file.exists()) {
            try (Scanner inputFile = new Scanner(file)) {
                 while (inputFile.hasNext()) {
                     int trainNo = inputFile.nextInt();
                     inputFile.nextLine(); 
                     String trainName = inputFile.nextLine();
                     String trainModel = inputFile.nextLine();
                     trainList.add(new Train(trainNo, trainName, trainModel));          
                 }
           }
       }
        return trainList;
   }

    // GET TRAIN LIST
    public ArrayList<Train> getTrainList() throws FileNotFoundException{
        ArrayList<Train> trainList = readFile("trainFile.txt");
        return trainList;
    }

    // WRITE INTO FILE
    public static boolean writeFile(String filename, ArrayList<Train> trainList)  throws FileNotFoundException {
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
            System.out.println("\nERROR WRITING TO THE FILE. \n");
        }
        return write;
    }

    // TRAIN MODIFICATION (DRIVER)
    public void trainModification(Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;
    
        while(cont==true){
            System.out.println("==================================================");
            System.out.println("          Train Information Modification");
            System.out.println("==================================================");
            System.out.println("1. View existing train information");
            System.out.println("2. Update an existing train information");
            System.out.println("3. Add a new train information");
            System.out.println("4. Delete an existing train information");
            System.out.println("\n* Press # to go back");
            System.out.println("==================================================");
           
            do{
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();

                if (userInput.equals("1")) {
                    viewTrain();
                } else if (userInput.equals("2")) {
                    updateTrainInfo(scanner);
                } else if (userInput.equals("3")) {
                    addTrain(scanner);
                } else if (userInput.equals("4")) {
                    deleteTrain(scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("\nINVALID INPUT. PLEASE ENTER (1/2/3/4/#).\n");
                }     
            }while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    // ----------------------------------------------VIEW TRAINS----------------------------------------------
    public void viewTrain() throws Exception{
        ArrayList<Train> trainList = getTrainList();
        
        if (trainList.size()==0){
            System.out.println("\nNO TRAINS IN THE RECORD.\n");
        }else{
            System.out.printf("%-30s\t%-30s\t%10d\n", "Train No", "Train Name", "Train Model");
            System.out.println("===========================================================================");
        }
        for (int i=0; i< trainList.size(); i++){
            System.out.println(trainList.get(i).toString() + "\n");
        }

    }

    // ----------------------------------------------ADD TRAIN----------------------------------------------
    public void addTrain(Scanner scanner) throws FileNotFoundException {
        String trainName;
        String trainModel; 
        String userInput;
        boolean added = false;
        ArrayList<Train> trainList = getTrainList();

        System.out.println("==================================================");
        System.out.println("              Add Train Information");
        System.out.println("==================================================");
            
        System.out.print("Enter train name > ");
        trainName = scanner.nextLine(); 
    
        System.out.print("Enter train model > ");
        trainModel = scanner.nextLine(); 

        System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
        userInput = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

        if(userInput.equalsIgnoreCase("Y")){
            trainList.add(new Train(trainName, trainModel));
            added = writeFile("trainFile.txt", trainList);
            if (added == true){
                System.out.println("\nTRAIN HAS ADDED\n");
            }else{
                System.out.println("\nFAILED TO ADD THE TRAIN.\n");
            }
        }else{
            System.out.println("\nMODIFICATION CANCELLED.\n");
        }
        
    }

    // ----------------------------------------------UPDATE TRAIN----------------------------------------------
    public void updateTrainInfo(Scanner scanner) throws Exception {
        String userInput;
        String userInput2;
        String trainName;
        int trainNo;
        int index = 0;
        boolean cont = true;
        boolean found = false;
        boolean updated = false;
        Train obj = new Train();
        Schedule obj2 = new Schedule();
        ArrayList<Train> trainList = obj.getTrainList();
        ArrayList<Schedule> scheduleList = obj2.getScheduleList();

        System.out.println("==================================================");
        System.out.println("              Update Train Information");
        System.out.println("==================================================");
        do{
            System.out.print("Enter train no to search the train (Press # to exit) > ");
            trainNo = BackendStaff.validateIntegerInput(scanner, "Enter train no to search the train (Press # to exit) > ");

            if (trainNo==-1)
                break;
    
            for (int i = 0; i < trainList.size(); i++) {
                if (trainNo==trainList.get(i).getTrainNo()) {
                    found = true;
                    index = i;
                 }
            }
            if (found == true){
                System.out.println("Do you want to update the train information as shown below ?");
                System.out.println();
                System.out.println(trainList.get(index).toString());
                
                System.out.print("Enter your option (Y-Yes/N-No) > ");
                userInput = BackendStaff.validateYNInput(scanner, "Enter your option (Y-Yes/N-No) > ");
                do{
                    if(userInput.equalsIgnoreCase("Y")){
                        System.out.println("==================================================");
                        System.out.println("The field that can be updated :");
                        System.out.println("1. Train name");
                        System.out.println("* Press # to exit");
                        System.out.println("==================================================");
                       
                        do {
                            System.out.print("Enter option in number stated above > ");
                            userInput = scanner.nextLine();

                            if (userInput.equals("1")) {
                                System.out.print("Enter new train name > ");
                                trainName = scanner.nextLine();
                                System.out.print("Do you confirm with the changes? (Y-Yes/N-No) > ");
                                userInput2 = BackendStaff.validateYNInput(scanner, "Do you confirm with the changes? (Y-Yes/N-No) > ");
                                if (userInput2.equalsIgnoreCase("Y")){
                                    for (Schedule schedule : scheduleList) {
                                        if (schedule.getOperatedTrain().getTrainNo()==(trainList.get(index).getTrainNo())) {
                                            schedule.getOperatedTrain().changeTrainName(trainName);
                                        }
                                    }
                                    trainList.get(index).changeTrainName(trainName);
                                    updated = writeFile("trainfile.txt", trainList);
                                    if (!scheduleList.isEmpty()){
                                        Schedule.writeFile(scheduleList);
                                    }
                                    
                                    if (updated){
                                        System.out.println("\nTRAIN INFORMATION HAS UPDATED.\n");
                                    }else{
                                        System.out.println("\nFAILED TO UPDATE THE TRAIN INFORMATION.\n");
                                    }
                                }else{
                                    System.out.println("\nMODIFICATION CANCELLED.\n");
                                }     
                            } else if (userInput.equals("#")){
                                cont = false;
                                return; 
                            }else {
                                System.out.println("\nINVALID OPTION. PLEASE ENTER (1/#).\n");
                            }            
                        } while (!userInput.equals("1") && !userInput.equals("#"));
                
                    }else {
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
                }while(cont==true);
            }else{
                System.out.println("\nTRAIN NOT FOUND. PLEASE SEARCH AGAIN.\n");
            }  
        } while (!found);      
    }

    // ----------------------------------------------DELETE TRAIN----------------------------------------------
    public void deleteTrain(Scanner scanner) throws Exception {
        int trainNo;
        boolean found = false;
        boolean deleted = false;
        boolean deleted2 = false;
        boolean hasSchedule = false;
        String userInput;
        int index = 0;
        Train obj = new Train();
        Schedule obj2 = new Schedule();
        ArrayList<Train> trainList = obj.getTrainList();
        ArrayList<Schedule> scheduleList = obj2.getScheduleList();
    
        System.out.println("==================================================");
        System.out.println("              Delete Train Information");
        System.out.println("==================================================");
    
        do {
            System.out.print("Enter train no to search the train (Press # to exit) > ");
            trainNo = BackendStaff.validateIntegerInput(scanner, "Enter train no to search the train (Press # to exit) > ");

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
                System.out.println();
                System.out.println(trainList.get(index).toString());
                System.out.println();
                System.out.print("Enter your option (Y-Yes/N-No) > ");
                userInput = BackendStaff.validateYNInput(scanner, "Enter your option (Y-Yes/N-No) > ");
    
                if (userInput.equalsIgnoreCase("Y")) {
                    for (int j=0; j<scheduleList.size(); j++){
                        if (scheduleList.get(j).getOperatedTrain().getTrainNo()==trainList.get(index).getTrainNo()){
                            hasSchedule = true;
                            break;
                        }   
                    }
                    if(hasSchedule){
                        System.out.println("\nPLEASE THINK CAREFULLY AS IT WILL REMOVE THE SCHEDULE BELOW AS WELL.\n");
                        System.out.println();
                        for (int j=0; j<scheduleList.size(); j++){
                            if (scheduleList.get(j).getOperatedTrain().getTrainNo()==trainList.get(index).getTrainNo()){
                                System.out.println(scheduleList.get(j).toString());
                                System.out.println();
                            }   
                        }
                    }        
    
                    System.out.print("Do you confirm? (Y-Yes/N-No) > ");
                    userInput = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/N-No) > ");
    
                    if (userInput.equalsIgnoreCase("Y")) {
                            
                        // Remove all associated schedules
                        Iterator<Schedule> iterator = scheduleList.iterator();
                        while (iterator.hasNext()) {
                            Schedule schedule = iterator.next();
                            if (schedule.getOperatedTrain().getTrainNo() == trainList.get(index).getTrainNo()) {
                                iterator.remove();
                            }
                        }
                        trainList.remove(index);
                        deleted = writeFile("trainFile.txt", trainList);
                        deleted2 = Schedule.writeFile(scheduleList);

                        if (deleted && deleted2) {
                            System.out.println("\nTRAIN INFORMATION HAS REMOVED.\n");
                        }else{
                            System.out.println("\nFAILED TO REMOVE THE TRAIN INFORMATION.\n");
                        }
                                
                    } else {
                        System.out.println("\nMODIFICATION CANCELLED.\n");
                    }
                                                     
                } else {
                    found = false;
                }
                
            } else {
                System.out.println("\nTRAIN NOT FOUND. PLEASE SEARCH AGAIN.\n");
            }
           
        } while (!found);
    }

}