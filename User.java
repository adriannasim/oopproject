public class User {
    //variable declaration
    private LoginInfo login;
    private String fullname;
    private String email;

    //constructors
    User() {
        this.login = new LoginInfo("", "");
        this.fullname = "";
        this.email = "";
    }
    User(String username, String password, String fullname, String email) {
        this.login = new LoginInfo(username, password);
        this.fullname = "";
        this.email = "";
    }

    //setters
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    //getters
    public String getFullname(String username) {
        return fullname;
    }
    public String getEmail(String username) {
        return email;
    }
}
