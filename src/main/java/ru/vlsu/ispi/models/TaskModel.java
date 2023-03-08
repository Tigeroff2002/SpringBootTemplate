package ru.vlsu.ispi.models;

import ru.vlsu.ispi.enums.TaskStatus;
import ru.vlsu.ispi.enums.TaskType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TaskModel {
    @NotNull(message = "Id should be not null")
    private Long TaskId;
    public Long getTaskId() {
        return TaskId;
    }
    public void setTaskId(Long taskId) {
        TaskId = taskId;
    }

    @NotEmpty(message = "Provide a not empty task caption")
    private String Caption;
    public String getCaption() {
        return Caption;
    }
    public void setCaption(String caption) {
        Caption = caption;
    }

    private float Price;
    public float getPrice() {
        return Price;
    }
    public void setPrice(float price) {
        Price = price;
    }

    private TaskType Type;
    public TaskType getType(){
        return Type;
    }
    public void setType(TaskType type) {
        Type = type;
    }

    @NotEmpty(message = "Provide a not empty task description")
    private String Description;
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }

    private Long ExecutorId;
    public Long getExecutorId() {
        return ExecutorId;
    }
    public void setExecutorId(Long executorId){
        ExecutorId = executorId;
    }

    private ru.vlsu.ispi.enums.TaskStatus TaskStatus;
    public TaskStatus getTaskStatus(){
        return TaskStatus;
    }
    public void setTaskStatus(TaskStatus taskStatus){
        TaskStatus = taskStatus;
    }
}
