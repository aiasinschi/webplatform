package ro.iasinschi.webplatform.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * File UserData.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/10/2015 5:56 PM.
 */
@Entity
public class UserData {

    @Id
    @GeneratedValue
    private long id;

    private long userid;

    private String tablename;

    private int role;

    public UserData() {
    }

    public UserData(long userid, String tablename, int role) {
        this.userid = userid;
        this.tablename = tablename;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String databasename) {
        this.tablename = databasename;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}
