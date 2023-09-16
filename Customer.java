public class Customer extends User{
    //variables declaration
    private String contactNo;
    private Character gender;

    //constructors
    Customer() {
        super();
        this.contactNo = "";
        this.gender = ' ';
    }
    Customer(String username, String password, String fullname, String email, String contactNo, Character gender) {
        super(username, password, fullname, email);
        this.contactNo = contactNo;
        this.gender = gender;
    }

    //setters
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public void setGender(Character gender) {
        this.gender = gender;
    }

    //getters
    public String getContactNo(String username) {
        return contactNo;
    }
    public Character getGender(String username) {
        return gender;
    }

    //toString
    public String toString() {
        return super.toString() + String.format("Contact: %s\nGender: %c", contactNo, gender);
    }
}
