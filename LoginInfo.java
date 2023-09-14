public class LoginInfo {
    //variable declaration
    private String username;
    private String password;

    //constructors
    LoginInfo() {
        this.username = "";
        this.password = "";
    }
    LoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    //setters
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //getters
    public String getUsername() {
        return username;
    }
    public String getPassword(String username) {
        return password;
    }
}