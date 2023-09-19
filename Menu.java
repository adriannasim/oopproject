import java.util.Scanner;

public class Menu{   
    public static void main(String[] args) throws Exception{
        //variables declaration
        boolean loop = true;
        //methods declaration
        Scanner input = new Scanner(System.in); //scanner
        Login login = new Login();
        
        System.out.printf("=================================\n");
        System.out.printf("Welcome to Train Ticketing System\n");
        do {
            System.out.printf("=================================\n");
            System.out.printf("%-14s%s\n"," ", "MENU");
            System.out.printf("=================================\n");
            System.out.printf("1. Customer Login Menu\n");
            System.out.printf("2. Staff Login Menu\n");
            System.out.printf("3. Exit\n");
            System.out.printf("=================================\n > ");

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
                //quit
                case 3:
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
                    break;
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
}
