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
    private static ArrayList<Login> custLoginInfo = new ArrayList<>();
    private static ArrayList<Login> staffLoginInfo = new ArrayList<>();

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
        int loop = 1;
        while (loop == 1) {
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
                    createStaffAccount();
                    break;
                default: 
                    System.out.printf("Invalid input, please enter your choice again.\n");
            }
        }   
    }
    public static void custLoginMenu() {
        int loop = 1;
        while (loop == 1) {
            System.out.printf("=================================\n");
            System.out.printf("%-7s%s\n"," ", "CUSTOMER LOGIN MENU");
            System.out.printf("=================================\n");
            System.out.printf("1. Login\n");
            System.out.printf("2. Create Account\n");

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    loginToCust();
                    break;
                case 2:
                    createCustAccount();
                    break;
                default: 
                    System.out.printf("Invalid input, please enter your choice again.\n");
            }
        }   
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

        readAdminInfo();

        for (int i=0; i < staffLoginInfo.size(); i++) {
            if (inUsername == staffLoginInfo.get(i).username) {
                if (inPassword == staffLoginInfo.get(i).password) {
                    System.out.printf("Login Successful\n");
                }
            }
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

        readAdminInfo();

        for (int i=0; i < custLoginInfo.size(); i++) {
            if (inUsername == custLoginInfo.get(i).username) {
                if (inPassword == custLoginInfo.get(i).password) {
                    System.out.printf("Login Successful\n");
                }
            }
        }
    }
    //create account
    public static void createStaffAccount() {
        
    }
    public static void createCustAccount() {
        
    }

    //read file
    public static void readAdminInfo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("adminFile.txt"))) {
            String info;
            while ((info = reader.readLine()) != null) {
                String[] parts = info.split(" ");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    staffLoginInfo.add(new Login(username, password));
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
                String[] parts = info.split(" ");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    staffLoginInfo.add(new Login(username, password));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}