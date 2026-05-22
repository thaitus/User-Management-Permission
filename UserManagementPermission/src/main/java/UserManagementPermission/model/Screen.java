package UserManagementPermission.model;

import jakarta.persistence.*;

@Entity
@Table(name = "`Screen`")
public class Screen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screenID")
    private int screenID;

    @Column(name = "screenName")
    private String screenName;

    public Screen() {}

    public int getScreenID() { return screenID; }
    public void setScreenID(int screenID) { this.screenID = screenID; }

    public String getScreenName() { return screenName; }
    public void setScreenName(String screenName) { this.screenName = screenName; }
}