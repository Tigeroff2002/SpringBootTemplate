package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.TaskStatus;
import ru.vlsu.ispi.models.TaskModel;
import ru.vlsu.ispi.repositories.TaskRepository;
import ru.vlsu.ispi.repositories.UserRepository;

import java.time.LocalDateTime;
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
        newTask.setStatus(TaskStatus.ToDo);

        taskRepository.save(newTask);

        int newId = taskRepository.calculateMaxTaskId(newTask.getCreatedate());

        newTask.setId(Integer.toUnsignedLong(newId));

        return newTask;
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
}
