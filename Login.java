import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    //variable declaration
    private String username;
    private String password;

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

    //login to account
    //staff
    public void checkStaffLogin(String username, String password, Staff staff) {
        //read from staff file
        Staff.readStaffInfo();

        //check if user input matches any username and password in file
        boolean loginSuccessful = false;
        for (int i=0; i < staffDetails.size(); i++) {
            //matches
            if (username.equals(staffDetails.get(i).getUsername()) && password.equals(staffDetails.get(i).getPassword())) {
                System.out.printf("Login Successful\n");
                loginSuccessful = true;
                staff.staffMenu();
            }
        }
        //no match
        if (loginSuccessful == false) {
            System.out.printf("Incorrect username/password. Please try again.\n");
        }
    }
    //customer
    public void checkCustLogin(String username, String password, Customer cust) {
        //read from cust file
        Customer.readCustInfo();

        //check if user input matches any username and password in file
        boolean loginSuccessful = false;
        for (int i=0; i < custDetails.size(); i++) {
            //matches
            if (username.equals(custDetails.get(i).getUsername()) && password.equals(custDetails.get(i).getPassword())) {
                System.out.printf("Login Successful\n");
                loginSuccessful = true;
                cust.custMenu();
            }
        }
        //no match
        if (loginSuccessful == false) {
            System.out.printf("Incorrect username/password. Please try again.\n");
        }
    }

    //DRIVER login
    public void driverLogin(int choice) {
        //variables declaration
        String inUsername, inPassword;
        //method declaration
        Scanner input = new Scanner(System.in); //scanner
        Staff staff = new Staff();
        Customer cust = new Customer();

        //login menu
        //staff
        if (choice == 1) {
            boolean loop = true;
            do {
                System.out.printf("=================================\n");
                System.out.printf("%-8s%s\n"," ", "STAFF LOGIN MENU");
                System.out.printf("=================================\n");
                System.out.printf("1. Login\n");
                System.out.printf("2. Create Account\n > ");

                int choice2 = input.nextInt();
                switch (choice2) {
                    //login
                    case 1:
                        System.out.printf("=================================\n");
                        System.out.printf("%-11s%s\n"," ", "STAFF LOGIN");
                        System.out.printf("=================================\n");
                        System.out.printf("Please enter your username > ");
                        inUsername = input.next();
                        System.out.printf("Please enter your password > ");
                        inPassword = input.next();
                        checkStaffLogin(inUsername, inPassword, staff);
                        break;
                    //create account
                    case 2:
                        staff.driverStaff();
                        break;
                    default: 
                        System.out.printf("Invalid input, please enter your choice again.\n");
                }
            } while (loop); 
        //customer
        } else if (choice == 2) {
            boolean loop = true;
            do {
                System.out.printf("=================================\n");
                System.out.printf("%-7s%s\n"," ", "CUSTOMER LOGIN MENU");
                System.out.printf("=================================\n");
                System.out.printf("1. Login\n");
                System.out.printf("2. Create Account\n > ");

                int choice2 = input.nextInt();

                switch (choice2) {
                    //login
                    case 1:
                        System.out.printf("=================================\n");
                        System.out.printf("%-8s%s\n"," ", "CUSTOMER LOGIN");
                        System.out.printf("=================================\n");
                        System.out.printf("Please enter your username > ");
                        inUsername = input.next();
                        System.out.printf("Please enter your password > ");
                        inPassword = input.next();
                        checkCustLogin(inUsername, inPassword, cust);
                        break;
                    //create account
                    case 2:
                        cust.driverCustomer();
                        break;
                    default: 
                        System.out.printf("Invalid input, please enter your choice again.\n");
                }
            } while (loop);  
        }
        input.close();
    }
}