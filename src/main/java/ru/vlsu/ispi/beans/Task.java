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
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    private Date createdate;

    private float price;

    @Enumerated(value = EnumType.STRING)
    private TaskType type;

    private String description;

    @ManyToOne
    private User executor;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    public Task() {

    }

    public Task(String caption, Date createdate, float price, TaskType type, String description, User executor, TaskStatus status) {
        this.caption = caption;
        this.createdate = createdate;
        this.price = price;
        this.type = type;
        this.description = description;
        this.executor = executor;
        this.status = status;
    }
}