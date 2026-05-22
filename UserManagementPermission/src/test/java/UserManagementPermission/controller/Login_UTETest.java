package UserManagementPermission.controller;

import UserManagementPermission.model.User;
import UserManagementPermission.repository.GroupRepository;
import UserManagementPermission.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
public class Login_UTETest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private GroupRepository groupRepository;

    // UTC Item 1: Truy cập khi chưa đăng nhập (Test bằng Interceptor hoặc security layer)
    // Thực tế trong file login thì nó là get("/login")
    @Test
    public void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    // UTC Item 2: Submit rỗng
    @Test
    public void testSubmitEmptyCredentials() throws Exception {
        mockMvc.perform(post("/login")
                .param("userName", "")
                .param("pass", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("errorUserName"))
                .andExpect(model().attributeExists("errorPass"));
    }

    // UTC Item 3: Tài khoản không tồn tại
    @Test
    public void testUserNotFound() throws Exception {
        // Mock service
        Object[] failResult = {false, "Tài khoản không tồn tại trên hệ thống", "error_username"};
        when(userService.authenticateUser("notfound", "password")).thenReturn(failResult);

        mockMvc.perform(post("/login")
                .param("userName", "notfound")
                .param("pass", "password"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("errorUserName"));
    }

    // UTC Item 4: Sai mật khẩu
    @Test
    public void testWrongPassword() throws Exception {
        Object[] failResult = {false, "Sai mật khẩu", "error_password"};
        when(userService.authenticateUser("user1", "wrongpass")).thenReturn(failResult);

        mockMvc.perform(post("/login")
                .param("userName", "user1")
                .param("pass", "wrongpass"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("errorPass"));
    }

    // UTC Item 5: Tài khoản bị khóa (Banned)
    @Test
    public void testBannedUser() throws Exception {
        Object[] failResult = {false, "Tài khoản của bạn đã bị khóa", "error_locked"};
        when(userService.authenticateUser("banned_user", "123456")).thenReturn(failResult);

        mockMvc.perform(post("/login")
                .param("userName", "banned_user")
                .param("pass", "123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }

    // UTC Item 6: Đăng nhập thành công
    @Test
    public void testLoginSuccess() throws Exception {
        User mockUser = new User();
        mockUser.setUserID(1);
        mockUser.setUserName("admin");
        
        Object[] successResult = {true, mockUser};
        when(userService.authenticateUser("admin", "123456")).thenReturn(successResult);
        // Do nothing for void updateActiveStatus
        
        mockMvc.perform(post("/login")
                .param("userName", "admin")
                .param("pass", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }
}
