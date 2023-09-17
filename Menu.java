import java.util.Scanner;

public class Menu {   
    public static void main(String[] args) {
        //variables declaration
        int loop = 1;
        //methods declaration
        Scanner input = new Scanner(System.in); //scanner
        
        System.out.printf("=================================\n");
        System.out.printf("Welcome to Train Ticketing System\n");
        System.out.printf("=================================\n");
        while (loop == 1) {
            System.out.printf("%-14s%s\n"," ", "MENU");
            System.out.printf("=================================\n");
            System.out.printf("1. User Login Menu\n");
            System.out.printf("2. Admin Login Menu\n");
            System.out.printf("3. Exit\n");
            System.out.printf("=================================\n >");

            //accept user input
            int choice = input.nextInt();

            //call chooseMenu function
            chooseMenu(choice);
        }
    }  

    //invoke classes based on user's input
    public static void chooseMenu(int choice) {
        switch(choice) {
            case 1:
                System.out.printf("User Login\n");
                //UserLogin();
                break;
            case 2:
                Login.staffLoginMenu();
                break;
            case 3:
                System.out.printf("Quit\n");
                //QuitProgram();
                break;
            default:
                System.out.printf("Invalid input, please enter your choice again.\n");
        }
    }
}
