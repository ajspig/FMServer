package request;
/**
 * request objects give what are service classes need
 * this gives our loginService class what it needs
 * this will login an existing user
 */

public class LoginRequest {
    /**
     * we need these two private data members to authenticate our user
     */
    String username;
    String password;
    /**
     * constructor for our class
     * creates a loginRequest object and declares all of our
     * private data members
     * @param username
     * @param password
     */

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
