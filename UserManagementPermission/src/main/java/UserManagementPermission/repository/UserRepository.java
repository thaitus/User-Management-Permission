package UserManagementPermission.repository;

import UserManagementPermission.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = """
            SELECT * FROM `User` 
            WHERE userName = ?1
            """, nativeQuery = true)
    User findByUserName(String userName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `User` SET isActive = ?1 WHERE userID = ?2", nativeQuery = true)
    void updateActiveStatus(boolean isActive, int userId);
    
    // -------------------------------------------------------------------
    // ĐÃ CHUYỂN TOÀN BỘ XUỐNG DƯỚI ĐÂY SANG NATIVE QUERY CHUẨN DỰ ÁN
    // -------------------------------------------------------------------

    @Modifying
    @Transactional
    @Query(value = "UPDATE `User` SET fullName = :fullName WHERE userID = :userId", nativeQuery = true)
    void updateFullName(@Param("fullName") String fullName, @Param("userId") int userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `User` SET fullName = :fullName, gender = :gender, " +
                   "phone = :phone, email = :email, position = :position " +
                   "WHERE userID = :userId", nativeQuery = true)
    void updateProfile(@Param("fullName") String fullName, 
                       @Param("gender") String gender,
                       @Param("phone") String phone, 
                       @Param("email") String email, 
                       @Param("position") String position, 
                       @Param("userId") int userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `User` SET avatar = :avatar WHERE userID = :userId", nativeQuery = true)
    void updateAvatar(@Param("avatar") String avatar, @Param("userId") int userId);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE `User` SET pass = :newPass WHERE userID = :userId", nativeQuery = true)
    void changePassword(@Param("newPass") String newPass, @Param("userId") int userId);
    
    // Tìm kiếm bằng thuật toán LIKE và sắp xếp theo Tên
    @Query(value = "SELECT * FROM `User` WHERE fullName LIKE CONCAT('%', :keyword, '%') OR userName LIKE CONCAT('%', :keyword, '%') ORDER BY fullName ASC", nativeQuery = true)
    List<User> searchUsers(@Param("keyword") String keyword);
    // Check trùng username (UTC_011)
    @Query(value = "SELECT COUNT(*) FROM `User` WHERE userName = :userName", nativeQuery = true)
    int countByUserName(@Param("userName") String userName);

    // Thêm User mới
    // 1. Sửa đoạn cuối VALUES từ 'true' thành 'false' (Mặc định Offline)
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `User` (userName, pass, fullName, position, isActive) VALUES (:userName, :pass, :fullName, :position, false)", nativeQuery = true)
    void insertUser(@Param("userName") String userName, @Param("pass") String pass, @Param("fullName") String fullName, @Param("position") String position);

    // 2. Thêm trường hợp 'none' vào Sắp xếp (Mặc định xếp theo userID)
    @Query(value = """
        SELECT * FROM `User` 
        WHERE (fullName LIKE CONCAT('%', :keyword, '%') OR userName LIKE CONCAT('%', :keyword, '%'))
        ORDER BY 
            CASE WHEN :sortDir = 'none' THEN userID END ASC,
            CASE WHEN :sortBy = 'userName' AND :sortDir = 'asc' THEN userName END ASC,
            CASE WHEN :sortBy = 'userName' AND :sortDir = 'desc' THEN userName END DESC,
            CASE WHEN :sortBy = 'fullName' AND :sortDir = 'asc' THEN fullName END ASC,
            CASE WHEN :sortBy = 'fullName' AND :sortDir = 'desc' THEN fullName END DESC,
            CASE WHEN :sortBy = 'position' AND :sortDir = 'asc' THEN position END ASC,
            CASE WHEN :sortBy = 'position' AND :sortDir = 'desc' THEN position END DESC,
            CASE WHEN :sortBy = 'isActive' AND :sortDir = 'asc' THEN isActive END ASC,
            CASE WHEN :sortBy = 'isActive' AND :sortDir = 'desc' THEN isActive END DESC
        """, 
        countQuery = "SELECT count(*) FROM `User` WHERE fullName LIKE CONCAT('%', :keyword, '%') OR userName LIKE CONCAT('%', :keyword, '%')",
        nativeQuery = true)
    Page<User> findAllUsersNative(@Param("keyword") String keyword, @Param("sortBy") String sortBy, @Param("sortDir") String sortDir, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `User` WHERE userID = :userId", nativeQuery = true)
    void deleteUser(@Param("userId") int userId);

    // Lệnh Sửa thông tin user dành cho Admin
    @Modifying
    @Transactional
    @Query(value = "UPDATE `User` SET fullName = :fullName, position = :position, isEnabled = :isEnabled WHERE userID = :userId", nativeQuery = true)
    void updateUserByAdmin(@Param("userId") int userId, @Param("fullName") String fullName, @Param("position") String position, @Param("isEnabled") boolean isEnabled);

    // 1. Lấy danh sách user ĐANG NẰM TRONG 1 nhóm cụ thể
    @Query(value = "SELECT u.* FROM `User` u JOIN `usergroup` ug ON u.userID = ug.userID WHERE ug.groupID = :groupId", nativeQuery = true)
    List<User> findUsersByGroupId(@org.springframework.data.repository.query.Param("groupId") int groupId);

    // 2. Lấy danh sách user CHƯA CÓ NHÓM (Dùng LEFT JOIN để tìm những user không có mặt trong bảng usergroup)
    @Query(value = "SELECT u.* FROM `User` u LEFT JOIN `usergroup` ug ON u.userID = ug.userID WHERE ug.groupID IS NULL", nativeQuery = true)
    List<User> findUsersWithoutGroup();

    // 3. Xóa user khỏi bảng usergroup
    @org.springframework.data.jpa.repository.Modifying
    @jakarta.transaction.Transactional
    @Query(value = "DELETE FROM `usergroup` WHERE userID = :userId", nativeQuery = true)
    void removeUserFromGroup(@org.springframework.data.repository.query.Param("userId") int userId);

    // 4. Thêm user vào bảng usergroup
    @org.springframework.data.jpa.repository.Modifying
    @jakarta.transaction.Transactional
    @Query(value = "INSERT INTO `usergroup` (userID, groupID) VALUES (:userId, :groupId)", nativeQuery = true)
    void addUserToGroup(@org.springframework.data.repository.query.Param("userId") int userId, @org.springframework.data.repository.query.Param("groupId") int groupId);

    // 5. Cập nhật lại cột position trong bảng User để đồng bộ hiển thị
    @org.springframework.data.jpa.repository.Modifying
    @jakarta.transaction.Transactional
    @Query(value = "UPDATE `User` SET position = :groupName WHERE userID = :userId", nativeQuery = true)
    void updateUserPosition(@org.springframework.data.repository.query.Param("userId") int userId, @org.springframework.data.repository.query.Param("groupName") String groupName);
}