package UserManagementPermission.controller;

import UserManagementPermission.model.User;
import UserManagementPermission.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam(value = "userName", required = false) String userName,
                               @RequestParam(value = "pass", required = false) String pass,
                               HttpSession session,
                               Model model) {
        
        boolean isUserNameEmpty = (userName == null || userName.isEmpty());
        boolean isPassEmpty = (pass == null || pass.isEmpty());

        // Validate báo lỗi từng ô cụ thể theo UTC
        if (isUserNameEmpty || isPassEmpty) {
            if (isUserNameEmpty) {
                model.addAttribute("errorUserName", "Không được bỏ trống.");
            }
            if (isPassEmpty) {
                model.addAttribute("errorPass", "Không được bỏ trống.");
            }
            
            // Giữ lại nội dung ô tài khoản nếu người dùng chỉ quên nhập mật khẩu
            if (!isUserNameEmpty) {
                model.addAttribute("userName", userName);
            }
            return "login";
        }

        Object[] authResult = userService.authenticateUser(userName, pass);
        boolean isSuccess = (boolean) authResult[0];

        if (isSuccess) {
            User user = (User) authResult[1];
            
            userService.updateActiveStatus(true, user.getUserID());
            user.setActive(true);
            
            session.setAttribute("loggedInUser", user);
            return "redirect:/home"; 
        } else {
            String errorMessage = (String) authResult[1];
            String errorType = (String) authResult[2];
            
            // Tách lỗi theo đúng UTC
            if (errorType.equals("error_password")) {
                model.addAttribute("errorPass", errorMessage);
                model.addAttribute("userName", userName); // Giữ lại userName
            } else if (errorType.equals("error_username")) {
                model.addAttribute("errorUserName", errorMessage);
                // Cố tình KHÔNG truyền userName để UI tự xóa trắng (TC_003)
            } else if (errorType.equals("error_locked")) {
                model.addAttribute("error", errorMessage); // Lỗi khóa account thì báo ở trên cùng
                model.addAttribute("userName", userName);
            }

            return "login";
        }
    }
    // 1. CHỨC NĂNG ĐĂNG XUẤT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            // Cập nhật trạng thái active = 0 (Offline) xuống Database trước khi đăng xuất
            userService.updateActiveStatus(false, user.getUserID());
        }
        session.invalidate(); // Hủy toàn bộ phiên làm việc
        return "redirect:/login";
    }

    
    
}