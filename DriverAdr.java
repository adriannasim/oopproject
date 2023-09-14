import java.util.*;
import java.io.*;

public class DriverAdr {
    public static void main(String[] args) {
        //variables declaration
        int choice, loop = 1;

        //objects declaration
        Scanner input = new Scanner(System.in);

        //print menu
        System.out.printf("=================================\n");
        System.out.printf("Welcome to Train Ticketing System\n");
        System.out.printf("=================================\n");
        System.out.printf("%-13s%s\n"," ", "MENU");
        System.out.printf("=================================\n");
        System.out.printf("1. User Login\n");
        System.out.printf("2. Admin Login\n");
        System.out.printf("3. Exit\n");
        System.out.printf("=================================\n >");

        //accept user input
        choice = input.nextInt();

        //invoke classes based on user's input
        while (loop == 1) {
            switch(choice) {
                case 1:
                    System.out.printf("User Login\n");
                    //UserLogin();
                    break;
                case 2:
                    System.out.printf("Admin Login\n");
                    //AdminLogin();
                    break;
                case 3:
                    System.out.printf("Quit\n");
                    //QuitProgram();
                    break;
                default:
                    System.out.printf("Invalid input, please enter your choice again.\n");
            }
        }
        //close scanner
        input.close();
    }
}