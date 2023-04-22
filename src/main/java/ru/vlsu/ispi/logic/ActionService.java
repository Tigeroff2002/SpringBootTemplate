package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.ispi.beans.Action;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.extrabeans.ExtraTask;
import ru.vlsu.ispi.enums.ActionType;
import ru.vlsu.ispi.repositories.ActionRepository;
import ru.vlsu.ispi.repositories.TaskRepository;
import ru.vlsu.ispi.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static ru.vlsu.ispi.enums.RoleType.User;

@Service
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Action saveAction(Long userId, Long taskId, ActionType type) {
        if (userId < 0 || taskId < 0){
            throw new IllegalArgumentException("Ids should be greater than zero");
        }

        User user = userRepository.findUserById(userId);
        Task task = taskRepository.findTaskById(taskId);

        if (user != null && task != null){
            Action action = new Action();
            action.setActiontype(type);
            action.setUser(user);
            action.setTask(task);
            action.setFormalized(false);
            Date currentDate = new Date();
            action.setTimemoment(currentDate);
            actionRepository.save(action);

            int newId = actionRepository.calculateMaxActionId(currentDate);
            action.setId(Integer.toUnsignedLong(newId));

            return action;
        }
        else {
            return null;
        }
    }

    public Action editActionType(ActionType type, Long actionId){
        Action action = actionRepository.findActionById(actionId);

        if (action != null){
            action.setActiontype(type);
            actionRepository.editActionType(actionId, type);

            return action;
        }
        else {
            return null;
        }
    }

    public Action editActionFormalized(boolean isFormalized, Long actionId){
        Action action = actionRepository.findActionById(actionId);

        if (action != null){
            action.setFormalized(isFormalized);
            actionRepository.editActionFormalized(actionId, isFormalized);

            return action;
        }
        else {
            return null;
        }
    }

    public void deleteAction(Long actionId){
        actionRepository.deleteAction(actionId);
    }

    public Action findActionById(Long actionId){
        return actionRepository.findActionById(actionId);
    }

    public List<Action> findAllActionsOfUser(Long userId){
        return actionRepository.getAllActionsFromCertainUser(userId);
    }

    public List<Action> findAllActionsRelatedForCertainTask(Long taskId){
        return actionRepository.getAllActionsRelatesToCertainTask(taskId);
    }

    public List<Action> findAllActionsByType(ActionType type){
        return actionRepository.getAllActionsByType(type);
    }

    public List<Action> findAllActionsByUserAndType(Long userId, ActionType type){
        return actionRepository.getAllActionsByUserAndType(userId, type);
    }

    public List<Action> findAllActionsByTaskAndType(Long taskId, ActionType type){
        return actionRepository.getAllActionsByTaskAndType(taskId, type);
    }


    public Optional<Action> findCertainActionByWholeParams(Long userId, Long taskId, ActionType type){
        return actionRepository.getActionByWholeParams(userId, taskId, type).stream().findFirst();
    }

    public List<Action> findAllActionsForMyTasks(Long userId){
        List<Task> myTasks = taskRepository.getAllTaskFromCertainExecutor(userId);

        List<Action> actions = new ArrayList<>();

        for (var task:myTasks) {
            Long currentTaskId = task.getId();
            List<Action> actionsForCurrentTask = actionRepository.getAllActionsRelatesToCertainTask(currentTaskId);
            for (var action: actionsForCurrentTask ){
                actions.add(action);
            }
        }
        return actions;
    }

    public List<Action> findAllActionsWithCertainTypeForMyTasks(Long userId, ActionType type){
        List<Task> myTasks = taskRepository.getAllTaskFromCertainExecutor(userId);

        List<Action> actions = new ArrayList<>();

        for (var task:myTasks) {
            Long currentTaskId = task.getId();
            List<Action> actionsForCurrentTask = actionRepository.getAllActionsByTaskAndType(currentTaskId, type);
            for (var action: actionsForCurrentTask ){
                actions.add(action);
            }
        }
        return actions;
    }

    public List<Task> findAllLikedUserTasks(Long userId){
        List<Task> allTasks = taskRepository.findAll();

        List<Task> likedTasks = new ArrayList<>();

        for (var task:allTasks){
            if (actionRepository.getActionLastLikedStatus(userId, task.getId()) == ActionType.Liked){
                likedTasks.add(task);
            }
        }

        return likedTasks;
    }

    public List<ExtraTask> nameAllLikedAndUnlikedTasks(Long userId){
        List<Task> allTasks = taskRepository.findAll();

        List<ExtraTask> extraTasks = new ArrayList<>();

        for (var task: allTasks){
            ExtraTask extraTask = new ExtraTask();
            extraTask.setId(task.getId());
            extraTask.setCaption(task.getCaption());
            extraTask.setPrice(task.getPrice());
            extraTask.setType(task.getType());
            extraTask.setCreatedate(task.getCreatedate());
            extraTask.setDescription(task.getDescription());
            extraTask.setExecutor(task.getExecutor());
            extraTask.setStatus(task.getStatus());

            if (actionRepository.getActionLastLikedStatus(userId, task.getId()) == ActionType.Liked){
                extraTask.Liked = "Liked";
            }
            else {
                extraTask.Liked = "Unliked";
            }
            extraTasks.add(extraTask);
        }

        return extraTasks;
    }

    public List<ExtraTask> nameAllLikedAndUnlikedTasksByUser(Long userId){
        List<Task> allTasks = taskRepository.getAllTaskFromCertainExecutor(userId);

        List<ExtraTask> extraTasks = new ArrayList<>();

        for (var task: allTasks){
            ExtraTask extraTask = new ExtraTask();
            extraTask.setId(task.getId());
            extraTask.setCaption(task.getCaption());
            extraTask.setPrice(task.getPrice());
            extraTask.setType(task.getType());
            extraTask.setCreatedate(task.getCreatedate());
            extraTask.setDescription(task.getDescription());
            extraTask.setExecutor(task.getExecutor());
            extraTask.setStatus(task.getStatus());

            if (actionRepository.getActionLastLikedStatus(userId, task.getId()) == ActionType.Liked){
                extraTask.Liked = "Liked";
            }
            else {
                extraTask.Liked = "Unliked";
            }
            extraTasks.add(extraTask);
        }

        return extraTasks;
    }

    public ExtraTask nameLikedOrUnlikedTask(Long userId, Long taskId){
        Task task = taskRepository.findTaskById(taskId);

        if (task != null){
            ExtraTask extraTask = new ExtraTask();
            extraTask.setId(task.getId());
            extraTask.setCaption(task.getCaption());
            extraTask.setPrice(task.getPrice());
            extraTask.setType(task.getType());
            extraTask.setCreatedate(task.getCreatedate());
            extraTask.setDescription(task.getDescription());
            extraTask.setExecutor(task.getExecutor());
            extraTask.setStatus(task.getStatus());
            if (actionRepository.getActionLastLikedStatus(userId, task.getId()) == ActionType.Liked){
                extraTask.Liked = "Liked";
            }
            else {
                extraTask.Liked = "Unliked";
            }
            return extraTask;
        }
        return null;
    }
}
