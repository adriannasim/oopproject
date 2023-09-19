import java.util.Scanner;

public class Menu {   
    public static void main(String[] args) {
        //variables declaration
        boolean loop = true;
        //methods declaration
        Scanner input = new Scanner(System.in); //scanner
        Login login = new Login();
        
        System.out.println("==================================================");
        System.out.println("        WELCOME TO TRAIN TICKETING SYSTEM");
        do {
            System.out.println("==================================================");
            System.out.println("                       Menu");
            System.out.println("==================================================");
            System.out.printf("1. Customer Login Menu\n");
            System.out.printf("2. Staff Login Menu\n");
            System.out.println("\n* Press # to exit");
            System.out.println("==================================================");
            System.out.print("Enter your option > ");

            //accept user input
            if (input.hasNextInt()) {
                int choice = input.nextInt();

                //choose user's input choice
                switch (choice) {
                //login menus
                case 1:
                case 2:
                    login.driverLogin(choice);
                    break;
                default:
                    System.out.printf("Invalid input, please enter your choice again.\n");
                }
            } else {
                //quit
                if (input.next().equals("#")) {
                    boolean loop2 = true;
                    do {
                        System.out.printf("Are you sure you want to quit (Y/N)? > ");
                        String choice1 = input.next();
                        if (choice1.length() == 1) {
                            switch (choice1.charAt(0)) {
                                //confirm quit
                                case 'Y':
                                case 'y':
                                    System.out.printf("Bye bye!");
                                    input.close();
                                    System.exit(0);
                                    break;
                                //cancel quit
                                case 'N':
                                case 'n':
                                    loop2 = false;
                                    break;
                                default: 
                                    System.out.printf("Invalid input, please enter your choice again.\n");
                            }
                        } else {
                            System.out.printf("Invalid input, please enter your choice again.\n");
                        }
                    } while (loop2);
                } else {
                    System.out.printf("Invalid input, please enter your choice again.\n");
                    //clear buffer
                    input.next();
                }
            }
        } while (loop);
    }  
}
