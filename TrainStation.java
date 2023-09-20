import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;



public class TrainStation implements Serializable{
      private String locationId;
      private String locationName;
      private int numOfPlatform;

      //-----------------------------------CONSTRUCTOR---------------------------------------- 
      // NO-ARG CONSTRUCTOR
      TrainStation(){
         locationId = "Undefined";
         locationName = "Undefined";
         numOfPlatform = 0;
      }

      // PARAMETERIZED CONSTRUCTOR
      TrainStation(String locationId, String locationName, int numOfPlatform){
         this.locationId = locationId;
         this.locationName = locationName;
         this.numOfPlatform = numOfPlatform;
      }

      TrainStation(String locationName, int numOfPlatform){
         locationId = locationName.charAt(0) + String.valueOf((int) (10000 + (Math.random() * (20000 - 10000 + 1))));
         this.locationName = locationName;
         this.numOfPlatform = numOfPlatform;
      }
 
      //------------------------------------METHOD----------------------------------------- 

      // GETTER
      public String getLocationId(){
         return locationId;
      }
 
      public String getLocationName(){
         return locationName;
      }

      public int getNumOfPlatform(){
        return numOfPlatform;
      }


      // SETTER
      public void changeLocationName(String locationName){
         this.locationName = locationName;
     }
      
      public void changeNumOfPlatform(int numOfPlatform){
         this.numOfPlatform = numOfPlatform;
      }

      // TO STRING
      public String toString(){
         return String.format("%-30s\t%-30s\t%10d", locationId, locationName, numOfPlatform);
      }

      // GET STATION LIST
      public ArrayList<TrainStation> getStationList() throws Exception{
         ArrayList<TrainStation> stationList = readFile("stationFile.txt");
         return stationList;
     }

      // WRITE INTO FILE
      public static boolean writeFile(String filename, ArrayList<TrainStation> stationList) throws Exception{
         boolean write = false;

         try {
            FileWriter fwrite = new FileWriter(filename, false);
            try (Writer output = new BufferedWriter(fwrite)) {
                for(int i=0; i<stationList.size(); i++){
                    output.write(stationList.get(i).getLocationId() + "\n");
                    output.write(stationList.get(i).getLocationName() + "\n");
                    output.write(stationList.get(i).getNumOfPlatform() + "\n");
                }
                write = true;
            }
        } catch (IOException e) {
            System.out.println("\nERROR WRITING TO THE FILE. \n");
        }
        return write;
    }

    // READ FROM FILE
    public static ArrayList<TrainStation> readFile(String filename) throws Exception {
        File file = new File(filename);
        ArrayList<TrainStation> stationList = new ArrayList<TrainStation>();

        if (file.exists()) {
            try (Scanner inputFile = new Scanner(file)) {
                 while (inputFile.hasNext()) {
                    String locationId = inputFile.nextLine();
                    String locationName = inputFile.nextLine();
                    int numOfPlatform = inputFile.nextInt();
                    inputFile.nextLine();
                    stationList.add(new TrainStation(locationId, locationName, numOfPlatform));          
                 }
           }
       }
        return stationList;   
    }

