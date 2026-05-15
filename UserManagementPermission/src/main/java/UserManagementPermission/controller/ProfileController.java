package UserManagementPermission.controller;

import UserManagementPermission.model.User;
import UserManagementPermission.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller // THIẾU CÁI NÀY NÊN SPRING KHÔNG NHẬN BIẾT ĐƯỢC CLASS
@RequestMapping("/profile") // ĐỂ GOM NHÓM CÁC ĐƯỜNG DẪN BẮT ĐẦU BẰNG /profile
public class ProfileController {

    // CHỖ NÀY CỰC KỲ QUAN TRỌNG: PHẢI KHAI BÁO THÌ MỚI HẾT LỖI userService
    private final UserService userService;

    // KHỞI TẠO CONSTRUCTOR ĐỂ SPRING TIÊM BEAN VÀO
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    // 2. CHỨC NĂNG XEM HỒ SƠ CÁ NHÂN
    @GetMapping
    public String showProfilePage(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentPage", "profile");
        return "profile"; 
    }

    // 3. CHỨC NĂNG CẬP NHẬT HỒ SƠ
    @PostMapping("/update") // Vì đã có @RequestMapping("/profile") ở đầu nên chỉ cần /update
    public String updateProfile(
            @RequestParam("fullName") String fullName,
            @RequestParam("gender") String gender,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("position") String position,
            HttpSession session) {
        
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            userService.updateProfile(fullName, gender, phone, email, position, user.getUserID());

            user.setFullName(fullName);
            user.setGender(gender);
            user.setPhone(phone);
            user.setEmail(email);
            user.setPosition(position);
            
            session.setAttribute("loggedInUser", user);
        }
        return "redirect:/profile?success=true";
    }

    // XỬ LÝ UPLOAD AVATAR
    @PostMapping("/avatar")
    public String uploadAvatar(@RequestParam("avatarFile") MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null && !file.isEmpty()) {
            try {
                String fileName = "avatar_" + user.getUserID() + "_" + file.getOriginalFilename();
                String uploadDir = "uploads/";
                Path uploadPath = Paths.get(uploadDir);
                
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                String avatarUrl = "/uploads/" + fileName;
                
                userService.updateAvatar(avatarUrl, user.getUserID());
                user.setAvatar(avatarUrl);
                session.setAttribute("loggedInUser", user);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/profile?success=true";
    }

    // ĐỔI MẬT KHẨU
    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam("oldPass") String oldPass,
            @RequestParam("newPass") String newPass,
            @RequestParam("confirmPass") String confirmPass,
            HttpSession session, RedirectAttributes ra) {
        
        User user = (User) session.getAttribute("loggedInUser");
        boolean hasError = false;
        boolean isNewPassValid = true; // Cờ kiểm soát luồng kiểm tra

        // 1. KIỂM TRA MẬT KHẨU CŨ
        if (oldPass.isBlank()) {
            ra.addFlashAttribute("errorOldPass", "Mật khẩu cũ không được bỏ trống");
            hasError = true;
        } else if (!user.getPass().equals(oldPass)) {
            ra.addFlashAttribute("errorOldPass", "Mật khẩu cũ không chính xác");
            hasError = true; 
            // SAI -> Không add oldPassVal -> Tự động xóa ô mật khẩu cũ
        } else {
            // ĐÚNG -> Giữ lại mật khẩu cũ trên form
            ra.addFlashAttribute("oldPassVal", oldPass); 
        }

        // 2. KIỂM TRA MẬT KHẨU MỚI (Ưu tiên check trùng)
        if (newPass.isBlank()) {
            ra.addFlashAttribute("errorNewPass", "Mật khẩu mới không được bỏ trống");
            hasError = true;
            isNewPassValid = false;
        } else if (newPass.equals(oldPass)) {
            ra.addFlashAttribute("errorNewPass", "Mật khẩu mới không được trùng với mật khẩu cũ");
            hasError = true;
            isNewPassValid = false; 
            // SAI -> Không add newPassVal và confirmPassVal -> Xóa sạch 2 ô dưới (Chỉ giữ ô cũ)
        } else {
            // ĐÚNG -> Giữ lại nội dung mật khẩu mới
            ra.addFlashAttribute("newPassVal", newPass); 
        }

        // 3. KIỂM TRA XÁC NHẬN (Chỉ chạy khi Mật khẩu mới không bị lỗi trùng/trống)
        if (isNewPassValid) {
            if (confirmPass.isBlank()) {
                ra.addFlashAttribute("errorConfirmPass", "Xác nhận mật khẩu không được bỏ trống");
                hasError = true;
            } else if (!newPass.equals(confirmPass)) {
                ra.addFlashAttribute("errorConfirmPass", "Xác nhận mật khẩu không khớp");
                hasError = true;
                // SAI -> Xóa ô xác nhận, giữ lại ô mật khẩu mới và mật khẩu cũ
            } else {
                ra.addFlashAttribute("confirmPassVal", confirmPass);
            }
        }

        if (hasError) return "redirect:/profile?openModal=true";

        // 4. THÀNH CÔNG
        userService.changePassword(newPass, user.getUserID());
        user.setPass(newPass);
        session.setAttribute("loggedInUser", user);
        ra.addFlashAttribute("pwdSuccess", "Đã đổi mật khẩu bảo mật thành công!");
        return "redirect:/profile";
    }
}