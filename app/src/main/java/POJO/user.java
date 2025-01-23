package POJO;

public class user {

    private String username;
    private String password;

    // Constructor vacío
    public user() {
    }

    // Constructor con parámetros
    public user(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter para username
    public String getUsername() {
        return username;
    }

    // Setter para username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter para password
    public String getPassword() {
        return password;
    }

    // Setter para password
    public void setPassword(String password) {
        this.password = password;
    }
}
