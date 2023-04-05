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
import ru.vlsu.ispi.logic.ActionService;
import ru.vlsu.ispi.logic.TaskService;
import ru.vlsu.ispi.logic.UserService;

import java.sql.SQLException;
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
    public String LikeTask(@PathVariable Long userId, @PathVariable Long taskId, RedirectAttributes attributes, HttpServletRequest request) throws SQLException {
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

    @GetMapping("{userId}/task/{taskId}/unlike")
    public String UnlikeTask(@PathVariable Long userId, @PathVariable Long taskId,
                             RedirectAttributes attributes, HttpServletRequest request) throws SQLException{
        User user = userHandler.FindUserById(userId);
        Task task = taskHandler.findTaskById(taskId);

        if (user == null || task == null) {
            return "redirect:/";
        }
        else {
            Action action = actionsHandler.findCertainActionByWholeParams(userId, taskId, ActionType.Liked).get();

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

    @GetMapping("{userId}/task/{taskId}/uncomment")
    public String DeleteCommentTask(@PathVariable Long userId, @PathVariable Long taskId,
                              RedirectAttributes attributes, HttpServletRequest request) throws SQLException{
        User user = userHandler.FindUserById(userId);
        Task task = taskHandler.findTaskById(taskId);

        if (user == null || task == null) {
            return "redirect:/";
        }
        else {
            Action action = actionsHandler.findCertainActionByWholeParams(userId, taskId, ActionType.Commented).get();

            if (action == null){
                return getPreviousPageByRequest(request).orElse("/");
            }
            else {
                actionsHandler.deleteAction(action.getId());
                attributes.addFlashAttribute("user", user);

                return getPreviousPageByRequest(request).orElse("/");
            }
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

    @GetMapping("{userId}/task/{taskId}/unpreformalize")
    public String AbortPreformalizeTask(@PathVariable Long userId, @PathVariable Long taskId,
                                        RedirectAttributes attributes, HttpServletRequest request) throws SQLException{
        User user = userHandler.FindUserById(userId);
        Task task = taskHandler.findTaskById(taskId);

        if (user == null || task == null) {
            return "redirect:/";
        }
        else {
            Action action = actionsHandler.findCertainActionByWholeParams(userId, taskId, ActionType.Preformalized).get();

            if (action == null){
                return getPreviousPageByRequest(request).orElse("/");
            }
            else {
                action.setActiontype(ActionType.UnPreformalized);
                action.setFormalized(false);
                actionsHandler.editActionType(action.getActiontype(), action.getId());
                attributes.addFlashAttribute("user", user);

                return getPreviousPageByRequest(request).orElse("/");
            }
        }
    }

    @GetMapping("{userId}/task/{taskId}/formalizeEvent")
    public String FormalizeTask(@PathVariable Long userId, @PathVariable Long taskId,
                                RedirectAttributes attributes, HttpServletRequest request) throws SQLException {
        User user = userHandler.FindUserById(userId);
        Task task = taskHandler.findTaskById(taskId);

        if (user == null || task == null) {
            return "redirect:/";
        }
        else {
            Action action = actionsHandler.findCertainActionByWholeParams(userId, taskId, ActionType.Preformalized).get();

            if (action == null){
                return getPreviousPageByRequest(request).orElse("/");
            }
            else {
                action.setFormalized(true);
                actionsHandler.editActionFormalized(true, action.getId());
                attributes.addFlashAttribute("user", user);

                return getPreviousPageByRequest(request).orElse("/");
            }
        }
    }

    protected Optional<String> getPreviousPageByRequest(HttpServletRequest request)
    {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }
}
