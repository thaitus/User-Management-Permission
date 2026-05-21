package UserManagementPermission.config;

import UserManagementPermission.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();

        // 1. Cho phép các URL tĩnh (CSS, JS, Hình ảnh) đi qua thoải mái
        if (uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/images/")) {
            return true;
        }

        // 2. Kiểm tra đăng nhập (Chưa đăng nhập thì đuổi ra trang login)
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Loại trừ URL login để không bị vòng lặp vô hạn
            if (!uri.equals("/login")) {
                response.sendRedirect("/login");
                return false;
            }
            return true;
        }

        // Nếu đã đăng nhập mà cố vào lại /login thì đẩy về home
        if (uri.equals("/login")) {
            response.sendRedirect("/home");
            return false;
        }

        // 3. Cho phép các Màn hình Mặc định (Trang chủ, Profile, Đăng xuất, Báo lỗi)
        if (uri.startsWith("/home") || uri.startsWith("/profile") || uri.startsWith("/logout") || uri.startsWith("/403")) {
            return true;
        }

        // 4. KIỂM TRA PHÂN QUYỀN TỪ SESSION
        // 4. KIỂM TRA PHÂN QUYỀN TỪ SESSION (Map giờ dùng Key là Integer)
        @SuppressWarnings("unchecked")
        Map<Integer, Map<String, Boolean>> userPermissions = (Map<Integer, Map<String, Boolean>>) session.getAttribute("userPermissions");

        if (userPermissions == null) {
            response.sendRedirect("/403");
            return false;
        }

        // A. Cụm màn hình Quản lý Người dùng (/users) -> screenID = 1
        if (uri.startsWith("/users")) {
            Map<String, Boolean> perm = userPermissions.get(1); // Lấy quyền của ID 1
            
            if (perm == null || !perm.getOrDefault("canView", false)) {
                response.sendRedirect("/403");
                return false;
            }
            if (uri.equals("/users/add") || uri.equals("/users/edit") || uri.equals("/users/delete")) {
                if (!perm.getOrDefault("canEdit", false)) {
                    response.sendRedirect("/403");
                    return false;
                }
            }
        }

        // B. Cụm màn hình Nhóm người dùng (/groups) - Chuẩn bị cho tương lai
        if (uri.startsWith("/groups")) {
            Map<String, Boolean> perm = userPermissions.get(2); // Lấy quyền của ID 2
            if (perm == null || !perm.getOrDefault("canView", false)) {
                response.sendRedirect("/403");
                return false;
            }
            if (uri.equals("/groups/add") || uri.equals("/groups/edit") || uri.equals("/groups/delete")) {
                if (!perm.getOrDefault("canEdit", false)) {
                    response.sendRedirect("/403");
                    return false;
                }
            }
        }

        // C. Cụm màn hình Phân quyền (/permissions) - Chuẩn bị cho tương lai
        if (uri.startsWith("/permissions")) {
            Map<String, Boolean> perm = userPermissions.get(3); // Lấy quyền của ID 3
            if (perm == null || !perm.getOrDefault("canView", false)) {
                response.sendRedirect("/403");
                return false;
            }
        }

        return true; // Qua hết các ải trên thì cho phép đi tiếp!
    }
}