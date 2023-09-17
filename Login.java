import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    //variable declaration
    private String username;
    private String password;

    //methods declaration
    static Scanner input = new Scanner(System.in); //scanner

    //array list declaration
    private static ArrayList<Customer> custDetails = new ArrayList<>();
    private static ArrayList<Staff> staffDetails = new ArrayList<>();

    //constructors
    Login() {
        this.username = "";
        this.password = "";
    }
    Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    //setters
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //getters
    public String getUsername() {
        return username;
    }
    public String getPassword(String username) {
        return password;
    }

    //login menu
    public static void staffLoginMenu() {
        boolean loop = true;
        do {
            System.out.printf("=================================\n");
            System.out.printf("%-8s%s\n"," ", "STAFF LOGIN MENU");
            System.out.printf("=================================\n");
            System.out.printf("1. Login\n");
            System.out.printf("2. Create Account\n");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    loginToStaff();
                    break;
                case 2:
                    Staff.createStaffAccount();
                    break;
                default: 
                    System.out.printf("Invalid input, please enter your choice again.\n");
            }
        } while (loop); 
    }
    public static void custLoginMenu() {
        boolean loop = true;
        do {
            System.out.printf("=================================\n");
            System.out.printf("%-7s%s\n"," ", "CUSTOMER LOGIN MENU");
            System.out.printf("=================================\n");
            System.out.printf("1. Login\n");
            System.out.printf("2. Create Account\n > ");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    loginToCust();
                    break;
                case 2:
                    Customer.createCustAccount();
                    break;
                default: 
                    System.out.printf("Invalid input, please enter your choice again.\n");
            }
        } while (loop);  
    }

    //login to account
    public static void loginToStaff() {
        System.out.printf("=================================\n");
        System.out.printf("%-11s%s\n"," ", "STAFF LOGIN");
        System.out.printf("=================================\n");
        System.out.printf("Please enter your username > ");
        String inUsername = input.next();
        System.out.printf("Please enter your password > ");
        String inPassword = input.next();

        readStaffInfo();

        boolean loginSuccessful = false;
        for (int i=0; i < staffDetails.size(); i++) {
            if (inUsername.equals(staffDetails.get(i).getUsername()) && inPassword.equals(staffDetails.get(i).getPassword())) {
                System.out.printf("Login Successful\n");
                loginSuccessful = true;
                Staff.staffMenu();
            }
        }
        if (loginSuccessful == false) {
            System.out.printf("Incorrect username/password. Please try again.\n");
        }
    }
    public static void loginToCust() {
        System.out.printf("=================================\n");
        System.out.printf("%-8s%s\n"," ", "CUSTOMER LOGIN");
        System.out.printf("=================================\n");
        System.out.printf("Please enter your username > ");
        String inUsername = input.next();
        System.out.printf("Please enter your password > ");
        String inPassword = input.next();

        readCustInfo();

        boolean loginSuccessful = false;
        for (int i=0; i < custDetails.size(); i++) {
            if (inUsername.equals(custDetails.get(i).getUsername()) && inPassword.equals(custDetails.get(i).getPassword())) {
                System.out.printf("Login Successful\n");
                Customer.custMenu();

            }
        }
        if (loginSuccessful == false) {
            System.out.printf("Incorrect username/password. Please try again.\n");
        }
    }

    //read file
    public static void readStaffInfo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("adminFile.txt"))) {
            String info;
            while ((info = reader.readLine()) != null) {
                String[] parts = info.split("\\|\\|");
                if (parts.length == 6) {
                    String username = parts[0];
                    String password = parts[1];
                    String fullname = parts[2];
                    String email = parts[3];
                    String staffId = parts[4];     
                    char staffType = parts[5].charAt(0);
                    
                    //add details from file to arraylist
                    Staff staff = new Staff(username, password, fullname, email, staffType);
                    staff.setStaffId(staffId);
                    staffDetails.add(staff);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readCustInfo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("custFile.txt"))) {
            String info;
            while ((info = reader.readLine()) != null) {
                String[] parts = info.split("\\|\\|");
                if (parts.length == 6) {
                    String username = parts[0];
                    String password = parts[1];
                    String fullname = parts[2];
                    String email = parts[3];
                    String contactNo = parts[4];
                    char gender = parts[5].charAt(0);
                    
                    //add details from file to arraylist
                    Customer cust = new Customer(username, password, fullname, email, contactNo, gender);
                    custDetails.add(cust);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}