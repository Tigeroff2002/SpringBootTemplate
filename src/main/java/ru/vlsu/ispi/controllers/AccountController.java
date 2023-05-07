package ru.vlsu.ispi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
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
import ru.vlsu.ispi.beans.extrabeans.Pagination;
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

    @GetMapping("default_index")
    public String DefaultIndex(HttpSession session){

        if (session.getAttribute("rowToFind") == null){
            session.setAttribute("rowToFind", "empty");
        }

        if (session.getAttribute("type") == null){
            session.setAttribute("type", "default");
        }

        if (session.getAttribute("status") == null){
            session.setAttribute("status", "default");
        }

        if (session.getAttribute("liked") == null){
            session.setAttribute("liked", "default");
        }

        if (session.getAttribute("viewed") == null){
            session.setAttribute("viewed", "default");
        }

        if (session.getAttribute("sorter") == null){
            session.setAttribute("sorter", "default_sort");
        }

        if (session.getAttribute("elementsPerPage") == null){
            session.setAttribute("elementsPerPage", 5);
        }

        if (session.getAttribute("currentPageNumber") == null){
            session.setAttribute("currentPageNumber", 1);
        }

        var rowToFind = (String) session.getAttribute("rowToFind");
        var type = (String) session.getAttribute("type");
        var status = (String) session.getAttribute("status");
        var liked = (String) session.getAttribute("liked");
        var viewed = (String) session.getAttribute("viewed");
        var sorter = (String) session.getAttribute("sorter");

        var elementsPerPage = (int) session.getAttribute("elementsPerPage");

        var currentPageNumber = 1;
        session.setAttribute("currentPageNumber", currentPageNumber);

        return "redirect:/account/index?rowToFind=" + rowToFind + "&type=" + type + "&status=" + status + "&liked=" + liked + "&viewed=" + viewed + "&sorter="
                + sorter + "&elementsPerPage=" + Integer.toString(elementsPerPage) + "&currentPageNumber=" + Integer.toString(currentPageNumber);
    }

    @GetMapping("index1")
    public String RedirectAmongThePages(@RequestParam("currentPageNumber") int currentPageNumber,
                                        @RequestParam("elementsPerPage") int elementsPerPage,
                                        HttpSession session){

        if (currentPageNumber <= 0 || elementsPerPage <= 0){
            return "redirect:/account/default_index";
        }

        if (session.getAttribute("rowToFind") == null){
            session.setAttribute("rowToFind", "empty");
        }

        if (session.getAttribute("type") == null){
            session.setAttribute("type", "default");
        }

        if (session.getAttribute("status") == null){
            session.setAttribute("status", "default");
        }

        if (session.getAttribute("liked") == null){
            session.setAttribute("liked", "default");
        }

        if (session.getAttribute("viewed") == null){
            session.setAttribute("viewed", "default");
        }

        if (session.getAttribute("sorter") == null){
            session.setAttribute("sorter", "default_sort");
        }

        session.setAttribute("elementsPerPage", elementsPerPage);

        session.setAttribute("currentPageNumber", currentPageNumber);

        var rowToFind = (String) session.getAttribute("rowToFind");
        var type = (String) session.getAttribute("type");
        var status = (String) session.getAttribute("status");
        var liked = (String) session.getAttribute("liked");
        var viewed = (String) session.getAttribute("viewed");
        var sorter = (String) session.getAttribute("sorter");

        return "redirect:/account/index?rowToFind=" + rowToFind + "&type=" + type + "&status=" + status + "&liked=" + liked + "&viewed=" + viewed + "&sorter="
                + sorter + "&elementsPerPage=" + Integer.toString(elementsPerPage) + "&currentPageNumber=" + Integer.toString(currentPageNumber);
    }

    @GetMapping("index2")
    public String RedirectAmongFilters(@RequestParam(name = "rowToFind") String rowToFind,
                                       @RequestParam(name = "type") String type,
                                       @RequestParam(name = "status") String status,
                                       @RequestParam(name = "liked") String liked,
                                       @RequestParam(name = "viewed") String viewed,
                                       @RequestParam(name = "sorter") String sorter,
                                       HttpSession session){

        if (stringIsNullOrEmptyOrBlank(rowToFind) || stringIsNullOrEmptyOrBlank(type)
                || stringIsNullOrEmptyOrBlank(status) || stringIsNullOrEmptyOrBlank(liked)
                    || stringIsNullOrEmptyOrBlank(viewed) || stringIsNullOrEmptyOrBlank(sorter)){
            return "redirect:/account/default_index";
        }

        session.setAttribute("rowToFind", rowToFind);

        session.setAttribute("type", type);

        session.setAttribute("status", status);

        session.setAttribute("liked", liked);

        session.setAttribute("viewed", viewed);

        session.setAttribute("sorter", sorter);

        var elementsPerPage = (int) session.getAttribute("elementsPerPage");

        var currentPageNumber = 1;
        session.setAttribute("currentPageNumber", currentPageNumber);

        return "redirect:/account/index?rowToFind=" + rowToFind + "&type=" + type + "&status=" + status + "&liked=" + liked + "&viewed=" + viewed + "&sorter="
                + sorter + "&elementsPerPage=" + Integer.toString(elementsPerPage) + "&currentPageNumber=" + Integer.toString(currentPageNumber);

    }

    @GetMapping("index")
    public String AuthIndexFiltering(@RequestParam(name = "rowToFind") String rowToFind,
                                     @RequestParam(name = "type") String type,
                                     @RequestParam(name = "status") String status,
                                     @RequestParam(name = "liked") String liked,
                                     @RequestParam(name = "viewed") String viewed,
                                     @RequestParam(name = "sorter") String sorter,
                                     @RequestParam(name = "elementsPerPage") int elementsPerPage,
                                     @RequestParam(name = "currentPageNumber") int currentPageNumber,
                                     Model model,
                                     HttpServletRequest request,
                                     HttpSession session) throws SQLException {

        if (currentPageNumber == 0){
            return "redirect:/account/default_index";
        }

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                model.addAttribute("user", user);

                List<ExtraTask> taskList = actionHandler.nameAllLikedAndUnlikedTasks(userId);

                var filterSet = new WholeFilterSet(rowToFind, type, status, liked, viewed, sorter);

                var obtainedTaskList = userHandler.filterByRowParameters(taskList, rowToFind, filterSet, sorter);

                if (obtainedTaskList.size() == 0){

                    session.setAttribute("rowToFind", "empty");
                    session.setAttribute("type", "default");
                    session.setAttribute("status", "default");
                    session.setAttribute("liked", "default");
                    session.setAttribute("viewed", "viewed");
                    session.setAttribute("sorter", "default_sort");

                    return "redirect:/account/default_index";
                }

                var pagination = new Pagination();

                pagination.setElementsPerPage(elementsPerPage);

                pagination.setCurrentPageNumber(currentPageNumber);

                var listOfListsTasks = taskHandler.distributeTasksByPages(obtainedTaskList, pagination);

                pagination.setLastPageNumber(listOfListsTasks.size() - 1);

                session.setAttribute("lastPageNumber", pagination.getLastPageNumber());

                var listTasksForCurrentPage = taskHandler.getElementsForCurrentPage(listOfListsTasks, currentPageNumber);

                if (listTasksForCurrentPage == null) {

                    return "redirect:/";
                }

                List<Notification> notificationList = actionHandler.findAllNotificationsOfUser(userId);

                model.addAttribute("taskList", listTasksForCurrentPage);

                model.addAttribute("filterSet", new WholeFilterSet(rowToFind, type, status, liked, viewed, sorter));

                model.addAttribute("notificationList", notificationList);

                model.addAttribute("pagination", pagination);

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

            session.removeAttribute("rowToFind");
            session.removeAttribute("type");
            session.removeAttribute("status");
            session.removeAttribute("sorter");
            session.removeAttribute("currentPageNumber");
            session.removeAttribute("lastPageNumber");
            session.removeAttribute("elementsPerPage");

            return "redirect:/account/";
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

            session.removeAttribute("rowToFind");
            session.removeAttribute("type");
            session.removeAttribute("status");
            session.removeAttribute("sorter");
            session.removeAttribute("currentPageNumber");
            session.removeAttribute("lastPageNumber");
            session.removeAttribute("elementsPerPage");

            return "redirect:/account/default_index";
        }
        else {
            return "redirect:/account/register";
        }
    }

    @GetMapping("logout")
    public String Logout(Model model, HttpSession session){

        session.removeAttribute("userId");
        session.removeAttribute("rowToFind");
        session.removeAttribute("type");
        session.removeAttribute("status");
        session.removeAttribute("liked");
        session.removeAttribute("viewed");
        session.removeAttribute("sorter");
        session.removeAttribute("currentPageNumber");
        session.removeAttribute("lastPageNumber");
        session.removeAttribute("elementsPerPage");

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

    private boolean stringIsNullOrEmptyOrBlank(String row){
        return row == null || row.isEmpty() || row.trim().isEmpty();
    }
}
