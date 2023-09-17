import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Customer extends User{
    //variables declaration
    private String contactNo;
    private char gender;

    //arrayList for customer info
    private static ArrayList<Login> custLoginInfo = new ArrayList<>();

    //constructors
    Customer() {
        super();
        this.contactNo = "";
        this.gender = ' ';
    }
    Customer(String username, String password, String fullname, String email, String contactNo, char gender) {
        super(username, password, fullname, email);
        this.contactNo = contactNo;
        this.gender = gender;

        //writing details to arrayList
        custLoginInfo.add(new Login(username, password));
    }

    //setters
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }

    //getters
    public String getContactNo(String username) {
        return contactNo;
    }
    public char getGender(String username) {
        return gender;
    }

    //toString
    // public String toString() {
    //     return super.toString() + String.format("Contact: %s\nGender: %c", contactNo, gender);
    // }

    //writing all info into file
    public static void writeCustInfo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("custFile.txt"))) {
            for (Login login : custLoginInfo) {
                writer.write(login.getUsername() + " " + login.getPassword(login.getUsername()));
                writer.newLine();
            }
            System.out.println("User list has been saved to custFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
