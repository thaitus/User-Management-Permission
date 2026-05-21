package UserManagementPermission.controller;

import UserManagementPermission.model.User;
import UserManagementPermission.repository.GroupRepository;
import UserManagementPermission.model.ScreenPermissionProjection;
import UserManagementPermission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    private final UserService userService;
    private final GroupRepository groupRepository;
    
    @Autowired
    public LoginController(UserService userService, GroupRepository groupRepository) {
        this.userService = userService;
        this.groupRepository = groupRepository;
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

        // Validate báo lỗi từng ô cụ thể
        if (isUserNameEmpty || isPassEmpty) {
            if (isUserNameEmpty) {
                model.addAttribute("errorUserName", "Không được bỏ trống.");
            }
            if (isPassEmpty) {
                model.addAttribute("errorPass", "Không được bỏ trống.");
            }
            if (!isUserNameEmpty) {
                model.addAttribute("userName", userName);
            }
            return "login"; // Trả về trang login nếu trống
        }

        Object[] authResult = userService.authenticateUser(userName, pass);
        boolean isSuccess = (boolean) authResult[0];

        // NẾU ĐĂNG NHẬP THÀNH CÔNG
        if (isSuccess) {
            User user = (User) authResult[1];
            
            userService.updateActiveStatus(true, user.getUserID());
            user.setActive(true);
            session.setAttribute("loggedInUser", user);

            // NẠP PHÂN QUYỀN
            List<ScreenPermissionProjection> permList = groupRepository.findUserPermissions(user.getUserID());
            Map<Integer, Map<String, Boolean>> permissionMap = new HashMap<>();
            
            if (permList != null) {
                for (ScreenPermissionProjection p : permList) {
                    Map<String, Boolean> rights = new HashMap<>();
                    
                    // Vì giờ nó là Boolean rồi nên chỉ cần check null là dùng luôn
                    rights.put("canView", p.getCanView() != null && p.getCanView());
                    rights.put("canEdit", p.getCanEdit() != null && p.getCanEdit());
                    
                    permissionMap.put(p.getScreenId(), rights);
                }
            }
            session.setAttribute("userPermissions", permissionMap);
            
            return "redirect:/home";
        } 
        
        // NẾU ĐĂNG NHẬP THẤT BẠI (Chạy xuống đây)
        String errorMessage = (String) authResult[1];
        String errorType = (String) authResult[2];
        
        if (errorType.equals("error_password")) {
            model.addAttribute("errorPass", errorMessage);
            model.addAttribute("userName", userName);
        } else if (errorType.equals("error_username")) {
            model.addAttribute("errorUserName", errorMessage);
        } else if (errorType.equals("error_locked")) {
            model.addAttribute("error", errorMessage);
            model.addAttribute("userName", userName);
        }

        // Đảm bảo LUÔN LUÔN có một lệnh return String ở cuối cùng của hàm
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            userService.updateActiveStatus(false, user.getUserID());
        }
        session.invalidate(); 
        return "redirect:/login";
    }
}