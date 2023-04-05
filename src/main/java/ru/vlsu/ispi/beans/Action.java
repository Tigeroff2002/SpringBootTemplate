package ru.vlsu.ispi.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vlsu.ispi.enums.ActionType;

import java.util.Date;

@Data
@Entity
@Table(name = "Actions")
@AllArgsConstructor
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Task task;

    @ManyToOne
    private User user;

    private Date timemoment;

    @Enumerated(value = EnumType.STRING)
    private ActionType actiontype;

    public boolean isFormalized;

    public Action(){

    }
}
