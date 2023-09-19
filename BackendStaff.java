import java.util.Scanner;

public class BackendStaff extends Staff {
    //variables declaration
    private static int nextBackendStaffId;

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

    //backend menu
    public void backendMenu() {
        Scanner input = new Scanner(System.in); //scanner
        boolean loop = true;

        do {
            System.out.printf("=================================\n");
            System.out.printf("%-3s%s\n"," ", "BACKEND STAFF MENU");
            System.out.printf("=================================\n");
            System.out.printf("1. Train Information Modification\n");
            System.out.printf("2. Schedule Modification\n");
            System.out.printf("3. Train Station Information Modification\n");
            System.out.printf("4. Food and Beverage Modification\n");
            System.out.printf("5. Reports\n");
            System.out.printf("6. Log out\n");
            System.out.printf("=================================\n > ");

            //accept user input
            if (input.hasNextInt()) {
                int choice = input.nextInt();

                //choose user's input choice
                switch (choice) {
                //Train Modification Menu
                case 1:

                    break;
                //Schedule Modification
                case 2:
                    
                    break;
                //Train Station Information Modification
                case 3:

                    break;
                //FnB Modification
                case 4: 

                    break;
                //Reports
                case 5:
                    
                    break;
                case 6:
                    boolean loop2 = true;
                    do {
                        System.out.printf("Are you sure you want to quit (Y/N)? > ");
                        String choice1 = input.next();
                        if (choice1.length() == 1) {
                            switch (choice1.charAt(0)) {
                                //confirm quit
                                case 'Y':
                                case 'y':
                                    System.out.printf("Bye bye!");
                                    input.close();
                                    System.exit(0);
                                    break;
                                //cancel quit
                                case 'N':
                                case 'n':
                                    loop2 = false;
                                    break;
                                default: 
                                    System.out.printf("Invalid input, please enter your choice again.\n");
                            }
                        } else {
                            System.out.printf("Invalid input, please enter your choice again.\n");
                        }
                    } while (loop2);
                    break;
                default:
                    System.out.printf("Invalid input, please enter your choice again.\n");
                }
            } else {
                System.out.printf("Invalid input, please enter your choice again.\n");
                //clear buffer
                input.next();
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

        System.out.printf("=================================\n");
        System.out.printf("%-3s%s\n"," ", "CREATE BACKEND STAFF ACCOUNT");
        System.out.printf("=================================\n");
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
}