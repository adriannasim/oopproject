import java.util.*;
import java.io.*;

public class DriverAdr {
    public static void main(String[] args) {
        /*START OF HEADER*/
        //variables declaration
        

        //objects/instances declaration
        Scanner input = new Scanner(System.in); //scanner
        Menu menu = new Menu(); //menu
        /*END OF HEADER*/

        /*START OF MAIN PROGRAM*/
        //call printMenu function
        menu.printMenu();
        /*END OF MAIN PROGRAM*/

        /*START OF FOOTER*/
        //close scanner
        input.close();
        /*END OF FOOTER*/
    }
}