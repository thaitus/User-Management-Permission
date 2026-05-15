package UserManagementPermission.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity báo cho Spring biết đây là class dùng để map với Database
@Entity
// Sử dụng backtick (`) để mapping đúng với bảng `User` đã tạo, tránh trùng từ khóa hệ thống
@Table(name = "`User`") 
public class User {
    
    // @Id đánh dấu trường này là Khóa chính (Primary Key)
    @Id
    @Column(name = "userID")
    private int userID;
    
    @Column(name = "userName")
    private String userName;
    
    @Column(name = "pass")
    private String pass;
    
    @Column(name = "isActive")
    private boolean isActive;
    
    @Column(name = "isEnabled")
    private boolean isEnabled;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "position")
    private String position;

    @Column(name = "avatar")
    private String avatar = "/images/default-avatar.png";

    @jakarta.persistence.Transient
    private String department = "Ban Giám Đốc";

    public User() {
    }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }

    public boolean isEnabled() { return isEnabled; }
    public void setEnabled(boolean enabled) { this.isEnabled = enabled; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}