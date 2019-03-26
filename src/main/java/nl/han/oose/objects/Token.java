package nl.han.oose.objects;



public class Token {
    private String user;
    private String token;

    public Token() {}

    public Token(String token, String user) {
        this.token = token;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}