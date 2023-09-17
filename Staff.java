public class Staff extends User{
    private String staffId;
    private char staffType;

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
        } else if (staffType == 'F') {
            this.staffId = String.format(staffType + Integer.toString(CounterStaff.nextFrontendStaffId));
            CounterStaff.nextFrontendStaffId++;
        }
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
}
