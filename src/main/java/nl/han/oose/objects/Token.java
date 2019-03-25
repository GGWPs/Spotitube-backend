package nl.han.oose.objects;

public class Token {
    private String username;
    private String token;

    public Token() {
    }

    public Token(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}