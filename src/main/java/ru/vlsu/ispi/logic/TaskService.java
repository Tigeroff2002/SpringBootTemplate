package ru.vlsu.ispi.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlsu.ispi.beans.Task;
import ru.vlsu.ispi.repositories.TaskRepository;

import java.util.List;
import java.util.Random;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Long GenerateNewTask(Long bound) {
        return new Random().nextLong(bound);
    }

    public Task getTaskById(Long id){
        return taskRepository.findTaskById(id);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
}
