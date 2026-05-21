package UserManagementPermission.model; // Thay đổi theo package của ông

public interface ScreenPermissionProjection {
    Integer getScreenId(); // Vẫn dùng ID để né lỗi font Tiếng Việt
    Boolean getCanView();  // Trả về Boolean
    Boolean getCanEdit();  // Trả về Boolean
}