package ru.vlsu.ispi.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import ru.vlsu.ispi.enums.TaskStatus;
import ru.vlsu.ispi.enums.TaskType;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Entity
@Table(name="Tasks")
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "caption")
    private String caption;

    private Date createDate;

    private float price;

    @Enumerated(value = EnumType.STRING)
    private TaskType type;

    @Column(columnDefinition = "description")
    private String description;

    @ManyToOne
    private User executor;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus taskStatus;

    public Task() {

    }

    public Task(String caption, Date createDate, float price, TaskType type, String description, User executor, TaskStatus taskStatus) {
        this.caption = caption;
        this.createDate = createDate;
        this.price = price;
        this.type = type;
        this.description = description;
        this.executor = executor;
        this.taskStatus = taskStatus;
    }
}