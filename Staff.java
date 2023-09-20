import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;                           
import java.util.ArrayList;
import java.util.Scanner;

public class Staff extends User implements ReadAndWrite {
    //variables declaration
    private String staffId;
    private char staffType;

    //arrayList for customer info
    protected static ArrayList<Staff> staffDetails = new ArrayList<>();

    //constructors
    Staff() {
        super();
        this.staffId = "";
        this.staffType = ' ';
    }
    Staff(String username, String password, String fullname, String email, String staffId, char staffType) {
        super(username, password, fullname, email);
        this.staffId = staffId;
        this.staffType = staffType;

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
    public char getStaffType() {
        return staffType;
    }

    //staff menu
    public int staffMenu(Login login) {
        //read from file
        readFile();
        for (int i = 0; i < staffDetails.size(); i++) {
            if ((login.getUsername().equals(staffDetails.get(i).getUsername())) && staffDetails.get(i).getStaffType() == 'B') {
                return 1;
            } else if ((login.getUsername().equals(staffDetails.get(i).getUsername())) && staffDetails.get(i).getStaffType() == 'C') {
                return 2;
            }
        }
        return 0;
    }

    //write file
    public static void writeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("staffFile.txt", true))) {
            for (Staff staff : staffDetails) {
                writer.write(staff.getUsername() + "||" + staff.getPassword() + "||" + staff.getFullname(staff.getUsername()) + "||" + staff.getEmail(staff.getUsername()) + "||" + staff.staffId + "||" + staff.staffType);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //read file
    public static void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("staffFile.txt"))) {
            String info;
            while ((info = reader.readLine()) != null) {
                //delete whitespaces
                info = info.trim();

                String[] parts = info.split("\\|\\|");
                if (parts.length == 6) {
                    String username = parts[0];
                    String password = parts[1];
                    String fullname = parts[2];
                    String email = parts[3];
                    String staffId = parts[4];     
                    char staffType = parts[5].charAt(0);
                    
                    //add details from file to arraylist
                    Staff staff = new Staff(username, password, fullname, email, staffId, staffType);
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
            System.out.println("==================================================");
            System.out.println("                Create Staff Login");
            System.out.println("==================================================");
            System.out.printf("1. Create backend staff\n");
            System.out.printf("2. Create counter staff\n");
            System.out.println("\n* Press # to return");
            System.out.println("==================================================");
            System.out.print("Enter your option > ");

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
                    case 3:
                        return;
                    default:
                    System.out.printf("Invalid input, please enter your choice again.\n"); 
                }   
            } else {
                if (input.next().equals("#")) {
                    return;
                } else {
                    System.out.printf("Invalid input, please enter your choice again.\n");
                    //clear buffer
                    input.next();
                }
            }
        } while (loop);
        input.close();
    }
}
