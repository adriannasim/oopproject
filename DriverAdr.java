import java.util.*;
import java.io.*;

public class DriverAdr {
    public static void main(String[] args) {
        /*START OF HEADER*/
        //variables declaration
        Customer cust1 = new Customer("adr", "1628", "Adrianna Sim", "adriannasim@gmail.com", "0164121629", 'F');

        //objects/instances declaration
        Scanner input = new Scanner(System.in); //scanner
        Menu menu = new Menu(); //menu
        /*END OF HEADER*/

        /*START OF MAIN PROGRAM*/
        //call printMenu function
        System.out.println(cust1.toString());
        menu.printMenu();
        /*END OF MAIN PROGRAM*/

        /*START OF FOOTER*/
        //close scanner
        input.close();
        /*END OF FOOTER*/
    }
}