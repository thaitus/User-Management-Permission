package UserManagementPermission.controller;

import UserManagementPermission.model.User;
import UserManagementPermission.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
public class ChangePass_UTETest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private MockHttpSession session;
    private User mockUser;

    @BeforeEach
    public void setup() {
        mockUser = new User();
        mockUser.setUserID(1);
        mockUser.setUserName("admin");
        mockUser.setPass("oldPass123");
        
        session = new MockHttpSession();
        session.setAttribute("loggedInUser", mockUser);
    }

    // UTC Item 2: Sai mật khẩu cũ
    @Test
    public void testWrongOldPassword() throws Exception {
        mockMvc.perform(post("/profile/change-password")
                .session(session)
                .param("oldPass", "wrongOldPass")
                .param("newPass", "newPass123")
                .param("confirmPass", "newPass123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile?openModal=true"))
                .andExpect(flash().attributeExists("errorOldPass"))
                .andExpect(flash().attribute("errorOldPass", "Mật khẩu cũ không chính xác"));
    }

    // UTC Item 3: Mật khẩu xác nhận không khớp
    @Test
    public void testConfirmPasswordMismatch() throws Exception {
        mockMvc.perform(post("/profile/change-password")
                .session(session)
                .param("oldPass", "oldPass123")
                .param("newPass", "newPass123")
                .param("confirmPass", "notMatchPass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile?openModal=true"))
                .andExpect(flash().attributeExists("errorConfirmPass"))
                .andExpect(flash().attribute("errorConfirmPass", "Xác nhận mật khẩu không khớp"));
    }

    // UTC Item 4: Bỏ trống trường
    @Test
    public void testBlankFields() throws Exception {
        mockMvc.perform(post("/profile/change-password")
                .session(session)
                .param("oldPass", "")
                .param("newPass", "")
                .param("confirmPass", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile?openModal=true"))
                .andExpect(flash().attributeExists("errorOldPass"))
                .andExpect(flash().attributeExists("errorNewPass"));
    }

    // UTC Item thêm: Mật khẩu mới trùng mật khẩu cũ
    @Test
    public void testNewPasswordSameAsOld() throws Exception {
        mockMvc.perform(post("/profile/change-password")
                .session(session)
                .param("oldPass", "oldPass123")
                .param("newPass", "oldPass123")
                .param("confirmPass", "oldPass123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile?openModal=true"))
                .andExpect(flash().attributeExists("errorNewPass"));
    }

    // UTC Item 5: Đổi mật khẩu thành công
    @Test
    public void testChangePasswordSuccess() throws Exception {
        mockMvc.perform(post("/profile/change-password")
                .session(session)
                .param("oldPass", "oldPass123")
                .param("newPass", "newPass123")
                .param("confirmPass", "newPass123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"))
                .andExpect(flash().attributeExists("pwdSuccess"));

        // Verify update called
        verify(userService).changePassword(eq("newPass123"), eq(1));
    }
}
