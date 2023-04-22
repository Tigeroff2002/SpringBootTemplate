package ru.vlsu.ispi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.TaskStatus;
import ru.vlsu.ispi.enums.TaskType;
import ru.vlsu.ispi.logic.ActionService;
import ru.vlsu.ispi.logic.TaskService;
import ru.vlsu.ispi.logic.UserService;
import ru.vlsu.ispi.models.TaskModel;

import java.sql.SQLException;
import java.util.Optional;

@Controller
@RequestMapping(value = "/menu/")
public class TaskController {
    @Autowired
    private UserService userHandler;
    @Autowired
    private TaskService taskHandler;

    @Autowired
    private ActionService actionHandler;

    @GetMapping("{userId}/new-task")
    public String CreateTask(@PathVariable Long userId, Model model) throws SQLException {
        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            model.addAttribute("task", new TaskModel());
            model.addAttribute("user", user);

            return "createTask";
        }
    }

    @PostMapping("{userId}/createPost")
    public String SubmitCreateTask(@PathVariable Long userId, @ModelAttribute TaskModel taskModel, RedirectAttributes attributes) throws SQLException{
        if (taskModel == null){
            throw new IllegalArgumentException("Null model was provided");
        }

        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task task = taskHandler.SaveTask(taskModel, userId);

            attributes.addFlashAttribute("task", task);
            attributes.addFlashAttribute("user", user);

            return "redirect:/menu/" + Long.toString(userId) + "/details/task/" + Long.toString(task.getId()) + "";
        }
    }

    @GetMapping("{userId}/details/task/{taskId}")
    public String TaskDetails(@PathVariable Long userId, @PathVariable Long taskId, Model model) throws SQLException{
        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task extraTask = actionHandler.nameLikedOrUnlikedTask(userId, taskId);

            if (extraTask != null){
                model.addAttribute("task", extraTask);
                model.addAttribute("user", user);
                return "details";
            }
            else {
                return "redirect:/account/index/" + Long.toString(userId) + "";
            }
        }
    }

    @GetMapping("details/task/{taskId}")
    public String TaskWithoutUserDetails(@PathVariable Long taskId, Model model) throws SQLException{
        Task task = taskHandler.findTaskById(taskId);

        if (task != null){
            model.addAttribute("task", task);

            return "details_nouser";
        }
        else {
            return "redirect:/";
        }
    }

    @GetMapping("{userId}/edit/task/{taskId}")
    public String EditTask(@PathVariable Long userId, @PathVariable Long taskId, Model model) throws SQLException{
        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task task = taskHandler.findTaskById(taskId);

            if (task != null){
                model.addAttribute("task", task);
                model.addAttribute("user", user);
                return "editTask";
            }
            else {
                return "redirect:/account/index/" + Long.toString(userId) + "";
            }
        }
    }

    @PostMapping("{userId}/task/{taskId}/editPost")
    public String SubmitEditTask(@PathVariable Long userId, @PathVariable Long taskId, @ModelAttribute TaskModel taskModel, RedirectAttributes attributes)
            throws SQLException{
        if (taskModel == null){
            throw new IllegalArgumentException("Null model was provided");
        }
        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task task = taskHandler.editTask(taskModel, taskId);

            attributes.addFlashAttribute("task", task);
            attributes.addFlashAttribute("user", user);

            return "redirect:/account/lk/" + Long.toString(userId) + "";
        }
    }

    @PostMapping("{userId}/task/{taskId}/request/editPost")
    public String SubmitRequestEditTask(@PathVariable Long userId, @PathVariable Long taskId,
                                        @ModelAttribute TaskModel taskModel, RedirectAttributes attributes) throws SQLException {
        if (taskModel == null){
            throw new IllegalArgumentException("Null model was provided");
        }
        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task task = taskHandler.editTask(taskModel, taskId);

            attributes.addFlashAttribute("task", task);
            attributes.addFlashAttribute("user", user);

            return "redirect:/account/lk/" + Long.toString(userId) + "";
        }
    }

    @GetMapping("{userId}/delete/task/{taskId}")
    public String DeleteTask(@PathVariable Long userId, @PathVariable Long taskId, Model model, RedirectAttributes attributes) throws SQLException{
        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task task = taskHandler.findTaskById(taskId);

            if (task != null){
                taskHandler.deleteTask(taskId);
                attributes.addFlashAttribute("user", user);
                return "redirect:/account/lk/" + Long.toString(userId) + "";
            }
            else {
                attributes.addFlashAttribute("user", user);
                return "redirect:/account/index/" + Long.toString(userId) + "";
            }
        }
    }

    @GetMapping("{userId}/request/delete/task/{taskId}")
    public String RequestDeleteTask(@PathVariable Long userId, @PathVariable Long taskId, Model model,
                                    RedirectAttributes attributes, HttpServletRequest request) throws SQLException{
        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task task = taskHandler.findTaskById(taskId);
            if (task == null){
                return "redirect:/";
            }
            else {
                return getPreviousPageByRequest(request).orElse("/");
            }
        }
    }

    @GetMapping("{userId}/room/task/{taskId}")
    public String TaskRoom(@PathVariable Long userId, @PathVariable Long taskId, Model model) throws SQLException{
        User user = userHandler.FindUserById(userId);
        if (user == null){
            return "redirect:/";
        }
        else {
            Task task = taskHandler.findTaskById(taskId);

            if (task != null){
                model.addAttribute("task", task);
                model.addAttribute("user", user);
                return "taskRoomPage";
            }
            else {
                return "redirect:/account/index/" + Long.toString(userId) + "";
            }
        }
    }

    protected Optional<String> getPreviousPageByRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }
}
