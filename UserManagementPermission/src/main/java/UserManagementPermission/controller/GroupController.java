package UserManagementPermission.controller;

import UserManagementPermission.model.Group;
import UserManagementPermission.service.GroupService;
import UserManagementPermission.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private final UserService userService;
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    // 1. Hiển thị danh sách nhóm
    @GetMapping
    public String listGroups(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "groupName") String sortBy,
                             @RequestParam(defaultValue = "asc") String sortDir,
                             @RequestParam(required = false) String keyword) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, 10, sort);

        Page<Group> groupPage = groupService.getPaginatedGroups(keyword, pageable);

        model.addAttribute("groupList", groupPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", groupPage.getTotalPages());
        model.addAttribute("totalElements", groupPage.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword);

        return "groups";
    }

    // 2. Thêm nhóm mới
    @PostMapping("/add")
    public String addGroup(@RequestParam("groupName") String groupName, RedirectAttributes redirectAttributes) {
        if (groupName == null || groupName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMsg", "Tên nhóm không được để trống!");
            return "redirect:/groups";
        }

        if (groupService.isGroupNameExists(groupName)) {
            redirectAttributes.addFlashAttribute("errorMsg", "Tên nhóm đã tồn tại!");
            return "redirect:/groups";
        }

        Group group = new Group();
        group.setGroupName(groupName.trim());
        groupService.saveGroup(group);

        redirectAttributes.addFlashAttribute("successMsg", "Thêm nhóm mới thành công!");
        return "redirect:/groups";
    }

    // 3. Cập nhật nhóm
    @PostMapping("/edit")
    public String editGroup(@RequestParam("groupId") int groupId,
                            @RequestParam("groupName") String groupName,
                            RedirectAttributes redirectAttributes) {
        Group existingGroup = groupService.getGroupById(groupId);
        if (existingGroup == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "Không tìm thấy nhóm!");
            return "redirect:/groups";
        }

        if (groupName == null || groupName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMsg", "Tên nhóm không được để trống!");
            return "redirect:/groups";
        }

        // Bỏ qua check trùng nếu tên không đổi
        if (!existingGroup.getGroupName().equalsIgnoreCase(groupName.trim()) && groupService.isGroupNameExists(groupName)) {
            redirectAttributes.addFlashAttribute("errorMsg", "Tên nhóm đã tồn tại!");
            return "redirect:/groups";
        }

        existingGroup.setGroupName(groupName.trim());
        groupService.saveGroup(existingGroup);

        redirectAttributes.addFlashAttribute("successMsg", "Cập nhật nhóm thành công!");
        return "redirect:/groups";
    }

    // 4. Xóa nhóm
    @PostMapping("/delete")
    public String deleteGroup(@RequestParam("groupId") int groupId, RedirectAttributes redirectAttributes) {
        try {
            groupService.deleteGroup(groupId);
            redirectAttributes.addFlashAttribute("successMsg", "Xóa nhóm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Lỗi: Không thể xóa nhóm này!");
        }
        return "redirect:/groups";
    }

    // 1. Mở trang Quản lý thành viên của 1 nhóm
    @GetMapping("/{id}/members")
    public String manageMembers(@PathVariable("id") int groupId, Model model) {
        Group group = groupService.getGroupById(groupId);
        if (group == null) return "redirect:/groups";

        model.addAttribute("group", group);
        model.addAttribute("currentMembers", userService.getUsersByGroupId(groupId)); // Danh sách bên trái
        model.addAttribute("availableUsers", userService.getUsersWithoutGroup()); // Danh sách bên phải (để pick)
        
        return "group-members"; // Ta sẽ tạo file HTML này ở bước sau
    }

    // 2. Nút: Thêm các user được tick chọn vào nhóm
    @PostMapping("/{id}/members/add")
    public String addMembersToGroup(@PathVariable("id") int groupId, 
                                    @RequestParam("groupName") String groupName,
                                    @RequestParam(value = "userIds", required = false) List<Integer> userIds, 
                                    RedirectAttributes ra) {
        if (userIds != null && !userIds.isEmpty()) {
            userService.addUsersToGroup(groupId, groupName, userIds);
            ra.addFlashAttribute("successMsg", "Đã thêm " + userIds.size() + " thành viên vào nhóm!");
        } else {
            ra.addFlashAttribute("errorMsg", "Bạn chưa chọn người dùng nào!");
        }
        return "redirect:/groups/" + groupId + "/members";
    }

    // 3. Nút: Đuổi 1 user ra khỏi nhóm
    @PostMapping("/{id}/members/remove")
    public String removeMemberFromGroup(@PathVariable("id") int groupId, 
                                        @RequestParam("userId") int userId, 
                                        RedirectAttributes ra) {
        userService.removeUserFromGroup(userId);
        ra.addFlashAttribute("successMsg", "Đã xóa thành viên khỏi nhóm!");
        return "redirect:/groups/" + groupId + "/members";
    }
}