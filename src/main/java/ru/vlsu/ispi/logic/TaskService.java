package ru.vlsu.ispi.logic;

import ch.qos.logback.core.joran.sanity.Pair;
import org.apache.commons.collections4.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.beans.extrabeans.ExtraTask;
import ru.vlsu.ispi.beans.extrabeans.Pagination;
import ru.vlsu.ispi.enums.TaskStatus;
import ru.vlsu.ispi.models.TaskModel;
import ru.vlsu.ispi.repositories.TaskRepository;
import ru.vlsu.ispi.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Long GenerateNewTask(Long bound) {
        return new Random().nextLong(bound);
    }

    public Task SaveTask(TaskModel taskModel, Long userId){
        if (taskModel == null){
            throw new IllegalArgumentException("Null register model was provided");
        }

        Task newTask = new Task();
        newTask.setCaption(taskModel.getCaption());
        newTask.setType(taskModel.getType());
        newTask.setStatus(taskModel.getStatus());
        newTask.setDescription(taskModel.getDescription());
        newTask.setPrice(taskModel.getPrice());

        User user = userRepository.findUserById(userId);

        if (user == null){
            User testUser = userRepository.findUserById(46L);
            testUser.setId(46L);
            newTask.setExecutor(testUser);
        }
        else {
            user.setId(userId);
            newTask.setExecutor(user);
        }

        newTask.setCreatedate(new Date());

        taskRepository.save(newTask);

        int newId = taskRepository.calculateMaxTaskId(newTask.getCreatedate());

        newTask.setId(Integer.toUnsignedLong(newId));

        return newTask;
    }

    public Task editTask(TaskModel taskModel, Long taskId){
        if (taskModel == null){
            throw new IllegalArgumentException("Null register model was provided");
        }

        Task newTask = new Task();
        newTask.setId(taskId);
        newTask.setCaption(taskModel.getCaption());
        newTask.setType(taskModel.getType());
        newTask.setStatus(taskModel.getStatus());
        newTask.setDescription(taskModel.getDescription());
        newTask.setPrice(taskModel.getPrice());
        taskRepository.updateTaskInfo(taskId, newTask.getCaption(), newTask.getType(), newTask.getStatus(), newTask.getDescription(), newTask.getPrice());

        return newTask;
    }

    public void deleteTask(Long taskId){
        taskRepository.deleteTask(taskId);
    }

    public Task findTaskById(Long id){
        return taskRepository.findTaskById(id);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public List<Task> getAllExecutorTasks(Long executorId){
        return taskRepository.getAllTaskFromCertainExecutor(executorId);
    }

    public List<List<ExtraTask>> distributeTasksByPages(List<ExtraTask> allTasks, Pagination pagination){

        var elementsPerPage = pagination.getElementsPerPage();

        var count = allTasks.size();

        var rawLastPageNumber = count / elementsPerPage;

        var lastPageNumber = count % elementsPerPage == 0 ? rawLastPageNumber : rawLastPageNumber + 1;

        var listOfLists = new ArrayList<List<ExtraTask>>(lastPageNumber + 1);

        listOfLists.add(new ArrayList<ExtraTask>());

        for (var i = 1; i < lastPageNumber + 1; i++){

            var currentList = new ArrayList<ExtraTask>();

            for (var j = (i - 1) * elementsPerPage; j < i * elementsPerPage; j++){

                if (j >= count){
                   break;
                }

                currentList.add(allTasks.get(j));
            }

            listOfLists.add(currentList);
        }

        return listOfLists;
    }

    public List<ExtraTask> getElementsForCurrentPage(List<List<ExtraTask>> listOfLists, int currentPageNumber){

        return currentPageNumber < listOfLists.size() ? listOfLists.get(currentPageNumber) : null;
    }
}
