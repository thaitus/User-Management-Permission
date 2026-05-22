package UserManagementPermission.repository;

import UserManagementPermission.model.Group;
import UserManagementPermission.model.ScreenPermissionProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    // 1. Kéo toàn bộ màn hình và quyền của 1 nhóm lên.
    // Trả về một List Object thô, tí nữa Service sẽ ép kiểu sang PermissionDTO
    @Query(value = "SELECT s.screenID, s.screenName, " +
            "COALESCE(p.canView, 0) as canView, " +
            "COALESCE(p.canEdit, 0) as canEdit " +
            "FROM `Screen` s " +
            "LEFT JOIN `Permission` p ON s.screenID = p.screenID AND p.groupID = :groupId", 
            nativeQuery = true)
    List<Object[]> getMatrixByGroupId(@Param("groupId") int groupId);

    // 2. Xóa sạch quyền cũ của 1 nhóm (Dọn đường để lưu quyền mới)
    @Modifying
    @jakarta.transaction.Transactional
    @Query(value = "DELETE FROM `Permission` WHERE groupID = :groupId", nativeQuery = true)
    void deletePermissionsByGroupId(@Param("groupId") int groupId);

    // 3. Chèn quyền mới vào (Batch chèn)
    @Modifying
    @jakarta.transaction.Transactional
    @Query(value = "INSERT INTO `Permission` (groupID, screenID, canView, canEdit) VALUES (:groupId, :screenId, :canView, :canEdit)", nativeQuery = true)
    void savePermission(@Param("groupId") int groupId, @Param("screenId") int screenId, @Param("canView") boolean canView, @Param("canEdit") boolean canEdit);
}