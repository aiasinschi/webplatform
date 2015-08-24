package ro.iasinschi.webplatform.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * File PlatformUser.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/2/2015 9:16 AM.
 */
@Entity
public class PlatformUser {

    @Id
    private long id;

    private String username;

    private String completeName;

    private String email;

    private String password;

    public PlatformUser() {
        id = -1;
        username = "";
        completeName = "Mock Up User";
        email = "aiasinschi@gmail.com";
    }

    public PlatformUser(String username, String completeName, String email, String password) {
        this.username = username;
        this.completeName = completeName;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PlatformUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", completeName='" + completeName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
