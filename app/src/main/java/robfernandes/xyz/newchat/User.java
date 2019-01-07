package robfernandes.xyz.newchat;

/**
 * Created by Roberto Fernandes on 07/01/2019.
 */
public class User {
    private String uid;
    private String username;
    private String url;

    public User(String uid, String username, String url) {
        this.uid = uid;
        this.username = username;
        this.url = url;
    }

    public String getUid() {
        return uid;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }
}
