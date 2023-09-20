import java.util.ArrayList;
import java.util.Scanner;

public class Login{
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
        Staff staff = new Staff();
        staff.readFile();
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
        Customer cust = new Customer();
        cust.readFile();
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
    public void driverLogin(int choice) throws Exception{

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
                System.out.println("==================================================");
                System.out.println("               Customer Login Menu");
                System.out.println("==================================================");
                System.out.printf("1. Login\n");
                System.out.printf("2. Create Account\n");
                System.out.println("\n* Press # to return");
                System.out.println("==================================================");
                System.out.print("Enter your option > ");

                if (input.hasNextInt()) {
                    int choice2 = input.nextInt();
                    switch (choice2) {
                    //login
                    case 1:
                        //clear screen
                        for (int i = 0; i < 100; i++) {
                            System.out.println();
                        }
                        System.out.println("==================================================");
                        System.out.println("                  Customer Login");
                        System.out.println("==================================================");
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
                            //clear screen
                            for (int i = 0; i < 100; i++) {
                                System.out.println();
                            }
                            System.out.printf("Login Successful\n");
                            cust.custMenu(login);
                        //if username and password is wrong
                        } else {
                            //clear screen
                            for (int i = 0; i < 100; i++) {
                                System.out.println();
                            }
                            System.out.printf("Incorrect username/password. Please try again.\n");
                        }
                        break;
                    //create account
                    case 2:
                        //clear screen
                        for (int i = 0; i < 100; i++) {
                            System.out.println();
                        }
                        cust.driverCustomer();
                        break;
                    default:
                        //clear screen
                        for (int i = 0; i < 100; i++) {
                            System.out.println();
                        }
                        System.out.printf("Invalid input, please enter your choice again.\n");
                    }
                } else {
                    if (input.next().equals("#")) {
                        //clear screen
                        for (int i = 0; i < 100; i++) {
                            System.out.println();
                        }
                        return;
                    } else {
                        //clear screen
                        for (int i = 0; i < 100; i++) {
                            System.out.println();
                        }
                        System.out.printf("Invalid input, please enter your choice again.\n");
                    }
                }
            } while (loop);  
        //staff
        } else if (choice == 2) {
            boolean loop = true;
            do {
                System.out.println("==================================================");
                System.out.println("                 Staff Login Menu");
                System.out.println("==================================================");
                System.out.printf("1. Login\n");
                System.out.printf("2. Create Account\n");
                System.out.println("\n* Press # to return");
                System.out.println("==================================================");
                System.out.print("Enter your option > ");

                if (input.hasNextInt()) {
                    int choice2 = input.nextInt();
                    switch (choice2) {
                        //login
                        case 1:
                            //clear screen
                            for (int i = 0; i < 100; i++) {
                                System.out.println();
                            }
                            System.out.println("==================================================");
                            System.out.println("                   Staff Login");
                            System.out.println("==================================================");
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
                                //clear screen
                                for (int i = 0; i < 100; i++) {
                                    System.out.println();
                                }
                                System.out.printf("Login Successful\n\n");
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
                                //clear screen
                                for (int i = 0; i < 100; i++) {
                                    System.out.println();
                                }
                                System.out.printf("Incorrect username/password. Please try again.\n");
                            }
                            break;
                        //create account
                        case 2:
                            //clear screen
                            for (int i = 0; i < 100; i++) {
                                System.out.println();
                            }
                            staff.driverStaff();
                            break;
                        default: 
                            //clear screen
                            for (int i = 0; i < 100; i++) {
                                System.out.println();
                            }
                            System.out.printf("Invalid input, please enter your choice again.\n");
                    }
                } else {
                    if (input.next().equals("#")) {
                        for (int i = 0; i < 100; i++) {
                            System.out.println();
                        }
                        return;
                    } else {
                        //clear screen
                        for (int i = 0; i < 100; i++) {
                            System.out.println();
                        }
                        System.out.printf("Invalid input, please enter your choice again.\n");
                    }
                }
            } while (loop); 
        }
    }
}