    // TRAIN STATION MODIFICATION (DRIVER)
    public void stationModification(Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;
    
        while (cont) {
            System.out.println("==================================================");
            System.out.println("      Train Station Information Modification");
            System.out.println("==================================================");
            System.out.println("1. View existing train station information");
            System.out.println("2. Update train station information");
            System.out.println("3. Add a new train station information");
            System.out.println("4. Delete an existing train station information");
            System.out.println("\n* Press # to go back");
            System.out.println("==================================================");
            
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
               
                if (userInput.equals("1")) {
                    viewStation();
                } else if (userInput.equals("2")) {
                    updateStationInfo(scanner);
                } else if (userInput.equals("3")) {
                    addStation(scanner);
                } else if (userInput.equals("4")) {
                    deleteStation(scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("\nINVALID OPTION. PLEASE SEARCH AGAIN.\n");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3") && !userInput.equals("4") && !userInput.equals("#"));
        }
    }

    // ----------------------------------------------VIEW STATION----------------------------------------------
    public void viewStation() throws Exception{
        ArrayList<TrainStation> stationList = getStationList();
       
        if (stationList.size()==0){
            System.out.println("\nNO STATION IN THE RECORD.\n");
        }else{
            System.out.printf("%-30s\t%-30s\t%10d\n", "Station ID", "Station Name", "No.of Platform");
            System.out.println("===========================================================================");
        }
        for (int i=0; i< stationList.size(); i++){
            System.out.println(stationList.get(i).toString() + "\n");
        }

    }

    
    // ----------------------------------------------ADD STATION----------------------------------------------
    public void addStation(Scanner scanner) throws Exception {
      String locationName;
      int numOfPlatform; 
      boolean added = false;
      boolean duplicated = false;
      String userInput;
      ArrayList<TrainStation> stationList = getStationList();

      System.out.println("==================================================");
      System.out.println("         Add Train Station Information");
      System.out.println("==================================================");

      do {
          System.out.print("Enter new station name > ");
          locationName = scanner.nextLine(); // Read the station name
          duplicated = false; // Reset duplicated to false before checking the list
      
          for (int i = 0; i < stationList.size(); i++) {
              if (locationName.equalsIgnoreCase(stationList.get(i).getLocationName())) {
                  duplicated = true;
                  System.out.println("\nTHE STATION NAME EXISTS. PLEASE USE ANOTHER NAME.\n");
                  break; // Exit the loop as soon as a duplicate is found
              }
          }
      } while (duplicated);
      
      System.out.print("Enter number of platform provided > ");
      numOfPlatform = BackendStaff.validateIntegerInput(scanner, "Enter number of platform provided > ");

      System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
      userInput = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");

      if(userInput.equalsIgnoreCase("Y")){
          stationList.add(new TrainStation(locationName, numOfPlatform));
          added = writeFile("stationFile.txt", stationList);
          if (added == true){
              System.out.println("\nSTATION HAS ADDED.\n");
          }else{
              System.out.println("\nFAILED TO ADD THE TRAIN STATION.\n");
          }
      }else{
          System.out.println("\nMODIFICATION.\n");
      }       
  }
  
  // ----------------------------------------------UPDATE STATION----------------------------------------------
  public void updateStationInfo(Scanner scanner) throws Exception{
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
   Schedule obj = new Schedule();
   ArrayList<TrainStation> stationList = getStationList();
   ArrayList<Schedule> scheduleList = obj.getScheduleList();

   System.out.println("==================================================");
   System.out.println("         Update Train station Information");
   System.out.println("==================================================");

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
           System.out.println();
           System.out.println(stationList.get(index).toString());

           System.out.print("Enter your option (Y-Yes/N-No) > ");
           userInput = BackendStaff.validateYNInput(scanner, "Enter your option (Y-Yes/N-No) > ");
           do{  
               if (userInput.equalsIgnoreCase("Y")) {
                   System.out.println("==================================================");
                   System.out.println("The field that can be updated :");
                   System.out.println("1. Train staion name");
                   System.out.println("2. Number of platform");
                   System.out.println("* Press # to exit");
                   System.out.println("==================================================");
                   
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
                                       System.out.println("\nTHE STATION NAME EXISTS. PLEASE USE ANOTHER NAME.\n");
                                       break; // Exit the loop as soon as a duplicate is found
                                   }
                               }
                           } while (duplicated);
                           System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
                           userInput2 = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");
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
                               updated = writeFile("stationFile.txt", stationList);
                               updated2 = Schedule.writeFile(scheduleList);
                               if(updated && updated2){
                                   System.out.println("\nTHE STATION HAS UPDATED.\n");
                               }else{
                                   System.out.println("\nUNABLE TO UPDATE THE STATION.\n");
                               }
                           }else{
                               System.out.println("\nMODIFICATION CANCELLED.\n");
                           }
                       } else if (userInput.equals("2")) {
                           System.out.print("Enter new number of platform > ");
                           numOfPlatform = BackendStaff.validateIntegerInput(scanner, "Enter new number of platform > ");
                           System.out.print("Do you confirm? (Y-Yes/ N-No) > ");
                           userInput2 = BackendStaff.validateYNInput(scanner, "Do you confirm? (Y-Yes/ N-No) > ");
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
                               updated = writeFile("stationfile.txt", stationList);
                               if (!scheduleList.isEmpty()){
                                    updated2 = Schedule.writeFile(scheduleList);
                               }
                               if(updated){
                                   System.out.println("\nTHE STATION HAS UPDATED.\n");
                               }else{
                                   System.out.println("\nFAILED TO UPDATE THE TRAIN STATION INFORMATION.\n");
                               }
                           }else{
                               System.out.println("\nMODIFICATION CANCELLED.\n");
                           }
                       } else if (userInput.equals("#")) {
                           cont = false;
                           return;
                       } else {
                           System.out.println("\nINVALID OPTION. PLEASE ENTER (1/2/#).\n");
                       }
                   } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("#"));
               } else {
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
           System.out.println("\nSTATION NOT FOUND. PLEASE SEARCH AGAIN.\n");       
       }
   } while (!found);
}

