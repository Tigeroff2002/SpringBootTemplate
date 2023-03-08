package ru.vlsu.ispi.logic;

import ru.vlsu.ispi.DAO.TaskDAO;
import ru.vlsu.ispi.logic.abstractions.ITaskHandler;

import java.util.Random;


public class TaskHandler implements ITaskHandler {

    private final TaskDAO taskDAO;

    public TaskHandler(TaskDAO taskDAO){
        this.taskDAO = taskDAO;
    }

    @Override
    public Long GenerateNewTask(Long bound) {
        return new Random().nextLong(bound);
    }
}
