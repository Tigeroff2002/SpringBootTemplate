package ru.vlsu.ispi.models;

import ru.vlsu.ispi.beans.User;
import ru.vlsu.ispi.enums.TaskStatus;
import ru.vlsu.ispi.enums.TaskType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TaskModel {
    @NotEmpty(message = "Provide a not empty task caption")
    private String caption;
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }

    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    private TaskType type;
    public TaskType getType(){
        return type;
    }
    public void setType(TaskType type) {
        this.type = type;
    }

    @NotEmpty(message = "Provide a not empty task description")
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
