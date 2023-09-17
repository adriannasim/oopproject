import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
    
    //create account
    public static void createStaffAccount() {
        
    }

    //customer menu
    public static void staffMenu() {

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
}
