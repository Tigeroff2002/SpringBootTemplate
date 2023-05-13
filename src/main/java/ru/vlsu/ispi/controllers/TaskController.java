package ru.vlsu.ispi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlsu.ispi.beans.ChatMessage;
import ru.vlsu.ispi.beans.Event;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.extrabeans.ExtraMessage;
import ru.vlsu.ispi.enums.TaskStatus;
import ru.vlsu.ispi.enums.TaskType;
import ru.vlsu.ispi.logic.ActionService;
import ru.vlsu.ispi.logic.TaskService;
import ru.vlsu.ispi.logic.UserService;
import ru.vlsu.ispi.models.TaskModel;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
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

    @GetMapping("createTask")
    public String CreateTask(Model model, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            User user = userHandler.FindUserById(userId);
            if (user != null){
                model.addAttribute("task", new TaskModel());
                model.addAttribute("user", user);

                return "createTask";
            }
        }

        return "redirect:/";
    }

    @PostMapping("createTaskPost")
    public String SubmitCreateTask(@ModelAttribute TaskModel taskModel, RedirectAttributes attributes, HttpSession session) throws SQLException{
        if (taskModel == null){
            throw new IllegalArgumentException("Null model was provided");
        }

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                Task task = taskHandler.SaveTask(taskModel, userId);

                attributes.addFlashAttribute("task", task);
                attributes.addFlashAttribute("user", user);

                return "redirect:/menu/details/task?id=" + Long.toString(task.getId());
            }
        }

        return "redirect:/";
    }

    @GetMapping("details/task")
    public String TaskDetails(@RequestParam(name = "id") Long taskId, Model model, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                Task extraTask = actionHandler.nameLikedOrUnlikedTask(userId, taskId);

                actionHandler.MarkTaskAsViewed(userId, taskId);

                if (extraTask != null){
                    model.addAttribute("task", extraTask);
                    model.addAttribute("user", user);
                    return "details";
                }
                else {
                    return "redirect:/account/default_index";
                }
            }
        }

        if (userId == null){
            var task = taskHandler.findTaskById(taskId);

            if (task != null){
                model.addAttribute("task", task);
                return "details_nouser";
            }
        }

        return "redirect:/";
    }

    @GetMapping("edit/task")
    public String EditTask(@RequestParam(name = "id") Long taskId, Model model, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                Task task = taskHandler.findTaskById(taskId);

                if (task != null){
                    model.addAttribute("task", task);
                    model.addAttribute("user", user);
                    return "editTask";
                }
                else {
                    return "redirect:/account/default_index";
                }
            }
        }

        return "redirect:/";
    }

    @PostMapping("editPost/task")
    public String SubmitEditTask(@RequestParam(name = "id") Long taskId, @ModelAttribute TaskModel taskModel,
                                 RedirectAttributes attributes, HttpSession session) throws SQLException {
        if (taskModel == null){
            throw new IllegalArgumentException("Null model was provided");
        }

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                Task task = taskHandler.editTask(taskModel, taskId);

                attributes.addFlashAttribute("task", task);
                attributes.addFlashAttribute("user", user);

                return "redirect:/account/lk";
            }
        }

        return "redirect:/";
    }

    @PostMapping("request/editPost/task")
    public String SubmitRequestEditTask(@RequestParam(name = "id") Long taskId,
                                        @ModelAttribute TaskModel taskModel, RedirectAttributes attributes, HttpSession session) throws SQLException {
        if (taskModel == null){
            throw new IllegalArgumentException("Null model was provided");
        }

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                Task task = taskHandler.editTask(taskModel, taskId);

                attributes.addFlashAttribute("task", task);
                attributes.addFlashAttribute("user", user);

                return "redirect:/account/lk";
            }
        }

        return "redirect:/";
    }

    @GetMapping("delete/task")
    public String DeleteTask(@RequestParam(name = "id") Long taskId, Model model,
                             RedirectAttributes attributes, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                Task task = taskHandler.findTaskById(taskId);

                if (task != null){
                    taskHandler.deleteTask(taskId);
                    attributes.addFlashAttribute("user", user);
                    return "redirect:/account/lk";
                }
                else {
                    attributes.addFlashAttribute("user", user);
                    return "redirect:/account/default_index";
                }
            }
        }

        return "redirect:/";
    }

    @GetMapping("request/delete/task")
    public String RequestDeleteTask(@RequestParam(name = "id") Long taskId, Model model,
                                    RedirectAttributes attributes, HttpServletRequest request, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                Task task = taskHandler.findTaskById(taskId);
                if (task == null){
                    return "redirect:/";
                }
                else {
                    return getPreviousPageByRequest(request).orElse("/");
                }
            }
        }

        return "redirect:/";
    }

    @GetMapping("room/task")
    public String TaskRoom(@RequestParam(name = "id") Long taskId, Model model, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                Task task = taskHandler.findTaskById(taskId);

                if (task != null){

                    var extraMessageList = new ArrayList<ExtraMessage>();

                    Event event = null;

                    if (!Objects.equals(task.getExecutor().getId(), userId)){

                        event = actionHandler.GetCurrentEventForUserEndTask(userId, taskId);
                    }
                    else {

                    }

                    if (event != null){
                        var messageList = actionHandler.findAllChatMessageByEvent(event.getId());

                        for (var message: messageList){
                            var extraMessage = new ExtraMessage();
                            extraMessage.setTime(message.getTime());
                            extraMessage.setText(message.getText());
                            extraMessage.setUser(message.getUser());
                            extraMessage.setViewed(message.isViewed());
                            extraMessage.setEdited(message.isEdited());
                            extraMessage.setEvent(message.getEvent());
                            extraMessage.setReplied(message.isReplied());

                            var fellowId = message.getUser().getId();

                            extraMessage.IsMine = Objects.equals(fellowId, userId);

                            extraMessageList.add(extraMessage);
                        }
                    }

                    var fellowName = task.getExecutor().getNickname();

                    model.addAttribute("task", task);

                    model.addAttribute("user", user);

                    model.addAttribute("messageList", extraMessageList);

                    model.addAttribute("fellowName", fellowName);

                    model.addAttribute("myName", user.getNickname());

                    model.addAttribute("newMessage", new ChatMessage());

                    return "taskRoomPage";
                }
                else {
                    return "redirect:/account/default_index";
                }
            }
        }

        return "redirect:/";
    }

    @GetMapping("room/task/newMessage")
    public String SendNewChatMessage(@RequestParam(name = "userId") Long userId, HttpServletRequest request , HttpSession session){

        return getPreviousPageByRequest(request).orElse("/");
    }

    protected Optional<String> getPreviousPageByRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }
}
