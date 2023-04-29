package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.ispi.beans.Action;
import ru.vlsu.ispi.beans.Event;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.extrabeans.ExtraTask;
import ru.vlsu.ispi.enums.ActionType;
import ru.vlsu.ispi.enums.EventStatus;
import ru.vlsu.ispi.repositories.ActionRepository;
import ru.vlsu.ispi.repositories.EventRepository;
import ru.vlsu.ispi.repositories.TaskRepository;
import ru.vlsu.ispi.repositories.UserRepository;

import java.util.*;

import static ru.vlsu.ispi.enums.RoleType.User;

@Service
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public Action saveAction(Long userId, Long taskId, ActionType type, boolean isViewed) {
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
            action.setFormalized(isViewed);
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

    public Event saveEvent(Long actionId, EventStatus status){
        if (actionId < 0){
            throw new IllegalArgumentException("Action id should be greater than zero");
        }

        Action action = actionRepository.findActionById(actionId);

        if (action != null){
            Event event = new Event();
            event.setEventstatus(status);

            var taskPrice = action.getTask().getPrice();

            var eventPrice = taskPrice * (100 + COMMISSION_PERCENT) / 100;

            event.setTotalprice(eventPrice);

            event.setTaskaction(action);

            var dateNow = new Date();

            var calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.DATE, DAYS_NUMBER);

            var dateEnd = calendar.getTime();

            event.setFormalizedate(dateNow);
            event.setCompletedate(dateEnd);

            eventRepository.save(event);

            int newId = eventRepository.calculateMaxEventId(dateNow);
            event.setId(Integer.toUnsignedLong(newId));

            return event;
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

    public Event editEventType(EventStatus status, Long eventId){
        var event = eventRepository.findEventById(eventId);

        if (event != null){
            event.setEventstatus(status);
            eventRepository.setCertainStatusForEvent(status, eventId);

            return event;
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

    public void deleteEvent(Long eventId){
        eventRepository.deleteById(eventId);
    }

    public Action findActionById(Long actionId){
        return actionRepository.findActionById(actionId);
    }

    public Event findEventById(Long eventId){
        return eventRepository.findEventById(eventId);
    };

    public List<Action> findAllActionsOfUser(Long userId){
        return actionRepository.getAllActionsFromCertainUser(userId);
    }

    public List<Event> findAllEventsOfExecutor(Long executorId){
        return eventRepository.getAllEventsFromCertainExecutor(executorId);
    }

    public List<Event> findAllEventsOfEmployer(Long employerId){
        return eventRepository.getAllEventsFromCertainEmployer(employerId);
    }

    public List<Event> findAllEventsByCertainExecutorAndEmployer(Long executorId, Long employerId){
        return eventRepository.getAllEventsFromCertainExecutorAndEmployer(executorId, employerId);
    }

    public List<Action> findAllActionsRelatedForCertainTask(Long taskId){
        return actionRepository.getAllActionsRelatesToCertainTask(taskId);
    }

    public List<Event> findAllEventsRelatedForCertainTask(Long taskId){
        return eventRepository.getAllEventsRelatesToCertainTask(taskId);
    }

    public List<Action> findAllActionsByType(ActionType type){
        return actionRepository.getAllActionsByType(type);
    }

    public List<Event> findAllEventsByType(EventStatus status){
        return eventRepository.getAllEventsByStatus(status);
    }

    public List<Action> findAllActionsByUserAndType(Long userId, ActionType type){
        return actionRepository.getAllActionsByUserAndType(userId, type);
    }

    public List<Event> findAllEventsByExecutorAndStatus(Long executorId, EventStatus status){
        return eventRepository.getAllActionsByExecutorAndStatus(executorId, status);
    }

    public List<Event> findAllByEmployerAndStatus(Long employerId, EventStatus status){
        return eventRepository.getAllActionsByEmployerAndStatus(employerId, status);
    }

    public List<Action> findAllActionsByTaskAndType(Long taskId, ActionType type){
        return actionRepository.getAllActionsByTaskAndType(taskId, type);
    }

    public List<Event> findAllEventsByTaskAndType(Long taskId, EventStatus status){
        return eventRepository.getAllActionsByTaskAndStatus(taskId, status);
    }

    public List<Action> findCertainActionByWholeParams(Long userId, Long taskId, ActionType type){
        return actionRepository.getActionByWholeParams(userId, taskId, type);
    }

    public Optional<Event> findCertainLastEventByWholeParams(Long executorId, Long taskId){
        return eventRepository.getLstEventByExecutorAndTaskAndStatus(executorId, taskId);
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

    public Action getLastActionByUserAndTask(Long userId, Long taskId){
        return actionRepository.getLastActionByUserAndTask(userId, taskId);
    }

    public List<Action> findAllActionsWithCertainTypeForMyTasks(Long userId, ActionType type){
        List<Task> myTasks = taskRepository.getAllTaskFromCertainExecutor(userId);

        List<Action> actions = new ArrayList<>();

        for (var task:myTasks) {
            Long currentTaskId = task.getId();
            List<Action> actionsForCurrentTask = actionRepository.getAllActionsByTaskAndType(currentTaskId, type);
            actions.addAll(actionsForCurrentTask);
        }
        return actions;
    }

    public List<Event> findAllEventsWithCertainTypeForMyTasks(Long userId, EventStatus status){
        List<Task> myTasks = taskRepository.getAllTaskFromCertainExecutor(userId);

        List<Event> events = new ArrayList<>();

        for (var task:myTasks){
            Long currentTaskId = task.getId();

            List<Event> eventsForCurrentTask = eventRepository.getAllActionsByTaskAndStatus(currentTaskId, status);
            events.addAll(eventsForCurrentTask);
        }
        return events;
    }

    public List<ExtraTask> findAllLikedUserTasks(Long userId){
        List<Task> allTasks = taskRepository.findAll();

        List<ExtraTask> likedTasks = new ArrayList<>();

        for (var task:allTasks){
            if (actionRepository.getActionLastLikedStatus(userId, task.getId()) == ActionType.Liked){

                ExtraTask extraTask = new ExtraTask();
                extraTask.setId(task.getId());
                extraTask.setCaption(task.getCaption());
                extraTask.setPrice(task.getPrice());
                extraTask.setType(task.getType());
                extraTask.setCreatedate(task.getCreatedate());
                extraTask.setDescription(task.getDescription());
                extraTask.setExecutor(task.getExecutor());
                extraTask.setStatus(task.getStatus());
                extraTask.IsViewed = true;

                likedTasks.add(extraTask);
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

            var lastUserAction = actionRepository.getSomeLastActionByUserAndTask(userId, task.getId());

            var lastAction = actionRepository.getLastActionByTask(task.getId());

            if (lastAction != null){

                if (lastAction.getActiontype() == ActionType.Preformalized){
                    continue;
                }
            }

            if (lastUserAction != null){
                extraTask.IsViewed = lastUserAction.isFormalized;
            }
            else {
                extraTask.IsViewed = false;
            }

            if (Objects.equals(task.getExecutor().getId(), userId)){
                extraTask.Liked = "LikeProhibit";
            }
            else {
                if (actionRepository.getActionLastLikedStatus(userId, task.getId()) == ActionType.Liked){
                    extraTask.Liked = "Liked";
                }
                else {
                    extraTask.Liked = "Unliked";
                }
            }

            extraTasks.add(extraTask);
        }

        return extraTasks;
    }

    public List<ExtraTask> nameAllLikedAndUnlikedTasks(){
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

            var lastAction = actionRepository.getLastActionByTask(task.getId());

            if (lastAction != null){
                if (lastAction.getActiontype() == ActionType.Preformalized){
                    continue;
                }
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

            if (Objects.equals(task.getExecutor().getId(), userId)){
                extraTask.Liked = "LikeProhibit";
            }
            else {
                if (actionRepository.getActionLastLikedStatus(userId, task.getId()) == ActionType.Liked){
                    extraTask.Liked = "Liked";
                }
                else {
                    extraTask.Liked = "Unliked";
                }
            }

            return extraTask;
        }
        return null;
    }

    public List<ExtraTask> nameAllLikedAndUnlikedTasksByExecutor(Long userId, Long executorId){
        List<ExtraTask> allTasks = nameAllLikedAndUnlikedTasks(userId);

        List<ExtraTask> extraTasks = new ArrayList<>();

        for (var task:allTasks){
            if (Objects.equals(task.getExecutor().getId(), executorId)){
                extraTasks.add(task);
            }
        }

        return extraTasks;
    }

    public List<ExtraTask> MarkAllMyTasks(Long executorId){
        var myTasks = taskRepository.getAllTaskFromCertainExecutor(executorId);

        List<ExtraTask> likedTasks = new ArrayList<>();

        for (var task:myTasks){
            var currentTaskId = task.getId();

            ExtraTask extraTask = new ExtraTask();
            extraTask.setId(task.getId());
            extraTask.setCaption(task.getCaption());
            extraTask.setPrice(task.getPrice());
            extraTask.setType(task.getType());
            extraTask.setCreatedate(task.getCreatedate());
            extraTask.setDescription(task.getDescription());
            extraTask.setExecutor(task.getExecutor());
            extraTask.setStatus(task.getStatus());

            var lastAction = actionRepository.getLastActionByTask(task.getId());

            if (lastAction != null){
                if (lastAction.getActiontype() == ActionType.Liked){
                    extraTask.EmployerId = lastAction.getUser().getId();

                    extraTask.Liked = "Liked";
                }
                else if (lastAction.getActiontype() == ActionType.Preformalized){
                    extraTask.EmployerId = lastAction.getUser().getId();

                    extraTask.Liked = "Preformalized";
                }
                else {
                    extraTask.Liked = "Empty";
                }
            }

            likedTasks.add(extraTask);
        }

        return likedTasks;
    }

    public void MarkTaskAsViewed(Long userId, Long taskId){
        var user = userRepository.findUserById(userId);
        var task = taskRepository.findTaskById(taskId);

        if (user != null || task != null){
            var action = actionRepository.getLastActionByUserAndTask(userId, taskId);

            if (action != null){
                if (!action.isFormalized){
                    action.setFormalized(true);
                    saveAction(userId, taskId, action.getActiontype(), true);
                }
            }
            else {
                saveAction(userId, taskId, ActionType.Viewed, true);
            }
        }
    }

    private final float COMMISSION_PERCENT = 5f;
    private int DAYS_NUMBER = 7;
}
