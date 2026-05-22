package UserManagementPermission.config;

import UserManagementPermission.model.User;
import UserManagementPermission.service.UserService;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@Component
public class SessionListener implements HttpSessionListener {

    private final UserService userService;

    public SessionListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Có thể thêm logic khi session vừa tạo nếu cần
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        if (session != null) {
            User user = (User) session.getAttribute("loggedInUser");
            if (user != null) {
                // Tự động set trạng thái Offline khi trình duyệt bị tắt hoặc session timeout
                try {
                    userService.updateActiveStatus(false, user.getUserID());
                } catch (Exception e) {
                    System.err.println("Lỗi khi cập nhật trạng thái Offline: " + e.getMessage());
                }
            }
        }
    }
}
