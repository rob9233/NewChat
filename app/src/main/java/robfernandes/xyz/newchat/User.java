package robfernandes.xyz.newchat;

/**
 * Created by Roberto Fernandes on 07/01/2019.
 */
public class User {
    private String id;
    private String username;
    private String url;

    public User(String id, String username, String url) {
        this.id = id;
        this.username = username;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
