package ru.vlsu.ispi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlsu.ispi.beans.Action;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.ActionType;
import ru.vlsu.ispi.enums.EventStatus;
import ru.vlsu.ispi.enums.RoleType;
import ru.vlsu.ispi.logic.ActionService;
import ru.vlsu.ispi.logic.TaskService;
import ru.vlsu.ispi.logic.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/actions/")
public class ActionsController {

    @Autowired
    private UserService userHandler;

    @Autowired
    private TaskService taskHandler;

    @Autowired
    private ActionService actionsHandler;

    @GetMapping("like/task")
    public String LikeTask(@RequestParam(name = "id") Long taskId, RedirectAttributes attributes,
                           HttpServletRequest request, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                var task = taskHandler.findTaskById(taskId);
                if (task != null){
                    Action action = actionsHandler.saveAction(userId, taskId, ActionType.Liked, true);
                    attributes.addFlashAttribute("user", user);
                }

                return getPreviousPageByRequest(request).orElse("/");
            }
        }

        return "redirect:/";
    }

    @GetMapping("comment/task")
    public String CommentTask(@RequestParam(name = "id") Long taskId, RedirectAttributes attributes,
                              HttpServletRequest request, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                var task = taskHandler.findTaskById(taskId);
                if (task != null){
                    Action action = actionsHandler.saveAction(userId, taskId, ActionType.Commented, true);
                    attributes.addFlashAttribute("user", user);
                }

                return getPreviousPageByRequest(request).orElse("/");
            }
        }

        return "redirect:/";
    }

    @GetMapping("preformalize/task")
    public String PreformalizeTask(@RequestParam(name = "id") Long taskId, RedirectAttributes attributes,
                                   HttpServletRequest request, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                var task = taskHandler.findTaskById(taskId);
                if (task != null){
                    Action action = actionsHandler.saveAction(userId, taskId, ActionType.Preformalized, true);
                    attributes.addFlashAttribute("user", user);
                }

                return getPreviousPageByRequest(request).orElse("/");
            }
        }

        return "redirect:/";
    }


    @GetMapping("unlike/task")
    public String UnlikeTask(@RequestParam(name = "id") Long taskId, RedirectAttributes attributes,
                             HttpServletRequest request, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                var task = taskHandler.findTaskById(taskId);
                if (task != null){
                    Action action = actionsHandler.findCertainActionByWholeParams(userId, taskId, ActionType.Liked).get(0);
                    if (action != null){
                        action.setActiontype(ActionType.Unliked);
                        actionsHandler.editActionType(action.getActiontype(), action.getId());
                        attributes.addFlashAttribute("user", user);
                    }
                }

                return getPreviousPageByRequest(request).orElse("/");
            }
        }

        return "redirect:/";
    }

    @GetMapping("formalizeEvent/task")
    public String FormalizeEvent(@RequestParam(name = "id") Long taskId,
                                 RedirectAttributes attributes, HttpServletRequest request, HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                    var task = taskHandler.findTaskById(taskId);
                    if (task != null){
                        var action = actionsHandler.getLastActionByUserAndTask(userId, taskId);
                        if (action != null){
                            if (action.getActiontype() == ActionType.Liked){
                                action.setActiontype(ActionType.Preformalized);
                                action.setTask(task);
                                action.setFormalized(true);

                                var actionId = action.getId();
                                actionsHandler.editActionFormalized(true, actionId);
                                actionsHandler.saveEvent(action.getId(), EventStatus.InProgress);
                                actionsHandler.saveNotification(userId, taskId, actionId);

                                attributes.addFlashAttribute("user", user);
                            }
                        }
                        return "redirect:/menu/room/task?id=" + Long.toString(taskId);
                    }

                return getPreviousPageByRequest(request).orElse("/");
            }
        }

        return "redirect:/";
    }

    @GetMapping("rejectEvent/event")
    public String RejectEvent(@RequestParam(name = "id") Long eventId, RedirectAttributes attributes,
                              HttpServletRequest request, HttpSession session) throws SQLException{

        var userId = (Long) session.getAttribute("userId");
        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                var event = actionsHandler.editEventType(EventStatus.Rejected, eventId);

                var actionId = event.getTaskaction().getId();
                var action = actionsHandler.findActionById(actionId);

                if (action != null) {
                    actionsHandler.editActionType(ActionType.UnPreformalized, actionId);

                    var taskId = action.getTask().getId();
                    var employerId = action.getUser().getId();

                    actionsHandler.saveAction(employerId, taskId, ActionType.Liked, true);
                }

                return getPreviousPageByRequest(request).orElse("/");
            }
        }

        return "redirect:/";
    }

    @GetMapping("requestRole")
    public String requestRoleChanging(@RequestParam(name = "role") String role, HttpServletRequest request,
                                      HttpSession session) throws SQLException {

        var userId = (Long) session.getAttribute("userId");

        if (userId != null){
            var user = userHandler.FindUserById(userId);
            if (user != null){
                role = role.toLowerCase();

                if (role.equals("admin") || role.equals("moderator") || role.equals("user")){
                    var roleType = RoleType.valueOf(role);

                    if (user.getRole() != roleType){
                        // call some new controller method
                    }
                }

                return getPreviousPageByRequest(request).orElse("/");
            }
        }

        return "redirect:/";
    }

    protected Optional<String> getPreviousPageByRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }
}
