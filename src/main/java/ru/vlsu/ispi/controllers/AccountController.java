package ru.vlsu.ispi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlsu.ispi.beans.Notification;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.extrabeans.ExtraTask;
import ru.vlsu.ispi.beans.extrabeans.ExtraUser;
import ru.vlsu.ispi.beans.extrabeans.WholeFilterSet;
import ru.vlsu.ispi.enums.FilterBy;
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

    @GetMapping("index1")
    public String DefaultIndex(){
        return "redirect:/account/index" + DEFAULT_FILTER_HEADER;
    }

    @GetMapping("index")
    public String AuthIndexFiltering(@RequestParam(name = "rowToFind") String rowToFind,
                                     @RequestParam(name = "filter") String filter,
                                     @RequestParam(name = "sorter") String sorter,
                                     Model model,
                                     HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                model.addAttribute("user", user);

                List<ExtraTask> taskList = actionHandler.nameAllLikedAndUnlikedTasks(userId);

                var wholeFilterName = "Filtering By Params: RowToFind = " + rowToFind + ", filters = [" + filter + "], sortBy = " + sorter;

                var obtainedTaskList = userHandler.filterByRowParameters(taskList, rowToFind, filter, sorter);

                List<Notification> notificationList = actionHandler.findAllNotificationsOfUser(userId);

                model.addAttribute("taskList", obtainedTaskList);

                model.addAttribute("filterSet", new WholeFilterSet(rowToFind, filter, sorter));

                model.addAttribute("notificationList", notificationList);

                return "auth_index";
            }
        }

        return "redirect:/";
    }

    @GetMapping("paramindex")
    public String AuthIndexFiltering(@RequestParam(name = "rowToFind") String rowToFind,
                                     @RequestParam(name = "type") String type,
                                     @RequestParam(name = "status") String status,
                                     @RequestParam(name = "liked") String liked,
                                     @RequestParam(name = "unviewed") String unviewed,
                                     @RequestParam(name = "Sorter") String sorter,
                                     Model model,
                                     HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                model.addAttribute("user", user);

                List<ExtraTask> taskList = actionHandler.nameAllLikedAndUnlikedTasks(userId);

                //var wholeFilterName = "Filtering By Params: RowToFind = " + rowToFind + ", filters = [" + filter + "], sortBy = " + sorter;

                var filter = "type=" + type.toString() + "&status=" + status.toString() + "&liked=" + liked.toString() + "&unviewed=" + unviewed.toString();

                var obtainedTaskList = userHandler.filterByRowParameters(taskList, rowToFind, filter, sorter);

                List<Notification> notificationList = actionHandler.findAllNotificationsOfUser(userId);

                model.addAttribute("taskList", obtainedTaskList);

                model.addAttribute("filterSet", new WholeFilterSet(rowToFind, filter, sorter));

                model.addAttribute("notificationList", notificationList);

                return "auth_index";
            }
        }

        return "redirect:/";
    }

    @GetMapping("lk")
    public String LK(Model model, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.nameRoleUser(userId);
            if (user != null){
                List<ExtraTask> taskList1 = actionHandler.MarkAllMyTasks(userId);

                List<ExtraTask> taskList2 = actionHandler.findAllLikedUserTasks(userId);

                model.addAttribute("user", user);
                model.addAttribute("taskList1", taskList1);
                model.addAttribute("taskList2", taskList2);

                return "lk";
            }
        }

        return "redirect:/";
    }

    @GetMapping("profile")
    public String Profile(@RequestParam(name = "executorId") Long executorId, Model model, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            if (Objects.equals(userId, executorId)){
                return "redirect:/account/lk";
            }

            var user = userHandler.FindUserById(userId);
            if (user != null){
                var executor = userHandler.FindUserById(executorId);

                if (executor == null){
                    return "redirect:/account/index1";
                }
                else {
                    List<ExtraTask> taskList = actionHandler.nameAllLikedAndUnlikedTasksByExecutor(userId, executorId);

                    model.addAttribute("executor", executor);
                    model.addAttribute("user", user);
                    model.addAttribute("taskList", taskList);

                    return "profile";
                }
            }
        }

        if (userId == null){
            var executor = userHandler.FindUserById(executorId);
            if (executor != null){
                List<Task> taskList = taskHandler.getAllExecutorTasks(executorId);

                model.addAttribute("executor", executor);
                model.addAttribute("taskList", taskList);

                return "profile_nouser";
            }
        }

        return "redirect:/";
    }

    @GetMapping("lk/admin")
    public String AdminPage(Model model, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                if (user.getRole() == RoleType.Admin){
                    List<Task> allTasks = taskHandler.getAllTasks();
                    List<User> allUsers = userHandler.getAllUsers();

                    model.addAttribute("user", user);
                    model.addAttribute("userList", allUsers);
                    model.addAttribute("taskList", allTasks);

                    return "adminPage";
                }
                else {
                    return "redirect:/account/lk";
                }
            }
        }

        return "redirect:/";
    }

    @GetMapping("lk/moderator")
    public String ModeratorPage(Model model, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                if (user.getRole() == RoleType.Moderator){
                    List<Task> allTasks = taskHandler.getAllTasks();

                    model.addAttribute("user", user);
                    model.addAttribute("taskList", allTasks);

                    return "moderatorPage";
                }
                else {
                    return "redirect:/account/lk";
                }
            }
        }

        return "redirect:/";
    }

    @GetMapping("register")
    public String Register(Model model){
        model.addAttribute("register", new RegisterModel());

        return "registerForm";
    }

    @PostMapping("registerPost")
    public String RegisterPost(@ModelAttribute RegisterModel registerModel, RedirectAttributes attributes, HttpSession session) throws SQLException {
        if (registerModel == null){
            throw new IllegalArgumentException("Null register model was provided");
        }

        User user = userHandler.RegisterUser(registerModel);

        if (user == null) {
            return "redirect:/account/login";
        }
        else if (user.getId() != -1){
            attributes.addFlashAttribute("user", user);
            session.setAttribute("userId", user.getId());
            return "redirect:/account/index/" + Long.toString(user.getId()) + DEFAULT_FILTER_HEADER;
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
    public String LoginPost(@ModelAttribute LoginModel loginModel, RedirectAttributes attributes, HttpSession session) throws SQLException{
        if (loginModel == null){
            throw new IllegalArgumentException("Null login model was provided");
        }
        User user = userHandler.LoginUser(loginModel);

        if (user != null){
            attributes.addFlashAttribute("user", user);
            session.setAttribute("userId", user.getId());
            return "redirect:/account/index" + DEFAULT_FILTER_HEADER;
        }
        else {
            return "redirect:/account/register";
        }
    }

    @GetMapping("logout")
    public String Logout(Model model, HttpSession session){

        session.removeAttribute("userId");
        return "redirect:/";
    }

    @PostMapping("role/editPost/user")
    public String EditUserRole(@RequestParam(name = "siteUserId") Long siteUserId,
                               HttpServletRequest request, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                var siteUser = userHandler.FindUserById(siteUserId);
                if (siteUser != null){
                    return getPreviousPageByRequest(request).orElse("/");
                }
            }
        }

        return "redirect:/";
    }

    protected Optional<String> getPreviousPageByRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }

    private static final String DEFAULT_FILTER_HEADER = "?rowToFind=empty&filter=default_filter&sorter=default_sort";
}
