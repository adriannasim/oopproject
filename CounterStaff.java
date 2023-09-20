import java.util.Scanner;

public class CounterStaff extends Staff {
    //variables declaration
    private static int nextCounterStaffId;

    //getter
    public int getCounterStaffId() {
        readFile();
        nextCounterStaffId = 1000;
        int count = 0;
        for (int i = 0; i < staffDetails.size(); i++) {
            if (staffDetails.get(i).getStaffType() == 'C') {
                count++;
            }
        }
        return nextCounterStaffId + count;
    }

    //counter menu
    public void counterMenu() throws Exception{
        Scanner input = new Scanner(System.in); //scanner

        System.out.println("==================================================");
        System.out.println("                Counter Staff Menu");
        System.out.println("==================================================");
        System.out.printf("1. Buy Tickets\n");
        System.out.printf("2. Buy Food and Beverages\n");
        System.out.println("\n* Press # to log out");
        System.out.println("==================================================");
        System.out.print("Enter your option > ");

        //accept user input
        if (input.hasNextInt()) {
            int choice = input.nextInt();
            //make purchase
            Purchase.makePurchase(choice);
            //choose user's input choice
        } else {
            if (input.next().equals("#")) {
                System.out.printf("Logged out.\n\n");
                return;
            } else {
                System.out.printf("Invalid input, please enter your choice again.\n");
                //clear buffer
                input.next();
            }
        }
    }

    //create counter staff account
    public void createCounterStaff() {
        //variables declaration
        String regex = "^[a-zA-Z0-9 ]+$";  //regex with space
        String regex2 = "^[a-zA-Z0-9]+$";  //regex without space
        boolean check = true;  //loop
        String inUsername, inPassword, inFullname, inEmail, inStaffId;
        char inStaffType;
        
        //methods declaration
        Scanner input = new Scanner(System.in); //scanner

        System.out.println("==================================================");
        System.out.println("            Create Counter Staff Login");
        System.out.println("==================================================");
        System.out.printf("Enter 'X' to exit at any point\n\n");
        
        //set staff type
        inStaffType = 'C';
        //set staff id
        inStaffId = 'C' + Integer.toString(getCounterStaffId());
        System.out.printf("=================================\n");
        System.out.printf("STAFF ID: %s\n", inStaffId);
        System.out.printf("=================================\n");

        //username
        do {
            System.out.printf("Please enter your username (no spaces/special characters) > ");
            inUsername = input.nextLine();
            //check if username exist in custFile.txt
            readFile();
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
        writeFile();
        System.out.println("Registration successful. Please login now.");
        input.close();
    }
}
