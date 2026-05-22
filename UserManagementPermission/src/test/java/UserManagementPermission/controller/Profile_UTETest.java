package UserManagementPermission.controller;

import UserManagementPermission.model.User;
import UserManagementPermission.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
public class Profile_UTETest {

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
        mockUser.setFullName("Hoang Thai Tu");
        
        session = new MockHttpSession();
        session.setAttribute("loggedInUser", mockUser);
    }

    // UTC Item 1: Khởi tạo màn hình
    @Test
    public void testShowProfilePage() throws Exception {
        mockMvc.perform(get("/profile").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attribute("currentPage", "profile"));
    }

    // UTC Item 2 & 6: Cập nhật thông tin thành công (Bao gồm cập nhật các trường rỗng thành giá trị mới)
    @Test
    public void testUpdateProfileSuccess() throws Exception {
        mockMvc.perform(post("/profile/update")
                .session(session)
                .param("fullName", "Hoang Thai Tu Edit")
                .param("gender", "Nam")
                .param("phone", "0123456789")
                .param("email", "test@test.com")
                .param("position", "Admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile?success=true"));

        verify(userService).updateProfile("Hoang Thai Tu Edit", "Nam", "0123456789", "test@test.com", "Admin", 1);
    }

    // UTC Item 3: Thay đổi Avatar
    @Test
    public void testUploadAvatar() throws Exception {
        MockMultipartFile avatarFile = new MockMultipartFile("avatarFile", "test.png", "image/png", "test image content".getBytes());

        mockMvc.perform(multipart("/profile/avatar")
                .file(avatarFile)
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile?success=true"));

        verify(userService).updateAvatar(anyString(), anyInt());
    }

    // Ngoại lệ: Cố gắng truy cập profile khi chưa đăng nhập
    @Test
    public void testShowProfileWithoutLogin() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
