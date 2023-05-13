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
    private Action taskaction;

    @ManyToOne
    private User Executor;

    @Enumerated(value = EnumType.STRING)
    private EventStatus eventstatus;

    private Date formalizedate;

    private Date completedate;

    private float totalprice;

    public Event(){

    }
}