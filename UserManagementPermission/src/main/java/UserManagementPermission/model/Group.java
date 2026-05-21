package UserManagementPermission.model; // Nhớ đổi package theo project của ông
import org.hibernate.annotations.Formula;
import jakarta.persistence.*;

@Entity
@Table(name = "`group`") // Dùng backtick vì 'group' là từ khóa nhạy cảm trong SQL
public class Group {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int groupID;
    
    private String groupName;

    @Formula("(SELECT COUNT(ug.userID) FROM `usergroup` ug WHERE ug.groupID = groupID)")
    private int memberCount;

    // Getters và Setters
    public int getGroupID() { return groupID; }
    public void setGroupID(int groupID) { this.groupID = groupID; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }
}