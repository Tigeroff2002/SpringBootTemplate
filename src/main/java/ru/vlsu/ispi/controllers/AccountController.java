package ru.vlsu.ispi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.extrabeans.ExtraTask;
import ru.vlsu.ispi.beans.extrabeans.ExtraUser;
import ru.vlsu.ispi.enums.RoleType;
import ru.vlsu.ispi.enums.TaskType;
import ru.vlsu.ispi.logic.ActionService;
import ru.vlsu.ispi.logic.TaskService;
import ru.vlsu.ispi.logic.UserService;
import ru.vlsu.ispi.models.LoginModel;
import ru.vlsu.ispi.models.RegisterModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(value = "/account/")
public class AccountController {
    @Autowired
    private UserService userHandler;

    @Autowired
    private TaskService taskHandler;

    @Autowired
    private ActionService actionHandler;

    public AccountController(){
    }

    @GetMapping("index/{id}")
    public String AuthIndex(@PathVariable Long id, Model model) throws SQLException{
        User user = userHandler.FindUserById(id);

        if (user == null){
            return "redirect:/";
        }
        else {
            model.addAttribute("user", user);

            List<ExtraTask> taskList = actionHandler.nameAllLikedAndUnlikedTasks(id);

            model.addAttribute("taskList", taskList);

            return "auth_index";
        }
    }

    @GetMapping("lk/{id}")
    public String LK(@PathVariable Long id, Model model) throws SQLException{
        ExtraUser user = userHandler.nameRoleUser(id);

        if (user == null){
            return "redirect:/";
        }
        else {
            List<ExtraTask> taskList1 = actionHandler.MarkAllMyTasks(id);

            List<Task> taskList2 = actionHandler.findAllLikedUserTasks(id);

            model.addAttribute("user", user);
            model.addAttribute("taskList1", taskList1);
            model.addAttribute("taskList2", taskList2);

            return "lk";
        }
    }

    @GetMapping("profile/{id}/executor/{executorId}")
    public String Profile(@PathVariable Long id, @PathVariable Long executorId, Model model) throws SQLException{

        if (Objects.equals(id, executorId)){
            return "redirect:/account/lk/" + Long.toString(id);
        }
        User user = userHandler.FindUserById(id);

        if (user == null){
            return "redirect:/";
        }
        else {
            User executor = userHandler.FindUserById(executorId);

            if (executor == null){
                return "redirect:/account/index/" + Long.toString(id) + "";
            }
            else {
                List<ExtraTask> taskList = actionHandler.nameAllLikedAndUnlikedTasksByExecutor(id, executorId);

                model.addAttribute("executor", executor);
                model.addAttribute("user", user);
                model.addAttribute("taskList", taskList);

                return "profile";
            }
        }
    }

    @GetMapping("profile/executor/{executorId}")
    public String ProfileNoUser(@PathVariable Long executorId, Model model) throws SQLException{
        User executor = userHandler.FindUserById(executorId);

        if (executor == null){
            return "redirect:/";
        }
        else {
            List<Task> taskList = taskHandler.getAllExecutorTasks(executorId);

            model.addAttribute("executor", executor);
            model.addAttribute("taskList", taskList);

            return "profile_nouser";
        }
    }

    @GetMapping("lk/admin/{id}")
    public String AdminPage(@PathVariable Long id, Model model) throws SQLException{
        User user = userHandler.FindUserById(id);

        if (user == null){
            return "redirect:/";
        }
        else if (user.getRole() == RoleType.Admin) {
            List<Task> allTasks = taskHandler.getAllTasks();
            List<User> allUsers = userHandler.getAllUsers();

            model.addAttribute("user", user);
            model.addAttribute("userList", allUsers);
            model.addAttribute("taskList", allTasks);

            return "adminPage";
        }
        else {

            return "redirect:/account/lk/" + Long.toString(user.getId()) + "";
        }
    }

    @GetMapping("lk/moderator/{id}")
    public String ModeratorPage(@PathVariable Long id, Model model) throws SQLException{
        User user = userHandler.FindUserById(id);

        if (user == null){
            return "redirect:/";
        }
        else if (user.getRole() == RoleType.Moderator) {
            List<Task> allTasks = taskHandler.getAllTasks();

            model.addAttribute("user", user);
            model.addAttribute("taskList", allTasks);

            return "moderatorPage";
        }
        else {

            return "redirect:/account/lk/" + Long.toString(user.getId()) + "";
        }
    }

    @GetMapping("register")
    public String Register(Model model){
        model.addAttribute("register", new RegisterModel());

        return "registerForm";
    }

    @PostMapping("registerPost")
    public String RegisterPost(@ModelAttribute RegisterModel registerModel, RedirectAttributes attributes) throws SQLException {
        if (registerModel == null){
            throw new IllegalArgumentException("Null register model was provided");
        }

        User user = userHandler.RegisterUser(registerModel);

        if (user == null) {
            return "redirect:/account/login";
        }
        else if (user.getId() != -1){
            attributes.addFlashAttribute("user", user);
            return "redirect:/account/index/" + Long.toString(user.getId()) + "";
        }
        else {
            return "redirect:/account/register";
        }
    }

    @GetMapping("login")
    public String Login(Model model){
        model.addAttribute("login", new LoginModel());

        return "loginForm";
    }

    @PostMapping("loginPost")
    public String LoginPost(@ModelAttribute LoginModel loginModel, RedirectAttributes attributes) throws SQLException{
        if (loginModel == null){
            throw new IllegalArgumentException("Null login model was provided");
        }
        User user = userHandler.LoginUser(loginModel);

        if (user != null){
            attributes.addFlashAttribute("user", user);
            return "redirect:/account/index/" + Long.toString(user.getId()) + "";
        }
        else {
            return "redirect:/account/register";
        }
    }

    @GetMapping("logout")
    public String Logout(Model model){
        return "redirect:/";
    }

    @PostMapping("{userId}/role/user/editPost/{siteUserId}")
    public String EditUserRole(@PathVariable("userId") Long userId, @PathVariable("siteUserId") Long siteUserId,
                               HttpServletRequest request) throws SQLException {
        User user = userHandler.FindUserById(userId);
        User siteUser = userHandler.FindUserById(siteUserId);

        if (user != null && siteUser != null){
            return getPreviousPageByRequest(request).orElse("/");
        }
        return "redirect:/";
    }

    protected Optional<String> getPreviousPageByRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }
}
