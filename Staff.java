import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Staff extends User{
    private String staffId;
    private char staffType;

    //arrayList for customer info
    private static ArrayList<Login> staffLoginInfo = new ArrayList<>();

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
        staffLoginInfo.add(new Login(username, password));
    }

    //setters
    public void setStaffType(char staffType) {
        this.staffType = staffType;
    }

    //getters
    public char getStaffType(String staffId) {
        return staffType;
    }

    //toString
    // public String toString() {
    //     return super.toString() + String.format("Staff Id: %s\nStaff Type: %c\n", staffId, staffType);
    // }

    //writing all info into file
    public static void writeAdminInfo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("adminFile.txt"))) {
            for (Login login : staffLoginInfo) {
                writer.write(login.getUsername() + " " + login.getPassword(login.getUsername()));
                writer.newLine();
            }
            System.out.println("User list has been saved to adminFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
