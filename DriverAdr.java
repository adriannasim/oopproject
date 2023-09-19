import java.util.*;
import java.io.*;

public class DriverAdr {
    public static void main(String[] args) {
        /*START OF HEADER*/
        //variables declaration
        // Customer cust1 = new Customer("adr", "1628", "Adrianna Sim", "adriannasim@gmail.com", "0164121629", 'F');
        // Staff admin1 = new Staff("bri", "3428", "Brianna Sim", "briannasim@gmail.com", 'B');
        // Staff admin2 = new Staff("hehe", "9628", "Hehe Sim", "hehe@gmail.com", 'B');
        ArrayList<Customer> custDetails = Customer.custDetails;
        ArrayList<Staff> staffDetails = Staff.staffDetails;



        //objects/instances declaration
        Scanner input = new Scanner(System.in); //scanner
        Menu menu = new Menu(); //menu
        /*END OF HEADER*/

        /*START OF MAIN PROGRAM*/
        // System.out.println(cust1.toString());
        // System.out.println(admin1.toString());
        // System.out.println(admin2.toString());

        //call method to write details into file
        // Staff.writeStaffInfo();
        // Customer.writeCustInfo();

        // Staff admin3 = new Staff("hoho", "5555", "huh Sim", "huhu@gmail.com", 'C');
        // System.out.println(admin3.toString());
        // Staff.writeStaffInfo();

        //call printMenu function
        // System.out.printf("=================================\n");
        // System.out.printf("%-5s%s\n"," ", "CREATE CUSTOMER ACCOUNT");
        // System.out.printf("=================================\n");

        //print file contents
        Customer.readCustInfo();
        System.out.println(custDetails);
        Staff.readStaffInfo();
        System.out.println(staffDetails);
        for (Staff staff : staffDetails) {
            System.out.print(staff.getUsername() + "||" + staff.getPassword() + "||" + staff.getFullname(staff.getUsername()) + "||" + staff.getEmail(staff.getUsername()) + "||" + staff.getStaffId() + "||" + staff.getStaffType() + "\n");
        }

        /*END OF MAIN PROGRAM*/

        /*START OF FOOTER*/
        //close scanner
        input.close();
        /*END OF FOOTER*/
    }
}