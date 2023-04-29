package ru.vlsu.ispi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlsu.ispi.beans.Action;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.ActionType;
import ru.vlsu.ispi.enums.EventStatus;
import ru.vlsu.ispi.logic.ActionService;
import ru.vlsu.ispi.logic.TaskService;
import ru.vlsu.ispi.logic.UserService;

import java.sql.SQLException;
import java.util.List;
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

    @GetMapping("{userId}/task/{taskId}/like")
    public String LikeTask(@PathVariable Long userId, @PathVariable Long taskId, RedirectAttributes attributes, HttpServletRequest request)
            throws SQLException {
        User user = userHandler.FindUserById(userId);
        Task task = taskHandler.findTaskById(taskId);

        if (user == null || task == null) {
            return "redirect:/";
        }
        else {
            Action action = actionsHandler.saveAction(userId, taskId, ActionType.Liked);
            attributes.addFlashAttribute("user", user);

            return getPreviousPageByRequest(request).orElse("/");
        }
    }

    @GetMapping("{userId}/task/{taskId}/comment")
    public String CommentTask(@PathVariable Long userId, @PathVariable Long taskId,
                              RedirectAttributes attributes, HttpServletRequest request) throws SQLException{
        User user = userHandler.FindUserById(userId);
        Task task = taskHandler.findTaskById(taskId);

        if (user == null || task == null) {
            return "redirect:/";
        }
        else {
            Action action = actionsHandler.saveAction(userId, taskId, ActionType.Commented);
            attributes.addFlashAttribute("user", user);

            return getPreviousPageByRequest(request).orElse("/");
        }
    }

    @GetMapping("{userId}/task/{taskId}/preformalize")
    public String PreformalizeTask(@PathVariable Long userId, @PathVariable Long taskId,
                                   RedirectAttributes attributes, HttpServletRequest request) throws SQLException {
        User user = userHandler.FindUserById(userId);
        Task task = taskHandler.findTaskById(taskId);

        if (user == null || task == null) {
            return "redirect:/";
        }
        else {
            Action action = actionsHandler.saveAction(userId, taskId, ActionType.Preformalized);
            attributes.addFlashAttribute("user", user);

            return getPreviousPageByRequest(request).orElse("/");
        }
    }


    @GetMapping("{userId}/task/{taskId}/unlike")
    public String UnlikeTask(@PathVariable Long userId, @PathVariable Long taskId,
                             RedirectAttributes attributes, HttpServletRequest request) throws SQLException{
        User user = userHandler.FindUserById(userId);
        Task task = taskHandler.findTaskById(taskId);

        if (user == null || task == null) {
            return "redirect:/";
        }
        else {
            Action action = actionsHandler.findCertainActionByWholeParams(userId, taskId, ActionType.Liked).get(0);

            if (action == null){
                return getPreviousPageByRequest(request).orElse("/");
            }
            else {
                action.setActiontype(ActionType.Unliked);
                actionsHandler.editActionType(action.getActiontype(), action.getId());
                attributes.addFlashAttribute("user", user);

                return getPreviousPageByRequest(request).orElse("/");
            }
        }
    }

    @GetMapping("{userId}/executors/{executorId}/task/{taskId}/unpreformalize")
    public String AbortPreformalizeTask(@PathVariable Long userId, @PathVariable Long executorId, @PathVariable Long taskId,
                                        RedirectAttributes attributes, HttpServletRequest request) throws SQLException{
        User user = userHandler.FindUserById(userId);
        User executor = userHandler.FindUserById(executorId);
        Task task = taskHandler.findTaskById(taskId);

        if (user == null || executor == null || task == null) {
            return "redirect:/";
        }
        else {
            Action action = actionsHandler.findCertainActionByWholeParams(executorId, taskId, ActionType.Preformalized).get(0);

            if (action == null){
                return getPreviousPageByRequest(request).orElse("/");
            }
            else {
                action.setActiontype(ActionType.UnPreformalized);
                action.setFormalized(false);
                actionsHandler.editActionType(action.getActiontype(), action.getId());

                var event = actionsHandler.findCertainLastEventByWholeParams(executorId, taskId).get();

                if (event != null){
                    actionsHandler.editEventType(EventStatus.Rejected, event.getId());
                }

                attributes.addFlashAttribute("user", user);

                return getPreviousPageByRequest(request).orElse("/");
            }
        }
    }

    @GetMapping("{userId}/executors/{employerId}/task/{taskId}/formalizeEvent")
    public String FormalizeEvent(@PathVariable Long userId, @PathVariable Long employerId, @PathVariable Long taskId,
                                RedirectAttributes attributes, HttpServletRequest request) throws SQLException {
        User user = userHandler.FindUserById(userId);
        User employer = userHandler.FindUserById(employerId);
        Task task = taskHandler.findTaskById(taskId);

        if (user == null || employer == null || task == null) {
            return "redirect:/";
        }
        else {
            var action = actionsHandler.getLastActionByUserAndTask(employerId, taskId);

            if (action != null){
                if (action.getActiontype() == ActionType.Liked){
                    action.setActiontype(ActionType.Preformalized);
                    action.setFormalized(true);
                    actionsHandler.editActionFormalized(true, action.getId());
                    actionsHandler.saveEvent(action.getId(), EventStatus.InProgress);
                    attributes.addFlashAttribute("user", user);
                }

                return getPreviousPageByRequest(request).orElse("/");
            }
            else {
                return "redirect:/";
            }
        }
    }

    @GetMapping("{userId}/request")
    public String requestRoleChanging(@PathVariable Long userId, HttpServletRequest request){

        return getPreviousPageByRequest(request).orElse("/");
    }

    protected Optional<String> getPreviousPageByRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }
}
