package UserManagementPermission.controller;

import UserManagementPermission.model.Group;
import UserManagementPermission.model.PermissionDTO;
import UserManagementPermission.model.User;
import UserManagementPermission.service.GroupService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PermissionController.class)
public class RolePermission_UTETest {

    @Autowired
    private MockMvc mockMvc;

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

    // Khởi tạo màn hình
    @Test
    public void testShowPermissionPage() throws Exception {
        Group group = new Group();
        group.setGroupID(1);
        group.setGroupName("Admin Group");
        
        when(groupService.getAllGroups()).thenReturn(Arrays.asList(group));
        when(groupService.getGroupById(1)).thenReturn(group);
        when(groupService.getGroupPermissions(1)).thenReturn(Arrays.asList(new PermissionDTO(1, "Test Screen", true, true)));

        mockMvc.perform(get("/permissions").param("groupId", "1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("permissions"))
                .andExpect(model().attributeExists("allGroups"))
                .andExpect(model().attributeExists("matrix"));
    }

    // Load quyền của một nhóm cụ thể
    @Test
    public void testShowPermissionsForSpecificGroup() throws Exception {
        Group group = new Group();
        group.setGroupID(2);
        
        when(groupService.getAllGroups()).thenReturn(Arrays.asList(group));
        when(groupService.getGroupById(2)).thenReturn(group);
        
        mockMvc.perform(get("/permissions").session(session).param("groupId", "2"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("selectedGroup"));
    }

    // Cập nhật quyền (Toggle)
    @Test
    public void testUpdatePermission() throws Exception {
        mockMvc.perform(post("/permissions/save")
                .session(session)
                .param("groupId", "1")
                .param("viewScreens", "1,2")
                .param("editScreens", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/permissions?groupId=1"))
                .andExpect(flash().attributeExists("successMsg"));

        verify(groupService).updateGroupMatrix(eq(1), anyList(), anyList());
    }
}
