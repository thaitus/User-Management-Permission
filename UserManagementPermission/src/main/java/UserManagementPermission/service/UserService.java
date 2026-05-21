package UserManagementPermission.service;

import UserManagementPermission.model.User;
import UserManagementPermission.repository.UserRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<User> getAllUsers() {
        // Hàm findAll() là hàm mặc định có sẵn của JpaRepository, 
        // không vi phạm quy tắc Native Query của dự án đâu, ông yên tâm!
        return userRepository.findAll(); 
    }
    public List<User> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findAll(); // Nếu không gõ gì thì lấy tất cả
        }
        return userRepository.searchUsers(keyword.trim());
    }
    public boolean isUserNameExists(String userName) {
        return userRepository.countByUserName(userName) > 0;
    }

    public void createUser(String userName, String pass, String fullName, String position) {
        userRepository.insertUser(userName, pass, fullName, position);
    }

    public Page<User> getUsersWithPagination(String keyword, int page, String sortBy, String sortDir) {
        // Khai báo phân trang (mặc định 10 dòng/trang). 
        // BỎ HẲN thằng Sort đi vì mình đã cấu hình Sort bằng CASE WHEN dưới Native Query rồi.
        Pageable pageable = PageRequest.of(page, 10); 
        
        // Truyền đủ 4 tham số vào Repository
        return userRepository.findAllUsersNative(keyword, sortBy, sortDir, pageable);
    }

    public void deleteUser(int userId) {
        userRepository.deleteUser(userId);
    }

    public void updateUserByAdmin(int userId, String fullName, String position, boolean isEnabled) {
        userRepository.updateUserByAdmin(userId, fullName, position, isEnabled);
    }

    // Lấy danh sách thành viên (Để Controller gọi)
    public List<User> getUsersByGroupId(int groupId) {
        return userRepository.findUsersByGroupId(groupId);
    }

    public List<User> getUsersWithoutGroup() {
        return userRepository.findUsersWithoutGroup();
    }

    // Xử lý XÓA thành viên khỏi nhóm
    @jakarta.transaction.Transactional
    public void removeUserFromGroup(int userId) {
        userRepository.removeUserFromGroup(userId);
        userRepository.updateUserPosition(userId, "Chưa có"); // Reset lại Vai trò thành rỗng
    }

    // Xử lý THÊM NHIỀU thành viên vào nhóm
    @jakarta.transaction.Transactional
    public void addUsersToGroup(int groupId, String groupName, List<Integer> userIds) {
        if (userIds != null && !userIds.isEmpty()) {
            for (int userId : userIds) {
                // Đảm bảo xóa sạch liên kết cũ (nếu có lỗi kẹt dữ liệu) trước khi thêm mới
                userRepository.removeUserFromGroup(userId); 
                userRepository.addUserToGroup(userId, groupId);
                userRepository.updateUserPosition(userId, groupName); // Đồng bộ tên nhóm vào cột position
            }
        }
    }
}