// ----------------------------------------------DELETE STATIONS----------------------------------------------
public void deleteStation(Scanner scanner) throws Exception {
   boolean found = false;
   boolean deleted = false;
   boolean deleted2 = false;
   String userInput;
   int index = 0;
   Schedule obj = new Schedule();
   boolean hasSchedule = false;
   ArrayList<TrainStation> stationList = getStationList();
   ArrayList<Schedule> scheduleList = obj.getScheduleList();

   System.out.println("==================================================");
   System.out.println("        Delete Train Station Information");
   System.out.println("==================================================");

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
           System.out.println();
           System.out.println(stationList.get(index).toString());
           System.out.print("Enter your option (Y-Yes/N-No) > ");
           userInput = BackendStaff.validateYNInput(scanner, "Enter your option (Y-Yes/N-No) > ");
           
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
                   System.out.println("\nPLEASE THINKS CAREFULLY AS IT WILL REMOVE THE SCHEDULE BELOW AS WELL.\n");
                    System.out.println();
                   for (int j = 0; j < scheduleList.size(); j++) {
                       if (scheduleList.get(j).getDepartLocation().getLocationName().equals(stationList.get(index).getLocationName()) ||
                           scheduleList.get(j).getArriveLocation().getLocationName().equals(stationList.get(index).getLocationName())) {
                           System.out.println(scheduleList.get(j).toString());
                           System.out.println();
                       }
                   }
               }
               System.out.print("Do you confirm to delete the station (Y-Yes/N-No)? ");
               userInput = BackendStaff.validateYNInput(scanner,"Do you confirm to delete the station (Y-Yes/N-No) ?");
               if(userInput.equalsIgnoreCase("Y")){
                Iterator<Schedule> iterator = scheduleList.iterator();
                while (iterator.hasNext()) {
                    Schedule schedule = iterator.next();
                    if (schedule.getDepartLocation().getLocationName().equals(stationList.get(index).getLocationName()) ||
                        schedule.getArriveLocation().getLocationName().equals(stationList.get(index).getLocationName())) {
                        iterator.remove();

                    }
                }
                
                   stationList.remove(index); 
                   deleted = writeFile("stationFile.txt", stationList);
                   if (hasSchedule){
                        deleted2 = Schedule.writeFile(scheduleList);
                   }
                   
                       
                   if (deleted){
                       System.out.println("\nSTATION HAS REMOVED.\n");
                   }else{
                       System.out.println("\nFAILED TO DELETE THE TRAIN STATION.\n");
                   }

               }else {
                   System.out.println("\nMODIFICATION CANCELLED.\n");
               }
           } else  {
               found = false;
           }
       }else{
           System.out.println("\nTRAIN STATION NOT FOUND. PLEASE SEARCH AGAIN.\n");
       }        
   } while (!found);
}

     
}
