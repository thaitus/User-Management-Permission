package UserManagementPermission.controller;

import UserManagementPermission.model.User;
import UserManagementPermission.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // <--- Import chuẩn ở đây
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) { 
        this.userService = userService; 
    }

    @GetMapping
    public String showUserList(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "userID") String sortBy, // Đổi mặc định thành userID
            @RequestParam(defaultValue = "none") String sortDir,  // Đổi mặc định thành none
            HttpSession session, Model model) {
        
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";

        Page<User> userPage = userService.getUsersWithPagination(keyword, page, sortBy, sortDir);
        
        model.addAttribute("userList", userPage.getContent());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("menuPage", "users"); // Đổi tên để tránh trùng với biến page
        model.addAttribute("totalElements", userPage.getTotalElements());
        
        return "users";
    }
    @PostMapping("/add")
    public String addUser(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("fullName") String fullName,
            @RequestParam(value = "position", required = false) String position,
            RedirectAttributes ra) {

        boolean hasError = false;

        // UTC_010: Bỏ trống tất cả
        if (userName.isEmpty() && password.isEmpty() && confirmPassword.isEmpty() && fullName.isEmpty()) {
            ra.addFlashAttribute("errorGeneral", "Không được bỏ trống");
            return "redirect:/users?openAddModal=true";
        }

        // 1. KIỂM TRA TÀI KHOẢN (Cột trái - Dòng 1)
        if (userName.isEmpty()) {
            ra.addFlashAttribute("errorUserName", "Tài khoản không được bỏ trống");
            hasError = true;
        } else if (userName.endsWith(" ")) {
            ra.addFlashAttribute("errorUserName", "Tài khoản không đúng định dạng. Vui lòng thử lại");
            hasError = true;
        } else if (userService.isUserNameExists(userName)) {
            ra.addFlashAttribute("errorUserName", "Tài khoản hiện đã có người dùng. Vui lòng tạo tài khoản khác");
            hasError = true;
            ra.addFlashAttribute("userNameVal", userName);
        } else {
            ra.addFlashAttribute("userNameVal", userName);
        }

        // 2. KIỂM TRA MẬT KHẨU (Cột trái - Dòng 2)
        if (password.isEmpty()) {
            ra.addFlashAttribute("errorPassword", "Mật khẩu không được bỏ trống");
            hasError = true;
        }

        // 3. KIỂM TRA XÁC NHẬN MẬT KHẨU (Cột trái - Dòng 3)
        if (confirmPassword.isEmpty()) {
            ra.addFlashAttribute("errorConfirmPassword", "Xác nhận mật khẩu không được bỏ trống");
            hasError = true;
        } else if (!confirmPassword.equals(password)) {
            ra.addFlashAttribute("errorConfirmPassword", "Xác nhận mật khẩu không trùng với mật khẩu. Vui lòng thử lại");
            hasError = true;
        } else {
            ra.addFlashAttribute("confirmPasswordVal", confirmPassword);
        }

        // 4. KIỂM TRA HỌ VÀ TÊN (Cột phải - Dòng 1)
        if (fullName.isEmpty()) {
            ra.addFlashAttribute("errorFullName", "Họ tên không được bỏ trống");
            hasError = true;
        } else if (fullName.matches(".*\\d.*")) {
            ra.addFlashAttribute("errorFullName", "Họ tên không đúng định dạng. Vui lòng thử lại");
            hasError = true;
        } else {
            fullName = fullName.trim();
            ra.addFlashAttribute("fullNameVal", fullName);
        }

        // Giữ lại giá trị Nhóm
        if (position != null) ra.addFlashAttribute("positionVal", position);

        if (hasError) return "redirect:/users?openAddModal=true";

        // THÀNH CÔNG
        userService.createUser(userName, password, fullName, position);
        ra.addFlashAttribute("successMsg", "Tạo tài khoản thành công!");
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userId") int userId, RedirectAttributes ra) {
        try {
            userService.deleteUser(userId);
            ra.addFlashAttribute("successMsg", "Đã xóa tài khoản thành công!");
        } catch (Exception e) {
            // Đề phòng tài khoản này đã dính khóa ngoại (Foreign Key) ở bảng khác
            ra.addFlashAttribute("errorGeneral", "Không thể xóa tài khoản này vì dữ liệu đang được liên kết!");
        }
        return "redirect:/users";
    }

    @PostMapping("/edit")
    public String editUser(
            @RequestParam("userId") int userId,
            @RequestParam("fullName") String fullName,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "isEnabled", defaultValue = "true") boolean isEnabled,
            RedirectAttributes ra) {
        
        // 1. Tự động cắt khoảng trắng thừa (Kể cả khoảng trắng giữa các từ)
        String trimmedName = (fullName != null) ? fullName.trim().replaceAll("\\s+", " ") : "";

        // 2. Chặn lỗi bỏ trống
        if (trimmedName.isEmpty()) {
            ra.addFlashAttribute("errorGeneral", "Cập nhật thất bại: Họ tên không được bỏ trống!");
            return "redirect:/users";
        }

        // 3. Chặn số và ký tự đặc biệt (Chỉ lấy chữ Unicode và khoảng trắng)
        if (!trimmedName.matches("^[a-zA-Z\\p{L}\\s]+$")) {
            ra.addFlashAttribute("errorGeneral", "Cập nhật thất bại: Họ tên không được chứa số hay ký tự đặc biệt!");
            return "redirect:/users";
        }

        // 4. Nếu vượt qua mọi bài test, tiến hành Update
        userService.updateUserByAdmin(userId, trimmedName, position, isEnabled);
        ra.addFlashAttribute("successMsg", "Đã cập nhật thông tin tài khoản thành công!");
        
        return "redirect:/users";
    }
}