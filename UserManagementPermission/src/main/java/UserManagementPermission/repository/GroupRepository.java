package UserManagementPermission.repository;

import UserManagementPermission.model.Group;
import UserManagementPermission.model.ScreenPermissionProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query(value = """
        SELECT s.screenID AS screenId, 
               MAX(p.canView) AS canView, 
               MAX(p.canEdit) AS canEdit 
        FROM usergroup ug
        JOIN permission p ON ug.groupID = p.groupID
        JOIN screen s ON p.screenID = s.screenID
        WHERE ug.userID = :userId
        GROUP BY s.screenID
        """, nativeQuery = true)
    List<ScreenPermissionProjection> findUserPermissions(@Param("userId") int userId);

    // Tìm kiếm nhóm theo tên có phân trang (Bỏ qua hoa thường)
    Page<Group> findByGroupNameContainingIgnoreCase(String keyword, Pageable pageable);

    // Kiểm tra xem tên nhóm đã tồn tại chưa (Dùng để bắt lỗi khi Thêm mới/Sửa)
    boolean existsByGroupNameIgnoreCase(String groupName);
}