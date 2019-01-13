package robfernandes.xyz.newchat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Roberto Fernandes on 07/01/2019.
 */
public class User implements Parcelable {
    private String uid;
    private String username;
    private String url;

    public User() {
    }

    public User(String uid, String username, String url) {
        this.uid = uid;
        this.username = username;
        this.url = url;
    }

    protected User(Parcel in) {
        uid = in.readString();
        username = in.readString();
        url = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {

        return url;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(username);
        dest.writeString(url);
    }
}
