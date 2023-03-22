package ru.vlsu.ispi.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vlsu.ispi.enums.EventStatus;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "Events")
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Task task;

    @ManyToOne
    private User employer;

    @Enumerated(value = EnumType.STRING)
    private EventStatus status;

    private Date completedate;

    private float totalprice;

    public Event(Task task, User employer, EventStatus status, Date completedate, float totalprice) {
        this.task = task;
        this.employer = employer;
        this.status = status;
        this.completedate = completedate;
        this.totalprice = totalprice;
    }
}