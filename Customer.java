import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Customer extends User {
    // variables declaration
    private String contactNo;
    private char gender;

    // arrayList for customer info
    protected static ArrayList<Customer> custDetails = new ArrayList<>();

    // constructors
    Customer() {
        super();
        this.contactNo = "";
        this.gender = ' ';
    }

    Customer(String username, String password, String fullname, String email, String contactNo, char gender) {
        super(username, password, fullname, email);
        this.contactNo = contactNo;
        this.gender = gender;

        //add into arraylist
        custDetails.add(this);
    }

    // setters
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    // getters
    public String getContactNo(String username) {
        return contactNo;
    }

    public char getGender(String username) {
        return gender;
    }

    // writing all info into file
    public void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("custFile.txt"))) {
            for (Customer cust : custDetails) {
                writer.write(cust.getUsername() + "||" + cust.getPassword() + "||"
                        + cust.getFullname(cust.getUsername()) + "||" + cust.getEmail(cust.getUsername()) + "||"
                        + cust.contactNo + "||" + cust.gender);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // read file
    public void readFile() {
        //clear array before reading
        custDetails.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("custFile.txt"))) {
            String info;
            while ((info = reader.readLine()) != null) {
                // delete whitespaces
                info = info.trim();

                String[] parts = info.split("\\|\\|");
                if (parts.length == 6) {
                    String username = parts[0];
                    String password = parts[1];
                    String fullname = parts[2];
                    String email = parts[3];
                    String contactNo = parts[4];
                    char gender = parts[5].charAt(0);

                    // add details from file to constructor
                    Customer cust = new Customer(username, password, fullname, email, contactNo, gender);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // customer menu
    public void custMenu(Login login) throws Exception {
        Scanner input = new Scanner(System.in); // scanner

        System.out.println("==================================================");
        System.out.println("                  Customer Menu");
        System.out.println("==================================================");
        System.out.printf("1. Buy Tickets\n");
        System.out.printf("2. Buy Food and Beverages\n");
        System.out.printf("3. Edit Profile\n");
        System.out.printf("4. View FnB Purchase History\n");
        System.out.printf("5. View Ticket Purchase\n");

        System.out.println("\n* Press # to log out");
        System.out.println("==================================================");
        System.out.print("Enter your option > ");

        // accept user input
        if (input.hasNextInt()) {
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                case 2:
                    // make purchase
                    Purchase.makePurchase(choice, login);
                    break;
                case 3:
                    editAccount(login);
                    break;
                case 4:
                    DriverTw.viewFnBHistory(login);
                    break;
                case 5:
                    DriverTw.viewTicketHistory(login);
                    break;
                default:
                    //clear screen
                    for (int i = 0; i < 100; i++) {
                        System.out.println();
                    }
                    System.out.printf("Invalid input, please enter your choice again.\n");
                    
            }
            // choose user's input choice
        } else {
            if (input.next().equals("#")) {
                System.out.printf("Logged out.\n\n");
                return;
            } else {
                System.out.printf("Invalid input, please enter your choice again.\n");
                // clear buffer
                input.next();
            }
        }
    }

    // DRIVER customer
    public void driverCustomer() {
        // create account
        Scanner input = new Scanner(System.in); // scanner
        String regex = "^[a-zA-Z0-9 ]+$"; // regex with space
        String regex2 = "^[a-zA-Z0-9]+$"; // regex without space
        String regex3 = "^01[2-9]-\\d{7,8}$"; // regex for phone no.
        boolean check = true; // loop

        // variables declaration
        String inUsername, inPassword, inFullname, inEmail, inContact, inGender;

        System.out.println("==================================================");
        System.out.println("              Create Customer Login");
        System.out.println("==================================================");
        System.out.printf("Enter 'X' to exit at any point\n\n");

        // username
        do {
            System.out.printf("\nPlease enter your username (no spaces/special characters) > ");
            inUsername = input.nextLine();
            // check if username exist in custFile.txt
            readFile();
            for (int i = 0; i < custDetails.size(); i++) {
                if (inUsername.equals(custDetails.get(i).getUsername())) {
                    //clear screen
                    for (int i = 0; i < 100; i++) {
                        System.out.println();
                    }
                    System.out.printf("This username is already in used. Please login using your existing username.\n");
                    return;
                }
            }
            if (!inUsername.matches(regex2)) {
                System.out.printf("No spaces/special characters allowed. Please try again.\n");
            } else if (inUsername.equalsIgnoreCase("X")) {
                //clear screen
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                return;
            } else {
                check = false;
            }
        } while (check);
        // password
        check = true;
        do {
            System.out.printf("\nPlease enter your password (no spaces) > ");
            inPassword = input.nextLine();
            if (inPassword.contains(" ")) {
                System.out.printf("No spaces allowed. Please try again.\n");
            } else if (inPassword.equalsIgnoreCase("X")) {
                //clear screen
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                return;
            } else {
                check = false;
            }
        } while (check);
        // fullname
        check = true;
        do {
            System.out.printf("\nPlease enter your full name (no special characters) > ");
            inFullname = input.nextLine();
            if (!inFullname.matches(regex)) {
                System.out.printf("No special characters allowed. Please try again.\n");
            } else if (inFullname.equalsIgnoreCase("X")) {
                //clear screen
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                return;
            } else {
                check = false;
            }
        } while (check);
        // email
        check = true;
        do {
            System.out.printf("\nPlease enter your email (no spaces) > ");
            inEmail = input.nextLine();
            if (inEmail.contains(" ")) {
                System.out.printf("No spaces allowed. Please try again.\n");
            } else if (inEmail.equalsIgnoreCase("X")) {
                //clear screen
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                return;
            } else {
                check = false;
            }
        } while (check);
        // contactNo
        check = true;
        do {
            System.out.printf("\nPlease enter your contact no. (eg. 012-3456789) > ");
            inContact = input.nextLine();
            if (!inContact.matches(regex3)) {
                System.out.printf("Please follow the format and try again (eg. 012-3456789). \n");
            } else if (inContact.equalsIgnoreCase("X")) {
                //clear screen
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                return;
            } else {
                check = false;
            }
        } while (check);
        // gender
        check = true;
        do {
            System.out.printf("\nPlease enter your gender (F=Female, M=Male) > ");
            inGender = input.nextLine();
            if (inGender.length() == 1 && (inGender.equalsIgnoreCase("F") || inGender.equalsIgnoreCase("M"))) {
                inGender = inGender.toUpperCase();
                check = false;
            } else if (inGender.equalsIgnoreCase("X")) {
                //clear screen
                for (int i = 0; i < 100; i++) {
                    System.out.println();
                }
                return;
            } else {
                System.out.printf("Please enter F/M only.\n");
            }
        } while (check);

        Customer cust = new Customer(inUsername, inPassword, inFullname, inEmail, inContact, inGender.charAt(0));
        writeFile();
        System.out.println("Registration successful. Please login now.\n");
    }

    public void editAccount(Login login) {
        Scanner scanner = new Scanner(System.in);
        int editOpt = 0;
        boolean continueInput = true;

        Customer cust = new Customer();
        boolean validAcc = false;

        String regex = "^[a-zA-Z0-9 ]+$"; // regex with space
        String regex3 = "^01[2-9]-\\\\d{7,8})$"; // regex for phone no.

        String fullname, email, contactNo;
        char gender;

        Customer customer = new Customer();
        customer.readFile();
        ArrayList<Customer> customers = Customer.custDetails;
        for (int i = 0; i < customers.size(); i++) {
            if (login.getUsername().equals(customers.get(i).getUsername())
                    && login.getPassword().equals(customers.get(i).getPassword())) {
                cust = customers.get(i);
                validAcc = true;
            }
        }

        if (validAcc) {
            System.out.println("1. Full Name");
            System.out.println("2. Email");
            System.out.println("3. Contact Number");
            System.out.println("4. Gender");
            System.out.println("Other - Exit");
            do {
                try {
                    System.out.print("Enter your option : ");
                    editOpt = scanner.nextInt();

                    continueInput = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Incorrect Input. Please try agin. ");
                    scanner.nextLine();
                }
            } while (continueInput);

            if (editOpt == 1) {

                do {
                    System.out.println("Enter  your full name (no special characters) >");
                    fullname = scanner.nextLine();
                } while (!fullname.matches(regex));

                cust.setFullname(fullname);

            } else if (editOpt == 2) {

                do {
                    System.out.printf("Enter your email (no spaces) > ");
                    email = scanner.next();
                } while (!email.contains(" "));

                cust.setEmail(email);

            } else if (editOpt == 3) {
                do {
                    System.out.println("Enter contact Number (eg. 012-3456789) > ");
                    contactNo = scanner.nextLine();
                } while (!contactNo.matches(regex3));

                cust.setContactNo(contactNo);

            } else if (editOpt == 4) {

                do {
                    System.out.println("Enter gender (F/M only) : ");
                    gender = scanner.next().charAt(0);
                    gender = Character.toUpperCase(gender);
                } while (gender != 'f' && gender != 'M');

                cust.setGender(gender);

            } else {
                System.out.println("Exit Editing");
            }

        }
    }
}
