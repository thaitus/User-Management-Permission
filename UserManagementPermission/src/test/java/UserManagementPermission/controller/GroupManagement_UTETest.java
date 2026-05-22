package UserManagementPermission.controller;

import UserManagementPermission.model.Group;
import UserManagementPermission.model.User;
import UserManagementPermission.service.GroupService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroupController.class)
public class GroupManagement_UTETest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @MockBean
    private UserManagementPermission.service.UserService userService;

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
    public void testShowGroupList() throws Exception {
        Page<Group> mockPage = new PageImpl<>(Arrays.asList(new Group()));
        when(groupService.getPaginatedGroups(org.mockito.ArgumentMatchers.nullable(String.class), org.mockito.ArgumentMatchers.any(org.springframework.data.domain.Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/groups").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("groups"))
                .andExpect(model().attributeExists("groupList"));
    }

    // Tìm kiếm
    @Test
    public void testSearchGroups() throws Exception {
        Page<Group> mockPage = new PageImpl<>(Collections.emptyList());
        when(groupService.getPaginatedGroups(org.mockito.ArgumentMatchers.eq("Manager"), org.mockito.ArgumentMatchers.any(org.springframework.data.domain.Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/groups").session(session).param("keyword", "Manager"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("keyword", "Manager"));
    }

    // Thêm nhóm mới thành công
    @Test
    public void testAddGroupSuccess() throws Exception {
        when(groupService.isGroupNameExists("NewGroup")).thenReturn(false);

        mockMvc.perform(post("/groups/add").session(session)
                .param("groupName", "NewGroup")
                .param("description", "A new group"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"))
                .andExpect(flash().attributeExists("successMsg"));

        verify(groupService).saveGroup(org.mockito.ArgumentMatchers.any(Group.class));
    }

    // Thêm nhóm mới thất bại (Trùng tên)
    @Test
    public void testAddGroupExists() throws Exception {
        when(groupService.isGroupNameExists("ExistingGroup")).thenReturn(true);

        mockMvc.perform(post("/groups/add").session(session)
                .param("groupName", "ExistingGroup")
                .param("description", "Desc"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"))
                .andExpect(flash().attributeExists("errorMsg"));
    }

    // Chỉnh sửa nhóm thành công
    @Test
    public void testEditGroupSuccess() throws Exception {
        Group mockGroup = new Group();
        mockGroup.setGroupName("OldGroup");
        when(groupService.getGroupById(1)).thenReturn(mockGroup);
        when(groupService.isGroupNameExists("UpdatedGroup")).thenReturn(false);

        mockMvc.perform(post("/groups/edit").session(session)
                .param("groupId", "1")
                .param("groupName", "UpdatedGroup")
                .param("description", "Updated desc")
                .param("isEnabled", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"))
                .andExpect(flash().attributeExists("successMsg"));

        verify(groupService).saveGroup(org.mockito.ArgumentMatchers.any(Group.class));
    }

    // Xóa nhóm thành công
    @Test
    public void testDeleteGroupSuccess() throws Exception {
        mockMvc.perform(post("/groups/delete").session(session)
                .param("groupId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"))
                .andExpect(flash().attributeExists("successMsg"));

        verify(groupService).deleteGroup(1);
    }
}
