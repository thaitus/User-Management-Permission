package UserManagementPermission.controller;

import UserManagementPermission.model.User;
import UserManagementPermission.service.GroupService;
import UserManagementPermission.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserManagement_UTETest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private GroupService groupService;

    private MockHttpSession session;
    private User mockUser;

    @BeforeEach
    public void setup() {
        mockUser = new User();
        mockUser.setUserID(1);
        mockUser.setUserName("admin");
        
        session = new MockHttpSession();
        session.setAttribute("loggedInUser", mockUser);

        java.util.Map<Integer, java.util.Map<String, Boolean>> userPermissions = new java.util.HashMap<>();
        java.util.Map<String, Boolean> perms = new java.util.HashMap<>();
        perms.put("canView", true);
        perms.put("canEdit", true);
        userPermissions.put(1, perms);
        userPermissions.put(2, perms);
        userPermissions.put(3, perms);
        session.setAttribute("userPermissions", userPermissions);
    }

    // UTC Item 1, 4, 5: Khởi tạo màn hình, Phân trang, Sắp xếp
    @Test
    public void testShowUserList() throws Exception {
        Page<User> mockPage = new PageImpl<>(Arrays.asList(new User(), new User()));
        when(userService.getUsersWithPagination(anyString(), anyInt(), anyString(), anyString())).thenReturn(mockPage);

        mockMvc.perform(get("/users").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attributeExists("userList"));
    }

    // UTC Item 2: Tìm kiếm có kết quả
    @Test
    public void testSearchWithResults() throws Exception {
        Page<User> mockPage = new PageImpl<>(Arrays.asList(new User()));
        when(userService.getUsersWithPagination("admin", 0, "userID", "none")).thenReturn(mockPage);

        mockMvc.perform(get("/users").session(session).param("keyword", "admin"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("keyword", "admin"))
                .andExpect(model().attribute("totalElements", 1L));
    }

    // UTC Item 3: Tìm kiếm không có kết quả
    @Test
    public void testSearchNoResults() throws Exception {
        Page<User> mockPage = new PageImpl<>(Collections.emptyList());
        when(userService.getUsersWithPagination("notfound", 0, "userID", "none")).thenReturn(mockPage);

        mockMvc.perform(get("/users").session(session).param("keyword", "notfound"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("totalElements", 0L));
    }

    // UTC Item 7: Thêm mới bỏ trống trường bắt buộc
    @Test
    public void testAddUserBlankFields() throws Exception {
        mockMvc.perform(post("/users/add").session(session)
                .param("userName", "")
                .param("password", "")
                .param("confirmPassword", "")
                .param("fullName", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users?openAddModal=true"))
                .andExpect(flash().attributeExists("errorGeneral"));
    }

    // UTC Item 8: Thêm mới sai định dạng họ tên (Được test bằng backend check empty hoặc regex tuỳ code, ở đây test username có space cuối)
    @Test
    public void testAddUserInvalidFormat() throws Exception {
        mockMvc.perform(post("/users/add").session(session)
                .param("userName", "admin ")
                .param("password", "123")
                .param("confirmPassword", "123")
                .param("fullName", "Admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users?openAddModal=true"))
                .andExpect(flash().attributeExists("errorUserName"));
    }

    // UTC Item 10: Thêm mới thành công
    @Test
    public void testAddUserSuccess() throws Exception {
        when(userService.isUserNameExists("newuser")).thenReturn(false);

        mockMvc.perform(post("/users/add").session(session)
                .param("userName", "newuser")
                .param("password", "123456")
                .param("confirmPassword", "123456")
                .param("fullName", "New User")
                .param("position", "User"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"))
                .andExpect(flash().attributeExists("successMsg"));
                
        verify(userService).createUser(anyString(), anyString(), anyString(), anyString());
    }

    // UTC Item 12: Chỉnh sửa thành công
    @Test
    public void testEditUserSuccess() throws Exception {
        when(userService.isUserNameExists("edituser")).thenReturn(false);

        mockMvc.perform(post("/users/edit").session(session)
                .param("userId", "2")
                .param("userName", "edituser")
                .param("fullName", "Edit User")
                .param("position", "User")
                .param("isEnabled", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"))
                .andExpect(flash().attributeExists("successMsg"));

        verify(userService).updateUserByAdmin(anyInt(), anyString(), anyString(), org.mockito.ArgumentMatchers.anyBoolean());
    }

    // UTC Item 14: Xóa thành công
    @Test
    public void testDeleteUserSuccess() throws Exception {
        mockMvc.perform(post("/users/delete").session(session)
                .param("userId", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"))
                .andExpect(flash().attributeExists("successMsg"));

        verify(userService).deleteUser(2);
    }
}
