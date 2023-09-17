import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends User{
    //variables declaration
    private String contactNo;
    private char gender;

    //arrayList for customer info
    private static ArrayList<Customer> custDetails = new ArrayList<>();

    //constructors
    Customer() {
        super();
        this.contactNo = "";
        this.gender = ' ';
    }
    Customer(String username, String password, String fullname, String email, String contactNo, char gender) {
        super(username, password, fullname, email);
        this.contactNo = contactNo;
        this.gender = gender;

        //writing details to arrayList
        custDetails.add(this);
    }

    //setters
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }

    //getters
    public String getContactNo(String username) {
        return contactNo;
    }
    public char getGender(String username) {
        return gender;
    }

    //toString
    // public String toString() {
    //     return super.toString() + String.format("Contact: %s\nGender: %c", contactNo, gender);
    // }
    
    //create account
    public static void createCustAccount() {
        Scanner input = new Scanner(System.in); //scanner
        String regex = "^[a-zA-Z0-9 ]+$";  //regex with space
        String regex2 = "^[a-zA-Z0-9]+$";  //regex without space
        String regex3 = "^01[2-9]-\\\\d{7,8})$";  //regex for phone no.
        boolean check = true;  //loop

        //variables declaration
        String inUsername, inPassword, inFullname, inEmail, inContact, inGender;

        System.out.printf("=================================\n");
        System.out.printf("%-5s%s\n"," ", "CREATE CUSTOMER ACCOUNT");
        System.out.printf("=================================\n");

        //username
        do {
            System.out.printf("Please enter your username (no spaces/special characters) > ");
            inUsername = input.next();
            if (!inUsername.matches(regex2)) {
                System.out.printf("No spaces/special characters allowed. Please try again.");
            } else {
                check = false;
            }
        } while (check);
        //password
        check = true;
        do {
            System.out.printf("Please enter your password (no spaces) > ");
            inPassword = input.next();
            if (!inPassword.contains(" ")) {
                System.out.printf("No spaces allowed. Please try again.");
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
                System.out.printf("No special characters allowed. Please try again.");
            } else {
                check = false;
            }
        } while (check);
        //email
        check = true;
        do {
            System.out.printf("Please enter your email (no spaces) > ");
            inEmail = input.next();
            if (!inEmail.contains(" ")) {
                System.out.printf("No spaces allowed. Please try again.");
            } else {
                check = false;
            }
        } while (check);
        //contactNo
        check = true;
        do {
            System.out.printf("Please enter your contact no. (eg. 012-3456789) > ");
            inContact = input.next();
            if (!inContact.matches(regex3)) {
                System.out.printf("Please follow the format (eg. 012-3456789)");
            } else {
                check = false;
            }
        } while (check);
        //gender
        check = true;
        do {
            System.out.printf("Please enter your gender (F=Female, M=Male) > ");
            inGender = input.next();
            if (inGender.length() == 1 && (inGender.equalsIgnoreCase("F") || inGender.equalsIgnoreCase("M"))) {
                inGender = inGender.toUpperCase();
                check = false;
            } else {
                System.out.printf("Please enter F/M only.\n");
            }
        } while (check);

        Customer cust = new Customer(inUsername, inPassword, inFullname, inEmail, inContact, inGender.charAt(0));
        writeCustInfo();
    }

    //customer menu
    public static void custMenu() {
        
    }

    //writing all info into file
    public static void writeCustInfo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("custFile.txt"))) {
            for (Customer cust : custDetails) {
                writer.write(cust.getUsername() + "||" + cust.getPassword() + "||" + cust.getFullname(cust.getUsername()) + "||" + cust.getEmail(cust.getUsername()) + "||" + cust.contactNo + "||" + cust.gender);
                writer.newLine();
            }
            System.out.println("Registration successful. Please login now.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
