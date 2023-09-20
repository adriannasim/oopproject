public class User {
    //variable declaration
    private Login login;
    private String fullname;
    private String email;

    //constructors
    User() {
        this.login = new Login("", "");
        this.fullname = "";
        this.email = "";
    }
    User(String username, String password, String fullname, String email) {
        this.login = new Login(username, password);
        this.fullname = fullname;
        this.email = email;
    }

    //setters
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    //getters
    public String getUsername() {
        return login.getUsername();
    }
    public String getPassword() {
        return login.getPassword();
    }
    public String getFullname(String username) {
        return fullname;
    }
    public String getEmail(String username) {
        return email;
    }

}
