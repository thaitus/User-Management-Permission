package UserManagementPermission.service;

import UserManagementPermission.model.Group;
import UserManagementPermission.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    // 1. Lấy danh sách nhóm (Hỗ trợ tìm kiếm và phân trang)
    public Page<Group> getPaginatedGroups(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return groupRepository.findByGroupNameContainingIgnoreCase(keyword.trim(), pageable);
        }
        return groupRepository.findAll(pageable);
    }

    // 2. Lấy toàn bộ danh sách nhóm không phân trang (Dùng để đổ vào thẻ <select> sau này)
    public Iterable<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // 3. Lấy thông tin 1 nhóm theo ID (Dùng để bật form Chỉnh sửa)
    public Group getGroupById(int id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.orElse(null);
    }

    // 4. Kiểm tra tên nhóm đã tồn tại chưa (Bắt lỗi trùng lặp)
    public boolean isGroupNameExists(String groupName) {
        return groupRepository.existsByGroupNameIgnoreCase(groupName.trim());
    }

    // 5. Thêm mới hoặc Cập nhật nhóm
    public void saveGroup(Group group) {
        // Cắt khoảng trắng thừa trước khi lưu xuống DB
        if (group.getGroupName() != null) {
            group.setGroupName(group.getGroupName().trim().replaceAll("\\s+", " "));
        }
        groupRepository.save(group);
    }

    // 6. Xóa nhóm
    public void deleteGroup(int id) {
        groupRepository.deleteById(id);
    }

    // VŨ KHÍ MA TRẬN 1: Lấy danh sách quyền của 1 nhóm để vẽ giao diện
    public java.util.List<UserManagementPermission.model.PermissionDTO> getGroupPermissions(int groupId) {
        java.util.List<Object[]> results = groupRepository.getMatrixByGroupId(groupId);
        java.util.List<UserManagementPermission.model.PermissionDTO> dtoList = new java.util.ArrayList<>();
        
        for (Object[] row : results) {
            int screenId = ((Number) row[0]).intValue();
            String screenName = (String) row[1];
            boolean canView = ((Number) row[2]).intValue() == 1;
            boolean canEdit = ((Number) row[3]).intValue() == 1;
            dtoList.add(new UserManagementPermission.model.PermissionDTO(screenId, screenName, canView, canEdit));
        }
        return dtoList;
    }

    // VŨ KHÍ MA TRẬN 2: Lưu lại mớ bòng bong quyền mới mà Admin vừa gạt
    @jakarta.transaction.Transactional
    public void updateGroupMatrix(int groupId, java.util.List<Integer> viewScreens, java.util.List<Integer> editScreens) {
        // 1. Dọn sạch rác cũ
        groupRepository.deletePermissionsByGroupId(groupId);
        
        // 2. Lấy danh sách tất cả các màn hình mà Admin vừa gạt (Dùng Set để lọc trùng)
        java.util.Set<Integer> allInteractedScreens = new java.util.HashSet<>();
        if (viewScreens != null) allInteractedScreens.addAll(viewScreens);
        if (editScreens != null) allInteractedScreens.addAll(editScreens);

        // 3. Vòng lặp lưu xuống Database
        for (Integer screenId : allInteractedScreens) {
            boolean canView = viewScreens != null && viewScreens.contains(screenId);
            boolean canEdit = editScreens != null && editScreens.contains(screenId);
            groupRepository.savePermission(groupId, screenId, canView, canEdit);
        }
    }
}