package ro.iasinschi.webplatform.util.constants;

/**
 * File RoleType.java
 * TODO: Document me
 * <p/>
 * Created by Adrian Iasinschi (aiasinschi@gmail.com) on 1/11/2015 12:49 PM.
 */
public enum RoleType {

    ADMIN(0, "Admin"),

    USER(1, "User");

    private int value;

    private String description;

    private RoleType(int value, String description){
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
