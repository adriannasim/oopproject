import java.util.ArrayList;
import java.util.Scanner;

public class Login {
    //variable declaration
    private String username;
    private String password;

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
    public String getPassword() {
        return password;
    }

    //login to account
    //staff
    public boolean checkStaffLogin(Login login) {
        //read from staff file
        Staff.readStaffInfo();
        //access array in staff class
        ArrayList<Staff> staffDetails = Staff.staffDetails;

        //check if user input matches any username and password in file
        for (int i=0; i < staffDetails.size(); i++) {
            //matches
            if (login.getUsername().equals(staffDetails.get(i).getUsername()) && login.getPassword().equals(staffDetails.get(i).getPassword())) { 
                return true;
            }
        }
        //no match
        return false;
    }
    //customer
    public boolean checkCustLogin(Login login) {
        //read from cust file
        Customer.readCustInfo();
        //access array in customer class
        ArrayList<Customer> custDetails = Customer.custDetails;

        //check if user input matches any username and password in file
        for (int i=0; i < custDetails.size(); i++) {
            //matches
            if (login.getUsername().equals(custDetails.get(i).getUsername()) && login.getPassword().equals(custDetails.get(i).getPassword())) {
                return true;
            }
        }
        //no match
        return false;
    }

    //DRIVER login
    public void driverLogin(int choice) {

        //variables declaration
        String inUsername, inPassword;
        boolean loginSuccessful;

        //method declaration
        Scanner input = new Scanner(System.in); //scanner
        Staff staff = new Staff();
        BackendStaff back = new BackendStaff();
        CounterStaff counter = new CounterStaff();
        Customer cust = new Customer();
        Login login = new Login();

        //login menu
        //customer
        if (choice == 1) {
            boolean loop = true;
            do {
                System.out.printf("=================================\n");
                System.out.printf("%-7s%s\n"," ", "CUSTOMER LOGIN MENU");
                System.out.printf("=================================\n");
                System.out.printf("1. Login\n");
                System.out.printf("2. Create Account\n");
                System.out.printf("3. Return\n > ");

                if (input.hasNextInt()) {
                    int choice2 = input.nextInt();
                    switch (choice2) {
                    //login
                    case 1:
                        System.out.printf("=================================\n");
                        System.out.printf("%-8s%s\n"," ", "CUSTOMER LOGIN");
                        System.out.printf("=================================\n");
                        System.out.printf("Please enter your username > ");
                        inUsername = input.next();
                        login.setUsername(inUsername);
                        System.out.printf("Please enter your password > ");
                        inPassword = input.next();
                        login.setPassword(inPassword);
                        //check login
                        loginSuccessful = checkCustLogin(login);
                        //if username and password is correct
                        if (loginSuccessful == true) {
                            System.out.printf("Login Successful\n");
                            cust.custMenu();
                        //if username and password is wrong
                        } else {
                            System.out.printf("Incorrect username/password. Please try again.\n");
                        }
                        break;
                    //create account
                    case 2:
                        cust.driverCustomer();
                        break;
                    case 3: 
                        return;
                    default: 
                        System.out.printf("Invalid input, please enter your choice again.\n");
                    }
                } else {
                    System.out.printf("Invalid input, please enter your choice again.\n");
                    //clear buffer
                    input.next();
                }
            } while (loop);  
        //staff
        } else if (choice == 2) {
            boolean loop = true;
            do {
                System.out.printf("=================================\n");
                System.out.printf("%-8s%s\n"," ", "STAFF LOGIN MENU");
                System.out.printf("=================================\n");
                System.out.printf("1. Login\n");
                System.out.printf("2. Create Account\n");
                System.out.printf("3. Return\n > ");

                if (input.hasNextInt()) {
                    int choice2 = input.nextInt();
                    switch (choice2) {
                        //login
                        case 1:
                            System.out.printf("=================================\n");
                            System.out.printf("%-11s%s\n"," ", "STAFF LOGIN");
                            System.out.printf("=================================\n");
                            System.out.printf("Please enter your username > ");
                            inUsername = input.next();
                            login.setUsername(inUsername);
                            System.out.printf("Please enter your password > ");
                            inPassword = input.next();
                            login.setPassword(inPassword);
                            //check login
                            loginSuccessful = checkStaffLogin(login);
                            //if username and password is correct
                            if (loginSuccessful == true) {
                                System.out.printf("Login Successful\n");
                                int menuType = staff.staffMenu(login);
                                if (menuType == 1) {
                                    back.backendMenu();
                                } else if (menuType == 2) {
                                    counter.counterMenu();
                                } else {
                                    System.out.println("ERROR");
                                    return;
                                }
                            //if username and password is wrong
                            } else {
                                System.out.printf("Incorrect username/password. Please try again.\n");
                            }
                            break;
                        //create account
                        case 2:
                            staff.driverStaff();
                            break;
                        case 3: 
                            return;
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
        input.close();
    }
}