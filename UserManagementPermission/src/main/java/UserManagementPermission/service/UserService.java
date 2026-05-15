package UserManagementPermission.service;

import UserManagementPermission.model.User;
import UserManagementPermission.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Object[] authenticateUser(String userName, String pass) {
        
        // 1. Check khoảng trắng
        if (userName != null && userName.contains(" ")) {
            return new Object[]{false, "Tài khoản không hợp lệ. Vui lòng thử lại.", "error_username"};
        }

        User user = userRepository.findByUserName(userName);

        // 2. Check tồn tại
        if (user == null) {
            return new Object[]{false, "Tài khoản không hợp lệ hoặc không tồn tại. Vui lòng thử lại.", "error_username"};
        }

        // 3. Check trạng thái khóa (ĐƯA LÊN TRƯỚC THEO REVIEW)
        if (!user.isEnabled()) {
            return new Object[]{false, "Tài khoản của bạn đã bị khóa bởi admin.", "error_locked"};
        }

        // 4. Check password (ĐƯA XUỐNG SAU)
        if (!user.getPass().equals(pass)) {
            return new Object[]{false, "Mật khẩu không hợp lệ. Vui lòng thử lại", "error_password"};
        }

        // 5. Thành công
        return new Object[]{true, user, "success"}; 
    }

    public void updateActiveStatus(boolean isActive, int userId) {
        userRepository.updateActiveStatus(isActive, userId);
    }
    public void updateFullName(String fullName, int userId) {
        userRepository.updateFullName(fullName, userId);
    }
    public void updateProfile(String fullName, String gender, String phone, String email, String position, int userId) {
        userRepository.updateProfile(fullName, gender, phone, email, position, userId);
    }
    public void updateAvatar(String avatarUrl, int userId) {
        userRepository.updateAvatar(avatarUrl, userId);
    }
    public void changePassword(String newPass, int userId) {
        userRepository.changePassword(newPass, userId);
    }
}