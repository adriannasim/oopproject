import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;                           
import java.util.ArrayList;
import java.util.Scanner;

public class Staff extends User{
    private String staffId;
    private char staffType;

    //arrayList for customer info
    private static ArrayList<Staff> staffDetails = new ArrayList<>();

    //constructors
    Staff() {
        super();
        this.staffId = "";
        this.staffType = ' ';
    }
    Staff(String username, String password, String fullname, String email, char staffType) {
        super(username, password, fullname, email);
        this.staffType = staffType;
        if (staffType == 'B') {
            this.staffId = String.format(staffType + Integer.toString(BackendStaff.nextBackendStaffId));
            BackendStaff.nextBackendStaffId++;
        } else if (staffType == 'C') {
            this.staffId = String.format(staffType + Integer.toString(CounterStaff.nextCounterStaffId));
            CounterStaff.nextCounterStaffId++;
        }

        //writing details to arrayList
        staffDetails.add(this);
    }

    //setters
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    public void setStaffType(char staffType) {
        this.staffType = staffType;
    }

    //getters
    public String getStaffId() {
        return staffId;
    }
    public char getStaffType(String staffId) {
        return staffType;
    }

    //toString
    // public String toString() {
    //     return super.toString() + String.format("Staff Id: %s\nStaff Type: %c\n", staffId, staffType);
    // }

    //customer menu
    public void staffMenu() {

    }

    //write file
    public static void writeStaffInfo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("adminFile.txt"))) {
            for (Staff staff : staffDetails) {
                writer.write(staff.getUsername() + "||" + staff.getPassword() + "||" + staff.getFullname(staff.getUsername()) + "||" + staff.getEmail(staff.getUsername()) + "||" + staff.staffId + "||" + staff.staffType);
                writer.newLine();
            }
            System.out.println("Staff registration successful. Please login now.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //read file
    public static void readStaffInfo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("adminFile.txt"))) {
            String info;
            while ((info = reader.readLine()) != null) {
                String[] parts = info.split("\\|\\|");
                if (parts.length == 6) {
                    String username = parts[0];
                    String password = parts[1];
                    String fullname = parts[2];
                    String email = parts[3];
                    String staffId = parts[4];     
                    char staffType = parts[5].charAt(0);
                    
                    //add details from file to arraylist
                    Staff staff = new Staff(username, password, fullname, email, staffType);
                    staff.setStaffId(staffId);
                    staffDetails.add(staff);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //STAFF driver
    public void driverStaff() {
        //variables declaration
        boolean loop = true;

        //methods declaration
        Scanner input = new Scanner(System.in);
        BackendStaff backend = new BackendStaff();
        CounterStaff counter = new CounterStaff();

        //choose staff type
        do {
            System.out.printf("=================================\n");
            System.out.printf("%-8s%s\n"," ", "CREATE STAFF ACCOUNT");
            System.out.printf("=================================\n");
            System.out.printf("1. Create backend staff\n");
            System.out.printf("2. Create counter staff\n");
            System.out.printf("3. Exit\n");

            if (input.hasNextInt()) {
                int choice = input.nextInt();
                switch(choice) {
                    //create backend staff
                    case 1:
                        backend.createBackendStaff();
                        break;
                    //create counter staff
                    case 2:
                        counter.createCounterStaff();
                        break;
                    default:
                    System.out.printf("Invalid input, please enter your choice again.\n"); 
                }   
            } else {
                System.out.printf("Invalid input, please enter your choice again.\n");
            }
        } while (loop);
        input.close();
    }
}
