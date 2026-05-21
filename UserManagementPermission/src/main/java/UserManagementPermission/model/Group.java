package UserManagementPermission.model; // Nhớ đổi package theo project của ông

import jakarta.persistence.*;

@Entity
@Table(name = "`group`") // Dùng backtick vì 'group' là từ khóa nhạy cảm trong SQL
public class Group {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupID;
    
    private String groupName;

    // Getters và Setters
    public int getGroupID() { return groupID; }
    public void setGroupID(int groupID) { this.groupID = groupID; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
}