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
}