import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BackendStaff extends Staff {
    //variables declaration
    private static int nextBackendStaffId;
    private static final String TIME_REGEX = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
    private static final Pattern TIME_PATTERN = Pattern.compile(TIME_REGEX);

    //getter
    public int getBackendStaffId() {
        readStaffInfo();
        nextBackendStaffId = 1000;
        int count = 0;
        for (int i = 0; i < staffDetails.size(); i++) {
            if (staffDetails.get(i).getStaffType() == 'B') {
                count++;
            }
        }
        return nextBackendStaffId + count;
    }

    //------------------------------------------VALIDATE DOUBLE INPUT-------------------------------------------- 

    public static double validateDoubleInput(Scanner scanner, String str) {

        while (true) {
            try {
                String userInput = scanner.nextLine();
                return Double.parseDouble(userInput);
            } catch (NumberFormatException e) {
                System.out.println("\nINVALID INPUT. PLEASE ENTER A VALID PRICE.\n");
                System.out.print(str);
            }
        }
    }
    
    //-----------------------------------------VALIDATE INTEGER INPUT-------------------------------------------- 

    public static int validateIntegerInput(Scanner scanner, String str) {
        while (true) {
            try {
                String userInput = scanner.nextLine();
                if(userInput.equals("#")){
                    return -1;
                }
                return Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("\nINVALID INPUT. PLEASE ENTER A NUMBER.\n");
                System.out.print(str);
            }
        }
    }

    //------------------------------------------VALIDATE Y/N INPUT---------------------------------------------- 

    public static String validateYNInput(Scanner scanner, String str) {
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("Y")||userInput.equalsIgnoreCase("N")){
                return userInput;
            }else{
                System.out.println("\nINVALID INPUT. PLEASE ENTER (Y/N).\n");
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


    //backend menu
    public void backendMenu() throws Exception{
        Scanner input = new Scanner(System.in); //scanner
        boolean loop = true;
        Train train = new Train();
        Schedule schedule = new Schedule();
        TrainStation station = new TrainStation();

        do {
            System.out.println("==================================================");
            System.out.println("                Backend Staff Menu");
            System.out.println("==================================================");
            System.out.printf("1. Train Information Modification\n");
            System.out.printf("2. Schedule Modification\n");
            System.out.printf("3. Train Station Information Modification\n");
            System.out.printf("4. Food and Beverage Modification\n");
            System.out.printf("5. Reports\n");
            System.out.println("\n* Press # to logout");
            System.out.println("==================================================");
            System.out.print("Enter your option > ");

            //accept user input
            if (input.hasNextInt()) {
                int choice = BackendStaff.validateIntegerInput(input, "\nINVALID OPTION. PLEASE ENTER (1/2/3/4/#).\n");

                //choose user's input choice
                switch (choice) {
                //Train Modification Menu
                case 1:
                    train.trainModification(input);      
                    break;
                //Schedule Modification
                case 2:
                    schedule.scheduleModification(input);
                    break;
                //Train Station Information Modification
                case 3:
                    station.stationModification(input);
                    break;
                //FnB Modification
                case 4: 
                    foodAndBeverageModification(input);
                    break;
                //Reports
                case 5:
                    DriverTw.viewReport();
                    break;
                default:
                System.out.println("\nINVALID OPTION. PLEASE ENTER (1/2/3/4/#).\n");
                }
            } else {
                if (input.next().equals("#")) {
                    System.out.printf("Logged out.\n\n");
                    return;
                } else {
                    System.out.println("\nINVALID OPTION. PLEASE ENTER (1/2/3/4/#).\n");
                    //clear buffer
                    input.next();
                }
            }
        } while (loop);


    }

    //create backend account
    public void createBackendStaff() {
        //variables declaration
        String regex = "^[a-zA-Z0-9 ]+$";  //regex with space
        String regex2 = "^[a-zA-Z0-9]+$";  //regex without space
        boolean check = true;  //loop
        String inUsername, inPassword, inFullname, inEmail, inStaffId;
        char inStaffType;
        
        //methods declaration
        Scanner input = new Scanner(System.in); //scanner

        System.out.println("==================================================");
        System.out.println("            Create Backend Staff Login");
        System.out.println("==================================================");
        System.out.printf("Enter 'X' to exit at any point\n\n");

        //set staff type
        inStaffType = 'B';
        //set staff id
        inStaffId = 'B' + Integer.toString(getBackendStaffId());
        System.out.printf("=================================\n");
        System.out.printf("STAFF ID: %s\n", inStaffId);
        System.out.printf("=================================\n");

        //username
        do {
            System.out.printf("Please enter your username (no spaces/special characters) > ");
            inUsername = input.nextLine();
            //check if username exist in custFile.txt
            readStaffInfo();
            for (int i = 0; i < staffDetails.size(); i++) {
                if (inUsername.equals(staffDetails.get(i).getUsername())) {
                    System.out.printf("This username is already in used. Please login using your existing username.\n");
                    return;
                }
            }
            if (!inUsername.matches(regex2)) {
                System.out.printf("No spaces/special characters allowed. Please try again.\n");
            } else if (inUsername.equalsIgnoreCase("X")) {
                return;
            } else {
                check = false;
            }
        } while (check);
        //password
        check = true;
        do {
            System.out.printf("Please enter your password (no spaces) > ");
            inPassword = input.nextLine();
            if (inPassword.contains(" ")) {
                System.out.printf("No spaces allowed. Please try again.\n");
            } else if (inPassword.equalsIgnoreCase("X")) {
                return;
            } else {
                check = false;
            }
        } while (check);
        //fullname
        check = true;
        do {
            System.out.printf("Please enter your full name (no special characters) > ");
            inFullname = input.nextLine();
            if (!inFullname.matches(regex)) {
                System.out.printf("No special characters allowed. Please try again.\n");
            } else if (inFullname.equalsIgnoreCase("X")) {
                return;
            } else {
                check = false;
            }
        } while (check);
        //email
        check = true;
        do {
            System.out.printf("Please enter your email (no spaces) > ");
            inEmail = input.nextLine();
            if (inEmail.contains(" ")) {
                System.out.printf("No spaces allowed. Please try again.\n");
            } else if (inEmail.equalsIgnoreCase("X")) {
                return;
            } else {
                check = false;
            }
        } while (check);

        Staff staff = new Staff(inUsername, inPassword, inFullname, inEmail, inStaffId, inStaffType);
        writeStaffInfo();
        System.out.println("Registration successful. Please login now.");
        input.close();
    }

    public void foodAndBeverageModification(Scanner scanner) throws Exception {
        String userInput = "";
        boolean cont = true;
        Snacks snacks = new Snacks();
        Drinks drinks = new Drinks();
<<<<<<< HEAD
        ArrayList<Snacks> snacksList = snacks.getSnacksList();
=======
>>>>>>> 092b3d63d51866971a74e46e67dcd851d1d3eb41
    
        while (cont) {
            System.out.println("==================================================");
            System.out.println("   Food and Beverage Information Modification");
            System.out.println("==================================================");
            System.out.println("1. Snacks modification");
            System.out.println("2. Drinks modification");
            System.out.println("\n* Press # to go back");
            System.out.println("==================================================");
            
            do {
                System.out.print("Enter your option > ");
                userInput = scanner.nextLine();
               
                if (userInput.equals("1")) {
                    snacks.snacksModification(scanner);
                } else if (userInput.equals("2")) {
                    drinks.drinksModification(scanner);
                } else if (userInput.equals("#")) {
                    cont = false;
                } else {
                    System.out.println("\nINVALID OPTION. PLEASE ENETER (1/2/#).\n");
                }
    
            } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("#"));
        }
    }
}