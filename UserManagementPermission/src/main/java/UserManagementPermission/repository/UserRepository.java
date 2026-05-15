package UserManagementPermission.repository;

import UserManagementPermission.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = """
            SELECT * FROM `User` 
            WHERE userName = ?1
            """, nativeQuery = true)
    User findByUserName(String userName);

    // Thêm hàm này để cập nhật trạng thái online/offline
    @Modifying
    @Transactional
    @Query(value = "UPDATE `User` SET isActive = ?1 WHERE userID = ?2", nativeQuery = true)
    void updateActiveStatus(boolean isActive, int userId);
    
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.fullName = :fullName WHERE u.userID = :userId")
    void updateFullName(@Param("fullName") String fullName, @Param("userId") int userId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.fullName = :fullName, u.gender = :gender, " +
           "u.phone = :phone, u.email = :email, u.position = :position " +
           "WHERE u.userID = :userId")
    void updateProfile(@Param("fullName") String fullName, 
                       @Param("gender") String gender,
                       @Param("phone") String phone, 
                       @Param("email") String email, 
                       @Param("position") String position, 
                       @Param("userId") int userId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.avatar = :avatar WHERE u.userID = :userId")
    void updateAvatar(@Param("avatar") String avatar, @Param("userId") int userId);
    
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.pass = :newPass WHERE u.userID = :userId")
    void changePassword(@Param("newPass") String newPass, @Param("userId") int userId);
}