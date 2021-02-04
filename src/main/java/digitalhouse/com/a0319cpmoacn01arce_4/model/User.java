package digitalhouse.com.a0319cpmoacn01arce_4.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "authid")
    private String authID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "imageprofile")
    private String imageProfile;

    public User(Integer id, String authID, String name, String imageProfile) {
        this.id = id;
        this.authID = authID;
        this.name = name;
        this.imageProfile = imageProfile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }
}
