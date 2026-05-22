package UserManagementPermission.controller;

import jakarta.servlet.http.HttpSession;
import UserManagementPermission.repository.UserRepository;
import UserManagementPermission.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";
        model.addAttribute("currentPage", "home");
        
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.countByIsActive(); // Need to create this method in UserRepository
        long totalGroups = groupRepository.count();
        
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("activeUsers", activeUsers);
        model.addAttribute("totalGroups", totalGroups);
        
        return "home";
    }
    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
