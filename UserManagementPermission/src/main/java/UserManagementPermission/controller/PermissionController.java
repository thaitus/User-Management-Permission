package UserManagementPermission.controller;

import UserManagementPermission.model.Group;
import UserManagementPermission.model.PermissionDTO;
import UserManagementPermission.service.GroupService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/permissions")
public class PermissionController {

    private final GroupService groupService;

    // Tiêm GroupService vào để xử lý đọc/ghi ma trận quyền
    public PermissionController(GroupService groupService) {
        this.groupService = groupService;
    }

    // 1. Hiển thị Ma trận phân quyền
    @GetMapping
    public String showPermissionMatrix(@RequestParam(value = "groupId", required = false) Integer groupId,
                                       HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";

        // Gửi toàn bộ danh sách nhóm để đổ vào ô Chọn Nhóm (Dropdown)
        model.addAttribute("allGroups", groupService.getAllGroups());

        // Nếu Admin đã chọn một nhóm cụ thể để cấu hình
        if (groupId != null) {
            Group selectedGroup = groupService.getGroupById(groupId);
            if (selectedGroup != null) {
                model.addAttribute("selectedGroup", selectedGroup);
                
                // Móc ma trận quyền (Gồm tên màn hình + trạng thái Bật/Tắt) từ DB lên
                List<PermissionDTO> matrix = groupService.getGroupPermissions(groupId);
                model.addAttribute("matrix", matrix);
            }
        }

        model.addAttribute("currentPage", "permissions"); 
        return "permissions";
    }

    // 2. Xử lý lưu Ma trận khi Admin bấm nút xác nhận
    @PostMapping("/save")
    public String savePermissionMatrix(@RequestParam("groupId") int groupId,
                                       @RequestParam(value = "viewScreens", required = false) List<Integer> viewScreens,
                                       @RequestParam(value = "editScreens", required = false) List<Integer> editScreens,
                                       RedirectAttributes ra) {
        try {
            // Gọi hàm xử lý Transaction ghi đè mớ công tắc gạt xuống DB
            groupService.updateGroupMatrix(groupId, viewScreens, editScreens);
            ra.addFlashAttribute("successMsg", "Cập nhật ma trận phân quyền thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMsg", "Cập nhật ma trận thất bại! Vui lòng thử lại.");
        }
        
        // Quay trở lại đúng cái nhóm vừa cấu hình xong
        return "redirect:/permissions?groupId=" + groupId;
    }
}